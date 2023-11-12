package com.example.myzulipapp.channels.presentation.topics.ui_model

data class StreamTopicUiModel(
    val streamId: Int,
    val topicName: String,
    val lastMessage: String,
    val lastMessageDate: String
)
