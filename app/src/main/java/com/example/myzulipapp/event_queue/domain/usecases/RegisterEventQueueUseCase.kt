package com.example.myzulipapp.event_queue.domain.usecases

import com.example.myzulipapp.event_queue.domain.model.EventQueue
import com.example.myzulipapp.event_queue.domain.repository.EventQueueRepository
import javax.inject.Inject

class RegisterEventQueueUseCase @Inject constructor(private val repository: EventQueueRepository) {

    suspend fun execute(eventTypes: String): EventQueue {
        return repository.registerEventQueue(eventTypes)
    }
}
