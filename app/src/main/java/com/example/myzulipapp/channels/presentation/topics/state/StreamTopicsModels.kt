package com.example.myzulipapp.channels.presentation.topics.state

import com.example.myzulipapp.channels.presentation.topics.ui_model.StreamTopicUiModel
import com.example.myzulipapp.core.Command
import com.example.myzulipapp.core.Event
import com.example.myzulipapp.core.State
import com.example.myzulipapp.navigation.destination.ChatScreenArgs
import com.example.myzulipapp.utils.ImmutableList

data class StreamTopicsState(
    val streamTopics: ImmutableList<StreamTopicUiModel>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmptyState: Boolean = false,
    val isSearchOpen: Boolean = false,
    val querySearch: String = "",
) : State

sealed class StreamTopicsEvents : Event {
    sealed class Ui : StreamTopicsEvents() {
        data class LoadStreamTopics(val streamId: Int) : Ui()
        data object OpenSearch : Ui()
        data class SearchStreamTopics(val query: String) : Ui()
        data class ClearSearchResult(val isSearchOpen: Boolean) : Ui()
        data class OpenTopicChat(
            val streamId: Int,
            val topicName: String,
            val callback: (ChatScreenArgs) -> Unit
        ) : Ui()
    }

    sealed class Internal : StreamTopicsEvents() {
        data class StreamTopicsLoaded(
            val streamId: Int,
            val streamTopicsUIModel: List<StreamTopicUiModel>,
            val isFromCache: Boolean = false
        ) : Internal()

        data class SearchStreamTopicsLoaded(
            val streamTopicsUIModel: List<StreamTopicUiModel>
        ) : Internal()

        data class ErrorLoading(val error: Throwable) : Internal()
    }
}

sealed class StreamTopicsCommand : Command {
    data class LoadStreamTopics(val streamId: Int, val isFromCache: Boolean) : StreamTopicsCommand()
    data class SearchStreamTopics(val query: String) : StreamTopicsCommand()
    data object ClearSearchedStreamTopicsResult : StreamTopicsCommand()
}
