package com.example.myzulipapp.ui.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.myzulipapp.chat.domain.model.Reaction
import com.example.myzulipapp.ui.components.basic.AppMessage
import com.example.myzulipapp.ui.theme.MyZulipAppTheme
import com.example.myzulipapp.utils.ImmutableList

@Composable
fun OwnMessage(
    messageId: Int,
    text: String,
    time: String,
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
    color: Color = MyZulipAppTheme.colors.ownMessageBackgroundColor
) {
    AppMessage(
        messageId = messageId,
        text = text,
        time = time,
        color = color,
        timeColor = MyZulipAppTheme.colors.ownMessageTimeColor,
        isOwnReaction = isOwnReaction,
        reactions = reactions,
        openEmojiList = openEmojiList,
        onTapReaction = onTapReaction,
        modifier = modifier
    )
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun OwnMessagePreview() {
    MyZulipAppTheme {
        OwnMessage(
            messageId = 0,
            text = "Text",
            time = "Date",
            isOwnReaction = false,
            reactions = ImmutableList(emptyList()),
            openEmojiList = {},
            onTapReaction = { _, _, _, _, _ -> },
        )
    }
}
