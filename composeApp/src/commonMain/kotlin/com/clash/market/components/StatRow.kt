package com.clash.market.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun StatRow(label: String, value: String, icon: Painter?) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            color = MaterialTheme.colorScheme.onSurface,
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
        icon?.let {
            Spacer(Modifier.width(4.dp))
            Image(
                painter = it,
                contentDescription = label,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(4.dp))
        }
        Text(
            color = MaterialTheme.colorScheme.onSurface,
            text = ": $value",
            style = MaterialTheme.typography.labelSmall,
        )
    }
}