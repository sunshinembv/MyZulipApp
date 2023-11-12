package com.example.myzulipapp.ui.components.basic

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myzulipapp.R
import com.example.myzulipapp.ui.theme.MyZulipAppTheme

@Composable
fun AppFab(
    onClick: () -> Unit,
    @DrawableRes iconId: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MyZulipAppTheme.colors.floatingActionButtonColor,
    contentColor: Color = MyZulipAppTheme.colors.contentColor,
    contentDescription: String? = null
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .height(dimensionResource(id = R.dimen.fab_size))
            .width(dimensionResource(id = R.dimen.fab_size)),
        backgroundColor = backgroundColor,
        contentColor = contentColor,
    ) {
        Icon(imageVector = ImageVector.vectorResource(id = iconId), contentDescription)
    }
}

@Preview
@Composable
fun AppFabPreview() {
    MyZulipAppTheme {
        AppFab(
            onClick = {},
            iconId = R.drawable.baseline_create_24
        )
    }
}
