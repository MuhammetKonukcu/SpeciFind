package com.muhammetkonukcu.specifind.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import specifind.composeapp.generated.resources.Res
import specifind.composeapp.generated.resources.inter_bold
import specifind.composeapp.generated.resources.inter_light
import specifind.composeapp.generated.resources.inter_medium
import specifind.composeapp.generated.resources.inter_regular
import specifind.composeapp.generated.resources.inter_thin
import specifind.composeapp.generated.resources.inter_semibold
import specifind.composeapp.generated.resources.inter_extrabold
import org.jetbrains.compose.resources.Font

@Composable
fun AppTypography(): Typography {
    val interFamily = FontFamily(
        Font(Res.font.inter_thin, FontWeight.Thin),
        Font(Res.font.inter_light, FontWeight.Light),
        Font(Res.font.inter_regular, FontWeight.Normal),
        Font(Res.font.inter_medium, FontWeight.Medium),
        Font(Res.font.inter_semibold, FontWeight.SemiBold),
        Font(Res.font.inter_bold, FontWeight.Bold),
        Font(Res.font.inter_extrabold, FontWeight.ExtraBold)
    )

    return Typography(
        labelSmall = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Thin,
            fontSize = 12.sp,
            lineHeight = 12.sp,
            letterSpacing = 0.5.sp
        ),
        labelMedium = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.5.sp
        ),
        labelLarge = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),
        bodySmall = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.5.sp
        ),
        titleSmall = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),
        titleMedium = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.5.sp
        ),
        titleLarge = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.sp
        )
    )
}