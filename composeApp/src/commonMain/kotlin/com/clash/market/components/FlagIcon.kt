package com.clash.market.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun FlagIcon(
    countryCode: String,
    size: Dp = 32.dp,
    modifier: Modifier = Modifier
) {
    val url = "https://flagcdn.com/w40/${countryCode.lowercase()}.png"

    AsyncImage(
        model = url,
        contentDescription = "Flag of $countryCode",
        modifier = modifier.size(size),
        contentScale = ContentScale.Crop
    )
}