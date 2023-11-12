package com.example.myzulipapp.channels.domain.repository

import com.example.myzulipapp.channels.domain.model.Stream
import com.example.myzulipapp.channels.domain.model.StreamTopic

interface StreamRepository {
    suspend fun getSubscribedStreams(isFromCache: Boolean): List<Stream>
    suspend fun getSubscribedStreamsCache(): List<Stream>
    suspend fun getStreamTopics(streamId: Int, isFromCache: Boolean): List<StreamTopic>
    suspend fun getStreamTopicsCache(streamId: Int): List<StreamTopic>
    suspend fun createStream(streamName: String, streamDescription: String?): Stream
}
