package com.clash.market.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.clash.market.theme.ClashFont

@Composable
fun ClashLabel(
    leadingImage: ImageVector? = null,
    label: String,
    trailingImage: ImageVector? = null,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingImage?.let { Icon(imageVector = it, contentDescription = null) }
        Text(text = label, fontFamily = ClashFont)
        trailingImage?.let { Icon(imageVector = it, contentDescription = null) }
    }
}