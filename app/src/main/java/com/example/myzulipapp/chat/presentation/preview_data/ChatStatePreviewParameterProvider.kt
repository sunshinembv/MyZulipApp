package com.example.myzulipapp.chat.presentation.preview_data

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.myzulipapp.chat.domain.model.Reaction
import com.example.myzulipapp.chat.presentation.state.ChatState
import com.example.myzulipapp.chat.presentation.ui_model.ChatUiModel
import com.example.myzulipapp.chat.presentation.ui_model.MessageUiModel
import com.example.myzulipapp.utils.ImmutableList

private val messages = listOf(
    MessageUiModel(
        id = 0,
        streamId = 0,
        senderId = 0,
        recipientId = 0,
        userName = "UserName1",
        userIcon = "UserIcon1",
        message = "Message1",
        reactions = emptyList<Reaction>(),
        day = "8 Ноября",
        time = "10.10.2020",
        topicName = "TopicName1",
        isOwnMessage = true,
        isShowDate = true
    ),
    MessageUiModel(
        id = 1,
        streamId = 1,
        senderId = 1,
        recipientId = 2,
        userName = "UserName2",
        userIcon = "UserIcon2",
        message = "Message2",
        reactions = emptyList<Reaction>(),
        day = "8 Ноября",
        time = "10.10.2020",
        topicName = "TopicName2",
        isOwnMessage = false,
        isShowDate = false
    ),
    MessageUiModel(
        id = 2,
        streamId = 2,
        senderId = 2,
        recipientId = 1,
        userName = "UserName3",
        userIcon = "UserIcon3",
        message = "Message3",
        reactions = emptyList<Reaction>(),
        day = "8 Ноября",
        time = "10.10.2020",
        topicName = "TopicName3",
        isOwnMessage = true,
        isShowDate = false
    )
)

private val chatUIModel = ChatUiModel(
    topicName = "TopicName",
    topMessageId = 0,
    messages = ImmutableList(messages),
)

private val chatState = ChatState(
    data = chatUIModel
)

class ChatStatePreviewParameterProvider : PreviewParameterProvider<ChatState> {
    override val values: Sequence<ChatState>
        get() = sequenceOf(chatState)
}
