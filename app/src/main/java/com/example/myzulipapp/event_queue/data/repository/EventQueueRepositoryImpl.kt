package com.example.myzulipapp.event_queue.data.repository

import com.example.myzulipapp.event_queue.data.mapper.EventMapper
import com.example.myzulipapp.event_queue.data.mapper.EventQueueMapper
import com.example.myzulipapp.event_queue.data.model.EventData
import com.example.myzulipapp.event_queue.domain.model.EventQueue
import com.example.myzulipapp.event_queue.domain.model.QueueEvent
import com.example.myzulipapp.event_queue.domain.repository.EventQueueRepository
import com.example.myzulipapp.network.NetworkHandleService
import com.example.myzulipapp.network.ZulipApi
import javax.inject.Inject

class EventQueueRepositoryImpl @Inject constructor(
    private val zulipApi: ZulipApi,
    private val networkHandleService: NetworkHandleService,
    private val eventQueueMapper: EventQueueMapper,
    private val eventMapper: EventMapper,
) : EventQueueRepository {

    private val emptyEvent = EventData(
        id = -2,
        type = "empty",
        messageId = -1,
        message = null
    )

    override suspend fun registerEventQueue(eventTypes: String): EventQueue {
        return eventQueueMapper.toEventQueue(networkHandleService.apiCall {
            zulipApi.registerEventQueue(
                eventTypes
            )
        })
    }

    override suspend fun getEventsFromQueue(queueId: String, lastEventId: Int): QueueEvent {
        val eventRoot =
            networkHandleService.apiCall { zulipApi.getEventsFromQueue(queueId, lastEventId) }
        val event = eventRoot.events.firstOrNull() ?: emptyEvent
        return eventMapper.toEvent(event, queueId)
    }

    override suspend fun deleteEventQueue(queueId: String) {
        networkHandleService.apiCall { zulipApi.deleteEventQueue(queueId) }
    }
}
