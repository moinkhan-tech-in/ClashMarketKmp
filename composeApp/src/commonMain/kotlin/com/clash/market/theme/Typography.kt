package com.clash.market.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.clash_regular
import org.jetbrains.compose.resources.Font


private val ClashFont: FontFamily
    @Composable
    get() = FontFamily(
        Font(resource = Res.font.clash_regular, weight = FontWeight.Normal),
        Font(resource = Res.font.clash_regular, weight = FontWeight.Bold)
    )


val ClashTypography: Typography
    @Composable
    get() = Typography(
        displayLarge = MaterialTheme.typography.displayLarge.copy(fontFamily = ClashFont),
        displayMedium = MaterialTheme.typography.displayMedium.copy(fontFamily = ClashFont),
        displaySmall = MaterialTheme.typography.displaySmall.copy(fontFamily = ClashFont),
        headlineLarge = MaterialTheme.typography.headlineLarge.copy(fontFamily = ClashFont),
        headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontFamily = ClashFont),
        headlineSmall = MaterialTheme.typography.headlineSmall.copy(fontFamily = ClashFont),
        titleLarge = MaterialTheme.typography.titleLarge.copy(fontFamily = ClashFont),
        titleMedium = MaterialTheme.typography.titleMedium.copy(fontFamily = ClashFont),
        titleSmall = MaterialTheme.typography.titleSmall.copy(fontFamily = ClashFont),
        bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = ClashFont),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = ClashFont),
        bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = ClashFont),
        labelLarge = MaterialTheme.typography.labelLarge.copy(fontFamily = ClashFont),
        labelMedium = MaterialTheme.typography.labelMedium.copy(fontFamily = ClashFont),
        labelSmall = MaterialTheme.typography.labelSmall.copy(fontFamily = ClashFont)
    )
