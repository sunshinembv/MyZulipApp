package com.example.myzulipapp.channels.presentation.channels.preview_data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.myzulipapp.channels.presentation.channels.state.ChannelsState
import com.example.myzulipapp.channels.presentation.channels.ui_model.StreamUiModel
import com.example.myzulipapp.utils.ImmutableList

private val subscribedStreams = listOf(
    StreamUiModel(
        id = 1,
        title = "Title1",
        description = "Description1",
        inviteOnly = false,
        color = Color.DarkGray,
        dateCreated = "10.10.2020"
    ),
    StreamUiModel(
        id = 2,
        title = "Title2",
        description = "Description2",
        inviteOnly = false,
        color = Color.DarkGray,
        dateCreated = "10.10.2020"
    ),
    StreamUiModel(
        id = 3,
        title = "Title3",
        description = "Description3",
        inviteOnly = false,
        color = Color.DarkGray,
        dateCreated = "10.10.2020"
    ),
)

private val channelsState = ChannelsState(
    subscribedStreams = ImmutableList(subscribedStreams)
)

class ChannelsStatePreviewParameterProvider : PreviewParameterProvider<ChannelsState> {

    override val values: Sequence<ChannelsState>
        get() = sequenceOf(channelsState)
}
