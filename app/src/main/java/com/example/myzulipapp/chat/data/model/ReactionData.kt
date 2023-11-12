package com.example.myzulipapp.chat.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReactionData(
    @Json(name = "emoji_name")
    val emojiName: String,
    @Json(name = "emoji_code")
    val emojiCode: String,
    @Json(name = "reaction_type")
    val reactionType: String,
    @Json(name = "user_id")
    val userId: Int
)
