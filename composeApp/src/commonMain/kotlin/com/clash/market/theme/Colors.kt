package com.clash.market.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val ClashLightColors = lightColorScheme(
    primary = Color(0xFF2C2C2C),
    onPrimary = Color(0xFFFED36A),
    background = Color(0xFFFFF9E5),  // Light beige
    onBackground = Color(0xFF1E1E1E),
    surface = Color.White,
    onSurface = Color(0xFF2C2C2C),
    surfaceContainerLow = Color(0xFFFFF9E5)
)

val ClashDarkColors = darkColorScheme(
    primary = Color(0xFF2C2C2C),     // Bright gold
    onPrimary = Color(0xFFFED36A),
    background = Color(0xFFFFF9E5),
    onBackground = Color(0xFFE0E0E0),
    surface = Color.White,
    onSurface = Color(0xFF2C2C2C)
)

data class ClashColors(
    val clashNegative: Color = Color(0xFFB71C1C),
    val clashPositive: Color = Color(0xFF388E3C),
    val clashStone: Color = Color(0xFFDFD4C2)
)

val LocalClashColors = staticCompositionLocalOf { ClashColors() }