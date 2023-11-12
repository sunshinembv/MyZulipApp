package com.example.myzulipapp.channels.data.repository

import com.example.myzulipapp.channels.data.db.dao.StreamDao
import com.example.myzulipapp.channels.data.db.dao.StreamTopicDao
import com.example.myzulipapp.channels.data.mapper.StreamDataMapper
import com.example.myzulipapp.channels.domain.model.Stream
import com.example.myzulipapp.channels.domain.model.StreamTopic
import com.example.myzulipapp.channels.domain.repository.StreamRepository
import com.example.myzulipapp.network.NetworkHandleService
import com.example.myzulipapp.network.ZulipApi
import javax.inject.Inject

class StreamRepositoryImpl @Inject constructor(
    private val zulipApi: ZulipApi,
    private val streamDao: StreamDao,
    private val streamTopicDao: StreamTopicDao,
    private val networkHandleService: NetworkHandleService,
    private val mapper: StreamDataMapper
) : StreamRepository {

    override suspend fun getSubscribedStreams(
        isFromCache: Boolean
    ): List<Stream> {
        return if (isFromCache) {
            getSubscribedStreamsCache()
        } else {
            val subscribedStreamsData =
                networkHandleService.apiCall { zulipApi.getSubscribedStreams() }
            val subscribedStreams = subscribedStreamsData.subscriptions
                .map { streamData ->
                    mapper.toStream(streamData)
                }
            streamDao.removeSubscribedStreams()
            streamDao.insertStreams(mapper.toStreamEntity(subscribedStreams))
            subscribedStreams
        }
    }

    override suspend fun getSubscribedStreamsCache(): List<Stream> {
        val subscribedStreams = streamDao.getSubscribedStreams()
        return subscribedStreams.map { streamEntity ->
            mapper.toStream(streamEntity)
        }
    }

    override suspend fun getStreamTopics(
        streamId: Int,
        isFromCache: Boolean
    ): List<StreamTopic> {
        return if (isFromCache) {
            getStreamTopicsCache(streamId)
        } else {
            val streamTopicsRoot =
                networkHandleService.apiCall { zulipApi.getStreamTopicsById(streamId) }

            val singleMessageRootList = streamTopicsRoot.topics.map {
                networkHandleService.apiCall { zulipApi.getSingleMessageById(it.maxId, false) }
            }
            val streamTopics =
                mapper.toStreamTopicList(streamId, streamTopicsRoot.topics, singleMessageRootList)
            streamTopicDao.removeStreamTopics(streamId)
            streamTopicDao.insertStreamTopics(mapper.toStreamTopicEntityList(streamTopics))
            streamTopics
        }
    }

    override suspend fun getStreamTopicsCache(streamId: Int): List<StreamTopic> {
        val streamTopics = streamTopicDao.getStreamTopics(streamId)
        return mapper.toStreamTopicList(streamTopics)
    }

    override suspend fun createStream(streamName: String, streamDescription: String?): Stream {
        networkHandleService.apiCall {
            zulipApi.createStream(
                createSubscriptions(
                    streamName,
                    streamDescription
                )
            )
        }
        val streamId =
            networkHandleService.apiCall { zulipApi.getStreamIdByName(streamName) }.streamId
        return mapper.toStream(
            networkHandleService.apiCall { zulipApi.getStreamById(streamId) }.stream
        )
    }

    private fun createSubscriptions(streamName: String, streamDescription: String?): String {
        return if (streamDescription == null) {
            "[{\"name\": \"$streamName\"}]"
        } else {
            "[{\"name\": \"$streamName\", \"description\": \"$streamDescription\"}]"
        }
    }
}
