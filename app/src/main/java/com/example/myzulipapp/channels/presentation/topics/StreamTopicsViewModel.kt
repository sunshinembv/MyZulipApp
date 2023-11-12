package com.example.myzulipapp.channels.presentation.topics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myzulipapp.channels.presentation.topics.state.StreamTopicsActor
import com.example.myzulipapp.channels.presentation.topics.state.StreamTopicsEvents
import com.example.myzulipapp.channels.presentation.topics.state.StreamTopicsReducer
import com.example.myzulipapp.channels.presentation.topics.state.StreamTopicsState
import com.example.myzulipapp.core.BaseViewModel
import com.example.myzulipapp.navigation.destination.ChatScreenArgs
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StreamTopicsViewModel(
    private val streamId: Int,
    private val reducer: StreamTopicsReducer,
    private val actor: StreamTopicsActor,
) : BaseViewModel<StreamTopicsEvents, StreamTopicsState>() {

    override val state: StateFlow<StreamTopicsState>
        get() = reducer.state

    init {
        obtainEvent(StreamTopicsEvents.Ui.LoadStreamTopics(streamId))
    }

    fun obtainEvent(event: StreamTopicsEvents.Ui) {
        when (event) {
            is StreamTopicsEvents.Ui.LoadStreamTopics -> {
                handleEvent(event)
            }

            is StreamTopicsEvents.Ui.ClearSearchResult -> {
                handleEvent(event)
            }

            StreamTopicsEvents.Ui.OpenSearch -> {
                openSearch(event)
            }

            is StreamTopicsEvents.Ui.SearchStreamTopics -> {
                handleEvent(event)
            }

            is StreamTopicsEvents.Ui.OpenTopicChat -> {
                openTopicChat(event)
            }
        }
    }

    private fun handleEvent(event: StreamTopicsEvents) {
        viewModelScope.launch {
            val result = reducer.sendEvent(event)
            result.command?.let { actor.execute(it, ::handleEvent) }
        }
    }

    private fun openSearch(event: StreamTopicsEvents.Ui) {
        reducer.sendEvent(event)
    }

    private fun openTopicChat(event: StreamTopicsEvents.Ui.OpenTopicChat) {
        viewModelScope.launch {
            reducer.sendEvent(event)
            event.callback.invoke(ChatScreenArgs(event.streamId, event.topicName))
        }
    }

    class Factory(
        private val streamId: Int,
        private val reducer: StreamTopicsReducer,
        private val actor: StreamTopicsActor,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == StreamTopicsViewModel::class.java)
            return StreamTopicsViewModel(
                streamId,
                reducer,
                actor
            ) as T
        }
    }

    class AssistedFactory(
        private val reducer: StreamTopicsReducer,
        private val actor: StreamTopicsActor
    ) {
        fun create(streamId: Int): Factory {
            return Factory(streamId, reducer, actor)
        }
    }
}
