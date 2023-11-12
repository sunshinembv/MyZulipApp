package com.example.myzulipapp.channels.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myzulipapp.channels.data.db.contracts.StreamContract
import com.example.myzulipapp.channels.data.db.entities.StreamEntity

@Dao
interface StreamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStreams(streams: List<StreamEntity>)

    @Query("SELECT * FROM ${StreamContract.STREAM_TABLE_NAME}")
    suspend fun getSubscribedStreams(): List<StreamEntity>

    @Query("DELETE FROM ${StreamContract.STREAM_TABLE_NAME}")
    suspend fun removeSubscribedStreams()
}
