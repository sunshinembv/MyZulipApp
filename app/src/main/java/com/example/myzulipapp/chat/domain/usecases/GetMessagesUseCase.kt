package com.example.myzulipapp.chat.domain.usecases

import com.example.myzulipapp.chat.domain.model.Message
import com.example.myzulipapp.chat.domain.repository.MessageRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(private val repository: MessageRepository) {

    suspend fun execute(
        anchor: Long,
        streamId: Int,
        topicName: String,
        numBefore: Int,
        numAfter: Int,
        applyMarkdown: Boolean,
        isFromCache: Boolean
    ): List<Message> {
        return repository.getMessages(
            anchor,
            streamId,
            topicName,
            numBefore,
            numAfter,
            applyMarkdown,
            isFromCache
        )
    }
}
