package com.example.myzulipapp.chat.presentation.state

import com.example.myzulipapp.chat.domain.model.EmojiRequest
import com.example.myzulipapp.chat.domain.model.Message
import com.example.myzulipapp.chat.domain.model.MessageRequest
import com.example.myzulipapp.chat.domain.usecases.GetMessagesUseCase
import com.example.myzulipapp.chat.domain.usecases.GetSingleMessageByIdUseCase
import com.example.myzulipapp.chat.domain.usecases.SendMessageUseCase
import com.example.myzulipapp.chat.domain.usecases.TapReactionsUseCase
import com.example.myzulipapp.chat.domain.usecases.UploadImageUseCase
import com.example.myzulipapp.chat.presentation.mapper.MessageMapper
import com.example.myzulipapp.chat.presentation.ui_model.ChatUiModel
import com.example.myzulipapp.chat.presentation.ui_model.MessageUiModel
import com.example.myzulipapp.core.Actor
import com.example.myzulipapp.core.OwnUser
import com.example.myzulipapp.event_queue.domain.usecases.DeleteEventQueueUseCase
import com.example.myzulipapp.event_queue.domain.usecases.GetEventFromQueueUseCase
import com.example.myzulipapp.event_queue.domain.usecases.RegisterEventQueueUseCase
import com.example.myzulipapp.utils.ImmutableList
import javax.inject.Inject

