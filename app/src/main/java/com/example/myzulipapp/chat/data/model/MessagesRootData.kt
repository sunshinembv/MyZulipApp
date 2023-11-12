package com.example.myzulipapp.chat.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessagesRootData(val messages: List<MessageData>)
