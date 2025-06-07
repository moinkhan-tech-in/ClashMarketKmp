package com.clash.market.components.clash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_sward
import com.clash.market.components.ClashCard
import com.clash.market.models.PlayerItem
import com.clash.market.theme.ClashFont
import org.jetbrains.compose.resources.painterResource

@Composable
fun PlayerItemInfo(
    playerItem: PlayerItem
) {

}

@Composable
fun PlayerItemsInfoFlowRow(
    title: String,
    playerItems: List<PlayerItem>
) {
    ClashCard(title = title) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            playerItems.forEach { TroopCard(it) }
        }
    }
}

@Composable
fun TroopCard(
    troop: PlayerItem,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 4.dp,
        color = Color(0xFF2C2C2C).copy(alpha = 0.1f), // goldish clash tone
        modifier = modifier.padding(4.dp)
    ) {
        Box(modifier = Modifier.padding(6.dp)) {
            Image(
                modifier = Modifier.size(58.dp),
                painter = painterResource(Res.drawable.ic_sward),
                contentDescription = null
            )

            Text(
                text = "Lv. ${troop.level}",
                fontSize = 12.sp,
                fontFamily = ClashFont,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )
        }
    }
}