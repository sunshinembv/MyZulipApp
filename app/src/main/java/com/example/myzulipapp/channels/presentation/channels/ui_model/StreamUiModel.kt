package com.example.myzulipapp.channels.presentation.channels.ui_model

import androidx.compose.ui.graphics.Color

data class StreamUiModel(
    val id: Int,
    val title: String,
    val description: String,
    val inviteOnly: Boolean,
    val color: Color,
    val dateCreated: String
)
