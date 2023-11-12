package com.example.myzulipapp.event_queue.data.mapper

import com.example.myzulipapp.chat.data.mapper.MessagesMapper
import com.example.myzulipapp.event_queue.data.model.EventData
import com.example.myzulipapp.event_queue.domain.model.QueueEvent
import javax.inject.Inject

class EventMapper @Inject constructor(
    private val messageMapper: MessagesMapper
) {

    fun toEvent(event: EventData, queueId: String): QueueEvent {
        return QueueEvent(
            event.id,
            event.type,
            event.messageId ?: -1,
            event.message?.let { messageMapper.toMessage(it) },
            queueId
        )
    }
}
