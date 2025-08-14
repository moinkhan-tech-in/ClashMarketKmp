package com.clash.market.ui.contents.wardetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clash.market.components.ClashCard
import com.clash.market.components.PlayerListItem
import com.clash.market.components.widgets.ClashAttackStars
import com.clash.market.models.Player
import com.clash.market.utils.formatPercent

@Composable
fun PlayerWarAttackList(players: List<Player>, nameByTags: HashMap<String, String>) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Spacer(Modifier.size(10.dp))
        players.forEach { player ->
            ClashCard {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    PlayerListItem(player = player, onClick = {})
                    player.attacks.forEachIndexed { index, attack ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Attack ${index+1}.",
                                style = MaterialTheme.typography.labelMedium
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = nameByTags.get(attack.defenderTag).orEmpty(),
                                style = MaterialTheme.typography.labelMedium
                            )
                            Column {
                                Text(
                                    text = attack.destructionPercentage?.toDouble().formatPercent(),
                                    style = MaterialTheme.typography.labelMedium
                                )
                                ClashAttackStars(attack.stars)
                            }
                        }
                        if (player.attacks.lastIndex != index) {
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}