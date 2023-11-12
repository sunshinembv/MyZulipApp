package com.example.myzulipapp.chat.domain.usecases

import com.example.myzulipapp.chat.domain.model.MessageRequest
import com.example.myzulipapp.chat.domain.model.MessageResponse
import com.example.myzulipapp.chat.domain.repository.MessageRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val repository: MessageRepository) {

    suspend fun execute(messageRequest: MessageRequest): MessageResponse {
        return repository.sendMessage(messageRequest)
    }
}
