package com.example.myzulipapp

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.myzulipapp.navigation.MyZulipAppNavigation
import com.example.myzulipapp.ui.theme.MyZulipAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            @Suppress("DEPRECATION")
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        setContent {
            val isDarkModeValue = isSystemInDarkTheme()
            var isDarkMode by remember { mutableStateOf(isDarkModeValue) }

            MyZulipAppTheme(
                darkTheme = isDarkMode
            ) {
                val navController = rememberNavController()

                MyZulipAppNavigation(
                    navController = navController,
                    onThemeChanged = { isDarkMode = isDarkMode.not() }
                )
            }
        }
    }
}
