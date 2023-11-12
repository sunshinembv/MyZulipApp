package com.example.myzulipapp.chat.domain.usecases

import com.example.myzulipapp.chat.domain.model.Message
import com.example.myzulipapp.chat.domain.repository.MessageRepository
import javax.inject.Inject

class GetSingleMessageByIdUseCase @Inject constructor(private val repository: MessageRepository) {

    suspend fun execute(
        messageId: Int,
        applyMarkdown: Boolean
    ): Message {
        return repository.getSingleMessageById(messageId, applyMarkdown)
    }
}
