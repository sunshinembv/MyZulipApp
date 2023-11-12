package com.example.myzulipapp.chat.domain.model

data class Message(
    val id: Int,
    val streamId: Int,
    val topicName: String,
    val avatarUrl: String,
    val senderFullName: String,
    val content: String,
    val reactions: List<Reaction>,
    val senderId: Int,
    val recipientId: Int,
    val timestamp: Long
)
