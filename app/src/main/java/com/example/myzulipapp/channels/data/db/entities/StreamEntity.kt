package com.example.myzulipapp.channels.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.myzulipapp.channels.data.db.contracts.StreamContract

@Entity(
    tableName = StreamContract.STREAM_TABLE_NAME,
    indices = [Index(value = [StreamContract.Columns.STREAM_ID], unique = true)]
)
data class StreamEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = StreamContract.Columns.ID)
    val id: Int = 0,
    @ColumnInfo(name = StreamContract.Columns.STREAM_ID)
    val streamId: Int,
    @ColumnInfo(name = StreamContract.Columns.NAME)
    val name: String,
    @ColumnInfo(name = StreamContract.Columns.DESCRIPTION)
    val description: String,
    @ColumnInfo(name = StreamContract.Columns.INVITE_ONLY)
    val inviteOnly: Boolean,
    @ColumnInfo(name = StreamContract.Columns.COLOR)
    val color: String,
    @ColumnInfo(name = StreamContract.Columns.DATE_CREATED)
    val dateCreated: Long
)
