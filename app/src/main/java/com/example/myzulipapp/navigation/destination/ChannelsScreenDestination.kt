package com.example.myzulipapp.navigation.destination

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.myzulipapp.channels.di.DaggerChannelsComponent
import com.example.myzulipapp.channels.presentation.channels.ChannelsRoute
import com.example.myzulipapp.channels.presentation.channels.ChannelsViewModel
import com.example.myzulipapp.utils.appComponent
import com.example.myzulipapp.utils.daggerViewModel

private const val CHANNELS_SCREEN_ROUTE = "channels"

fun NavGraphBuilder.channelsScreen(
    onChannelClick: (streamId: Int) -> Unit,
    onContactsClick: () -> Unit,
    onThemeChanged: () -> Unit
) {

    composable(CHANNELS_SCREEN_ROUTE) {

        val component = DaggerChannelsComponent.factory().create(LocalContext.current.appComponent)

        val viewModel: ChannelsViewModel = daggerViewModel {
            component.getChannelsViewModel()
        }

        ChannelsRoute(
            viewModel = viewModel,
            onChannelClick = onChannelClick,
            onContactsClick = onContactsClick,
            onThemeChanged = onThemeChanged
        )
    }
}

fun NavController.navigateToChannelsScreen() {
    navigate(CHANNELS_SCREEN_ROUTE) {
        popUpTo(SPLASH_SCREEN_ROUTE) {
            inclusive = true
        }
    }
}
