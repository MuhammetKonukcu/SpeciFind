package com.muhammetkonukcu.specifind.lang

import org.jetbrains.compose.resources.StringResource
import specifind.composeapp.generated.resources.Res
import specifind.composeapp.generated.resources.en
import specifind.composeapp.generated.resources.tr

enum class AppLang(
    val code: String,
    val stringRes: StringResource
) {
    English("en", Res.string.en),
    Turkish("tr", Res.string.tr)
}