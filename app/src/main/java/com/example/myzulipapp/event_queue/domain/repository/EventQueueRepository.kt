package com.example.myzulipapp.event_queue.domain.repository

import com.example.myzulipapp.event_queue.domain.model.EventQueue
import com.example.myzulipapp.event_queue.domain.model.QueueEvent

interface EventQueueRepository {

    suspend fun registerEventQueue(eventTypes: String): EventQueue

    suspend fun getEventsFromQueue(queueId: String, lastEventId: Int): QueueEvent

    suspend fun deleteEventQueue(queueId: String)
}
