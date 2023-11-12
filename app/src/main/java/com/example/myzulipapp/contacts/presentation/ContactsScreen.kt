package com.example.myzulipapp.contacts.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import com.example.myzulipapp.contacts.presentation.preview_data.ContactsStatePreviewParameterProvider
import com.example.myzulipapp.contacts.presentation.state.ContactsEvents
import com.example.myzulipapp.contacts.presentation.state.ContactsState
import com.example.myzulipapp.contacts.presentation.ui_model.ContactUIModel
import com.example.myzulipapp.ui.components.ContactItem
import com.example.myzulipapp.ui.components.ContactItemShimmer
import com.example.myzulipapp.ui.components.MainSearchAppBar
import com.example.myzulipapp.ui.components.basic.AppTopAppBarIconItem
import com.example.myzulipapp.ui.theme.MyZulipAppTheme
import com.example.myzulipapp.utils.ImmutableList

@Composable
fun ContactsRoute(
    viewModel: ContactsViewModel,
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ContactsScreen(
        contactsState = state,
        popBackStack = popBackStack,
        obtainEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}

@Composable
fun ContactsScreen(
    contactsState: ContactsState,
    popBackStack: () -> Unit,
    obtainEvent: (ContactsEvents.Ui) -> Unit,
    modifier: Modifier = Modifier
) {

    val navigationIcon = AppTopAppBarIconItem(
        iconId = R.drawable.baseline_arrow_back_24,
    ) {
        popBackStack.invoke()
    }

    val actions = listOf(
        AppTopAppBarIconItem(
            iconId = R.drawable.baseline_search_24
        ) {
            obtainEvent(ContactsEvents.Ui.OpenSearch)
        }
    )

    val leadingIcon = AppTopAppBarIconItem(
        iconId = R.drawable.baseline_arrow_back_24,
    ) {
        obtainEvent(ContactsEvents.Ui.ClearSearchResult(false))
    }

    val trailingIcon = AppTopAppBarIconItem(
        iconId = R.drawable.baseline_close_24,
    ) {
        obtainEvent(ContactsEvents.Ui.ClearSearchResult(true))
    }

    Scaffold(
        topBar = {
            MainSearchAppBar(
                searchTextState = contactsState.querySearch,
                onTextChange = { query ->
                    obtainEvent(
                        ContactsEvents.Ui.SearchContact(
                            query
                        )
                    )
                },
                isSearchAppBarOpen = contactsState.isSearchOpen,
                title = stringResource(id = R.string.app_name),
                navigationIcon = navigationIcon,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                actions = actions
            )
        },
        drawerBackgroundColor = MyZulipAppTheme.colors.drawerColor,
        backgroundColor = MyZulipAppTheme.colors.backgroundColor,
        content = { padding ->
            ContactsShimmer(
                isLoading = contactsState.isLoading
            ) {
                if (contactsState.error == null) {
                    Contacts(
                        contacts = ImmutableList(contactsState.contacts?.list ?: emptyList()),
                        modifier = modifier.padding(padding)
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(contactsState.error)
                    }
                }
            }
        }
    )
}

@Composable
fun Contacts(contacts: ImmutableList<ContactUIModel>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(contacts.list) { contact ->
            ContactItem(uiModel = contact)
        }
    }
}

@Composable
fun ContactsShimmer(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
) {
    if (isLoading) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.indent_16dp))
        ) {
            ContactItemShimmer()
            ContactItemShimmer()
            ContactItemShimmer()
            ContactItemShimmer()
            ContactItemShimmer()
            ContactItemShimmer()
        }
    } else {
        contentAfterLoading()
    }
}

@Preview
@Composable
fun ContactsScreenPreview(
    @PreviewParameter(ContactsStatePreviewParameterProvider::class)
    contactsState: ContactsState,
) {
    MyZulipAppTheme {
        ContactsScreen(
            contactsState = contactsState,
            popBackStack = {},
            obtainEvent = {}
        )
    }
}