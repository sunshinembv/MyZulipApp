package com.example.myzulipapp.chat.presentation.state

import com.example.myzulipapp.core.Reducer
import com.example.myzulipapp.core.Result
import javax.inject.Inject

class ChatReducer @Inject constructor(
    state: ChatState
) : Reducer<ChatEvents, ChatState, ChatCommand>(state) {

    override fun reduce(event: ChatEvents, state: ChatState): Result<ChatCommand> {
        return when (event) {
            is ChatEvents.Internal.ErrorLoading -> {
                setState(
                    state.copy(error = event.error.message, isLoading = false, isEmptyState = false)
                )
                Result(null)
            }

            is ChatEvents.Internal.EventLoaded -> {
                when (event.queueEvent.type) {
                    MessageEventType.HEARTBEAT.type -> {
                        setState(
                            state.copy(
                                queueId = event.queueEvent.queueId,
                                lastEventId = event.queueEvent.id,
                                isEventLoading = false
                            )
                        )
                        val command = ChatCommand.GetEventsFromQueue(
                            event.queueEvent.queueId,
                            event.queueEvent.id
                        )
                        Result(command)
                    }

                    MessageEventType.MESSAGE.type,
                    MessageEventType.DELETE_MESSAGE.type -> {
                        setState(
                            state.copy(
                                queueId = event.queueEvent.queueId,
                                lastEventId = event.queueEvent.id,
                                isEventLoading = false
                            )
                        )
                        val command = ChatCommand.UpdateMessageList(event.queueEvent)
                        Result(command)
                    }

                    MessageEventType.UPDATE_MESSAGE.type,
                    MessageEventType.REACTION.type -> {
                        setState(
                            state.copy(
                                queueId = event.queueEvent.queueId,
                                lastEventId = event.queueEvent.id,
                                isEventLoading = false
                            )
                        )
                        val command = ChatCommand.UpdateMessage(event.queueEvent)
                        Result(command)
                    }

                    else -> {
                        Result(null)
                    }
                }
            }

            is ChatEvents.Internal.EventQueueRegistered -> {
                setState(
                    state.copy(
                        queueId = event.queueId,
                        lastEventId = event.lastEventId,
                        isEventLoading = true,
                        isEventQueueRegistered = true
                    )
                )
                val command = ChatCommand.GetEventsFromQueue(event.queueId, event.lastEventId)
                Result(command)
            }

            is ChatEvents.Internal.ImageUploaded -> {
                setState(
                    state.copy(uploadedImageUri = event.uri)
                )
                Result(null)
            }

            ChatEvents.Internal.MessageSent -> {
                setState(
                    state.copy(isNeedScroll = false)
                )
                Result(null)
            }

            is ChatEvents.Internal.MessagesLoaded -> {
                val newState = state.copy(
                    data = event.chatUIModel,
                    topMessageId = event.chatUIModel.topMessageId,
                    isLoading = false,
                    endReached = event.endReached,
                    isNewPageLoading = false
                )
                val command = when {
                    state.isEventQueueRegistering.not() -> {
                        setState(newState.copy(isEventQueueRegistering = true))
                        ChatCommand.RegisterEventQueue(EVENT_TYPES)
                    }

                    state.isEventLoading.not() -> {
                        setState(newState)
                        state.queueId?.let { queueId ->
                            state.lastEventId?.let { lastEventId ->
                                ChatCommand.GetEventsFromQueue(
                                    queueId,
                                    lastEventId
                                )
                            }
                        }
                    }

                    else -> {
                        setState(newState)
                        null
                    }
                }
                Result(command)
            }

            is ChatEvents.Internal.MessagesLoadedCache -> {
                setState(
                    state.copy(
                        data = event.chatUIModel,
                        isLoading = event.chatUIModel.messages.list.isEmpty(),
                        error = null,
                        isEmptyState = false
                    )
                )
                val command = ChatCommand.GetMessages(
                    streamId = event.streamId,
                    topicName = event.topicName,
                    anchor = ANCHOR_NEWEST,
                    numBefore = MESSAGE_NUM_BEFORE_FROM_NETWORK,
                    isFromCache = false
                )
                Result(command)
            }

            ChatEvents.Internal.ReactionTaped -> {
                Result(null)
            }

            is ChatEvents.Ui.DeleteEventQueue -> {
                setState(
                    state.copy(isEventQueueRegistering = false, isEventQueueRegistered = false)
                )
                val command = state.queueId?.let { ChatCommand.DeleteEventQueue(it) }
                Result(command)
            }

            ChatEvents.Internal.EventQueueDeleted -> {
                setState(
                    state.copy(
                        queueId = null,
                        lastEventId = null
                    )
                )
                Result(null)
            }

            is ChatEvents.Ui.GetMessages -> {
                setState(
                    state.copy(
                        isLoading = state.data?.messages?.list?.isEmpty() ?: false,
                        isEmptyState = false
                    )
                )
                val command = ChatCommand.GetMessages(
                    streamId = event.streamId,
                    topicName = event.topicName,
                    anchor = ANCHOR_NEWEST,
                    numBefore = MESSAGE_NUM_BEFORE_FROM_DB,
                    isFromCache = true
                )
                Result(command)
            }

            is ChatEvents.Ui.GetNextPageMessages -> {
                setState(
                    state.copy(
                        isLoading = false,
                        error = null,
                        isEmptyState = false,
                        isNeedScroll = false,
                        isNewPageLoading = true,
                    )
                )
                val command = ChatCommand.GetMessages(
                    streamId = event.streamId,
                    topicName = event.topicName,
                    anchor = state.topMessageId.toLong(),
                    numBefore = event.numBefore,
                    isFromCache = false
                )
                Result(command)
            }

            ChatEvents.Ui.RegisterEventQueue -> {
                if (state.isEventQueueRegistering.not()) {
                    setState(
                        state.copy(isEventQueueRegistering = true)
                    )
                    val command = ChatCommand.RegisterEventQueue(EVENT_TYPES)
                    Result(command)
                } else {
                    Result(null)
                }
            }

            is ChatEvents.Ui.SendMessage -> {
                setState(
                    state.copy(
                        isLoading = false,
                        error = null,
                        isEmptyState = false,
                        isNeedScroll = true,
                        uploadedImageUri = null,
                        typedText = null
                    )
                )
                val command = ChatCommand.SendMessage(
                    event.type,
                    event.to,
                    event.topic,
                    event.content
                )
                Result(command)
            }

            is ChatEvents.Ui.TapReaction -> {
                setState(
                    state.copy(clickedMessageId = 0)
                )
                val command = ChatCommand.TapReaction(
                    event.streamId,
                    event.topicName,
                    event.messageId,
                    event.emojiName,
                    event.emojiCode
                )
                Result(command)
            }

            is ChatEvents.Ui.UploadImage -> {
                val command = ChatCommand.UploadImage(event.fileUri)
                Result(command)
            }

            is ChatEvents.Ui.Typing -> {
                setState(
                    state.copy(typedText = event.text)
                )
                Result(null)
            }

            is ChatEvents.Ui.OpenEmojiBottomSheet -> {
                setState(state.copy(clickedMessageId = event.messageId))
                Result(null)
            }

            is ChatEvents.Internal.MessagesUpdated -> {
                val newState = state.copy(
                    data = event.chatUIModel,
                    topMessageId = event.chatUIModel.topMessageId,
                    isLoading = false
                )
                val command = when {
                    state.isEventQueueRegistering.not() -> {
                        setState(newState.copy(isEventQueueRegistering = true))
                        ChatCommand.RegisterEventQueue(EVENT_TYPES)
                    }

                    state.isEventLoading.not() -> {
                        setState(newState)
                        state.queueId?.let { queueId ->
                            state.lastEventId?.let { lastEventId ->
                                ChatCommand.GetEventsFromQueue(
                                    queueId,
                                    lastEventId
                                )
                            }
                        }
                    }

                    else -> {
                        setState(newState)
                        null
                    }
                }
                Result(command)
            }
        }
    }

    companion object {
        private const val MESSAGE_NUM_BEFORE_FROM_DB = 50
        private const val MESSAGE_NUM_BEFORE_FROM_NETWORK = 20
        private const val ANCHOR_NEWEST = 10000000000000000L
        private const val EVENT_TYPES =
            "[\"message\", \"update_message\", \"delete_message\", \"reaction\"]"
    }
}
