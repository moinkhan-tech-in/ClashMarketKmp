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
    topStartContent: (@Composable () -> Unit)? = null,
    title: String? = null,
    onClick: () -> Unit = {},
    topEndContent: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .padding(4.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = .3f),
                shape = RoundedCornerShape(10.dp)
            ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (title != null || topEndContent != null || topStartContent != null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    topStartContent?.let { it() }
                    title?.let {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = it,
                            color = Color(0xFF8B6508),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    topEndContent?.let { it() }
                }
            }
            content()
        }
    }
}


@Composable
fun ClashCardItem(
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = .3f),
                shape = RoundedCornerShape(10.dp)
            ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            content()
        }
    }
}
