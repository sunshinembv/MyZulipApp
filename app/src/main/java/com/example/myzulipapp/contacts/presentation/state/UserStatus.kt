package com.example.myzulipapp.contacts.presentation.state

import androidx.annotation.StringRes
import com.example.myzulipapp.R

enum class UserStatus(@StringRes val status: Int) {
    ACTIVE(R.string.online),
    IDLE(R.string.idle),
    OFFLINE(R.string.offline),
}
