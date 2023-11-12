package com.example.myzulipapp.contacts.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserListRootData(
    @Json(name = "members")
    val users: List<UserData>
)
