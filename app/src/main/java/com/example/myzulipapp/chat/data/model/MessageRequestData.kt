package com.example.myzulipapp.chat.data.model

data class MessageRequestData(
    val type: String,
    val to: Int,
    val subject: String,
    val content: String
)
