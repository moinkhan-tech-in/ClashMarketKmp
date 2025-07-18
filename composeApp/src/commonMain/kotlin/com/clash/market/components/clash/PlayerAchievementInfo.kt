package com.clash.market.components.clash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_star
import com.clash.market.components.AutoColumnGrid
import com.clash.market.components.ClashCard
import com.clash.market.components.ClashProgressBar
import com.clash.market.models.Achievement
import com.clash.market.models.FakeAchievement
import com.clash.market.theme.ClashFont
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PlayerAchievementInfo(achievements: List<Achievement>) {

    val village = achievements.map { it.village }.distinct()
    var currentVillage by remember { mutableStateOf(village.getOrNull(0)) }

    val filteredAchievements = remember(currentVillage) {
        achievements.filter { it.village == currentVillage }
    }

    ClashCard(
        title = "Achievements",
        topEndContent = { ClashQueueChip(items = village) { currentVillage = it } }
    ) {
        AutoColumnGrid(items = filteredAchievements) {
            PlayerAchievementItem(it)
        }
    }
}

@Composable
private fun PlayerAchievementItem(achievement: Achievement) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(achievement.name, fontFamily = ClashFont)
                achievement.completionInfo?.let {
                    Text(
                        it,
                        fontFamily = ClashFont,
                        fontSize = 12.sp
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(resource = Res.drawable.ic_star),
                    contentDescription = null
                )
                Text(
                    text = "${achievement.stars}/3",
                    fontFamily = ClashFont
                )
            }
        }
        val value = if (achievement.value >= achievement.target) {
            "Completed"
        } else {
            "${achievement.value}/${achievement.target}"
        }
        ClashProgressBar(
            label = value,
            progress = (achievement.value.toFloat() / achievement.target.toFloat()).coerceIn(
                0f,
                1f
            ),
            height = 20.dp
        )
    }
}

@Composable
@Preview
private fun PlayerAchievementItemPreview() {
    PlayerAchievementItem(FakeAchievement)
}