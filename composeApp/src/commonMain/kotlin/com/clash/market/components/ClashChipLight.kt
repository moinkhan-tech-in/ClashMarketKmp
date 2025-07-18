package com.clash.market.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_chevron_right
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ClashChipLight(
    text: String,
    trailingIcon: DrawableResource? = Res.drawable.ic_chevron_right,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
            .border(1.dp, color = Color.White, shape = RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.size(8.dp))
        Text(
            text = text,
            color = Color.White
        )
        Spacer(Modifier.size(2.dp))
        trailingIcon?.let {
            Image(
                painter = painterResource(it),
                modifier = Modifier.size(36.dp),
                contentDescription = null
            )
        }
    }
}