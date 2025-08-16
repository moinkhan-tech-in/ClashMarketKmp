package com.clash.market.components.widgets.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.clash.market.components.widgets.ClashImage

@Composable
fun ClashTabItem(tab: ClashTab, isSelected: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        tab.leadingImage?.let { ClashImage(modifier = Modifier.size(24.dp), it) }
        tab.title?.let {
            Text(
                it,
                color = if (isSelected) Color(0xFFFFD700) else Color.White
            )
        }
        tab.trailingImage?.let { ClashImage(modifier = Modifier.size(24.dp), it) }
    }
}