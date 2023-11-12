package com.example.myzulipapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.myzulipapp.R
import com.example.myzulipapp.channels.presentation.topics.state.StreamTopicsEvents
import com.example.myzulipapp.channels.presentation.topics.ui_model.StreamTopicUiModel
import com.example.myzulipapp.navigation.destination.ChatScreenArgs
import com.example.myzulipapp.ui.components.basic.ComponentRectangleLineLong
import com.example.myzulipapp.ui.components.basic.ComponentRectangleLineShort
import com.example.myzulipapp.ui.theme.MyZulipAppTheme

@Composable
fun TopicItem(
    uiModel: StreamTopicUiModel,
    onTopicClick: (ChatScreenArgs) -> Unit,
    obtainEvent: (StreamTopicsEvents.Ui) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(
                vertical = dimensionResource(id = R.dimen.indent_4dp),
                horizontal = dimensionResource(id = R.dimen.indent_16dp)
            )
            .clickable {
                obtainEvent(
                    StreamTopicsEvents.Ui.OpenTopicChat(
                        streamId = uiModel.streamId,
                        topicName = uiModel.topicName,
                        callback = onTopicClick
                    )
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = uiModel.topicName,
                    color = MyZulipAppTheme.colors.primaryTextColor,
                    style = MyZulipAppTheme.typography.body
                )
                Text(
                    text = uiModel.lastMessageDate,
                    color = MyZulipAppTheme.colors.secondaryTextColor,
                    style = MyZulipAppTheme.typography.body
                )
            }
            if (uiModel.lastMessage.isNotEmpty()) {
                Text(
                    text = uiModel.lastMessage,
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
fun TopicItemShimmer(
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
fun TopicItemPreview() {
    MyZulipAppTheme {
        TopicItem(
            uiModel = StreamTopicUiModel(
                streamId = 1,
                topicName = "Topic",
                lastMessage = "Message",
                lastMessageDate = "10.10.2020"
            ),
            onTopicClick = { _ -> },
            obtainEvent = {}
        )
    }
}
