package com.example.myzulipapp.channels.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myzulipapp.channels.data.db.contracts.StreamTopicContract
import com.example.myzulipapp.channels.data.db.entities.StreamTopicEntity

@Dao
interface StreamTopicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStreamTopics(topics: List<StreamTopicEntity>)

    @Query("SELECT * FROM ${StreamTopicContract.STREAM_TOPIC_TABLE_NAME} WHERE ${StreamTopicContract.Columns.STREAM_ID} = :streamId")
    suspend fun getStreamTopics(streamId: Int): List<StreamTopicEntity>

    @Query("DELETE FROM ${StreamTopicContract.STREAM_TOPIC_TABLE_NAME} WHERE ${StreamTopicContract.Columns.STREAM_ID} = :streamId")
    suspend fun removeStreamTopics(streamId: Int)
}
