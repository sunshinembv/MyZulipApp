package com.example.myzulipapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myzulipapp.R
import com.example.myzulipapp.ui.theme.MyZulipAppTheme

@Composable
fun SendMessageTextField(
    value: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit,
    onAttach: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onTextChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = stringResource(id = R.string.message),
                style = MyZulipAppTheme.typography.body
            )
        },
        trailingIcon = {
            Row {
                IconButton(onClick = onAttach) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_attach_file_24),
                        contentDescription = "Attach file",
                        tint = MyZulipAppTheme.colors.iconTintColor
                    )
                }
                IconButton(
                    onClick = onSend,
                    enabled = value.isNotEmpty()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_send_24),
                        contentDescription = "Send icon",
                        tint = if (value.isNotEmpty()) {
                            MyZulipAppTheme.colors.buttonColor
                        } else {
                            MyZulipAppTheme.colors.iconTintColor
                        }
                    )
                }
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = MyZulipAppTheme.colors.primaryTextColor,
            backgroundColor = MyZulipAppTheme.colors.backgroundColor,
            cursorColor = MyZulipAppTheme.colors.outlinedEditTextColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            placeholderColor = MyZulipAppTheme.colors.hintColor,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Preview(
    showBackground = true
)
@Composable
fun SendMessageTextFieldPreview() {
    MyZulipAppTheme {
        SendMessageTextField(
            value = "",
            onTextChange = {},
            onSend = {},
            onAttach = {}
        )
    }
}
