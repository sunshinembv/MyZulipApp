package com.example.myzulipapp.channels.presentation.channels.mapper

import com.example.myzulipapp.channels.domain.model.Stream
import com.example.myzulipapp.channels.presentation.channels.ui_model.StreamUiModel
import com.example.myzulipapp.utils.toColor
import javax.inject.Inject

class StreamUiMapper @Inject constructor() {

    fun toStreamsUiModel(streams: List<Stream>): List<StreamUiModel> {
        return streams.map { stream ->
            toStreamUiModel(stream)
        }
    }

    fun toStreamUiModel(stream: Stream): StreamUiModel {
        return StreamUiModel(
            stream.streamId,
            stream.name,
            stream.description,
            stream.inviteOnly,
            stream.color.toColor(),
            stream.dateCreated
        )
    }
}
