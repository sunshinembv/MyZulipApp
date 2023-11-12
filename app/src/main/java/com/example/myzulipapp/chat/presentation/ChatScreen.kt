package com.example.myzulipapp.chat.presentation

import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myzulipapp.R
import com.example.myzulipapp.chat.presentation.preview_data.ChatStatePreviewParameterProvider
import com.example.myzulipapp.chat.presentation.state.ChatEvents
import com.example.myzulipapp.chat.presentation.state.ChatState
import com.example.myzulipapp.chat.presentation.ui_model.MessageUiModel
import com.example.myzulipapp.ui.components.DateItem
import com.example.myzulipapp.ui.components.EmojiBottomSheet
import com.example.myzulipapp.ui.components.OwnMessage
import com.example.myzulipapp.ui.components.SendMessageTextField
import com.example.myzulipapp.ui.components.UserMessage
import com.example.myzulipapp.ui.components.basic.AppMessageShimmer
import com.example.myzulipapp.ui.components.basic.AppTopAppBar
import com.example.myzulipapp.ui.components.basic.AppTopAppBarIconItem
import com.example.myzulipapp.ui.theme.MyZulipAppTheme
import com.example.myzulipapp.utils.ImmutableList
import com.example.myzulipapp.utils.createSendImageMessage
import kotlinx.coroutines.launch

@Composable
fun ChatRoute(
    streamId: Int,
    topicName: String,
    viewModel: ChatViewModel,
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ChatScreen(
        streamId,
        topicName,
        state,
        popBackStack,
        viewModel::obtainEvent,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChatScreen(
    streamId: Int,
    topicName: String,
    chatState: ChatState,
    popBackStack: () -> Unit,
    obtainEvent: (ChatEvents.Ui) -> Unit,
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val openFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { obtainEvent(ChatEvents.Ui.UploadImage(uri)) }
    }

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = object : DefaultLifecycleObserver {
            override fun onPause(owner: LifecycleOwner) {
                super.onPause(owner)
                chatState.queueId?.let {
                    obtainEvent(ChatEvents.Ui.DeleteEventQueue(chatState.queueId))
                }
            }

            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                if (chatState.isEventQueueRegistering.not()) {
                    obtainEvent(ChatEvents.Ui.RegisterEventQueue)
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    val navigationIcon = AppTopAppBarIconItem(
        iconId = R.drawable.baseline_arrow_back_24,
    ) {
        popBackStack.invoke()
    }

    val coroutineScope = rememberCoroutineScope()

    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden,
            confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
        )

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            EmojiBottomSheet(
                onTapEmoji = { emojiName, emojiCode ->
                    coroutineScope.launch {
                        obtainEvent(
                            ChatEvents.Ui.TapReaction(
                                streamId = streamId,
                                topicName = topicName,
                                messageId = chatState.clickedMessageId,
                                emojiName = emojiName,
                                emojiCode = emojiCode
                            )
                        )
                        modalBottomSheetState.hide()
                    }
                }
            )
        },
        sheetShape = MyZulipAppTheme.shapes.topCornersStyle,
        sheetElevation = dimensionResource(id = R.dimen.indent_12dp),
        sheetBackgroundColor = MyZulipAppTheme.colors.backgroundColor,
        content = {
            Scaffold(
                topBar = {
                    AppTopAppBar(
                        title = topicName,
                        navigationIcon = navigationIcon
                    )
                },
                bottomBar = {
                    SendMessageTextField(
                        value = chatState.uploadedImageUri?.createSendImageMessage()
                            ?: chatState.typedText.orEmpty(),
                        onTextChange = { text ->
                            obtainEvent(
                                ChatEvents.Ui.Typing(
                                    text
                                )
                            )
                        },
                        onSend = {
                            obtainEvent(
                                ChatEvents.Ui.SendMessage(
                                    streamId = streamId,
                                    to = streamId,
                                    topic = topicName,
                                    content = chatState.uploadedImageUri?.createSendImageMessage()
                                        ?: chatState.typedText.orEmpty()
                                )
                            )
                        },
                        onAttach = {
                            openFileLauncher
                                .launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }
                    )
                },
                backgroundColor = MyZulipAppTheme.colors.chatBackgroundColor,
                content = { padding ->
                    MessageListShimmer(
                        isLoading = chatState.isLoading
                    ) {
                        if (chatState.error == null) {
                            MessageList(
                                streamId = streamId,
                                topicName = topicName,
                                messages = ImmutableList(
                                    chatState.data?.messages?.list?.reversed() ?: emptyList()
                                ),
                                isNewPageLoading = chatState.isNewPageLoading,
                                endReached = chatState.endReached,
                                isNeedScroll = chatState.isNeedScroll,
                                loadNextPage = obtainEvent,
                                openEmojiList = { messageId ->
                                    coroutineScope.launch {
                                        obtainEvent(ChatEvents.Ui.OpenEmojiBottomSheet(messageId))
                                        modalBottomSheetState.show()
                                    }
                                },
                                onTapReaction = { streamId, topicName, messageId, emojiName, emojiCode ->
                                    coroutineScope.launch {
                                        obtainEvent(
                                            ChatEvents.Ui.TapReaction(
                                                streamId = streamId,
                                                topicName = topicName,
                                                messageId = messageId,
                                                emojiName = emojiName,
                                                emojiCode = emojiCode
                                            )
                                        )
                                    }
                                },
                                modifier = modifier.padding(padding)
                            )
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(chatState.error)
                            }
                        }
                    }
                }
            )
        }
    )
}

