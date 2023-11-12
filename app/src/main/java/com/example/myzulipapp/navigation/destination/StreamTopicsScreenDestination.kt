package com.example.myzulipapp.navigation.destination

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myzulipapp.channels.di.DaggerChannelsComponent
import com.example.myzulipapp.channels.presentation.topics.StreamTopicsRoute
import com.example.myzulipapp.channels.presentation.topics.StreamTopicsViewModel
import com.example.myzulipapp.utils.appComponent
import com.example.myzulipapp.utils.daggerViewModel

private const val STREAM_ID_KEY = "streamId"
private const val TOPICS_SCREEN_ROUTE = "topics/{$STREAM_ID_KEY}"

fun NavGraphBuilder.topicsScreen(
    onTopicClick: (ChatScreenArgs) -> Unit,
    popBackStack: () -> Unit
) {

    composable(
        route = TOPICS_SCREEN_ROUTE,
        arguments = listOf(
            navArgument(STREAM_ID_KEY) { type = NavType.IntType }
        )
    ) { backStackEntry ->

        val streamId =
            backStackEntry.arguments?.getInt(STREAM_ID_KEY) ?: throw IllegalStateException()

        val component = DaggerChannelsComponent.factory().create(LocalContext.current.appComponent)
        val viewModel: StreamTopicsViewModel = daggerViewModel {
            component.getStreamTopicsViewModelAssistedFactory().create(streamId)
                .create(StreamTopicsViewModel::class.java)
        }

        StreamTopicsRoute(viewModel, onTopicClick, popBackStack)
    }
}

fun NavController.navigateToTopicsScreen(streamId: Int) {
    navigate("topics/$streamId")
}
