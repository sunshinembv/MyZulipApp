package com.example.myzulipapp.chat.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.myzulipapp.chat.data.db.contracts.MessageContract
import com.example.myzulipapp.chat.data.db.contracts.ReactionContract

@Entity(
    tableName = ReactionContract.REACTION_TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = MessageEntity::class,
            parentColumns = [MessageContract.Columns.MESSAGE_ID],
            childColumns = [ReactionContract.Columns.MESSAGE_ID]
        )
    ],
    //indices = [Index(value = [ReactionContract.Columns.EMOJI_CODE], unique = true)]
)
data class ReactionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ReactionContract.Columns.ID)
    val id: Int = 0,
    @ColumnInfo(name = ReactionContract.Columns.MESSAGE_ID)
    val messageId: Int,
    @ColumnInfo(name = ReactionContract.Columns.STREAM_ID)
    val streamId: Int,
    @ColumnInfo(name = ReactionContract.Columns.TOPIC_NAME)
    val topicName: String,
    @ColumnInfo(name = ReactionContract.Columns.EMOJI_NAME)
    val emojiName: String,
    @ColumnInfo(name = ReactionContract.Columns.EMOJI_CODE)
    val emojiCode: String,
    @ColumnInfo(name = ReactionContract.Columns.REACTION_TYPE)
    val reactionType: String,
    @ColumnInfo(name = ReactionContract.Columns.USER_ID)
    val userId: Int,
    @ColumnInfo(name = ReactionContract.Columns.COUNT)
    val count: Int,
)
