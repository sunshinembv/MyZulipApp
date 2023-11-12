package com.example.myzulipapp.chat.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.myzulipapp.chat.data.db.MessageWithReactions
import com.example.myzulipapp.chat.data.db.contracts.MessageContract
import com.example.myzulipapp.chat.data.db.entities.MessageEntity

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<MessageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(messages: MessageEntity)

    @Transaction
    @Query("SELECT * FROM ${MessageContract.MESSAGE_TABLE_NAME} WHERE ${MessageContract.Columns.STREAM_ID} = :streamId AND ${MessageContract.Columns.TOPIC_NAME} = :topicName")
    suspend fun getMessages(streamId: Int, topicName: String): List<MessageWithReactions>

    @Query("DELETE FROM ${MessageContract.MESSAGE_TABLE_NAME} WHERE ${MessageContract.Columns.STREAM_ID} = :streamId  AND ${MessageContract.Columns.TOPIC_NAME} = :topicName")
    suspend fun removeMessages(streamId: Int, topicName: String)

    @Query(
        "DELETE FROM ${MessageContract.MESSAGE_TABLE_NAME} " +
                "WHERE ${MessageContract.Columns.STREAM_ID} = :streamId " +
                "AND ${MessageContract.Columns.TOPIC_NAME} = :topicName AND ${MessageContract.Columns.ID} " +
                "NOT IN (SELECT ${MessageContract.Columns.ID} FROM ${MessageContract.MESSAGE_TABLE_NAME} ORDER BY ${MessageContract.Columns.ID} LIMIT 50)"
    )
    suspend fun removeExtraMessages(streamId: Int, topicName: String)
}
