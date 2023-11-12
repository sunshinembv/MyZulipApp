package com.example.myzulipapp.chat.domain.usecases

import com.example.myzulipapp.chat.domain.model.EmojiRequest
import com.example.myzulipapp.chat.domain.repository.MessageRepository
import javax.inject.Inject

class TapReactionsUseCase @Inject constructor(private val repository: MessageRepository) {

    suspend fun execute(emojiRequest: EmojiRequest, isReactionExists: Boolean) {
        repository.tapReactions(emojiRequest, isReactionExists)
    }
}
