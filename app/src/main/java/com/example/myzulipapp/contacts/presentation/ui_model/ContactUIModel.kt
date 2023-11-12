package com.example.myzulipapp.contacts.presentation.ui_model

import androidx.annotation.StringRes

data class ContactUIModel(
    val id: Int,
    val contactName: String,
    val contactEmail: String,
    @StringRes val contactStatus: Int,
    val contactIcon: String?
)
