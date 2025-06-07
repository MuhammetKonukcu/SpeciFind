package com.muhammetkonukcu.specifind

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import com.muhammetkonukcu.specifind.lang.AppLang
import com.muhammetkonukcu.specifind.lang.rememberAppLocale
import com.muhammetkonukcu.specifind.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

val LocalAppLocalization = compositionLocalOf {
    AppLang.English
}

@Composable
@Preview
fun MainScreen() {
    val currentLanguage = rememberAppLocale()
    CompositionLocalProvider(LocalAppLocalization provides currentLanguage) {
        AppTheme {
            Surface(
                modifier = Modifier.fillMaxSize().navigationBarsPadding(),
                color = MaterialTheme.colorScheme.background
            ) {

            }
        }
    }
}