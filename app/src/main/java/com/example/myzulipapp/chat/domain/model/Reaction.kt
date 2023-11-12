package com.example.myzulipapp.chat.domain.model

data class Reaction(
    val messageId: Int,
    val streamId: Int,
    val topicName: String,
    val emojiName: String,
    val emojiCode: String,
    val reactionType: String,
    val userId: Int,
    val count: Int
)
