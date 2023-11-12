package com.example.myzulipapp.event_queue.data.mapper

import com.example.myzulipapp.event_queue.data.model.EventQueueData
import com.example.myzulipapp.event_queue.domain.model.EventQueue
import javax.inject.Inject

class EventQueueMapper @Inject constructor() {

    fun toEventQueue(eventQueue: EventQueueData): EventQueue {
        return EventQueue(
            eventQueue.queueId,
            eventQueue.lastEventId
        )
    }
}
