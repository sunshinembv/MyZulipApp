package com.example.myzulipapp.chat.presentation.state

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.example.myzulipapp.chat.presentation.ui_model.ChatUiModel
import com.example.myzulipapp.core.Command
import com.example.myzulipapp.core.Event
import com.example.myzulipapp.core.State
import com.example.myzulipapp.event_queue.domain.model.QueueEvent

data class ChatState(
    val data: ChatUiModel? = null,
    val topMessageId: Int = 0,
    val clickedMessageId: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmptyState: Boolean = true,
    val isNeedScroll: Boolean = true,
    val uploadedImageUri: String? = null,
    val queueId: String? = null,
    val lastEventId: Int? = null,
    val isEventLoading: Boolean = false,
    val isEventQueueRegistered: Boolean = false,
    val isEventQueueRegistering: Boolean = false,
    val chatEvent: ChatEvents.Internal.EventLoaded? = null,
    val typedText: String? = null,
    val endReached: Boolean = false,
    val isNewPageLoading: Boolean = false
) : State

sealed class ChatEvents : Event {
    sealed class Ui : ChatEvents() {
        data class SendMessage(
            val streamId: Int,
            val to: Int,
            val topic: String,
            val content: String,
            val type: String = "stream",
        ) : Ui()

        data class GetMessages(
            val streamId: Int,
            val topicName: String,
            val isFromCache: Boolean,
            val numBefore: Int = 20,
            val numAfter: Int = 0,
            val applyMarkdown: Boolean = false,
        ) : Ui()

        data class GetNextPageMessages(
            val streamId: Int,
            val topicName: String,
            val numBefore: Int = 20,
            val numAfter: Int = 0,
            val applyMarkdown: Boolean = false
        ) : Ui()

        data class TapReaction(
            val streamId: Int,
            val topicName: String,
            val messageId: Int,
            val emojiName: String,
            val emojiCode: String
        ) : Ui()

        data class UploadImage(
            val fileUri: Uri
        ) : Ui()

        data class DeleteEventQueue(val queueId: String) : Ui()
        data object RegisterEventQueue : Ui()
        data class Typing(val text: String) : Ui()
        data class OpenEmojiBottomSheet(val messageId: Int) : Ui()
    }

    sealed class Internal : ChatEvents() {
        data class MessagesLoaded(val chatUIModel: ChatUiModel, val endReached: Boolean) :
            Internal()

        data class MessagesLoadedCache(
            val streamId: Int,
            val topicName: String,
            val chatUIModel: ChatUiModel
        ) : Internal()

        data class MessagesUpdated(val chatUIModel: ChatUiModel) : Internal()
        data class ImageUploaded(val uri: String) : Internal()
        data class ErrorLoading(val error: Throwable) : Internal()
        data class EventQueueRegistered(val queueId: String, val lastEventId: Int) : Internal()

        @Immutable
        data class EventLoaded(val queueEvent: QueueEvent) : Internal()
        data object ReactionTaped : Internal()
        data object MessageSent : Internal()
        data object EventQueueDeleted : Internal()
    }
}

sealed class ChatCommand : Command {
    data class SendMessage(
        val type: String = "stream",
        val to: Int,
        val topic: String,
        val content: String
    ) : ChatCommand()

    data class GetMessages(
        val streamId: Int,
        val topicName: String,
        val anchor: Long,
        val isFromCache: Boolean,
        val numBefore: Int = 20,
        val numAfter: Int = 0,
        val applyMarkdown: Boolean = false
    ) : ChatCommand()

    data class TapReaction(
        val streamId: Int,
        val topicName: String,
        val messageId: Int,
        val emojiName: String,
        val emojiCode: String
    ) : ChatCommand()

    data class UploadImage(
        val fileUri: Uri
    ) : ChatCommand()

    data class RegisterEventQueue(val eventTypes: String) : ChatCommand()

    data class GetEventsFromQueue(val queueId: String, val lastEventId: Int) : ChatCommand()

    data class UpdateMessage(val queueEvent: QueueEvent) : ChatCommand()

    data class UpdateMessageList(val queueEvent: QueueEvent) : ChatCommand()

    data class DeleteEventQueue(val queueId: String) : ChatCommand()
}

enum class MessageEventType(val type: String) {
    HEARTBEAT("heartbeat"),
    MESSAGE("message"),
    UPDATE_MESSAGE("update_message"),
    DELETE_MESSAGE("delete_message"),
    REACTION("reaction")
}
