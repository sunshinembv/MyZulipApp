package com.example.myzulipapp.channels.presentation.topics.preview_data

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.myzulipapp.channels.presentation.topics.state.StreamTopicsState
import com.example.myzulipapp.channels.presentation.topics.ui_model.StreamTopicUiModel
import com.example.myzulipapp.utils.ImmutableList

private val streamTopics = listOf(
    StreamTopicUiModel(
        0,
        "TopicName",
        "LastMessage",
        "10.10.2020"
    ),
    StreamTopicUiModel(
        1,
        "TopicName1",
        "LastMessage1",
        "10.10.2020"
    ),
    StreamTopicUiModel(
        2,
        "TopicName2",
        "LastMessage2",
        "10.10.2020"
    ),
)

private val streamTopicsState = StreamTopicsState(
    streamTopics = ImmutableList(streamTopics)
)

class StreamTopicsStatePreviewParameterProvider : PreviewParameterProvider<StreamTopicsState> {

    override val values: Sequence<StreamTopicsState>
        get() = sequenceOf(streamTopicsState)
}
