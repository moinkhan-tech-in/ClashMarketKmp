package com.clash.market.ui.screens.dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_coc_xp
import clashmarket.composeapp.generated.resources.ic_trophy
import com.clash.market.components.ClashCard
import com.clash.market.components.ClashChip
import com.clash.market.components.ClashLabelFlowRow
import com.clash.market.models.Player
import com.clash.market.theme.ClashFont
import org.jetbrains.compose.resources.painterResource

@Composable
fun PlayerInfo(
    player: Player,
    onEdit: () -> Unit
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
                    fontFamily = ClashFont,
                    color = Color.White,
                    fontSize = 20.sp,
                    style = TextStyle(shadow = Shadow(color = Color.Black, blurRadius = 10f))
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = player.tag, fontFamily = ClashFont)
                Text(text = player.role.orEmpty(), fontFamily = ClashFont)
            }
            ClashLabelFlowRow(player.labels)
        }
    }
}