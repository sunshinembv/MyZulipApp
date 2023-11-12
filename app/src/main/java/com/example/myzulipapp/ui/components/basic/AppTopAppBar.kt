package com.example.myzulipapp.ui.components.basic

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.myzulipapp.R
import com.example.myzulipapp.ui.theme.MyZulipAppTheme

@Composable
fun AppTopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    navigationIcon: AppTopAppBarIconItem? = null,
    actions: List<AppTopAppBarIconItem>? = null,
    backgroundColor: Color = MyZulipAppTheme.colors.appBarColor,
    contentColor: Color = MyZulipAppTheme.colors.contentColor,
    elevation: Dp = dimensionResource(id = R.dimen.indent_12dp)
) {
    TopAppBar(
        title = {
            title?.let {
                Text(
                    text = title,
                    style = MyZulipAppTheme.typography.toolbar
                )
            }
        },
        modifier = modifier.fillMaxWidth(),
        navigationIcon = {
            navigationIcon?.let {
                IconButton(onClick = navigationIcon.onClick) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = navigationIcon.iconId),
                        contentDescription = navigationIcon.contentDescription
                    )
                }
            }
        },
        actions = {
            actions?.map { action ->
                IconButton(onClick = action.onClick) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = action.iconId),
                        contentDescription = action.contentDescription
                    )
                }
            }
        },
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation
    )
}

data class AppTopAppBarIconItem(
    @DrawableRes val iconId: Int,
    val contentDescription: String? = null,
    val onClick: () -> Unit
)

@Preview
@Composable
fun AppTopAppBarPreview() {
    MyZulipAppTheme {
        AppTopAppBar(
            title = "TopAppBar",
            navigationIcon = AppTopAppBarIconItem(
                iconId = R.drawable.baseline_menu_24,
            ) {

            },
            actions = listOf(
                AppTopAppBarIconItem(
                    iconId = R.drawable.baseline_search_24
                ) {

                }
            )
        )
    }
}
