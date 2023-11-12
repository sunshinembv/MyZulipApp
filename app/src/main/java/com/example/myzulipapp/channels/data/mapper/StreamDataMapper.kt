package com.example.myzulipapp.channels.data.mapper

import com.example.myzulipapp.channels.data.db.entities.StreamEntity
import com.example.myzulipapp.channels.data.db.entities.StreamTopicEntity
import com.example.myzulipapp.channels.data.model.StreamData
import com.example.myzulipapp.channels.data.model.StreamTopicData
import com.example.myzulipapp.channels.domain.model.Stream
import com.example.myzulipapp.channels.domain.model.StreamTopic
import com.example.myzulipapp.chat.data.model.SingleMessageRootData
import com.example.myzulipapp.utils.toDate
import javax.inject.Inject

class StreamDataMapper @Inject constructor() {

    fun toStream(
        streamData: StreamData
    ): Stream {
        return Stream(
            streamId = streamData.streamId,
            name = streamData.name,
            description = streamData.description,
            inviteOnly = streamData.inviteOnly,
            color = streamData.color ?: DEFAULT_STREAM_COLOR,
            dateCreated = streamData.dateCreated.toDate(DATE_PATTERN)
        )
    }

    fun toStream(
        streamEntity: StreamEntity,
    ): Stream {
        return Stream(
            streamId = streamEntity.streamId,
            name = streamEntity.name,
            description = streamEntity.description,
            inviteOnly = streamEntity.inviteOnly,
            color = streamEntity.color,
            dateCreated = streamEntity.dateCreated.toDate(DATE_PATTERN),
        )
    }

    fun toStreamTopicList(
        streamId: Int,
        streamTopicDataList: List<StreamTopicData>,
        singleMessageList: List<SingleMessageRootData>
    ): List<StreamTopic> {
        return streamTopicDataList.map { streamTopicData ->
            val message = singleMessageList
                .firstOrNull { it.message.id == streamTopicData.maxId }?.message
            StreamTopic(
                streamId = streamId,
                name = streamTopicData.name,
                lastMessage = message?.content ?: "",
                lastMessageDate = message?.timestamp?.toDate(DATE_PATTERN) ?: ""
            )
        }
    }

    fun toStreamTopicList(streamTopicEntityList: List<StreamTopicEntity>): List<StreamTopic> {
        return streamTopicEntityList.map { streamTopicEntity ->
            StreamTopic(
                streamId = streamTopicEntity.streamId,
                name = streamTopicEntity.name,
                lastMessage = streamTopicEntity.lastMessage,
                lastMessageDate = streamTopicEntity.lastMessageDate
            )
        }
    }

    fun toStreamEntity(
        streams: List<Stream>
    ): List<StreamEntity> {
        return streams.map { stream ->
            StreamEntity(
                streamId = stream.streamId,
                name = stream.name,
                description = stream.description,
                inviteOnly = stream.inviteOnly,
                color = stream.color,
                dateCreated = System.currentTimeMillis()
            )
        }
    }

    fun toStreamTopicEntityList(
        streamTopics: List<StreamTopic>
    ): List<StreamTopicEntity> {
        return streamTopics.map { streamTopic ->
            StreamTopicEntity(
                streamId = streamTopic.streamId,
                name = streamTopic.name,
                lastMessage = streamTopic.lastMessage,
                lastMessageDate = streamTopic.lastMessageDate
            )
        }
    }

    companion object {
        private const val DATE_PATTERN = "dd.MM.YY"
        private const val DEFAULT_STREAM_COLOR = "#8247BB"
    }
}
