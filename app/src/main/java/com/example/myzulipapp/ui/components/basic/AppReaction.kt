package com.example.myzulipapp.ui.components.basic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myzulipapp.R
import com.example.myzulipapp.chat.domain.model.Reaction
import com.example.myzulipapp.ui.theme.MyZulipAppTheme

@Composable
fun AppReaction(
    reaction: Reaction,
    onTapReaction: (
        streamId: Int,
        topicName: String,
        messageId: Int,
        emojiName: String,
        emojiCode: String
    ) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(horizontal = dimensionResource(id = R.dimen.indent_8dp))
            .clickable {
                onTapReaction(
                    reaction.streamId,
                    reaction.topicName,
                    reaction.messageId,
                    reaction.emojiName,
                    reaction.emojiCode
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = String(Character.toChars(reaction.emojiCode.toInt(16))),
            fontSize = 16.sp,
        )
        Text(
            text = reaction.count.toString(),
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.indent_4dp)),
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
fun AppReactionPreview() {
    MyZulipAppTheme {
        AppReaction(
            reaction = Reaction(
                messageId = 0,
                streamId = 0,
                topicName = "",
                emojiName = "",
                emojiCode = "${"1f53a".toInt(16)}",
                reactionType = "",
                userId = 0,
                count = 5
            ),
            onTapReaction = { _, _, _, _, _ -> }
        )
    }
}
