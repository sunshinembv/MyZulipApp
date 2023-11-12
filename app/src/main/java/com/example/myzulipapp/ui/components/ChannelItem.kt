package com.example.myzulipapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.myzulipapp.R
import com.example.myzulipapp.channels.presentation.channels.state.ChannelsEvents
import com.example.myzulipapp.channels.presentation.channels.ui_model.StreamUiModel
import com.example.myzulipapp.ui.components.basic.ComponentIcon
import com.example.myzulipapp.ui.components.basic.ComponentRectangleLineLong
import com.example.myzulipapp.ui.components.basic.ComponentRectangleLineShort
import com.example.myzulipapp.ui.theme.MyZulipAppTheme

@Composable
fun ChannelItem(
    uiModel: StreamUiModel,
    onChannelClick: (streamId: Int) -> Unit,
    obtainEvent: (ChannelsEvents.Ui) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(
                vertical = dimensionResource(id = R.dimen.indent_4dp),
                horizontal = dimensionResource(id = R.dimen.indent_16dp)
            )
            .fillMaxWidth()
            .clickable {
                obtainEvent(
                    ChannelsEvents.Ui.OpenSubscribedStreamTopics(
                        uiModel.id,
                        onChannelClick
                    )
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = if (uiModel.inviteOnly) R.drawable.outline_lock_24 else R.drawable.baseline_numbers_24),
            contentDescription = "Icon",
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.channel_icon_size))
                .clip(CircleShape),
            tint = uiModel.color
        )
        Column(
            modifier = Modifier
                .padding(start = dimensionResource(id = R.dimen.indent_8dp))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = uiModel.title,
                    color = MyZulipAppTheme.colors.primaryTextColor,
                    style = MyZulipAppTheme.typography.body
                )
                Text(
                    text = uiModel.dateCreated,
                    color = MyZulipAppTheme.colors.secondaryTextColor,
                    style = MyZulipAppTheme.typography.body
                )
            }
            if (uiModel.description.isNotEmpty()) {
                Text(
                    text = uiModel.description,
                    modifier = Modifier.fillMaxWidth(0.5f),
                    color = MyZulipAppTheme.colors.secondaryTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MyZulipAppTheme.typography.body
                )
            }
        }
    }
}

@Composable
fun ChannelItemShimmer(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(
                vertical = dimensionResource(id = R.dimen.indent_4dp),
                horizontal = dimensionResource(id = R.dimen.indent_16dp)
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ComponentIcon()
        Column(
            modifier = Modifier
                .padding(start = dimensionResource(id = R.dimen.indent_8dp))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ComponentRectangleLineLong()
                ComponentRectangleLineShort()
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.indent_8dp)))
            ComponentRectangleLineShort()
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun ChannelItemPreview() {
    MyZulipAppTheme {
        ChannelItem(
            StreamUiModel(
                id = 0,
                title = "Title",
                description = "Description",
                inviteOnly = false,
                color = Color.Cyan,
                dateCreated = "10.10.2020"
            ),
            onChannelClick = {},
            obtainEvent = {}
        )
    }
}
