package com.example.myzulipapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.myzulipapp.R
import com.example.myzulipapp.chat.domain.model.Reaction
import com.example.myzulipapp.ui.components.basic.AppMessage
import com.example.myzulipapp.ui.theme.MyZulipAppTheme
import com.example.myzulipapp.utils.ImmutableList

@Composable
fun UserMessage(
    messageId: Int,
    text: String,
    time: String,
    icon: String,
    isOwnReaction: Boolean,
    reactions: ImmutableList<Reaction>,
    openEmojiList: (messageId: Int) -> Unit,
    onTapReaction: (
        streamId: Int,
        topicName: String,
        messageId: Int,
        emojiName: String,
        emojiCode: String
    ) -> Unit,
    modifier: Modifier = Modifier,
    userName: String = "",
    color: Color = MyZulipAppTheme.colors.messageBackgroundColor
) {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        AsyncImage(
            model = icon,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.user_icon_size))
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.indent_4dp)))
        AppMessage(
            messageId = messageId,
            userName = userName,
            text = text,
            time = time,
            color = color,
            timeColor = MyZulipAppTheme.colors.userMessageTimeColor,
            isOwnReaction = isOwnReaction,
            reactions = reactions,
            openEmojiList = openEmojiList,
            onTapReaction = onTapReaction,
            modifier = modifier
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun UserMessagePreview() {
    MyZulipAppTheme {
        UserMessage(
            messageId = 0,
            userName = "UserName",
            text = "Text",
            time = "Date",
            icon = "",
            isOwnReaction = false,
            reactions = ImmutableList(emptyList()),
            openEmojiList = {},
            onTapReaction = { _, _, _, _, _ -> },
        )
    }
}
