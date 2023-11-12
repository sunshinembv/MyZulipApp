package com.example.myzulipapp.channels.presentation.channels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myzulipapp.R
import com.example.myzulipapp.channels.presentation.channels.preview_data.ChannelsStatePreviewParameterProvider
import com.example.myzulipapp.channels.presentation.channels.state.ChannelsEvents
import com.example.myzulipapp.channels.presentation.channels.state.ChannelsState
import com.example.myzulipapp.channels.presentation.channels.ui_model.StreamUiModel
import com.example.myzulipapp.ui.components.ChannelItem
import com.example.myzulipapp.ui.components.ChannelItemShimmer
import com.example.myzulipapp.ui.components.CreateNewStreamBottomSheet
import com.example.myzulipapp.ui.components.MainSearchAppBar
import com.example.myzulipapp.ui.components.MenuDrawer
import com.example.myzulipapp.ui.components.basic.AppFab
import com.example.myzulipapp.ui.components.basic.AppTopAppBarIconItem
import com.example.myzulipapp.ui.theme.MyZulipAppTheme
import com.example.myzulipapp.utils.ImmutableList
import kotlinx.coroutines.launch

@Composable
fun ChannelsRoute(
    viewModel: ChannelsViewModel,
    onChannelClick: (streamId: Int) -> Unit,
    onContactsClick: () -> Unit,
    onThemeChanged: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ChannelsScreen(
        channelsState = state,
        onChannelClick = onChannelClick,
        onContactsClick = onContactsClick,
        onThemeChanged = onThemeChanged,
        obtainEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChannelsScreen(
    channelsState: ChannelsState,
    onChannelClick: (streamId: Int) -> Unit,
    onContactsClick: () -> Unit,
    onThemeChanged: () -> Unit,
    obtainEvent: (ChannelsEvents.Ui) -> Unit,
    modifier: Modifier = Modifier
) {

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val navigationIcon = AppTopAppBarIconItem(
        iconId = R.drawable.baseline_menu_24,
    ) {
        coroutineScope.launch {
            scaffoldState.drawerState.open()
        }
    }

    val leadingIcon = AppTopAppBarIconItem(
        iconId = R.drawable.baseline_arrow_back_24,
    ) {
        obtainEvent(ChannelsEvents.Ui.ClearSearchResult(false))
    }

    val trailingIcon = AppTopAppBarIconItem(
        iconId = R.drawable.baseline_close_24,
    ) {
        obtainEvent(ChannelsEvents.Ui.ClearSearchResult(true))
    }

    val actions = listOf(
        AppTopAppBarIconItem(
            iconId = R.drawable.baseline_search_24
        ) {
            obtainEvent(ChannelsEvents.Ui.OpenSearch)
        }
    )

    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden,
            confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
        )

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            if (modalBottomSheetState.isVisible) {
                CreateNewStreamBottomSheet(
                    name = channelsState.createStreamName,
                    description = channelsState.createStreamDescription,
                    onNameChange = { name ->
                        obtainEvent(
                            ChannelsEvents.Ui.CreateStreamName(
                                name
                            )
                        )
                    },
                    onDescriptionChange = { description ->
                        obtainEvent(
                            ChannelsEvents.Ui.CreateStreamDescription(
                                description
                            )
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            obtainEvent(
                                ChannelsEvents.Ui.CreateStream(
                                    streamName = channelsState.createStreamName,
                                    streamDescription = channelsState.createStreamDescription.ifEmpty { null }
                                )
                            )
                            modalBottomSheetState.hide()
                        }
                    }
                )
            }
        },
        modifier = Modifier.imePadding(),
        sheetShape = MyZulipAppTheme.shapes.topCornersStyle,
        sheetElevation = dimensionResource(id = R.dimen.indent_12dp),
        sheetBackgroundColor = MyZulipAppTheme.colors.backgroundColor,
        content = {
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    MainSearchAppBar(
                        searchTextState = channelsState.querySearch,
                        onTextChange = { query ->
                            obtainEvent(
                                ChannelsEvents.Ui.SearchSubscribedStream(
                                    query
                                )
                            )
                        },
                        isSearchAppBarOpen = channelsState.isSearchOpen,
                        title = stringResource(id = R.string.app_name),
                        navigationIcon = navigationIcon,
                        leadingIcon = leadingIcon,
                        trailingIcon = trailingIcon,
                        actions = actions
                    )
                },
                drawerContent = {
                    MenuDrawer(
                        onContactsClick = onContactsClick,
                        onThemeChanged = onThemeChanged
                    )
                },
                drawerBackgroundColor = MyZulipAppTheme.colors.drawerColor,
                floatingActionButton = {
                    AppFab(
                        onClick = {
                            coroutineScope.launch {
                                modalBottomSheetState.show()
                            }
                        },
                        iconId = R.drawable.baseline_create_24
                    )
                },
                backgroundColor = MyZulipAppTheme.colors.backgroundColor,
                content = { padding ->
                    ChannelListShimmer(
                        isLoading = channelsState.isLoading
                    ) {
                        if (channelsState.error == null) {
                            ChannelList(
                                channels = ImmutableList(
                                    channelsState.subscribedStreams?.list ?: emptyList()
                                ),
                                onChannelClick = onChannelClick,
                                obtainEvent = obtainEvent,
                                modifier = modifier.padding(padding),
                            )
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(channelsState.error)
                            }
                        }
                    }
                }
            )
        }
    )
}

@Composable
fun ChannelList(
    channels: ImmutableList<StreamUiModel>,
    onChannelClick: (streamId: Int) -> Unit,
    obtainEvent: (ChannelsEvents.Ui) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.indent_16dp))
    ) {
        items(channels.list) { channel ->
            ChannelItem(
                onChannelClick = onChannelClick,
                obtainEvent = obtainEvent,
                uiModel = channel,
            )
        }
        item {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.indent_40dp)))
        }
    }
}

@Composable
fun ChannelListShimmer(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
) {
    if (isLoading) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.indent_16dp))
        ) {
            ChannelItemShimmer()
            ChannelItemShimmer()
            ChannelItemShimmer()
            ChannelItemShimmer()
            ChannelItemShimmer()
            ChannelItemShimmer()
            ChannelItemShimmer()
            ChannelItemShimmer()
            ChannelItemShimmer()
            ChannelItemShimmer()
        }
    } else {
        contentAfterLoading()
    }
}

@Preview
@Composable
fun ChannelsScreenPreview(
    @PreviewParameter(ChannelsStatePreviewParameterProvider::class)
    channelsState: ChannelsState,
) {
    MyZulipAppTheme {
        ChannelsScreen(
            channelsState = channelsState,
            onChannelClick = {},
            onContactsClick = {},
            onThemeChanged = {},
            obtainEvent = {}
        )
    }
}
