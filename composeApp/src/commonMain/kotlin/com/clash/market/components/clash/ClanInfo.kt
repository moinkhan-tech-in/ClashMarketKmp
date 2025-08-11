package com.clash.market.components.clash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.clash.market.components.ClashCard
import com.clash.market.components.ClashGlossyButton
import com.clash.market.components.ClashLabelFlowRow
import com.clash.market.components.StatRow
import com.clash.market.models.ClanDetail

@Composable
internal fun ClanInfo(clan: ClanDetail) {
    ClashCard(
        title = "Clan - ${clan.name}",
        topEndContent = {
            clan.location?.name?.let { Text(text = it) }
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = clan.badgeUrls?.medium,
                modifier = Modifier.size(48.dp),
                contentDescription = clan.name
            )
            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(
                    text = clan.name.orEmpty(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = clan.tag.orEmpty(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            if (clan.labels.orEmpty().isNotEmpty()) {
                ClashLabelFlowRow(list = clan.labels.orEmpty())
            } else {
                ClashGlossyButton(
                    text = "Share",
                    onClick = {},
                    rightIcon = Icons.Default.Share
                )
            }
        }
        clan.description?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall
            )
        }
        if (clan.warWins != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatRow(
                    label = "War wins",
                    value = clan.warWins.toString(),
                    icon = null
                )
                StatRow(
                    label = "War Losses",
                    value = clan.warLosses.toString(),
                    icon = null
                )
                StatRow(
                    label = "War Ties",
                    value = clan.warTies.toString(),
                    icon = null
                )
            }
        }
    }
}
