package com.clash.market.ui.contents.wardetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_shield
import clashmarket.composeapp.generated.resources.ic_sward
import com.clash.market.components.ClashCard
import com.clash.market.components.ClashChip
import com.clash.market.components.PlayerListItem
import com.clash.market.components.widgets.ClashAttackStars
import com.clash.market.models.Attack
import com.clash.market.models.Player
import com.clash.market.utils.formatPercent
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun PlayerWarAttackList(
    players: List<Player>,
    nameByTags: HashMap<String, String>,
    onPlayerClick: (Player) -> Unit = {}
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        players.forEach { player ->
            ClashCard {

                PlayerListItem(
                    player = player,
                    onClick = { onPlayerClick(player) }
                )

                PlayerWarAttackSection(
                    headerIcon = Res.drawable.ic_sward,
                    headerText = "Attacks",
                    nameByTags = nameByTags,
                    attacks = player.attacks,
                )

                Spacer(Modifier.size(2.dp))


                PlayerWarAttackSection(
                    headerIcon = Res.drawable.ic_shield,
                    headerText = "Best Opponent attack",
                    nameByTags = nameByTags,
                    attacks = listOfNotNull(player.bestOpponentAttack)
                )
            }
        }
    }
}



@Composable
private fun PlayerWarAttackSection(
    headerIcon: DrawableResource,
    headerText: String,
    attacks: List<Attack>,
    nameByTags: HashMap<String, String>
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ClashChip(
            leadingImage = headerIcon,
            text = headerText
        )
        HorizontalDivider(Modifier.padding(start = 28.dp))
        if (attacks.isNotEmpty()) {
            attacks.forEachIndexed { index, attack ->
                PlayerWarAttackItem(
                    modifier = Modifier.padding(start = 28.dp),
                    attackNo = index + 1,
                    name = nameByTags[attack.attackerTag].orEmpty(),
                    destruction = attack.destructionPercentage?.toDouble()
                        .formatPercent(),
                    stars = attack.stars ?: 0
                )
            }
        } else {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "No Attacks",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun PlayerWarAttackItem(
    modifier: Modifier = Modifier,
    attackNo: Int,
    name: String,
    destruction: String,
    stars: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "#$attackNo",
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            modifier = Modifier.weight(1f),
            text = name,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = destruction,
            style = MaterialTheme.typography.labelMedium
        )
        ClashAttackStars(stars = stars)
    }
}