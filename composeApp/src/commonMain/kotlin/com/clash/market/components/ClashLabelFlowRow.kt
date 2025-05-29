package com.clash.market.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.clash.market.models.Label

@Composable
fun ClashLabelFlowRow(list: List<Label>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        list.forEach {
            ClashTooltipBox(it.name) {
                AsyncImage(
                    modifier = Modifier.size(36.dp),
                    model = it.iconUrls.medium,
                    contentDescription = it.name
                )
            }
        }
    }
}