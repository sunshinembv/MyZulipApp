package com.example.myzulipapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myzulipapp.channels.data.db.dao.StreamDao
import com.example.myzulipapp.channels.data.db.dao.StreamTopicDao
import com.example.myzulipapp.channels.data.db.entities.StreamEntity
import com.example.myzulipapp.channels.data.db.entities.StreamTopicEntity
import com.example.myzulipapp.chat.data.db.dao.MessageDao
import com.example.myzulipapp.chat.data.db.dao.ReactionDao
import com.example.myzulipapp.chat.data.db.entities.MessageEntity
import com.example.myzulipapp.chat.data.db.entities.ReactionEntity
import com.example.myzulipapp.db.ZulipDatabase.Companion.DB_VERSION

@Database(
    entities = [
        StreamEntity::class,
        StreamTopicEntity::class,
        MessageEntity::class,
        ReactionEntity::class
    ],
    version = DB_VERSION
)
abstract class ZulipDatabase : RoomDatabase() {

    abstract fun streamDao(): StreamDao
    abstract fun streamTopicDao(): StreamTopicDao
    abstract fun messageDao(): MessageDao
    abstract fun reactionDao(): ReactionDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "zulip-db"
    }
}
