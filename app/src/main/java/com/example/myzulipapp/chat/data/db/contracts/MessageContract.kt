package com.example.myzulipapp.chat.data.db.contracts

object MessageContract {
    const val MESSAGE_TABLE_NAME = "messages"

    object Columns {
        const val ID = "id"
        const val MESSAGE_ID = "message_id"
        const val STREAM_ID = "stream_id"
        const val TOPIC_NAME = "topic_name"
        const val AVATAR_URL = "avatar_url"
        const val SENDER_FULL_NAME = "sender_full_name"
        const val CONTENT = "content"
        const val SENDER_ID = "sender_id"
        const val RECIPIENT_ID = "recipient_id"
        const val TIMESTAMP = "timestamp"
    }
}
