package com.clash.market.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.clash_bold
import org.jetbrains.compose.resources.Font


val ClashFont: FontFamily
    @Composable
    get() = FontFamily(
        Font(
            resource = Res.font.clash_bold
        )
)
