package com.example.myzulipapp.splash_screen.presentation.state

import com.example.myzulipapp.contacts.domain.usecases.GetOwnUserUseCase
import com.example.myzulipapp.core.Actor
import com.example.myzulipapp.core.OwnUser
import javax.inject.Inject

class SplashActor @Inject constructor(
    private val getOwnUserUseCase: GetOwnUserUseCase
) : Actor<SplashCommand, SplashEvents.Internal> {

    override suspend fun execute(command: SplashCommand, onEvent: (SplashEvents.Internal) -> Unit) {
        try {
            when (command) {
                SplashCommand.GetOwnUser -> {
                    val ownUser = getOwnUserUseCase.execute()
                    OwnUser.ownUser = ownUser
                    onEvent(SplashEvents.Internal.OwnUserLoaded)
                }
            }
        } catch (t: Throwable) {
            onEvent(SplashEvents.Internal.ErrorLoading(t))
        }
    }
}
