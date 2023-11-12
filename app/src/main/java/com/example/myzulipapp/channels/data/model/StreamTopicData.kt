package com.example.myzulipapp.channels.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StreamTopicData(
    @Json(name = "max_id")
    val maxId: Int,
    val name: String
)
