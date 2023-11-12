package com.example.myzulipapp.contacts.data.repository

import com.example.myzulipapp.contacts.data.mapper.PresenceMapper
import com.example.myzulipapp.contacts.data.mapper.UserMapper
import com.example.myzulipapp.contacts.domain.model.Presence
import com.example.myzulipapp.contacts.domain.model.Presences
import com.example.myzulipapp.contacts.domain.model.User
import com.example.myzulipapp.contacts.domain.repository.UserRepository
import com.example.myzulipapp.network.NetworkHandleService
import com.example.myzulipapp.network.ZulipApi
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val zulipApi: ZulipApi,
    private val networkHandleService: NetworkHandleService,
    private val userMapper: UserMapper,
    private val presenceMapper: PresenceMapper,
) : UserRepository {

    override suspend fun getAllUsers(): List<User> {
        val allUsers = networkHandleService.apiCall { zulipApi.getAllUsers() }
        val presenceOfAllUsers = getPresenceOfAllUsers()
        return userMapper.toUserList(allUsers, presenceOfAllUsers)
    }

    override suspend fun getOwnUsers(): User {
        val userData = networkHandleService.apiCall { zulipApi.getOwnUser() }
        val userPresence = getUserPresenceById(userData.id)
        return userMapper.toUser(userData, userPresence)
    }

    override suspend fun getUserPresenceById(id: Int): Presence {
        return presenceMapper.toUserPresence(networkHandleService.apiCall {
            zulipApi.getUserPresenceById(id)
        }.presence)
    }

    override suspend fun getPresenceOfAllUsers(): Presences {
        return presenceMapper.toPresences(
            networkHandleService.apiCall { zulipApi.getPresenceAllUsers() }
        )
    }
}
