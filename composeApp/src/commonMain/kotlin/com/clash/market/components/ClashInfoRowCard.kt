package com.clash.market.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ClashInfoRowCard(
    infoList: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            infoList.forEachIndexed { index, it ->
                InfoRow(label = it.first, value = it.second)
                if (infoList.lastIndex != index) {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = .2f)
                    )
                }
            }
        }
    }
}

@Composable
fun ClashTripleInfoRowCard(
    infoList: List<Triple<String, String, String>>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
        tonalElevation = 4.dp,
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            infoList.forEachIndexed { index, it ->
                TripleInfoRow(it)
                if (infoList.lastIndex != index) {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        thickness = 1.dp,
                        color = Color.Black.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}


@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun TripleInfoRow(
    triple: Triple<String, String, String>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = triple.first,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = triple.second,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = triple.third,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
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

@Composable
@Preview
private fun ClashTripleInfoRowCardPreview() {
    ClashTripleInfoRowCard(
        infoList = listOf(
            Triple(
                "Left Value",
                "Key",
                "Right Value"
            ),
            Triple(
                "Left Value",
                "Key",
                "Right Value"
            )
        )
    )
}