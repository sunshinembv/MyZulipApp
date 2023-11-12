package com.example.myzulipapp.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myzulipapp.chat.presentation.state.ChatActor
import com.example.myzulipapp.chat.presentation.state.ChatEvents
import com.example.myzulipapp.chat.presentation.state.ChatReducer
import com.example.myzulipapp.chat.presentation.state.ChatState
import com.example.myzulipapp.core.BaseViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val streamId: Int,
    private val topicName: String,
    private val reducer: ChatReducer,
    private val actor: ChatActor,
) : BaseViewModel<ChatEvents, ChatState>() {

    override val state: StateFlow<ChatState>
        get() = reducer.state

    init {
        obtainEvent(
            ChatEvents.Ui.GetMessages(
                streamId = streamId,
                topicName = topicName,
                isFromCache = true
            )
        )
    }

    fun obtainEvent(event: ChatEvents.Ui) {
        when (event) {

            is ChatEvents.Ui.DeleteEventQueue -> {
                handleEvent(event)
            }

            is ChatEvents.Ui.GetMessages -> {
                handleEvent(event)
            }

            is ChatEvents.Ui.GetNextPageMessages -> {
                handleEvent(event)
            }

            ChatEvents.Ui.RegisterEventQueue -> {
                handleEvent(event)
            }

            is ChatEvents.Ui.SendMessage -> {
                handleEvent(event)
            }

            is ChatEvents.Ui.TapReaction -> {
                handleEvent(event)
            }

            is ChatEvents.Ui.UploadImage -> {
                handleEvent(event)
            }

            is ChatEvents.Ui.Typing -> {
                handleOnlyEvent(event)
            }

            is ChatEvents.Ui.OpenEmojiBottomSheet -> {
                handleOnlyEvent(event)
            }
        }
    }

    private fun handleEvent(event: ChatEvents) {
        viewModelScope.launch {
            val result = reducer.sendEvent(event)
            result.command?.let { actor.execute(it, ::handleEvent) }
        }
    }

    private fun handleOnlyEvent(event: ChatEvents.Ui) {
        reducer.sendEvent(event)
    }

    class Factory(
        private val streamId: Int,
        private val topicName: String,
        private val reducer: ChatReducer,
        private val actor: ChatActor,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == ChatViewModel::class.java)
            return ChatViewModel(
                streamId,
                topicName,
                reducer,
                actor
            ) as T
        }
    }

    class AssistedFactory(
        private val reducer: ChatReducer,
        private val actor: ChatActor
    ) {
        fun create(streamId: Int, topicName: String): Factory {
            return Factory(streamId, topicName, reducer, actor)
        }
    }
}
