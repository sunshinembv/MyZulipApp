package com.example.myzulipapp.event_queue.domain.usecases

import com.example.myzulipapp.event_queue.domain.model.QueueEvent
import com.example.myzulipapp.event_queue.domain.repository.EventQueueRepository
import javax.inject.Inject

class GetEventFromQueueUseCase @Inject constructor(private val repository: EventQueueRepository) {

    suspend fun execute(queueId: String, lastEventId: Int): QueueEvent {
        return repository.getEventsFromQueue(queueId, lastEventId)
    }
}
