package com.example.myzulipapp.channels.presentation.topics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myzulipapp.R
import com.example.myzulipapp.channels.presentation.topics.preview_data.StreamTopicsStatePreviewParameterProvider
import com.example.myzulipapp.channels.presentation.topics.state.StreamTopicsEvents
import com.example.myzulipapp.channels.presentation.topics.state.StreamTopicsState
import com.example.myzulipapp.channels.presentation.topics.ui_model.StreamTopicUiModel
import com.example.myzulipapp.navigation.destination.ChatScreenArgs
import com.example.myzulipapp.ui.components.MainSearchAppBar
import com.example.myzulipapp.ui.components.TopicItem
import com.example.myzulipapp.ui.components.TopicItemShimmer
import com.example.myzulipapp.ui.components.basic.AppTopAppBarIconItem
import com.example.myzulipapp.ui.theme.MyZulipAppTheme
import com.example.myzulipapp.utils.ImmutableList

@Composable
fun StreamTopicsRoute(
    viewModel: StreamTopicsViewModel,
    onTopicClick: (ChatScreenArgs) -> Unit,
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    StreamTopicsScreen(
        streamTopicsState = state,
        onTopicClick = onTopicClick,
        popBackStack = popBackStack,
        obtainEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}

@Composable
fun StreamTopicsScreen(
    streamTopicsState: StreamTopicsState,
    onTopicClick: (ChatScreenArgs) -> Unit,
    popBackStack: () -> Unit,
    obtainEvent: (StreamTopicsEvents.Ui) -> Unit,
    modifier: Modifier = Modifier
) {

    val scaffoldState = rememberScaffoldState()

    val navigationIcon = AppTopAppBarIconItem(
        iconId = R.drawable.baseline_arrow_back_24,
    ) {
        popBackStack.invoke()
    }

    val leadingIcon = AppTopAppBarIconItem(
        iconId = R.drawable.baseline_arrow_back_24,
    ) {
        obtainEvent(StreamTopicsEvents.Ui.ClearSearchResult(false))
    }

    val trailingIcon = AppTopAppBarIconItem(
        iconId = R.drawable.baseline_close_24,
    ) {
        obtainEvent(StreamTopicsEvents.Ui.ClearSearchResult(true))
    }

    val actions = listOf(
        AppTopAppBarIconItem(
            iconId = R.drawable.baseline_search_24
        ) {
            obtainEvent(StreamTopicsEvents.Ui.OpenSearch)
        }
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainSearchAppBar(
                searchTextState = streamTopicsState.querySearch,
                onTextChange = { query ->
                    obtainEvent(
                        StreamTopicsEvents.Ui.SearchStreamTopics(
                            query
                        )
                    )
                },
                isSearchAppBarOpen = streamTopicsState.isSearchOpen,
                title = stringResource(R.string.topic),
                navigationIcon = navigationIcon,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                actions = actions
            )
        },
        backgroundColor = MyZulipAppTheme.colors.backgroundColor
    ) { padding ->
        TopicsShimmer(
            isLoading = streamTopicsState.isLoading
        ) {
            if (streamTopicsState.error == null) {
                Topics(
                    topics = ImmutableList(streamTopicsState.streamTopics?.list ?: emptyList()),
                    onTopicClick = onTopicClick,
                    obtainEvent = obtainEvent,
                    modifier = modifier.padding(padding)
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(streamTopicsState.error)
                }
            }
        }
    }
}

@Composable
fun Topics(
    topics: ImmutableList<StreamTopicUiModel>,
    onTopicClick: (ChatScreenArgs) -> Unit,
    obtainEvent: (StreamTopicsEvents.Ui) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.indent_16dp)),
    ) {
        items(topics.list) { topic ->
            TopicItem(
                uiModel = topic,
                onTopicClick = onTopicClick,
                obtainEvent = obtainEvent
            )
        }
    }
}

@Composable
fun TopicsShimmer(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit
) {
    if (isLoading) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.indent_16dp))
        ) {
            TopicItemShimmer()
            TopicItemShimmer()
            TopicItemShimmer()
            TopicItemShimmer()
            TopicItemShimmer()
            TopicItemShimmer()
            TopicItemShimmer()
            TopicItemShimmer()
            TopicItemShimmer()
            TopicItemShimmer()
        }
    } else {
        contentAfterLoading()
    }
}

@Preview
@Composable
fun NewMessageScreenPreview(
    @PreviewParameter(StreamTopicsStatePreviewParameterProvider::class)
    streamTopicsState: StreamTopicsState,
) {
    MyZulipAppTheme {
        StreamTopicsScreen(
            streamTopicsState = streamTopicsState,
            onTopicClick = { _ -> },
            popBackStack = {},
            obtainEvent = {}
        )
    }
}
