package com.example.myzulipapp.channels.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StreamIdData(
    @Json(name = "stream_id")
    val streamId: Int
)
