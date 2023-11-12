package com.example.myzulipapp.utils

import android.content.Context
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.myzulipapp.MyApp
import com.example.myzulipapp.di.AppComponent
import com.example.myzulipapp.ui.theme.MyZulipAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import java.lang.Long.parseLong
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

val Context.appComponent: AppComponent
    get() = when (this) {
        is MyApp -> appComponent
        else -> applicationContext.appComponent
    }

fun Long.toDate(patternStr: String): String {
    val simpleDateFormat = SimpleDateFormat(patternStr, Locale.getDefault())
    return simpleDateFormat.format(Date(this * 1000))
}

fun String.toColor(): Color {
    return Color(parseLong("FF${this.removePrefix("#").uppercase()}", 16))
}

val Long.toMinutes: Int get() = TimeUnit.MILLISECONDS.toMinutes(this).toInt()

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
fun <T> search(
    query: String,
    searchCallback: (query: String) -> T,
): Flow<T> {
    return flow {
        emit(query)
    }
        .filter { it.isNotEmpty() }
        .distinctUntilChanged()
        .debounce(500)
        .mapLatest { filteredQuery ->
            searchCallback.invoke(filteredQuery)
        }.flowOn(Dispatchers.IO)
}

fun Modifier.shimmerLoadingAnimation(
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
): Modifier {
    return composed {

        val shimmerColors = listOf(
            MyZulipAppTheme.colors.shimmerColor.copy(alpha = 0.3f),
            MyZulipAppTheme.colors.shimmerColor.copy(alpha = 0.5f),
            MyZulipAppTheme.colors.shimmerColor.copy(alpha = 1.0f),
            MyZulipAppTheme.colors.shimmerColor.copy(alpha = 0.5f),
            MyZulipAppTheme.colors.shimmerColor.copy(alpha = 0.3f),
        )

        val transition = rememberInfiniteTransition(label = "")

        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
            label = "Shimmer loading animation",
        )

        this.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = angleOfAxisY),
            ),
        )
    }
}

fun String.findImageLink(): String {
    val userUploads = this.indexOf("/user_uploads/")
    val jpg = this.indexOf(".jpg", userUploads)
    val png = this.indexOf(".png", userUploads)
    val jpeg = this.indexOf(".jpeg", userUploads)
    return when {
        userUploads != -1 && jpg != -1 -> this.substring(userUploads, jpg) + ".jpg"
        userUploads != -1 && png != -1 -> this.substring(userUploads, png) + ".png"
        userUploads != -1 && jpeg != -1 -> this.substring(userUploads, jpeg) + ".jpeg"
        else -> ""
    }
}

fun String.createSendImageMessage(): String {
    val strSplit = this.split("/")
    val jpg = strSplit.lastOrNull()?.indexOf(".jpg") ?: -1
    val png = strSplit.lastOrNull()?.indexOf(".png") ?: -1
    val jpeg = strSplit.lastOrNull()?.indexOf(".jpeg") ?: -1
    val imageName = when {
        jpg != -1 -> strSplit.last().substring(0, jpg) + ".jpg"
        png != -1 -> strSplit.last().substring(0, png) + ".png"
        jpeg != -1 -> strSplit.last().substring(0, jpeg) + ".jpeg"
        else -> ""
    }
    return "[$imageName]($this)"
}
