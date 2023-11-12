package com.example.myzulipapp.channels.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myzulipapp.channels.data.db.contracts.StreamTopicContract

@Entity(
    tableName = StreamTopicContract.STREAM_TOPIC_TABLE_NAME
)
data class StreamTopicEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = StreamTopicContract.Columns.ID)
    val id: Int = 0,
    @ColumnInfo(name = StreamTopicContract.Columns.STREAM_ID)
    val streamId: Int,
    @ColumnInfo(name = StreamTopicContract.Columns.NAME)
    val name: String,
    @ColumnInfo(name = StreamTopicContract.Columns.LAST_MESSAGE)
    val lastMessage: String,
    @ColumnInfo(name = StreamTopicContract.Columns.LAST_MESSAGE_DATE)
    val lastMessageDate: String,
)
