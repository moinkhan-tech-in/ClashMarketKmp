package com.clash.market.components

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable

@Composable
fun ClashToggleButton(
    checked: Boolean,
    text: String? = null,
    onChange: (Boolean) -> Unit
) {
    Crossfade(checked) {
        if (checked) {
            ClashGlossyButton(
                type = ClashStyleButtonType.Positive,
                text = text ?: "ON",
                onClick = { onChange(checked.not()) }
            )
        } else {
            ClashGlossyButton(
                type = ClashStyleButtonType.Negative,
                text = text ?: "OFF",
                onClick = { onChange(checked.not()) }
            )
        }
    }
}