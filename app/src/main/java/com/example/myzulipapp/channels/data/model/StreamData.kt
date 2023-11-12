package com.example.myzulipapp.channels.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StreamData(
    @Json(name = "stream_id")
    val streamId: Int,
    val name: String,
    val description: String,
    @Json(name = "invite_only")
    val inviteOnly: Boolean,
    val color: String?,
    @Json(name = "date_created")
    val dateCreated: Long,
)
