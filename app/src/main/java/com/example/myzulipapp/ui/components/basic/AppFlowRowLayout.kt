package com.example.myzulipapp.ui.components.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myzulipapp.chat.domain.model.Reaction
import com.example.myzulipapp.ui.theme.MyZulipAppTheme
import com.example.myzulipapp.utils.ImmutableList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppFlowRowLayout(
    reactions: ImmutableList<Reaction>,
    isOwnReaction: Boolean,
    onTapReaction: (
        streamId: Int,
        topicName: String,
        messageId: Int,
        emojiName: String,
        emojiCode: String
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(reactions.list.size) { index ->
            Box(
                modifier = Modifier
                    .background(
                        if (isOwnReaction) {
                            MyZulipAppTheme.colors.ownReactionBackgroundColor
                        } else {
                            MyZulipAppTheme.colors.userReactionBackgroundColor
                        },
                        shape = MyZulipAppTheme.shapes.reactionsCornersStyle
                    )
            ) {
                AppReaction(
                    reaction = reactions.list[index],
                    onTapReaction = onTapReaction
                )
            }
        }
    }
}

@Preview
@Composable
fun AppFlowRowLayoutPreview() {
    MyZulipAppTheme {
        AppFlowRowLayout(
            reactions = ImmutableList(emptyList()),
            isOwnReaction = true,
            onTapReaction = { _, _, _, _, _ -> }
        )
    }
}
