package com.clash.market.components.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ClashProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    height: Dp = 24.dp,
    backgroundColor: Color = Color(0xFF3A2E1E),
    fillColor: Color = Color(0xFFFFC107),
    borderColor: Color = Color.Black,
    label: String? = null,
    icon: ImageVector? = null
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(borderColor)
            .padding(2.dp)
            .background(backgroundColor)
            .height(height)
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(fillColor)
                .fillMaxWidth(progress),
            verticalAlignment = Alignment.CenterVertically
        ) {}

        // Overlay text and icon
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            icon?.let {
                Icon(it, contentDescription = null, tint = Color.Black)
            }

            label?.let {
                Text(
                    it,
                    color = Color.White,
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        shadow = Shadow(color = Color.Black, blurRadius = 10f)
                    )
                )
            }
        }
    }
}

@Composable
@Preview
fun ClashProgressPreview() {
    ClashProgressBar(
        progress = 0.75f,
        label = "War Stars",
        icon = Icons.Default.Star,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}