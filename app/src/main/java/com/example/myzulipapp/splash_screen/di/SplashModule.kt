package com.example.myzulipapp.splash_screen.di

import com.example.myzulipapp.splash_screen.presentation.SplashViewModel
import com.example.myzulipapp.splash_screen.presentation.state.SplashActor
import com.example.myzulipapp.splash_screen.presentation.state.SplashReducer
import com.example.myzulipapp.splash_screen.presentation.state.SplashState
import dagger.Module
import dagger.Provides

@Module
class SplashModule {

    @Provides
    @SplashScope
    fun provideViewModel(reducer: SplashReducer, actor: SplashActor): SplashViewModel {
        return SplashViewModel.Factory(reducer, actor).create(SplashViewModel::class.java)
    }

    @Provides
    @SplashScope
    fun provideSplashState(): SplashState {
        return SplashState()
    }
}
