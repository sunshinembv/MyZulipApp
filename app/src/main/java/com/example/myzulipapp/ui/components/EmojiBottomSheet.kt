package com.example.myzulipapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myzulipapp.R
import com.example.myzulipapp.chat.presentation.ui_model.EmojiData
import com.example.myzulipapp.chat.presentation.ui_model.emojiSet
import com.example.myzulipapp.ui.theme.MyZulipAppTheme

@Composable
fun EmojiBottomSheet(
    onTapEmoji: (
        emojiName: String,
        emojiCode: String
    ) -> Unit,
    modifier: Modifier = Modifier,
    emojiList: List<EmojiData> = emojiSet,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = modifier
    ) {
        items(emojiList.size) { index ->
            Text(
                text = String(Character.toChars(emojiList[index].code.toInt(16))),
                modifier = modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.indent_12dp),
                        top = dimensionResource(id = R.dimen.indent_8dp),
                        end = dimensionResource(id = R.dimen.indent_8dp),
                        bottom = dimensionResource(id = R.dimen.indent_8dp)
                    )
                    .clickable {
                        onTapEmoji(
                            emojiList[index].name,
                            emojiList[index].code
                        )
                    },
                fontSize = 24.sp,
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun EmojiBottomSheetPreview() {
    MyZulipAppTheme {
        EmojiBottomSheet(
            emojiList = emptyList(),
            onTapEmoji = { _, _ -> },
        )
    }
}
