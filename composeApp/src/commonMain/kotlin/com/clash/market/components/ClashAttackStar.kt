package com.clash.market.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_star
import org.jetbrains.compose.resources.painterResource

@Composable
fun ClashAttackStars(stars: Int?) {
    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        repeat(stars ?: 0) {
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(Res.drawable.ic_star),
                contentDescription = null
            )
        }
    }
}