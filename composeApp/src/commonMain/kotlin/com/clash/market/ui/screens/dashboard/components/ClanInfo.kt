package com.clash.market.ui.screens.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.clash.market.components.ClashCard
import com.clash.market.components.ClashPositiveButton
import com.clash.market.theme.ClashFont

@Composable
internal fun ClanInfo(
    name: String,
    tag: String,
    clanImage: String,
    onShare: () -> Unit
) {
    ClashCard(title = "Clan - $name") {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = clanImage,
                modifier = Modifier.size(56.dp),
                contentDescription = name
            )
            Column(
                modifier = Modifier.weight(1f).padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = name, fontFamily = ClashFont)
                Text(text = tag, fontFamily = ClashFont)
            }
            ClashPositiveButton(
                text = "Share",
                onClick = onShare,
                rightIcon = Icons.Default.Share
            )
        }
    }
}
