package com.example.myzulipapp.splash_screen.presentation.state

import com.example.myzulipapp.core.Reducer
import com.example.myzulipapp.core.Result
import javax.inject.Inject

class SplashReducer @Inject constructor(
    state: SplashState
) : Reducer<SplashEvents, SplashState, SplashCommand>(state) {

    override fun reduce(event: SplashEvents, state: SplashState): Result<SplashCommand> {
        return when (event) {
            is SplashEvents.Internal.OwnUserLoaded -> {
                setState(
                    state.copy(
                        isOwnUserLoaded = true,
                        isLoading = false
                    )
                )
                Result(null)
            }

            SplashEvents.Ui.GetOwnUser -> {
                setState(
                    state.copy(
                        isLoading = true,
                        error = null,
                        isEmptyState = false
                    )
                )
                val command = SplashCommand.GetOwnUser
                Result(command)
            }

            is SplashEvents.Internal.ErrorLoading -> {
                setState(
                    state.copy(
                        error = event.error.message,
                        isLoading = false
                    )
                )
                Result(null)
            }

            is SplashEvents.Ui.OpenChannelsScreen -> {
                Result(null)
            }
        }
    }
}
