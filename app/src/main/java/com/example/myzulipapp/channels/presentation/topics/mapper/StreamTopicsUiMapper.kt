package com.example.myzulipapp.channels.presentation.topics.mapper

import com.example.myzulipapp.channels.domain.model.StreamTopic
import com.example.myzulipapp.channels.presentation.topics.ui_model.StreamTopicUiModel
import javax.inject.Inject

class StreamTopicsUiMapper @Inject constructor() {

    fun toStreamTopicsUiModel(
        streamId: Int,
        streamTopics: List<StreamTopic>
    ): List<StreamTopicUiModel> {
        return streamTopics.map { topic ->
            StreamTopicUiModel(
                streamId,
                topic.name,
                topic.lastMessage,
                topic.lastMessageDate
            )
        }
    }
}
