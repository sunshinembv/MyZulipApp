package com.example.myzulipapp.chat.data.db

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myzulipapp.chat.data.db.contracts.MessageContract
import com.example.myzulipapp.chat.data.db.contracts.ReactionContract
import com.example.myzulipapp.chat.data.db.entities.MessageEntity
import com.example.myzulipapp.chat.data.db.entities.ReactionEntity

data class MessageWithReactions(
    @Embedded
    val message: MessageEntity,
    @Relation(
        parentColumn = MessageContract.Columns.MESSAGE_ID,
        entityColumn = ReactionContract.Columns.MESSAGE_ID
    )
    val reactions: List<ReactionEntity>
)
