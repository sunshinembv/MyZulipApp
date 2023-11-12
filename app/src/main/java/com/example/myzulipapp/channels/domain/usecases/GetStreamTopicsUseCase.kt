package com.example.myzulipapp.channels.domain.usecases

import com.example.myzulipapp.channels.domain.model.StreamTopic
import com.example.myzulipapp.channels.domain.repository.StreamRepository
import javax.inject.Inject

class GetStreamTopicsUseCase @Inject constructor(private val repository: StreamRepository) {

    suspend fun execute(streamId: Int, isFromCache: Boolean): List<StreamTopic> {
        return repository.getStreamTopics(streamId, isFromCache)
    }
}
