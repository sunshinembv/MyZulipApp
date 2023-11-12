package com.example.myzulipapp.chat.presentation.ui_model

import com.example.myzulipapp.utils.ImmutableList

data class ChatUiModel(
    val topicName: String,
    val topMessageId: Int,
    val messages: ImmutableList<MessageUiModel>
)
