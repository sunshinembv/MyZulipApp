package com.example.myzulipapp.contacts.data.mapper

import com.example.myzulipapp.contacts.data.model.UserData
import com.example.myzulipapp.contacts.data.model.UserListRootData
import com.example.myzulipapp.contacts.domain.model.Aggregated
import com.example.myzulipapp.contacts.domain.model.Presence
import com.example.myzulipapp.contacts.domain.model.Presences
import com.example.myzulipapp.contacts.domain.model.User
import javax.inject.Inject

class UserMapper @Inject constructor() {

    private val defaultAggregated = Aggregated("offline", System.currentTimeMillis())
    fun toUserList(userListRootData: UserListRootData, presences: Presences): List<User> {
        return userListRootData.users.filter { !it.isBot }.map { userData ->
            User(
                userData.id,
                userData.name,
                userData.avatar,
                userData.email,
                userData.isActive,
                userData.isBot,
                Presence(presences.presences[userData.email]?.aggregated ?: defaultAggregated)
            )
        }
    }

    fun toUser(userData: UserData, userPresence: Presence?): User {
        return User(
            userData.id,
            userData.name,
            userData.avatar,
            userData.email,
            userData.isActive,
            userData.isBot,
            userPresence ?: Presence(defaultAggregated)
        )
    }
}
