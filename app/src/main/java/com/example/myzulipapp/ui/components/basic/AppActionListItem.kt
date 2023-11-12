package com.example.myzulipapp.ui.components.basic

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myzulipapp.R
import com.example.myzulipapp.ui.theme.MyZulipAppTheme

@Composable
fun AppActionListItem(
    title: String,
    @DrawableRes iconId: Int,
    onNavigateClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    Row(
        modifier
            .fillMaxWidth()
            .clickable { onNavigateClick.invoke() }) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconId),
            modifier = Modifier.padding(dimensionResource(id = R.dimen.indent_16dp)),
            contentDescription = contentDescription,
            tint = MyZulipAppTheme.colors.iconTintColor
        )
        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = dimensionResource(id = R.dimen.indent_8dp)),
            color = MyZulipAppTheme.colors.primaryTextColor,
            style = MyZulipAppTheme.typography.body
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun AppActionListItemPreview() {
    MyZulipAppTheme {
        AppActionListItem(
            title = "Action Item",
            iconId = R.drawable.outline_volume_up_24,
            onNavigateClick = {}
        )
    }
}
