package com.example.myzulipapp.channels.data.db.contracts

object StreamContract {
    const val STREAM_TABLE_NAME = "streams"

    object Columns {
        const val ID = "id"
        const val STREAM_ID = "stream_id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val INVITE_ONLY = "invite_only"
        const val COLOR = "color"
        const val DATE_CREATED = "date_created"
    }
}
