package com.example.myzulipapp.navigation

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.myzulipapp.navigation.destination.SPLASH_SCREEN_ROUTE
import com.example.myzulipapp.navigation.destination.channelsScreen
import com.example.myzulipapp.navigation.destination.chatScreen
import com.example.myzulipapp.navigation.destination.contactsScreen
import com.example.myzulipapp.navigation.destination.navigateToChannelsScreen
import com.example.myzulipapp.navigation.destination.navigateToChatScreen
import com.example.myzulipapp.navigation.destination.navigateToContactsScreen
import com.example.myzulipapp.navigation.destination.navigateToTopicsScreen
import com.example.myzulipapp.navigation.destination.splashScreen
import com.example.myzulipapp.navigation.destination.topicsScreen

@Composable
fun MyZulipAppNavigation(
    navController: NavHostController,
    onThemeChanged: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN_ROUTE,
        modifier = modifier.systemBarsPadding()
    ) {

        splashScreen(
            toChannelsScreen = navController::navigateToChannelsScreen
        )

        channelsScreen(
            onChannelClick = navController::navigateToTopicsScreen,
            onContactsClick = navController::navigateToContactsScreen,
            onThemeChanged = onThemeChanged
        )

        topicsScreen(
            onTopicClick = navController::navigateToChatScreen,
            popBackStack = navController::popBackStack
        )

        chatScreen(
            popBackStack = navController::popBackStack
        )

        contactsScreen(
            popBackStack = navController::popBackStack
        )
    }
}
