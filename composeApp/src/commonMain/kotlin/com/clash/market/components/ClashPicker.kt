package com.clash.market.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun <T> ClashPicker(
    items: List<T>,
    selectedIndex: Int,
    onItemChange: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentIndex by remember { mutableStateOf(selectedIndex) }
    Row(
        modifier = modifier.padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ClashGlossyButton(
            text = "<",
            onClick = {
                if (currentIndex == 0) {
                    currentIndex = items.lastIndex
                } else {
                    currentIndex -= 1
                }
                onItemChange(items[currentIndex])
            }
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(currentIndex) {
                Text(text = items[currentIndex].toString(), textAlign = TextAlign.Center)
            }
        }
        ClashGlossyButton(
            text = ">",
            onClick = {
                if (currentIndex == items.lastIndex) {
                    currentIndex = 0
                } else {
                    currentIndex += 1
                }
                onItemChange(items[currentIndex])
            }
        )
    }
}