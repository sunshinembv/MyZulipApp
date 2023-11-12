package com.example.myzulipapp.contacts.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserRootData(
    val user: UserData
)
