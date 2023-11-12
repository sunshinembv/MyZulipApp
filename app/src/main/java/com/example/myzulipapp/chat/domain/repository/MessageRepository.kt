package com.example.myzulipapp.chat.domain.repository

import android.net.Uri
import com.example.myzulipapp.chat.domain.model.EmojiRequest
import com.example.myzulipapp.chat.domain.model.Message
import com.example.myzulipapp.chat.domain.model.MessageRequest
import com.example.myzulipapp.chat.domain.model.MessageResponse

interface MessageRepository {
    suspend fun getMessages(
        anchor: Long,
        streamId: Int,
        topicName: String,
        numBefore: Int,
        numAfter: Int,
        applyMarkdown: Boolean,
        isFromCache: Boolean
    ): List<Message>

    suspend fun getMessagesCache(streamId: Int, topicName: String): List<Message>

    suspend fun getSingleMessageById(
        messageId: Int,
        applyMarkdown: Boolean
    ): Message

    suspend fun sendMessage(messageRequest: MessageRequest): MessageResponse

    suspend fun addReactions(emojiRequest: EmojiRequest)
    suspend fun removeReactions(emojiRequest: EmojiRequest)
    suspend fun tapReactions(emojiRequest: EmojiRequest, isReactionExists: Boolean)
    suspend fun uploadImage(fileUri: Uri): String
}
