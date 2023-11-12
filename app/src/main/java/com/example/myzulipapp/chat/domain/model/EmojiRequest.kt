package com.example.myzulipapp.chat.domain.model

data class EmojiRequest(
    val messageId: Int,
    val emojiName: String,
    val emojiCode: String
)
