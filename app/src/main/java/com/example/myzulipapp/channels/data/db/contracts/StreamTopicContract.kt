package com.example.myzulipapp.channels.data.db.contracts

object StreamTopicContract {
    const val STREAM_TOPIC_TABLE_NAME = "streamTopics"

    object Columns {
        const val ID = "id"
        const val STREAM_ID = "stream_id"
        const val NAME = "name"
        const val LAST_MESSAGE = "last_message"
        const val LAST_MESSAGE_DATE = "last_message_date"
    }
}
