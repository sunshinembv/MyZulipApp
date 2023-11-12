package com.example.myzulipapp.channels.domain.model

data class Stream(
    val streamId: Int,
    val name: String,
    val description: String,
    val inviteOnly: Boolean,
    val color: String,
    val dateCreated: String
)
