package com.example.myzulipapp.ui.components.basic

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.myzulipapp.R
import com.example.myzulipapp.chat.domain.model.Reaction
import com.example.myzulipapp.di.NetworkModule
import com.example.myzulipapp.ui.theme.MyZulipAppTheme
import com.example.myzulipapp.utils.ImmutableList
import com.example.myzulipapp.utils.findImageLink
import okhttp3.Credentials

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppMessage(
    messageId: Int,
    text: String,
    time: String,
    color: Color,
    timeColor: Color,
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
    userName: String = ""
) {
    Surface(
        modifier = modifier.combinedClickable(
            onLongClick = { openEmojiList(messageId) },
            onClick = {},
        ),
        color = color,
        shape = MyZulipAppTheme.shapes.cornersStyle
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.indent_10dp))
        ) {
            val (nameId, textId, imageId, timeId, reactionsId) = createRefs()

            val barrierTimeBottom = createBottomBarrier(
                textId, imageId, reactionsId,
                margin = dimensionResource(id = R.dimen.indent_8dp)
            )

            val barrierReactionsBottom = createBottomBarrier(
                textId, imageId,
                margin = dimensionResource(id = R.dimen.indent_8dp)
            )

            if (userName.isNotEmpty()) {
                Text(
                    text = userName,
                    modifier = Modifier.constrainAs(nameId) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    },
                    color = MyZulipAppTheme.colors.userNameColor,
                    style = MyZulipAppTheme.typography.body
                )
            }
            Text(
                text = text,
                modifier = Modifier.constrainAs(textId) {
                    start.linkTo(parent.start)
                    top.linkTo(nameId.bottom)
                },
                color = MyZulipAppTheme.colors.primaryTextColor
            )
            if (text.findImageLink().isNotEmpty()) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(
                        "${NetworkModule.BASE_URL}${text.findImageLink()}"
                    ).addHeader(
                        NetworkModule.AUTHORIZATION_HEADER, Credentials.basic(
                            NetworkModule.EMAIL,
                            NetworkModule.API_KEY
                        )
                    ).build(),
                    loading = {
                        AppMessageShimmer()
                    },
                    contentDescription = "Image",
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                        .constrainAs(imageId) {
                            start.linkTo(parent.start)
                            top.linkTo(textId.bottom)
                            end.linkTo(parent.end)
                            bottom.linkTo(barrierReactionsBottom)
                        }
                )
            }
            if (reactions.list.isNotEmpty()) {
                AppFlowRowLayout(
                    reactions = reactions,
                    modifier = Modifier.constrainAs(reactionsId) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(barrierReactionsBottom)
                    },
                    isOwnReaction = isOwnReaction,
                    onTapReaction = onTapReaction,
                )
            }
            Text(
                text = time,
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.indent_8dp))
                    .constrainAs(timeId) {
                        top.linkTo(barrierTimeBottom)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                color = timeColor,
                style = MyZulipAppTheme.typography.caption
            )
        }
    }
}

@Composable
fun AppMessageShimmer(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        ComponentRectangle()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun AppMessagePreview() {
    MyZulipAppTheme {
        AppMessage(
            messageId = 0,
            userName = "UserName",
            text = "Text",
            time = "10:10",
            isOwnReaction = true,
            openEmojiList = {},
            onTapReaction = { _, _, _, _, _ -> },
            reactions = ImmutableList(emptyList()),
            color = MyZulipAppTheme.colors.messageBackgroundColor,
            timeColor = MyZulipAppTheme.colors.ownMessageTimeColor,
        )
    }
}
