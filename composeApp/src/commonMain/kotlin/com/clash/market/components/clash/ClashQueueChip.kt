package com.clash.market.components.clash

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun <T> ClashQueueChip(
    items: List<T>,
    onItemChange: (T) -> Unit
) {
    var currentVillageIndex by remember { mutableStateOf(0) }
    if (items.count() > 1) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    if (items.lastIndex == currentVillageIndex) {
                        currentVillageIndex = 0
                    } else {
                        currentVillageIndex += 1
                    }
                    onItemChange(items[currentVillageIndex])
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(Modifier.size(4.dp))
            Text(
                text = items.getOrNull(currentVillageIndex)?.toString().orEmpty(),
                style = MaterialTheme.typography.bodyMedium
            )
            Icon(imageVector = Icons.AutoMirrored.Default.Sort, contentDescription = null)
        }
    }
}