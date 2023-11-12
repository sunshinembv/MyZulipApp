package com.example.myzulipapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myzulipapp.ui.components.basic.AppSearchAppBar
import com.example.myzulipapp.ui.components.basic.AppTopAppBar
import com.example.myzulipapp.ui.components.basic.AppTopAppBarIconItem

@Composable
fun MainSearchAppBar(
    searchTextState: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isSearchAppBarOpen: Boolean = false,
    title: String? = null,
    navigationIcon: AppTopAppBarIconItem? = null,
    leadingIcon: AppTopAppBarIconItem? = null,
    trailingIcon: AppTopAppBarIconItem? = null,
    actions: List<AppTopAppBarIconItem>? = null,
) {
    if (isSearchAppBarOpen) {
        AppSearchAppBar(
            text = searchTextState,
            onTextChange = onTextChange,
            modifier = modifier,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon
        )
    } else {
        AppTopAppBar(
            modifier = modifier,
            title = title,
            navigationIcon = navigationIcon,
            actions = actions
        )
    }
}
