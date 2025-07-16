package com.clash.market.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.clash_regular
import org.jetbrains.compose.resources.Font


val ClashFont: FontFamily
    @Composable
    get() = FontFamily(
        Font(resource = Res.font.clash_regular, weight = FontWeight.Normal),
        Font(resource = Res.font.clash_regular, weight = FontWeight.Bold)
    )


val ClashTypography: Typography
    @Composable
    get() = Typography(
        displayLarge = TextStyle(
            fontFamily = ClashFont,
            fontWeight = FontWeight.Normal,
            fontSize = 32.sp,
            letterSpacing = 1.sp
        ),
        displayMedium = TextStyle(
            fontFamily = ClashFont,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
        ),
        titleLarge = TextStyle(
            fontFamily = ClashFont,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp
        ),
        titleMedium = TextStyle(
            fontFamily = ClashFont,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = ClashFont,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        bodySmall =  TextStyle(
            fontFamily = ClashFont,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = ClashFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        labelSmall = TextStyle(
            fontFamily = ClashFont,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            letterSpacing = 0.5.sp
        ),
        labelMedium = TextStyle(
            fontFamily = ClashFont,
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            letterSpacing = 0.5.sp
        )
    )
