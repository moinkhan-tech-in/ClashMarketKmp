package com.clash.market.components.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClashTooltipBox(
    tooltipText: String,
    content: @Composable () -> Unit
) {
    val tooltipState = rememberTooltipState()
    val coroutineScope = rememberCoroutineScope()

    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                    .border(2.dp, MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = tooltipText,
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        },
        state = tooltipState
    ) {
        Box(
            modifier = Modifier
                .clickable {
                    coroutineScope.launch {
                        tooltipState.show()
                    }
                }
        ) {
            content()
        }
    }
}