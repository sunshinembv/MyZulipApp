package com.example.myzulipapp.splash_screen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myzulipapp.R
import com.example.myzulipapp.splash_screen.presentation.state.SplashEvents
import com.example.myzulipapp.splash_screen.presentation.state.SplashState
import com.example.myzulipapp.ui.theme.MyZulipAppTheme

@Composable
fun SplashRoute(
    viewModel: SplashViewModel,
    toChannelsScreen: () -> Unit,
    modifier: Modifier = Modifier
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    SplashScreen(
        splashState = state,
        toChannelsScreen = toChannelsScreen,
        obtainEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}

@Composable
fun SplashScreen(
    splashState: SplashState,
    toChannelsScreen: () -> Unit,
    obtainEvent: (SplashEvents.Ui) -> Unit,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(key1 = splashState.isOwnUserLoaded) {
        if (splashState.isOwnUserLoaded.not()) return@LaunchedEffect
        obtainEvent(SplashEvents.Ui.OpenChannelsScreen(toChannelsScreen))
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MyZulipAppTheme.colors.backgroundColor
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (splashState.error == null) {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(size = dimensionResource(id = R.dimen.logo_progress_bar_size)),
                        color = MyZulipAppTheme.colors.logoProgressBarColor,
                        strokeWidth = dimensionResource(id = R.dimen.logo_progress_bar_stroke_width)
                    )
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.zulip_icon),
                        contentDescription = "Splash Icon",
                        modifier = Modifier.fillMaxSize(0.2f)
                    )
                }
            } else {
                Text(
                    text = stringResource(id = R.string.login_loading_error),
                    modifier = Modifier.fillMaxWidth(0.7f),
                    color = MyZulipAppTheme.colors.primaryTextColor,
                    textAlign = TextAlign.Center,
                    style = MyZulipAppTheme.typography.body
                )
                TextButton(onClick = {
                    obtainEvent(SplashEvents.Ui.GetOwnUser)
                }) {
                    Text(
                        text = stringResource(id = R.string.retry),
                        color = MyZulipAppTheme.colors.buttonColor,
                        style = MyZulipAppTheme.typography.body
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    MyZulipAppTheme {
        SplashScreen(
            splashState = SplashState(error = "Error"),
            toChannelsScreen = {},
            obtainEvent = {},
        )
    }
}
