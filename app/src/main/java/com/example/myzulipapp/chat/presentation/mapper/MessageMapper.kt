package com.example.myzulipapp.chat.presentation.mapper

import com.example.myzulipapp.chat.domain.model.Message
import com.example.myzulipapp.chat.presentation.ui_model.MessageUiModel
import com.example.myzulipapp.core.OwnUser
import com.example.myzulipapp.utils.toDate
import javax.inject.Inject

class MessageMapper @Inject constructor() {

    fun toMessagesUIModel(
        messages: List<Message>,
        timePattern: String = "hh:mm",
        datePattern: String = "dd MMMM"
    ): List<MessageUiModel> {
        var date: String? = null
        val messageUIModel = mutableListOf<MessageUiModel>()
        messages.map { message ->
            val messageDate = message.timestamp.toDate(datePattern)
            val messageTime = message.timestamp.toDate(timePattern)
            messageUIModel.add(
                toMessageUIModel(
                    message,
                    messageDate,
                    messageTime,
                    date != messageDate
                )
            )
            if (date != messageDate) {
                date = messageDate
            }
        }
        return messageUIModel
    }

    private fun toMessageUIModel(
        message: Message,
        day: String,
        time: String,
        isShowDate: Boolean
    ): MessageUiModel {
        return MessageUiModel(
            id = message.id,
            streamId = message.streamId,
            senderId = message.senderId,
            recipientId = message.recipientId,
            userName = message.senderFullName,
            userIcon = message.avatarUrl,
            message = message.content,
            reactions = message.reactions,
            day = day,
            time = time,
            topicName = message.topicName,
            isOwnMessage = message.senderId == OwnUser.ownUser?.id,
            isShowDate = isShowDate
        )
    }
}
