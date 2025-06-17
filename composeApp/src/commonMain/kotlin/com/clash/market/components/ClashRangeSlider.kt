package com.clash.market.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.clash.market.theme.LocalClashColors

@Composable
fun ClashRangeSlider(
    valueRange: ClosedFloatingPointRange<Float>,
    value: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    modifier: Modifier = Modifier,
    steps: Int = 0
) {
    RangeSlider(
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange,
        steps = steps,

        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.primary, // Clash gold
            activeTrackColor = LocalClashColors.current.clashPositive, // Lighter gold
            inactiveTrackColor = LocalClashColors.current.clashNegative.copy(alpha = .5f), // Faded gold
            activeTickColor = Color.White,
            inactiveTickColor = Color.White
        )
    )
}

@Composable
fun ClashSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..100f,
    steps: Int = 0,
    modifier: Modifier = Modifier
) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange,
        steps = steps,
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.primary, // Clash gold
            activeTrackColor = LocalClashColors.current.clashPositive, // Lighter gold
            inactiveTrackColor = LocalClashColors.current.clashNegative.copy(alpha = .5f), // Faded gold
            activeTickColor = Color.White,
            inactiveTickColor = Color.White
        ),
        modifier = modifier
    )
}