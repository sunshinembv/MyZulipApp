package com.example.myzulipapp.event_queue.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventRootData(val events: List<EventData>)
