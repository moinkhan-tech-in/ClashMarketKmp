package com.clash.market.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.dp
import com.clash.market.theme.ClashFont
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ClashInfoRowCard(
    infoList: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF2C2C2C).copy(alpha = 0.1f)),
        tonalElevation = 4.dp,
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            infoList.forEachIndexed { index, it ->
                InfoRow(label = it.first, value = it.second)
                if (infoList.lastIndex != index) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        thickness = 1.dp,
                        color =  Color.Black.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                shadow = Shadow(color = Color.Black, blurRadius = 1f)
            ),
            fontFamily = ClashFont
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(
                shadow = Shadow(color = Color.Black, blurRadius = 1f)
            ),
            fontFamily = ClashFont
        )
    }
}

@Composable
@Preview
private fun ClashInfoRowCardPreview() {
    ClashInfoRowCard(
        infoList = listOf(
            "Location" to "India",
            "Language" to "English"
        )
    )
}