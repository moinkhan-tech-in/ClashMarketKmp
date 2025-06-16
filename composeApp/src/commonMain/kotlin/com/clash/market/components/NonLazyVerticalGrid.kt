package com.clash.market.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Composable
fun <T> NonLazyVerticalGrid(
    items: List<T>,
    columns: Int,
    modifier: Modifier = Modifier,
    dividerColor: Color = Color.Gray,
    dividerThickness: Dp = 1.dp,
    itemContent: @Composable (T) -> Unit
) {
    Column(modifier = modifier) {
        items.chunked(columns).forEachIndexed { rowIndex, rowItems ->
            Row(modifier = Modifier.fillMaxWidth()) {
                rowItems.forEachIndexed { columnIndex, item ->

                    // Main Item
                    Box(modifier = Modifier.weight(1f)) { itemContent(item) }

                    if (columnIndex != rowItems.lastIndex) {
                        // Vertical Divider between columns
                        VerticalDivider(
                            modifier = Modifier.fillMaxHeight()
                                .padding(vertical = 10.dp)
                                .padding(horizontal = 16.dp),
                            color = dividerColor,
                            thickness = dividerThickness
                        )
                    }
                }

                repeat(columns - rowItems.size) { Spacer(modifier = Modifier.weight(1f)) }
            }

            if (rowIndex != (items.chunked(columns).lastIndex)) {
                // Horizontal Divider between rows
                HorizontalDivider(
                    color = dividerColor,
                    thickness = dividerThickness,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 16.dp)
                )
            }
        }
    }
}


@Composable
fun <T> AutoColumnGrid(
    items: List<T>,
    minCellWidth: Dp,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        val totalWidth = maxWidth
        val columns = max(1, (totalWidth / minCellWidth).toInt())

        NonLazyVerticalGrid(
            items = items,
            columns = columns,
            itemContent = itemContent
        )
    }
}