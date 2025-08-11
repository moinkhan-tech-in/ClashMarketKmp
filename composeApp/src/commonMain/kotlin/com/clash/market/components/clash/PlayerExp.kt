package com.clash.market.components.clash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_coc_xp
import org.jetbrains.compose.resources.painterResource

@Composable
fun PlayerExp(
    exp: String,
    size: Dp = 44.dp,
    textSize: TextUnit = 16.sp
) {
    Box(contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.size(size),
            painter = painterResource(Res.drawable.ic_coc_xp), contentDescription = null
        )
        Text(
            text = exp,
            fontSize = textSize,
            color = Color.White,
            style = LocalTextStyle.current.copy(
                shadow = Shadow(color = Color.Black, blurRadius = 10f)
            )
        )
    }
}