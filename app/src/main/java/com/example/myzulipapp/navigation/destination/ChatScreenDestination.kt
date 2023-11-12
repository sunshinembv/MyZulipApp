package com.example.myzulipapp.navigation.destination

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myzulipapp.chat.di.DaggerChatComponent
import com.example.myzulipapp.chat.presentation.ChatRoute
import com.example.myzulipapp.chat.presentation.ChatViewModel
import com.example.myzulipapp.utils.appComponent
import com.example.myzulipapp.utils.daggerViewModel

private const val STREAM_ID_KEY = "streamId"
private const val TOPIC_NAME_KEY = "topicName"
private const val CHAT_SCREEN_ROUTE = "chat/{$STREAM_ID_KEY}/{$TOPIC_NAME_KEY}"

fun NavGraphBuilder.chatScreen(popBackStack: () -> Unit) {

    composable(
        route = CHAT_SCREEN_ROUTE,
        arguments = listOf(
            navArgument(STREAM_ID_KEY) { type = NavType.IntType },
            navArgument(TOPIC_NAME_KEY) { type = NavType.StringType }
        )
    ) { backStackEntry ->

        val streamId =
            backStackEntry.arguments?.getInt(STREAM_ID_KEY) ?: throw IllegalStateException()
        val topicName =
            backStackEntry.arguments?.getString(TOPIC_NAME_KEY) ?: throw IllegalStateException()

        val component = DaggerChatComponent.factory().create(LocalContext.current.appComponent)
        val viewModel: ChatViewModel = daggerViewModel {
            component.getChatViewModelAssistedFactory().create(streamId, topicName)
                .create(ChatViewModel::class.java)
        }

        ChatRoute(
            streamId = streamId,
            topicName = topicName,
            viewModel = viewModel,
            popBackStack = popBackStack
        )
    }
}

fun NavController.navigateToChatScreen(args: ChatScreenArgs) {
    navigate("chat/${args.streamId}/${args.topicName}")
}

data class ChatScreenArgs(val streamId: Int, val topicName: String)
