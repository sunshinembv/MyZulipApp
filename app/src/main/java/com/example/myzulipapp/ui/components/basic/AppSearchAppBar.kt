package com.example.myzulipapp.ui.components.basic

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myzulipapp.R
import com.example.myzulipapp.ui.theme.MyZulipAppTheme
import kotlinx.coroutines.job

@Composable
fun AppSearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: AppTopAppBarIconItem? = null,
    trailingIcon: AppTopAppBarIconItem? = null
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        this.coroutineContext.job.invokeOnCompletion {
            focusRequester.requestFocus()
        }
    }

    TextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        textStyle = MyZulipAppTheme.typography.body,
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                style = MyZulipAppTheme.typography.body
            )
        },
        leadingIcon = {
            leadingIcon?.let {
                IconButton(onClick = leadingIcon.onClick) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = leadingIcon.iconId),
                        contentDescription = leadingIcon.contentDescription,
                    )
                }
            }
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                trailingIcon?.let {
                    IconButton(onClick = trailingIcon.onClick) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = trailingIcon.iconId),
                            contentDescription = trailingIcon.contentDescription
                        )
                    }
                }
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MyZulipAppTheme.colors.contentColor,
            backgroundColor = MyZulipAppTheme.colors.appBarColor,
            cursorColor = MyZulipAppTheme.colors.contentColor,
            focusedBorderColor = MyZulipAppTheme.colors.appBarColor,
            unfocusedBorderColor = MyZulipAppTheme.colors.appBarColor,
            leadingIconColor = MyZulipAppTheme.colors.contentColor,
            trailingIconColor = MyZulipAppTheme.colors.contentColor,
            placeholderColor = MyZulipAppTheme.colors.hintColor
        )
    )
}

@Preview
@Composable
fun AppSearchAppBarPreview() {
    MyZulipAppTheme {
        AppSearchAppBar(
            text = "Search",
            onTextChange = {},
            leadingIcon = AppTopAppBarIconItem(R.drawable.baseline_arrow_back_24, "") {}
        )
    }
}
