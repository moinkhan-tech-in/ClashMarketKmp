package com.clash.market.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_trophy
import com.clash.market.theme.ClashFont
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ClashChip(
    leadingImage: DrawableResource? = null,
    leadingImageSize: DpSize = DpSize(24.dp, 24.dp),
    text: String,
    trailingImage: DrawableResource? = null,
    trailingImageSize: DpSize = DpSize(24.dp, 24.dp)
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingImage?.let {
            Image(
                modifier = Modifier.size(leadingImageSize),
                painter = painterResource(leadingImage),
                contentDescription = text
            )
        }
        Text(text = text, fontFamily = ClashFont)
        trailingImage?.let {
            Image(
                modifier = Modifier.size(trailingImageSize),
                painter = painterResource(trailingImage),
                contentDescription = text
            )
        }
    }
}

@Composable
@Preview
fun ClashChipPreview() {
    ClashChip(
        leadingImage = Res.drawable.ic_trophy,
        text = "1234",
        trailingImage = Res.drawable.ic_trophy
    )
}