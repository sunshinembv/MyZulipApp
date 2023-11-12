package com.example.myzulipapp.event_queue.domain.usecases

import com.example.myzulipapp.event_queue.domain.repository.EventQueueRepository
import javax.inject.Inject

class DeleteEventQueueUseCase @Inject constructor(private val repository: EventQueueRepository) {

    suspend fun execute(queueId: String) {
        return repository.deleteEventQueue(queueId)
    }
}
