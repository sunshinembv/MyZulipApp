package com.example.myzulipapp.chat.data.mapper

import android.util.Log
import com.example.myzulipapp.chat.data.db.entities.MessageEntity
import com.example.myzulipapp.chat.data.db.entities.ReactionEntity
import com.example.myzulipapp.chat.data.model.MessageData
import com.example.myzulipapp.chat.data.model.MessageRequestData
import com.example.myzulipapp.chat.data.model.MessageResponseData
import com.example.myzulipapp.chat.data.model.ReactionData
import com.example.myzulipapp.chat.domain.model.Message
import com.example.myzulipapp.chat.domain.model.MessageRequest
import com.example.myzulipapp.chat.domain.model.MessageResponse
import com.example.myzulipapp.chat.domain.model.Reaction
import com.example.myzulipapp.chat.presentation.ui_model.emojiSet
import com.example.myzulipapp.core.OwnUser
import javax.inject.Inject

class MessagesMapper @Inject constructor() {

    fun toMessages(messages: List<MessageData>): List<Message> {
        return messages.map { messageData ->
            toMessage(messageData)
        }
    }

    fun toMessage(messageData: MessageData): Message {
        return Message(
            id = messageData.id,
            streamId = messageData.streamId,
            topicName = messageData.subject,
            avatarUrl = messageData.avatarUrl,
            senderFullName = messageData.senderFullName,
            content = convertEmojiInString(messageData.content),
            reactions = toReactions(
                messageData.reactions,
                messageData.id,
                messageData.streamId,
                messageData.subject
            ),
            senderId = messageData.senderId,
            recipientId = messageData.recipientId,
            timestamp = messageData.timestamp
        )
    }

    fun toMessage(messageEntity: MessageEntity, reactions: List<Reaction>): Message {
        return Message(
            id = messageEntity.messageId,
            streamId = messageEntity.streamId,
            topicName = messageEntity.topicName,
            avatarUrl = messageEntity.avatarUrl,
            senderFullName = messageEntity.senderFullName,
            content = convertEmojiInString(messageEntity.content),
            reactions = reactions,
            senderId = messageEntity.senderId,
            recipientId = messageEntity.recipientId,
            timestamp = messageEntity.timestamp,
        )
    }

    fun toMessageEntities(messages: List<Message>): List<MessageEntity> {
        return messages.map { message ->
            MessageEntity(
                messageId = message.id,
                streamId = message.streamId,
                topicName = message.topicName,
                avatarUrl = message.avatarUrl,
                senderFullName = message.senderFullName,
                content = message.content,
                senderId = message.senderId,
                recipientId = message.recipientId,
                timestamp = message.timestamp
            )
        }
    }

    fun toMessageEntity(message: Message): MessageEntity {
        return MessageEntity(
            messageId = message.id,
            streamId = message.streamId,
            topicName = message.topicName,
            avatarUrl = message.avatarUrl,
            senderFullName = message.senderFullName,
            content = message.content,
            senderId = message.senderId,
            recipientId = message.recipientId,
            timestamp = message.timestamp
        )
    }

    fun toReactionEntity(reactions: List<Reaction>): List<ReactionEntity> {
        return reactions.map { reaction ->
            ReactionEntity(
                messageId = reaction.messageId,
                streamId = reaction.streamId,
                topicName = reaction.topicName,
                emojiName = reaction.emojiName,
                emojiCode = reaction.emojiCode,
                reactionType = reaction.reactionType,
                userId = reaction.userId,
                count = reaction.count
            )
        }
    }

    fun toMessageRequestData(messageRequest: MessageRequest): MessageRequestData {
        return MessageRequestData(
            type = messageRequest.type,
            to = messageRequest.to,
            subject = messageRequest.subject,
            content = messageRequest.content
        )
    }

    fun toMessageResponse(messageResponseData: MessageResponseData): MessageResponse {
        return MessageResponse(
            id = messageResponseData.id,
            msg = messageResponseData.msg,
            result = messageResponseData.result,
        )
    }

    private fun toReactions(
        reactions: List<ReactionData>,
        messageId: Int,
        streamId: Int,
        topicName: String
    ): List<Reaction> {
        val emojiCodeToUserId = mutableMapOf<String, Int>()
        reactions.forEach {
            if (it.userId == (OwnUser.ownUser?.id ?: -1)) emojiCodeToUserId[it.emojiCode] =
                it.userId
        }
        val countMap = reactions.groupingBy { reaction -> reaction.emojiCode }.eachCount()
            .filter { it.value > 1 }
        return reactions.map { reaction ->
            Reaction(
                messageId = messageId,
                streamId = streamId,
                topicName = topicName,
                emojiName = reaction.emojiName,
                emojiCode = reaction.emojiCode,
                reactionType = reaction.reactionType,
                userId = emojiCodeToUserId[reaction.emojiCode] ?: reaction.userId,
                count = countMap[reaction.emojiCode] ?: 1,
            )
        }.distinctBy { it.emojiCode }
    }

    fun toReactions(reactions: List<ReactionEntity>): List<Reaction> {
        return reactions.map { reactionEntity ->
            Reaction(
                messageId = reactionEntity.messageId,
                streamId = reactionEntity.streamId,
                topicName = reactionEntity.topicName,
                emojiName = reactionEntity.emojiName,
                emojiCode = reactionEntity.emojiCode,
                reactionType = reactionEntity.reactionType,
                userId = reactionEntity.userId,
                count = reactionEntity.count,
            )
        }
    }

    private fun convertEmojiInString(str: String): String {
        return try {
            val sb = StringBuffer()
            val splitStr = str.split(":")
            splitStr.forEach { split ->
                val emoji = emojiSet.find { it.name.contains(split) }
                if (emoji != null && split.isNotEmpty()) {
                    sb.deleteCharAt(sb.length - 1)
                    sb.append(String(Character.toChars(emoji.code.toInt(16))))
                } else {
                    sb.append("$split:")
                }
            }
            sb.deleteCharAt(sb.length - 1)
            return sb.toString()
        } catch (e: Exception) {
            Log.d(TAG, e.message ?: "")
            str
        }
    }

    companion object {
        private const val TAG = "com.example.myzulipapp.chat.data.mapper.tag"
    }
}
