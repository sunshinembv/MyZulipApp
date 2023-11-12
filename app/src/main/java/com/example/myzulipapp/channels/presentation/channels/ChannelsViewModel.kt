package com.example.myzulipapp.channels.presentation.channels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myzulipapp.channels.presentation.channels.state.ChannelsActor
import com.example.myzulipapp.channels.presentation.channels.state.ChannelsEvents
import com.example.myzulipapp.channels.presentation.channels.state.ChannelsReducer
import com.example.myzulipapp.channels.presentation.channels.state.ChannelsState
import com.example.myzulipapp.core.BaseViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChannelsViewModel(
    private val reducer: ChannelsReducer,
    private val actor: ChannelsActor,
) : BaseViewModel<ChannelsEvents, ChannelsState>() {

    override val state: StateFlow<ChannelsState>
        get() = reducer.state

    init {
        obtainEvent(ChannelsEvents.Ui.LoadSubscribedStreams(true))
    }

    fun obtainEvent(event: ChannelsEvents.Ui) {
        when (event) {
            is ChannelsEvents.Ui.CreateStream -> {
                handleEvent(event)
            }

            is ChannelsEvents.Ui.LoadSubscribedStreams -> {
                handleEvent(event)
            }

            ChannelsEvents.Ui.OpenSearch -> {
                handleOnlyEvent(event)
            }

            is ChannelsEvents.Ui.SearchSubscribedStream -> {
                handleEvent(event)
            }

            is ChannelsEvents.Ui.ClearSearchResult -> {
                handleEvent(event)
            }

            is ChannelsEvents.Ui.OpenSubscribedStreamTopics -> {
                openSubscribedStreamTopics(event)
            }

            is ChannelsEvents.Ui.CreateStreamDescription -> {
                handleOnlyEvent(event)
            }

            is ChannelsEvents.Ui.CreateStreamName -> {
                handleOnlyEvent(event)
            }
        }
    }

    private fun handleEvent(event: ChannelsEvents) {
        viewModelScope.launch {
            val result = reducer.sendEvent(event)
            result.command?.let { actor.execute(it, ::handleEvent) }
        }
    }

    private fun handleOnlyEvent(event: ChannelsEvents.Ui) {
        reducer.sendEvent(event)
    }

    private fun openSubscribedStreamTopics(event: ChannelsEvents.Ui.OpenSubscribedStreamTopics) {
        viewModelScope.launch {
            reducer.sendEvent(event)
            event.callback.invoke(event.streamId)
        }
    }

    class Factory @Inject constructor(
        private val reducer: ChannelsReducer,
        private val actor: ChannelsActor,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == ChannelsViewModel::class.java)
            return ChannelsViewModel(
                reducer,
                actor
            ) as T
        }
    }
}
