package com.example.myzulipapp.chat.presentation.ui_model

import com.example.myzulipapp.chat.domain.model.Reaction

data class MessageUiModel(
    val id: Int,
    val streamId: Int,
    val senderId: Int,
    val recipientId: Int,
    val userName: String,
    val userIcon: String,
    val message: String,
    val reactions: List<Reaction>,
    val day: String,
    val time: String,
    val topicName: String,
    val isOwnMessage: Boolean,
    val isShowDate: Boolean
)
