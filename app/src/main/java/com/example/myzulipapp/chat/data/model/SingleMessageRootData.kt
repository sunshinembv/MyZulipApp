package com.example.myzulipapp.chat.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SingleMessageRootData(val message: MessageData)
