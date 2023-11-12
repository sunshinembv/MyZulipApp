package com.example.myzulipapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myzulipapp.R
import com.example.myzulipapp.ui.theme.MyZulipAppTheme


@Composable
fun DateItem(date: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                MyZulipAppTheme.colors.dateBackgroundColor.copy(alpha = 0.3f),
                shape = MyZulipAppTheme.shapes.dateCornersStyle
            )
    ) {
        Text(
            text = date,
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.indent_12dp),
                    vertical = dimensionResource(id = R.dimen.indent_4dp)
                ),
            color = MyZulipAppTheme.colors.contentColor,
        )
    }
}

@Preview
@Composable
fun DateItemPreview() {
    MyZulipAppTheme {
        DateItem(date = "30 ноября")
    }
}
