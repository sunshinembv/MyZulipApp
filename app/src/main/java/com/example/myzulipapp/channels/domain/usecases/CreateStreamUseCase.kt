package com.example.myzulipapp.channels.domain.usecases

import com.example.myzulipapp.channels.domain.model.Stream
import com.example.myzulipapp.channels.domain.repository.StreamRepository
import javax.inject.Inject

class CreateStreamUseCase @Inject constructor(private val repository: StreamRepository) {

    suspend fun execute(streamName: String, streamDescription: String?): Stream {
        return repository.createStream(streamName, streamDescription)
    }
}
