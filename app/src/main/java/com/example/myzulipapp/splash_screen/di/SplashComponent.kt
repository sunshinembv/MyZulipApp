package com.example.myzulipapp.splash_screen.di

import com.example.myzulipapp.contacts.domain.repository.UserRepository
import com.example.myzulipapp.splash_screen.presentation.SplashViewModel
import dagger.Component

@Component(
    modules = [SplashModule::class], dependencies = [SplashDeps::class]
)
@SplashScope
interface SplashComponent {

    @Component.Factory
    interface Factory {
        fun create(
            splashDeps: SplashDeps
        ): SplashComponent
    }

    fun getViewModel(): SplashViewModel
}

interface SplashDeps {
    fun userRepository(): UserRepository
}
