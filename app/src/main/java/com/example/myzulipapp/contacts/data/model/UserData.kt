package com.example.myzulipapp.contacts.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserData(
    @Json(name = "user_id")
    val id: Int,
    @Json(name = "full_name")
    val name: String,
    @Json(name = "avatar_url")
    val avatar: String?,
    val email: String,
    @Json(name = "is_active")
    val isActive: Boolean,
    @Json(name = "is_bot")
    val isBot: Boolean
)
