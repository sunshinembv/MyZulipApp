package com.example.myzulipapp.ui.components.basic

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.myzulipapp.ui.theme.MyZulipAppTheme

@Composable
fun AppOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MyZulipAppTheme.typography.body,
    label: String? = null,
    shape: Shape = MyZulipAppTheme.shapes.cornersStyle,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth(),
        textStyle = textStyle,
        label = {
            label?.let { Text(text = label) }
        },
        shape = shape,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MyZulipAppTheme.colors.primaryTextColor,
            backgroundColor = MyZulipAppTheme.colors.backgroundColor,
            cursorColor = MyZulipAppTheme.colors.outlinedEditTextColor,
            focusedBorderColor = MyZulipAppTheme.colors.outlinedEditTextColor,
            focusedLabelColor = MyZulipAppTheme.colors.outlinedEditTextColor,
            unfocusedBorderColor = MyZulipAppTheme.colors.hintColor,
            unfocusedLabelColor = MyZulipAppTheme.colors.hintColor
        )
    )
}

@Preview
@Composable
fun AppOutlinedTextFieldPreview() {
    MyZulipAppTheme {
        AppOutlinedTextField(
            value = "OutlinedTextField",
            onValueChange = {}
        )
    }
}
