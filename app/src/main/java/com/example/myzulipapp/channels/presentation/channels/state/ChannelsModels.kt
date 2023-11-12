package com.example.myzulipapp.channels.presentation.channels.state

import com.example.myzulipapp.channels.presentation.channels.ui_model.StreamUiModel
import com.example.myzulipapp.core.Command
import com.example.myzulipapp.core.Event
import com.example.myzulipapp.core.State
import com.example.myzulipapp.utils.ImmutableList

data class ChannelsState(
    val subscribedStreams: ImmutableList<StreamUiModel>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmptyState: Boolean = true,
    val isSearchOpen: Boolean = false,
    val querySearch: String = "",
    val createStreamName: String = "",
    val createStreamDescription: String = ""
) : State

sealed class ChannelsEvents : Event {
    sealed class Ui : ChannelsEvents() {
        data class LoadSubscribedStreams(val isFromCache: Boolean) : Ui()
        data object OpenSearch : Ui()
        data class SearchSubscribedStream(val query: String) : Ui()
        data class ClearSearchResult(val isSearchOpen: Boolean) : Ui()
        data class CreateStream(val streamName: String, val streamDescription: String?) : Ui()
        data class OpenSubscribedStreamTopics(
            val streamId: Int,
            val callback: (streamId: Int) -> Unit
        ) : Ui()

        data class CreateStreamName(val name: String) : Ui()
        data class CreateStreamDescription(val description: String) : Ui()
    }

    sealed class Internal : ChannelsEvents() {
        data class SubscribedStreamsLoaded(
            val streamsUIModel: List<StreamUiModel>,
            val isFromCache: Boolean = false
        ) : Internal()

        data class SearchSubscribedStreamsLoaded(
            val streamsUIModel: List<StreamUiModel>
        ) : Internal()

        data class ErrorLoading(val error: Throwable) : Internal()
        data class StreamsUpdated(val subscribedStreamUiModel: StreamUiModel) : Internal()
    }
}

sealed class ChannelsCommand : Command {
    data class LoadSubscribedStreams(val isFromCache: Boolean) :
        ChannelsCommand()

    data class SearchSubscribedStream(val query: String) : ChannelsCommand()
    data object ClearSearchedSubscribedStreamResult : ChannelsCommand()
    data class CreateStream(val streamName: String, val streamDescription: String?) :
        ChannelsCommand()
}
