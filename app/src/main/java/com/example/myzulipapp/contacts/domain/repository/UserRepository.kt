package com.example.myzulipapp.contacts.domain.repository

import com.example.myzulipapp.contacts.domain.model.Presence
import com.example.myzulipapp.contacts.domain.model.Presences
import com.example.myzulipapp.contacts.domain.model.User

interface UserRepository {
    suspend fun getAllUsers(): List<User>
    suspend fun getOwnUsers(): User
    suspend fun getUserPresenceById(id: Int): Presence
    suspend fun getPresenceOfAllUsers(): Presences
}
