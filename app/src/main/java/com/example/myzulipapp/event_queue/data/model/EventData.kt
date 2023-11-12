package com.example.myzulipapp.event_queue.data.model

import com.example.myzulipapp.chat.data.model.MessageData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventData(
    val id: Int,
    val type: String,
    @Json(name = "message_id")
    val messageId: Int?,
    val message: MessageData?
)
