package com.example.myzulipapp.ui.components.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.myzulipapp.R
import com.example.myzulipapp.ui.theme.MyZulipAppTheme
import com.example.myzulipapp.utils.shimmerLoadingAnimation

@Composable
fun ComponentCircle() {
    Box(
        modifier = Modifier
            .background(color = Color.LightGray, shape = CircleShape)
            .size(dimensionResource(id = R.dimen.shimmer_component_size))
            .shimmerLoadingAnimation()
    )
}

@Composable
fun ComponentIcon() {
    Box(
        modifier = Modifier
            .clip(shape = MyZulipAppTheme.shapes.shimmerCornersStyle)
            .background(color = Color.LightGray)
            .size(dimensionResource(id = R.dimen.shimmer_component_icon_size))
            .shimmerLoadingAnimation()
    )
}

@Composable
fun ComponentRectangle() {
    Box(
        modifier = Modifier
            .clip(shape = MyZulipAppTheme.shapes.shimmerCornersStyle)
            .background(color = Color.LightGray)
            .height(dimensionResource(id = R.dimen.shimmer_component_height))
            .fillMaxWidth()
            .shimmerLoadingAnimation()
    )
}

@Composable
fun ComponentRectangleLineLong() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .size(
                height = dimensionResource(id = R.dimen.shimmer_rectangle_line_height),
                width = dimensionResource(id = R.dimen.shimmer_rectangle_line_long_width)
            )
            .shimmerLoadingAnimation()
    )
}

@Composable
fun ComponentRectangleLineShort() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .size(
                height = dimensionResource(id = R.dimen.shimmer_rectangle_line_height),
                width = dimensionResource(id = R.dimen.shimmer_rectangle_line_short_width)
            )
            .shimmerLoadingAnimation()
    )
}
