package com.example.myzulipapp.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class MyZulipAppColors(
    val appBarColor: Color,
    val tabSelectedColor: Color,
    val tabNotSelectedColor: Color,
    val floatingActionButtonColor: Color,
    val drawerTitleColor: Color,
    val drawerColor: Color,
    val buttonColor: Color,
    val outlinedEditTextColor: Color,
    val messageBackgroundColor: Color,
    val ownMessageBackgroundColor: Color,
    val primaryTextColor: Color,
    val secondaryTextColor: Color,
    val hintColor: Color,
    val iconTintColor: Color,
    val contentColor: Color,
    val backgroundColor: Color,
    val ownReactionBackgroundColor: Color,
    val userReactionBackgroundColor: Color,
    val logoProgressBarColor: Color,
    val chatBackgroundColor: Color,
    val dateBackgroundColor: Color,
    val shimmerColor: Color,
    val userNameColor: Color,
    val ownMessageTimeColor: Color,
    val userMessageTimeColor: Color,
    val errorColor: Color
)

data class MyZulipAppTypography(
    val heading1: TextStyle,
    val heading2: TextStyle,
    val body: TextStyle,
    val toolbar: TextStyle,
    val caption: TextStyle
)

data class MyZulipAppShape(
    val padding: Dp,
    val cornersStyle: Shape,
    val topCornersStyle: Shape,
    val bottomCornersStyle: Shape,
    val dateCornersStyle: Shape,
    val reactionsCornersStyle: Shape,
    val shimmerCornersStyle: Shape,
)

data class MyZulipAppImage(
    @DrawableRes val iconId: Int
)

object MyZulipAppTheme {
    internal val colors: MyZulipAppColors
        @Composable get() = LocalMyZulipAppColors.current

    internal val typography: MyZulipAppTypography
        @Composable get() = LocalMyZulipAppTypography.current

    internal val shapes: MyZulipAppShape
        @Composable get() = LocalMyZulipAppShape.current

    internal val images: MyZulipAppImage
        @Composable get() = LocalMyZulipAppImage.current
}

internal val LocalMyZulipAppColors = staticCompositionLocalOf<MyZulipAppColors> {
    error("No colors provided")
}

internal val LocalMyZulipAppTypography = staticCompositionLocalOf<MyZulipAppTypography> {
    error("No font provided")
}

internal val LocalMyZulipAppShape = staticCompositionLocalOf<MyZulipAppShape> {
    error("No shapes provided")
}

internal val LocalMyZulipAppImage = staticCompositionLocalOf<MyZulipAppImage> {
    error("No images provided")
}
