package com.clash.market.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.clash.market.models.Label
import com.clash.market.theme.ClashFont

@Composable
fun ClashLabelFlowRow(
    modifier: Modifier = Modifier,
    list: List<Label>,
    showText: Boolean = false,
    clashLabelSize: Dp = 40.dp
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        itemVerticalAlignment = Alignment.CenterVertically
    ) {
        list.forEach {
            ClashTooltipBox(it.name) {
                ClashLabelItem(
                    item = it,
                    showText = showText,
                    clashLabelSize = clashLabelSize
                )
            }
        }
    }
}

@Composable
fun ClashLabelItem(
    item: Label,
    showText: Boolean = false,
    clashLabelSize: Dp = 40.dp
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.size(clashLabelSize),
            model = item.iconUrls.medium,
            contentDescription = item.name
        )
        if (showText) {
            Text(item.name, fontFamily = ClashFont)
        }
    }
}