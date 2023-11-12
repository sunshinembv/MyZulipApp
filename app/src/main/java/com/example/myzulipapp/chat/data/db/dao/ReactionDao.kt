package com.example.myzulipapp.chat.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myzulipapp.chat.data.db.contracts.ReactionContract
import com.example.myzulipapp.chat.data.db.entities.ReactionEntity

@Dao
interface ReactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReactions(reactions: List<ReactionEntity>)

    @Query("DELETE FROM ${ReactionContract.REACTION_TABLE_NAME} WHERE ${ReactionContract.Columns.STREAM_ID} = :streamId  AND ${ReactionContract.Columns.TOPIC_NAME} = :topicName")
    suspend fun removeReactions(streamId: Int, topicName: String)
}
