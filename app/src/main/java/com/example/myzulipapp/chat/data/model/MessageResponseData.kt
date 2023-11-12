package com.example.myzulipapp.chat.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageResponseData(
    val id: Int,
    val msg: String,
    val result: String
)
