package com.example.myzulipapp.channels.domain.model

data class StreamTopic(
    val streamId: Int,
    val name: String,
    val lastMessage: String,
    val lastMessageDate: String,
)
