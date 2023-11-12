package com.example.myzulipapp.chat.domain.model

data class MessageRequest(
    val type: String,
    val to: Int,
    val subject: String,
    val content: String
)
