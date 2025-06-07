package com.clash.market.components.clash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_star
import com.clash.market.components.ClashCard
import com.clash.market.components.ClashProgressBar
import com.clash.market.models.Achievement
import com.clash.market.models.FakeAchievement
import com.clash.market.theme.ClashFont
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PlayerAchievementInfo(achievements: List<Achievement>) {
    ClashCard(title = "Achievements") {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            achievements.forEachIndexed { index, item ->
                PlayerAchievementItem(item)
                if (index != achievements.lastIndex) {
                    HorizontalDivider(Modifier.size(1.dp))
                }
            }
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
            Column(
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
        }
        val value = if (achievement.value >= achievement.target) {
            "Completed"
        } else {
            "${achievement.value}/${achievement.target}"
        }
        ClashProgressBar(
            label = value,
            progress = (achievement.value.toFloat() / achievement.target.toFloat()).coerceIn(0f, 1f),
            height = 20.dp
        )
    }
}

@Composable
@Preview
private fun PlayerAchievementItemPreview() {
    PlayerAchievementItem(FakeAchievement)
}