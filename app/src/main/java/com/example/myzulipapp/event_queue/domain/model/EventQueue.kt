package com.example.myzulipapp.event_queue.domain.model

data class EventQueue(
    val queueId: String,
    val lastEventId: Int
)
