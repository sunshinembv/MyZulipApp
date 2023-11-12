package com.example.myzulipapp.chat.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.myzulipapp.chat.data.db.contracts.MessageContract

@Entity(
    tableName = MessageContract.MESSAGE_TABLE_NAME,
    indices = [Index(value = [MessageContract.Columns.MESSAGE_ID], unique = true)]
)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MessageContract.Columns.ID)
    val id: Int = 0,
    @ColumnInfo(name = MessageContract.Columns.MESSAGE_ID)
    val messageId: Int,
    @ColumnInfo(name = MessageContract.Columns.STREAM_ID)
    val streamId: Int,
    @ColumnInfo(name = MessageContract.Columns.TOPIC_NAME)
    val topicName: String,
    @ColumnInfo(name = MessageContract.Columns.AVATAR_URL)
    val avatarUrl: String,
    @ColumnInfo(name = MessageContract.Columns.SENDER_FULL_NAME)
    val senderFullName: String,
    @ColumnInfo(name = MessageContract.Columns.CONTENT)
    val content: String,
    @ColumnInfo(name = MessageContract.Columns.SENDER_ID)
    val senderId: Int,
    @ColumnInfo(name = MessageContract.Columns.RECIPIENT_ID)
    val recipientId: Int,
    @ColumnInfo(name = MessageContract.Columns.TIMESTAMP)
    val timestamp: Long
)
