package com.muhammetkonukcu.specifind

import androidx.compose.ui.window.ComposeUIViewController
import com.muhammetkonukcu.specifind.di.initKoin
import com.muhammetkonukcu.specifind.screen.MainScreen

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    MainScreen()
}