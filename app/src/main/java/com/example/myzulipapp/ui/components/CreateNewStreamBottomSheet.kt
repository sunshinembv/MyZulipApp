package com.example.myzulipapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myzulipapp.R
import com.example.myzulipapp.ui.components.basic.AppButton
import com.example.myzulipapp.ui.components.basic.AppOutlinedTextField
import com.example.myzulipapp.ui.theme.MyZulipAppTheme
import kotlinx.coroutines.job

@Composable
fun CreateNewStreamBottomSheet(
    name: String,
    description: String,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        this.coroutineContext.job.invokeOnCompletion {
            focusRequester.requestFocus()
        }
    }

    Column(
        modifier = modifier
            .imePadding()
            .padding(
                horizontal = dimensionResource(id = R.dimen.indent_16dp),
                vertical = dimensionResource(id = R.dimen.indent_8dp)
            )
    ) {
        Text(
            text = stringResource(id = R.string.new_channel),
            color = MyZulipAppTheme.colors.primaryTextColor,
            style = MyZulipAppTheme.typography.heading2
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.indent_8dp)))
        AppOutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            modifier = Modifier.focusRequester(focusRequester),
            label = stringResource(id = R.string.name_required)
        )
        AppOutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = stringResource(id = R.string.description_not_required)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.indent_16dp)))
        AppButton(
            label = stringResource(id = R.string.create_stream),
            onClick = onClick
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun CreateNewStreamBottomSheetPreview() {
    MyZulipAppTheme {
        CreateNewStreamBottomSheet(
            name = "",
            description = "",
            onNameChange = {},
            onDescriptionChange = {},
            onClick = {}
        )
    }
}
