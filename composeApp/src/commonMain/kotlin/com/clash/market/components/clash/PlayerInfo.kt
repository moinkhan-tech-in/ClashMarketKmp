package com.clash.market.components.clash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_coc_xp
import clashmarket.composeapp.generated.resources.ic_star
import clashmarket.composeapp.generated.resources.ic_sward
import clashmarket.composeapp.generated.resources.ic_trophy
import com.clash.market.components.ClashCard
import com.clash.market.components.ClashChip
import com.clash.market.components.ClashInfoRowCard
import com.clash.market.components.ClashLabelFlowRow
import com.clash.market.components.StatRow
import com.clash.market.models.Player
import org.jetbrains.compose.resources.painterResource

@Composable
fun PlayerInfo(
    player: Player,
    showClanInfo: Boolean = false,
    onEdit: () -> Unit = {}
) {
    ClashCard(
        title = "Player - ${player.name}",
        topEndContent = {
            ClashChip(
                trailingImage = Res.drawable.ic_trophy,
                text = player.trophies.toString()
            )
        }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        modifier = Modifier.size(56.dp),
                        painter = painterResource(Res.drawable.ic_coc_xp), contentDescription = null
                    )
                    Text(
                        text = player.expLevel.toString(),
                        fontSize = 20.sp,
                        color = Color.White,
                        style = LocalTextStyle.current.copy(
                            shadow = Shadow(color = Color.Black, blurRadius = 10f)
                        )
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = player.tag, color = MaterialTheme.colorScheme.onSurface)
                    player.role?.readableName?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
                ClashLabelFlowRow(player.labels)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatRow(
                    label = "TH",
                    value = player.townHallLevel.toString(),
                    icon = null
                )
                StatRow(
                    label = "War",
                    value = player.warStars.toString(),
                    icon = painterResource(Res.drawable.ic_star)
                )
                StatRow(
                    label = "Best",
                    value = player.bestTrophies.toString(),
                    icon = painterResource(Res.drawable.ic_trophy)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatRow(
                    label = "Attacks",
                    value = player.attackWins.toString(),
                    icon = painterResource(Res.drawable.ic_sward)
                )
                StatRow(
                    label = "Defences",
                    value = player.defenseWins.toString(),
                    icon = painterResource(Res.drawable.ic_sward)
                )
            }

            if (player.donations != null && player.donationsReceived != null) {
                ClashInfoRowCard(
                    infoList = listOf(
                        Pair("Troops Donated", player.donations.toString()),
                        Pair("Troops Received", player.donationsReceived.toString()),
                    )
                )
            }
            if (showClanInfo) {
                player.clan?.let {
                    Spacer(Modifier.size(8.dp))
                    ClanInfo(it)
                }
            }
        }
    }
}