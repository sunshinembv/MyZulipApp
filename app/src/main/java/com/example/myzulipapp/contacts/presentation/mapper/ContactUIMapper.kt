package com.example.myzulipapp.contacts.presentation.mapper

import com.example.myzulipapp.contacts.domain.model.User
import com.example.myzulipapp.contacts.presentation.state.UserStatus
import com.example.myzulipapp.contacts.presentation.ui_model.ContactUIModel
import com.example.myzulipapp.utils.toMinutes
import javax.inject.Inject

class ContactUIMapper @Inject constructor() {

    fun toContactsUIModel(userList: List<User>): List<ContactUIModel> {
        return userList.map {
            toContactUIModel(it)
        }
    }

    private fun toContactUIModel(user: User): ContactUIModel {
        val isUserOffline =
            (System.currentTimeMillis() - user.presence.aggregated.timestamp * 1000).toMinutes >= 2
        return ContactUIModel(
            user.id,
            user.name,
            user.email,
            if (isUserOffline) UserStatus.OFFLINE.status else UserStatus.valueOf(user.presence.aggregated.status.uppercase()).status,
            user.avatar
        )
    }
}
