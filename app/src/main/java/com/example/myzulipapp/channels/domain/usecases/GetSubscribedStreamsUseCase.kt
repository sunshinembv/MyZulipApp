package com.example.myzulipapp.channels.domain.usecases

import com.example.myzulipapp.channels.domain.model.Stream
import com.example.myzulipapp.channels.domain.repository.StreamRepository
import javax.inject.Inject

class GetSubscribedStreamsUseCase @Inject constructor(private val repository: StreamRepository) {

    suspend fun execute(isFromCache: Boolean): List<Stream> {
        return repository.getSubscribedStreams(isFromCache)
    }
}
