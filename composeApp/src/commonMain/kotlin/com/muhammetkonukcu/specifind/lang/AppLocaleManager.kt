package com.muhammetkonukcu.specifind.lang

import androidx.compose.runtime.Composable

interface AppLocaleManager {
    fun getLocale(): String
}

@Composable
expect fun rememberAppLocale(): AppLang