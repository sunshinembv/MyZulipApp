package com.example.myzulipapp.contacts.domain.model

data class User(
    val id: Int,
    val name: String,
    val avatar: String?,
    val email: String,
    val isActive: Boolean,
    val isBot: Boolean,
    val presence: Presence
)
