package com.example.myzulipapp.chat.data.mapper

import com.example.myzulipapp.chat.data.model.EmojiRequestData
import com.example.myzulipapp.chat.domain.model.EmojiRequest
import javax.inject.Inject

class EmojiMapper @Inject constructor() {

    fun toEmojiRequestData(emojiRequest: EmojiRequest): EmojiRequestData {
        return EmojiRequestData(
            emojiRequest.messageId,
            emojiRequest.emojiName,
            emojiRequest.emojiCode,
        )
    }
}
