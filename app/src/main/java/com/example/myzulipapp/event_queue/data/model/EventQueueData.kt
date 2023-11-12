package com.example.myzulipapp.event_queue.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventQueueData(
    @Json(name = "queue_id")
    val queueId: String,
    @Json(name = "last_event_id")
    val lastEventId: Int
)
