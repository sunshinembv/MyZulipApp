package com.example.myzulipapp.chat.data.repository

import android.net.Uri
import android.provider.MediaStore
import com.example.myzulipapp.chat.data.db.MessageWithReactions
import com.example.myzulipapp.chat.data.db.dao.MessageDao
import com.example.myzulipapp.chat.data.db.dao.ReactionDao
import com.example.myzulipapp.chat.data.mapper.EmojiMapper
import com.example.myzulipapp.chat.data.mapper.MessagesMapper
import com.example.myzulipapp.chat.domain.model.EmojiRequest
import com.example.myzulipapp.chat.domain.model.Message
import com.example.myzulipapp.chat.domain.model.MessageRequest
import com.example.myzulipapp.chat.domain.model.MessageResponse
import com.example.myzulipapp.chat.domain.repository.MessageRepository
import com.example.myzulipapp.network.NetworkHandleService
import com.example.myzulipapp.network.ZulipApi
import com.example.myzulipapp.utils.ResourceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val zulipApi: ZulipApi,
    private val messageDao: MessageDao,
    private val reactionDao: ReactionDao,
    private val networkHandleService: NetworkHandleService,
    private val messagesMapper: MessagesMapper,
    private val emojiMapper: EmojiMapper,
    private val resourceProvider: ResourceProvider,
) : MessageRepository {

    override suspend fun getMessages(
        anchor: Long,
        streamId: Int,
        topicName: String,
        numBefore: Int,
        numAfter: Int,
        applyMarkdown: Boolean,
        isFromCache: Boolean
    ): List<Message> {
        return if (isFromCache) {
            val messageCache = getMessagesCache(streamId, topicName)
            reactionDao.removeReactions(streamId, topicName)
            messageDao.removeMessages(streamId, topicName)
            messageCache.reversed()
        } else {
            val narrow = createNarrow(streamId, topicName)
            val messageRootData = networkHandleService.apiCall {
                zulipApi.getMessages(
                    anchor,
                    numBefore,
                    numAfter,
                    narrow,
                    applyMarkdown
                )
            }
            val messages = messagesMapper.toMessages(messageRootData.messages)
            messageDao.insertMessages(messagesMapper.toMessageEntities(messages.reversed()))
            messageDao.removeExtraMessages(streamId, topicName)
            val messagesEntity = messageDao.getMessages(streamId, topicName)
            messages.map { message ->
                if (messagesEntity.find { it.message.messageId == message.id } != null) {
                    reactionDao.insertReactions(messagesMapper.toReactionEntity(message.reactions))
                }
            }
            messages
        }
    }

    override suspend fun getMessagesCache(streamId: Int, topicName: String): List<Message> {
        val messages = messageDao.getMessages(streamId, topicName)
        return getMessageCache(messages)
    }

    override suspend fun getSingleMessageById(
        messageId: Int,
        applyMarkdown: Boolean
    ): Message {
        val messageDataRoot = networkHandleService.apiCall {
            zulipApi.getSingleMessageById(messageId, applyMarkdown)
        }
        val message = messagesMapper.toMessage(messageDataRoot.message)
        val messagesEntity = messagesMapper.toMessageEntity(message)
        val reactionEntities = messagesMapper.toReactionEntity(message.reactions)

        messageDao.insertMessage(messagesEntity)
        if (reactionEntities.isEmpty()) {
            reactionDao.removeReactions(message.streamId, message.topicName)
        } else {
            reactionDao.insertReactions(reactionEntities)
        }

        return messagesMapper.toMessage(messageDataRoot.message)
    }

    override suspend fun sendMessage(messageRequest: MessageRequest): MessageResponse {
        val request = messagesMapper.toMessageRequestData(messageRequest)
        val subject = request.subject.ifBlank { DEFAULT_SUBJECT }
        val response = networkHandleService.apiCall {
            zulipApi.sendMessage(
                request.type,
                request.to,
                subject,
                request.content
            )
        }
        return messagesMapper.toMessageResponse(response)
    }

    override suspend fun addReactions(emojiRequest: EmojiRequest) {
        val request = emojiMapper.toEmojiRequestData(emojiRequest)
        networkHandleService.apiCall {
            zulipApi.addReactionByMessageId(
                request.messageId,
                request.emojiName,
                request.emojiCode,
            )
        }
    }

    override suspend fun removeReactions(emojiRequest: EmojiRequest) {
        val request = emojiMapper.toEmojiRequestData(emojiRequest)
        networkHandleService.apiCall {
            zulipApi.removeReactionByMessageId(
                request.messageId,
                request.emojiName,
                request.emojiCode,
            )
        }
    }

    override suspend fun tapReactions(emojiRequest: EmojiRequest, isReactionExists: Boolean) {
        if (isReactionExists) {
            removeReactions(emojiRequest)
        } else {
            addReactions(emojiRequest)
        }
    }

    override suspend fun uploadImage(fileUri: Uri): String {
        val folder = resourceProvider.getExternalFilesDir(ZULIP_IMAGE_FOLDER)
        val fileName = getFileName(fileUri)
        val originalFile = File(folder, fileName)
        val mediaType = resourceProvider.getContentResolver().getType(fileUri)

        originalFile.outputStream().use { fileOutputStream ->
            resourceProvider.getContentResolver().openInputStream(fileUri)?.use { inputStream ->
                inputStream.copyTo(fileOutputStream)
            }
        }

        val imagePart = originalFile.asRequestBody(mediaType?.toMediaType())
        val file =
            MultipartBody.Part.createFormData(ZULIP_MULTIPART_NAME, originalFile.name, imagePart)
        val imageUploadedResponse = networkHandleService.apiCall { zulipApi.uploadImage(file) }
        return imageUploadedResponse.uri
    }

    private fun getMessageCache(messagesWithReactions: List<MessageWithReactions>): List<Message> {
        return messagesWithReactions.map { messageWithReactions ->
            val reactions = messagesMapper.toReactions(messageWithReactions.reactions)
            messagesMapper.toMessage(messageWithReactions.message, reactions)
        }
    }

    private fun createNarrow(streamId: Int, topicName: String): String {
        return if (topicName.isNotBlank()) {
            val topic = parseTopicName(topicName)
            "[{\"operator\": \"stream\", \"operand\": $streamId}, {\"operator\": \"topic\", \"operand\": \"$topic\"}]"
        } else {
            "[{\"operator\": \"stream\", \"operand\": $streamId}]"
        }
    }

    private fun parseTopicName(topicName: String): String {
        val sb = StringBuffer()
        topicName.map {
            if (it == '"' || it == '\\') sb.append("\\")
            sb.append(it)
        }
        return sb.toString()
    }

    private suspend fun getFileName(fileUri: Uri): String {
        var fileName = ""
        withContext(Dispatchers.IO) {
            resourceProvider.getContentResolver().query(
                fileUri,
                null,
                null,
                null,
                null
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    fileName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
                }
            }
        }
        return fileName
    }

    companion object {
        private const val ZULIP_IMAGE_FOLDER = "zulipImageFolder"
        private const val ZULIP_MULTIPART_NAME = "filename"
        private const val DEFAULT_SUBJECT = "(без темы)"
    }
}
