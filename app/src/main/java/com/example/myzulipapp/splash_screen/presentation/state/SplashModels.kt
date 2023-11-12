package com.example.myzulipapp.splash_screen.presentation.state

import com.example.myzulipapp.core.Command
import com.example.myzulipapp.core.Event
import com.example.myzulipapp.core.State

data class SplashState(
    val isOwnUserLoaded: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmptyState: Boolean = true,
) : State

sealed class SplashEvents : Event {
    sealed class Ui : SplashEvents() {
        data object GetOwnUser : Ui()
        data class OpenChannelsScreen(
            val callback: () -> Unit
        ) : Ui()
    }

    sealed class Internal : SplashEvents() {
        data object OwnUserLoaded : Internal()
        data class ErrorLoading(val error: Throwable) : Internal()
    }
}

sealed class SplashCommand : Command {
    data object GetOwnUser : SplashCommand()
}
