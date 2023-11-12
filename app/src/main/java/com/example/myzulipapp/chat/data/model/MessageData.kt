package com.example.myzulipapp.chat.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageData(
    val id: Int,
    @Json(name = "avatar_url")
    val avatarUrl: String,
    @Json(name = "sender_full_name")
    val senderFullName: String,
    val content: String,
    val reactions: List<ReactionData>,
    @Json(name = "sender_id")
    val senderId: Int,
    @Json(name = "recipient_id")
    val recipientId: Int,
    val timestamp: Long,
    val subject: String,
    @Json(name = "stream_id")
    val streamId: Int,
)
