package com.clash.market.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Composable
fun <T> NonLazyVerticalGrid(
    items: List<T>,
    columns: Int,
    modifier: Modifier = Modifier,
    verticalSpacing: Dp = Dp.Unspecified,
    horizontalSpacing: Dp = Dp.Unspecified,
    itemContent: @Composable (T) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(verticalSpacing)
    ) {
        items.chunked(columns).forEachIndexed { rowIndex, rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(horizontalSpacing)
            ) {
                rowItems.forEachIndexed { columnIndex, item ->
                    Box(modifier = Modifier.weight(1f)) { itemContent(item) }
                }
                repeat(columns - rowItems.size) { Spacer(modifier = Modifier.weight(1f)) }
            }
        }
    }
}


@Composable
fun <T> AutoColumnGrid(
    items: List<T>,
    minCellWidth: Dp,
    verticalSpacing: Dp = 32.dp,
    horizontalSpacing: Dp = 32.dp,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        val totalWidth = maxWidth
        val columns = max(1, (totalWidth / minCellWidth).toInt())

        NonLazyVerticalGrid(
            items = items,
            columns = columns,
            itemContent = itemContent,
            verticalSpacing = verticalSpacing,
            horizontalSpacing = horizontalSpacing
        )
    }
}