class ChatActor @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val getSingleMessageByIdUseCase: GetSingleMessageByIdUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val tapReactionsUseCase: TapReactionsUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val registerEventQueueUseCase: RegisterEventQueueUseCase,
    private val getEventFromQueueUseCase: GetEventFromQueueUseCase,
    private val deleteEventQueueUseCase: DeleteEventQueueUseCase,
    private val messageMapper: MessageMapper
) : Actor<ChatCommand, ChatEvents.Internal> {

    private var chatUIModel = ChatUiModel("", 0, ImmutableList(emptyList()))
    private var messages = emptyList<Message>()

    override suspend fun execute(command: ChatCommand, onEvent: (ChatEvents.Internal) -> Unit) {
        try {
            when (command) {
                is ChatCommand.DeleteEventQueue -> {
                    deleteEventQueueUseCase.execute(command.queueId)
                    onEvent(ChatEvents.Internal.EventQueueDeleted)
                }

                is ChatCommand.GetEventsFromQueue -> {
                    val event =
                        getEventFromQueueUseCase.execute(command.queueId, command.lastEventId)
                    onEvent(ChatEvents.Internal.EventLoaded(event))
                }

                is ChatCommand.GetMessages -> {
                    val messagesData = getMessagesUseCase.execute(
                        command.anchor,
                        command.streamId,
                        command.topicName,
                        command.numBefore,
                        command.numAfter,
                        command.applyMarkdown,
                        command.isFromCache
                    )
                    messages =
                        messagesData.plus(messages).toSet().toList().sortedBy { it.timestamp }

                    val topMessageId = messages.firstOrNull()?.id ?: 0
                    val messagesUIModel = messageMapper.toMessagesUIModel(messages)
                    chatUIModel = ChatUiModel(
                        command.topicName,
                        topMessageId,
                        ImmutableList(messagesUIModel)
                    )
                    if (command.isFromCache) {
                        onEvent(
                            ChatEvents.Internal.MessagesLoadedCache(
                                command.streamId,
                                command.topicName,
                                chatUIModel
                            )
                        )
                    } else {
                        onEvent(
                            ChatEvents.Internal.MessagesLoaded(
                                chatUIModel,
                                messagesData.isEmpty()
                            )
                        )
                    }
                }

                is ChatCommand.RegisterEventQueue -> {
                    val eventQueue = registerEventQueueUseCase.execute(command.eventTypes)
                    onEvent(
                        ChatEvents.Internal.EventQueueRegistered(
                            eventQueue.queueId,
                            eventQueue.lastEventId
                        )
                    )
                }

                is ChatCommand.SendMessage -> {
                    val messageRequest =
                        createMessageRequest(
                            command.type,
                            command.to,
                            command.topic,
                            command.content
                        )
                    sendMessageUseCase.execute(messageRequest)
                    onEvent(ChatEvents.Internal.MessageSent)
                }

                is ChatCommand.TapReaction -> {
                    messages.find { it.id == command.messageId }?.let { message ->
                        val ownUserId = OwnUser.ownUser?.id ?: -1
                        val emojiRequest =
                            createEmojiRequest(
                                command.messageId,
                                command.emojiName,
                                command.emojiCode
                            )
                        val isReactionExists =
                            message.reactions.find { it.userId == ownUserId && it.emojiCode == command.emojiCode } != null
                        tapReactionsUseCase.execute(emojiRequest, isReactionExists)
                    }
                    onEvent(ChatEvents.Internal.ReactionTaped)
                }

                is ChatCommand.UpdateMessage -> {
                    messages.find { it.id == command.queueEvent.messageId }?.let { message ->
                        chatUIModel =
                            chatUIModel.copy(
                                messages = ImmutableList(
                                    getUpdatedMessageListInfo(
                                        message.id
                                    )
                                )
                            )
                    }
                    onEvent(ChatEvents.Internal.MessagesUpdated(chatUIModel))
                }

                is ChatCommand.UpdateMessageList -> {
                    val id = if (MessageEventType.MESSAGE.type == command.queueEvent.type) {
                        checkNotNull(command.queueEvent.message?.id)
                    } else {
                        command.queueEvent.messageId
                    }
                    chatUIModel =
                        chatUIModel.copy(
                            messages = ImmutableList(
                                getUpdatedMessageList(
                                    id,
                                    command.queueEvent.type
                                )
                            )
                        )
                    onEvent(
                        ChatEvents.Internal.MessagesUpdated(
                            chatUIModel
                        )
                    )
                }

                is ChatCommand.UploadImage -> {
                    val uploadImageResponse = uploadImageUseCase.execute(command.fileUri)
                    onEvent(ChatEvents.Internal.ImageUploaded(uploadImageResponse))
                }
            }
        } catch (t: Throwable) {
            onEvent(ChatEvents.Internal.ErrorLoading(t))
        }
    }

    private fun createMessageRequest(
        type: String,
        to: Int,
        topic: String,
        content: String
    ): MessageRequest {
        return MessageRequest(type, to, topic, content)
    }

    private suspend fun getUpdatedMessageList(
        messageId: Int,
        eventType: String,
        applyMarkdown: Boolean = false
    ): List<MessageUiModel> {
        return when (eventType) {
            MessageEventType.MESSAGE.type -> {
                val message =
                    getSingleMessageByIdUseCase.execute(messageId, applyMarkdown)
                messages = messages.plus(message)
                messageMapper.toMessagesUIModel(messages)
            }

            MessageEventType.DELETE_MESSAGE.type -> {
                messages = messages.filter { it.id != messageId }
                messageMapper.toMessagesUIModel(messages)
            }

            else -> {
                error(ILLEGAL_STATE)
            }
        }
    }

    private suspend fun getUpdatedMessageListInfo(
        messageId: Int,
        applyMarkdown: Boolean = false
    ): List<MessageUiModel> {
        val message =
            getSingleMessageByIdUseCase.execute(messageId, applyMarkdown)
        messages = messages.map { if (it.id == message.id) message else it }
        return messageMapper.toMessagesUIModel(messages)
    }

    private fun createEmojiRequest(
        messageId: Int,
        emojiName: String,
        emojiCode: String
    ): EmojiRequest {
        return EmojiRequest(messageId, emojiName, emojiCode)
    }

    companion object {
        private const val ILLEGAL_STATE = "Illegal State"
    }
}
