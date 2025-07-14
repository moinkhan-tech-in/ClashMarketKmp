package com.clash.market.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.clash.market.models.Label
import com.clash.market.theme.ClashFont
import com.clash.market.theme.LocalClashColors

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
                    clashLabelSize = clashLabelSize,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun ClashLabelItem(
    item: Label,
    showText: Boolean = false,
    isSelected: Boolean = false,
    clashLabelSize: Dp = 40.dp,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.clickable(onClick = onClick)) {
        Row(
            modifier = Modifier.padding(end = 8.dp),
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
        AnimatedVisibility(
            modifier = Modifier.matchParentSize(),
            visible = isSelected
        ) {
            Spacer(
                Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(LocalClashColors.current.clashPositive.copy(alpha = .2f))
            )
        }
    }

}