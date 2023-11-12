package com.example.myzulipapp.event_queue.domain.model

import com.example.myzulipapp.chat.domain.model.Message

data class QueueEvent(
    val id: Int,
    val type: String,
    val messageId: Int,
    val message: Message?,
    val queueId: String
)
