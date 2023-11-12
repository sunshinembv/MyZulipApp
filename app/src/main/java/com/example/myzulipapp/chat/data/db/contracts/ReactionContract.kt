package com.example.myzulipapp.chat.data.db.contracts

object ReactionContract {
    const val REACTION_TABLE_NAME = "reactions"

    object Columns {
        const val ID = "id"
        const val MESSAGE_ID = "message_id"
        const val STREAM_ID = "stream_id"
        const val TOPIC_NAME = "topic_name"
        const val EMOJI_NAME = "emoji_name"
        const val EMOJI_CODE = "emoji_code"
        const val REACTION_TYPE = "reaction_type"
        const val USER_ID = "user_id"
        const val COUNT = "count"
    }
}
