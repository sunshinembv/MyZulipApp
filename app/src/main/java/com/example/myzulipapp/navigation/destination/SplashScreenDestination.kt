package com.example.myzulipapp.navigation.destination

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.myzulipapp.splash_screen.di.DaggerSplashComponent
import com.example.myzulipapp.splash_screen.presentation.SplashRoute
import com.example.myzulipapp.splash_screen.presentation.SplashViewModel
import com.example.myzulipapp.utils.appComponent
import com.example.myzulipapp.utils.daggerViewModel

const val SPLASH_SCREEN_ROUTE = "splash"

fun NavGraphBuilder.splashScreen(
    toChannelsScreen: () -> Unit
) {

    composable(SPLASH_SCREEN_ROUTE) {

        val component = DaggerSplashComponent.factory().create(LocalContext.current.appComponent)

        val viewModel: SplashViewModel = daggerViewModel {
            component.getViewModel()
        }

        SplashRoute(viewModel = viewModel, toChannelsScreen = toChannelsScreen)
    }
}
