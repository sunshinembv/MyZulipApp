package com.example.myzulipapp.channels.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubscribedStreamRootData(val subscriptions: List<StreamData>)
