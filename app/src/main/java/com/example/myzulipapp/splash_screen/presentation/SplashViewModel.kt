package com.example.myzulipapp.splash_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myzulipapp.core.BaseViewModel
import com.example.myzulipapp.splash_screen.presentation.state.SplashActor
import com.example.myzulipapp.splash_screen.presentation.state.SplashEvents
import com.example.myzulipapp.splash_screen.presentation.state.SplashReducer
import com.example.myzulipapp.splash_screen.presentation.state.SplashState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val reducer: SplashReducer,
    private val actor: SplashActor,
) : BaseViewModel<SplashEvents, SplashState>() {

    override val state: StateFlow<SplashState>
        get() = reducer.state

    init {
        obtainEvent(SplashEvents.Ui.GetOwnUser)
    }

    fun obtainEvent(event: SplashEvents.Ui) {
        when (event) {
            SplashEvents.Ui.GetOwnUser -> {
                loadOwnUser(event)
            }

            is SplashEvents.Ui.OpenChannelsScreen -> {
                openChannelsScreen(event)
            }
        }
    }

    private fun loadOwnUser(event: SplashEvents) {
        viewModelScope.launch {
            val result = reducer.sendEvent(event)
            result.command?.let { actor.execute(it, ::loadOwnUser) }
        }
    }

    private fun openChannelsScreen(event: SplashEvents.Ui.OpenChannelsScreen) {
        viewModelScope.launch {
            reducer.sendEvent(event)
            event.callback.invoke()
        }
    }

    class Factory(
        private val reducer: SplashReducer,
        private val actor: SplashActor,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == SplashViewModel::class.java)
            return SplashViewModel(
                reducer,
                actor
            ) as T
        }
    }
}
