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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun PlayerItemsInfoFlowRow(
    title: String,
    playerItems: List<PlayerItem>
) {
    val village = playerItems.map { it.village }.distinct()
    var currentVillage by remember { mutableStateOf(village.getOrNull(0)) }

    val filteredItems = remember(currentVillage) {
        playerItems.filter { it.village == currentVillage }
    }

    ClashCard(
        title = title,
        topEndContent = {
            ClashQueueChip(items = village) { currentVillage = it }
        }
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            filteredItems.forEach { TroopCard(it) }
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
                modifier = Modifier.size(46.dp),
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