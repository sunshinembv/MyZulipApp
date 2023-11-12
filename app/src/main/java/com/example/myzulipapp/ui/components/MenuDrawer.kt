package com.example.myzulipapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.myzulipapp.R
import com.example.myzulipapp.core.OwnUser
import com.example.myzulipapp.ui.components.basic.AppActionListItem
import com.example.myzulipapp.ui.theme.MyZulipAppTheme

@Composable
fun MenuDrawer(
    onContactsClick: () -> Unit,
    onThemeChanged: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MyZulipAppTheme.colors.drawerTitleColor
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.indent_16dp))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AsyncImage(
                        model = OwnUser.ownUser?.avatar,
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.profile_icon_size))
                            .clip(CircleShape)

                    )
                    IconButton(
                        onClick = { onThemeChanged.invoke() },
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                id = MyZulipAppTheme.images.iconId
                            ),
                            contentDescription = "Mode Icon",
                            tint = MyZulipAppTheme.colors.contentColor
                        )
                    }
                }
                Column {
                    Text(
                        text = OwnUser.ownUser?.name ?: "",
                        color = MyZulipAppTheme.colors.contentColor,
                        style = MyZulipAppTheme.typography.body
                    )
                    Text(
                        text = OwnUser.ownUser?.email ?: "",
                        color = MyZulipAppTheme.colors.contentColor,
                        style = MyZulipAppTheme.typography.caption
                    )
                }
            }
        }
        AppActionListItem(
            iconId = R.drawable.outline_person_24,
            title = stringResource(id = R.string.contacts),
            onNavigateClick = onContactsClick
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun MenuDrawerPreview() {
    MyZulipAppTheme {
        MenuDrawer(
            onContactsClick = {},
            onThemeChanged = {}
        )
    }
}