@Composable
fun MessageList(
    streamId: Int,
    topicName: String,
    messages: ImmutableList<MessageUiModel>,
    isNewPageLoading: Boolean,
    endReached: Boolean,
    isNeedScroll: Boolean,
    loadNextPage: (ChatEvents.Ui) -> Unit,
    openEmojiList: (messageId: Int) -> Unit,
    onTapReaction: (
        streamId: Int,
        topicName: String,
        messageId: Int,
        emojiName: String,
        emojiCode: String
    ) -> Unit,
    modifier: Modifier = Modifier
) {

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.indent_16dp)),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.indent_16dp)),
        reverseLayout = true
    ) {
        if (isNeedScroll) {
            coroutineScope.launch {
                listState.animateScrollToItem(0)
            }
        }
        items(messages.list.size) { index ->
            val message = messages.list[index]
            if (index >= messages.list.size - 1 && endReached.not() && isNewPageLoading.not()) {
                loadNextPage(
                    ChatEvents.Ui.GetNextPageMessages(
                        streamId = streamId,
                        topicName = topicName
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (message.isOwnMessage)
                    Arrangement.End else
                    Arrangement.Start
            ) {
                if (message.isOwnMessage) {
                    OwnMessage(
                        messageId = message.id,
                        text = message.message,
                        time = message.time,
                        isOwnReaction = false,
                        reactions = ImmutableList(message.reactions),
                        openEmojiList = openEmojiList,
                        onTapReaction = onTapReaction,
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.own_message_indent)),
                    )
                } else {
                    UserMessage(
                        messageId = message.id,
                        userName = message.userName,
                        text = message.message,
                        time = message.time,
                        icon = message.userIcon,
                        isOwnReaction = true,
                        reactions = ImmutableList(message.reactions),
                        openEmojiList = openEmojiList,
                        onTapReaction = onTapReaction,
                        modifier = Modifier.padding(end = dimensionResource(id = R.dimen.user_message_indent)),
                    )
                }
            }
            if (message.isShowDate) {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.indent_8dp)))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DateItem(date = message.day)
                }
            }
        }
    }
}

@Composable
fun MessageListShimmer(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
) {
    if (isLoading) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.indent_16dp)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.indent_16dp)),
        ) {
            Row(horizontalArrangement = Arrangement.Start) {
                AppMessageShimmer(
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.user_message_indent))
                )
            }
            Row(horizontalArrangement = Arrangement.Start) {
                AppMessageShimmer(
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.user_message_indent))
                )
            }
            Row(horizontalArrangement = Arrangement.Start) {
                AppMessageShimmer(
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.user_message_indent))
                )
            }
            Row(horizontalArrangement = Arrangement.Start) {
                AppMessageShimmer(
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.user_message_indent))
                )
            }
        }
    } else {
        contentAfterLoading()
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ChatScreenPreview(
    @PreviewParameter(ChatStatePreviewParameterProvider::class)
    chatState: ChatState,
) {
    MyZulipAppTheme {
        ChatScreen(
            streamId = 0,
            topicName = "TopicName",
            chatState = chatState,
            popBackStack = {},
            obtainEvent = {}
        )
    }
}
