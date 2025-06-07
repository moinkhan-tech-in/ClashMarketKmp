package com.clash.market.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ClashCard(
    modifier: Modifier = Modifier,
    title: String? = null,
    onClick: () -> Unit = {},
    topEndContent: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .padding(4.dp)
            .border(2.dp, MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(10.dp)), // soft gold border
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                title?.let {
                    Text(
                        modifier = modifier.weight(1f),
                        text = it,
                        color = Color(0xFF8B6508),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                topEndContent?.let { it() }
            }
            content()
        }
    }
}