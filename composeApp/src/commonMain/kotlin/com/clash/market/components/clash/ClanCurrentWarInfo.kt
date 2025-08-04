package com.clash.market.components.clash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_star
import clashmarket.composeapp.generated.resources.ic_sward
import clashmarket.composeapp.generated.resources.ic_wall_breaker_barrel
import coil3.compose.AsyncImage
import com.clash.market.components.AnimatedTimeText
import com.clash.market.components.ClashCard
import com.clash.market.components.ClashChip
import com.clash.market.components.ClashTripleInfoRowCard
import com.clash.market.models.ClanDetail
import com.clash.market.models.WarState
import com.clash.market.models.dtos.CurrentWarResponse
import com.clash.market.models.dtos.FakeCurrentWarResponse
import com.clash.market.theme.ClashFont
import com.clash.market.ui.screens.warlogs.LossGradiant
import com.clash.market.ui.screens.warlogs.WinGradiant
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ClanCurrentWarInfo(
    war: CurrentWarResponse
) {
    val modifier = when (war.result) {
        "win" -> Modifier.background(WinGradiant)
        "lose" -> Modifier.background(LossGradiant)
        else -> Modifier
    }
    ClashCard(
        modifier = modifier,
        title = "War State - ${war.state?.readableName ?: war.result}",
        topEndContent = {
            ClanWarTopEndContent(war)
        }
    ) {
        when {
            listOf(WarState.IN_WAR, WarState.PREPARATION, WarState.WAR_ENDED).any { war.state == it } -> {
                ClanWarCard(war = war, showAttackStats = war.state != WarState.PREPARATION)
            }

            war.state == WarState.NOT_IN_WAR -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(Res.drawable.ic_wall_breaker_barrel),
                        contentDescription = null
                    )
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Your clan hasn’t entered the war zone… for now.",
                        lineHeight = 18.sp
                    )
                }
            }

            war.result != null -> {
                ClanWarCard(war = war, showAttackStats = false)
            }

            else -> {}
        }
    }
}

@Composable
fun ClanWarTopEndContent(war: CurrentWarResponse) {
    when {
        WarState.NOT_IN_WAR == war.state -> {}
        WarState.PREPARATION == war.state || WarState.IN_WAR == war.state -> {
            var remainTime by remember { mutableStateOf("") }
            LaunchedEffect(Unit) {
                while (true) {
                    remainTime = when (war.state) {
                        WarState.PREPARATION -> war.getPreparationTime()
                        WarState.IN_WAR -> war.getWarEndTime()
                        else -> ""
                    }
                    delay(1000)
                }
            }

            AnimatedTimeText(remainTime)
        }

        WarState.WAR_ENDED == war.state -> {}

        war.result != null -> {
            AnimatedTimeText(war.getWarEndedTime())
        }

        else -> {}
    }
}

@Composable
private fun ClanWarCard(
    war: CurrentWarResponse,
    showAttackStats: Boolean = true
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ClanCard(clan = war.clan, showAttackStats = showAttackStats)
            Column(
                modifier = Modifier.padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "VS",
                    fontFamily = ClashFont,
                    color = Color(0xFF8B6508),
                    fontSize = 22.sp,
                    style = TextStyle.Default.copy(
                        shadow = Shadow(color = Color.Black, blurRadius = 2f)
                    )
                )
                Spacer(Modifier.size(18.dp))
                Text(text = war.teamSize.toString(), fontFamily = ClashFont)
                Icon(Icons.Default.Groups, contentDescription = null)
            }
            ClanCard(clan = war.opponent, showAttackStats = showAttackStats)
        }

        val infoList = war.getDetailsForWarCard()
        AnimatedVisibility(infoList.isNotEmpty()) {
            ClashTripleInfoRowCard(
                modifier = Modifier.padding(top = 12.dp),
                infoList = infoList
            )
        }
    }
}

@Composable
private fun ClanCard(
    clan: ClanDetail,
    showAttackStats: Boolean = true
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = clan.badgeUrls?.medium,
            modifier = Modifier.size(56.dp),
            contentDescription = clan.name
        )
        Text(text = clan.name.orEmpty(), color = MaterialTheme.colorScheme.onSurface)
        Text(text = clan.tag.orEmpty(), color = MaterialTheme.colorScheme.onSurface)

        if (showAttackStats) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClashChip(text = clan.stars.toString(), trailingImage = Res.drawable.ic_star)
                Spacer(Modifier.size(12.dp))
                ClashChip(text = clan.attacks.toString(), trailingImage = Res.drawable.ic_sward)
            }
        }
    }
}

@Composable
@Preview
private fun ClanInWarCardPreview() {
    ClanWarCard(FakeCurrentWarResponse)
}