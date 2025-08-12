package com.clash.market.components.clash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_star
import clashmarket.composeapp.generated.resources.ic_sward
import clashmarket.composeapp.generated.resources.ic_wall_breaker_barrel
import coil3.compose.AsyncImage
import com.clash.market.ClashTheme
import com.clash.market.components.AnimatedTimeText
import com.clash.market.components.ClashAttackStars
import com.clash.market.components.ClashCard
import com.clash.market.components.ClashCardItem
import com.clash.market.components.ClashChip
import com.clash.market.components.ClashTripleInfoRowCard
import com.clash.market.models.Attack
import com.clash.market.models.ClanDetail
import com.clash.market.models.FakeClanDetailItem
import com.clash.market.models.WarState
import com.clash.market.models.dtos.CurrentWarResponse
import com.clash.market.theme.LocalClashColors
import com.clash.market.ui.screens.warlogs.LossGradiant
import com.clash.market.ui.screens.warlogs.WinGradiant
import com.clash.market.utils.ArrowSide
import com.clash.market.utils.calloutRectWithArrow
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ClanWarSummaryInfo(
    war: CurrentWarResponse
) {
    val modifier = when (war.result) {
        "win" -> Modifier.background(WinGradiant)
        "lose" -> Modifier.background(LossGradiant)
        else -> Modifier
    }
    ClashCard(
        modifier = modifier,
        title = "${war.state?.readableName ?: war.result}",
        topEndContent = {
            ClanWarTopEndContent(war)
        }
    ) {
        when {
            listOf(
                WarState.IN_WAR,
                WarState.PREPARATION,
                WarState.WAR_ENDED
            ).any { war.state == it } -> {
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
internal fun ClanWarAttacksInfo(
    attacks: List<Attack>,
    nameByTags: HashMap<String, String>,
    clanTags: HashSet<String>,
    opponentTags: HashSet<String>,
    membersMapPosition: HashMap<String, Int?>
) {
    Column(
        modifier = Modifier.padding(start = 8.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 28.dp),
            text = "Attack Events",
            style = MaterialTheme.typography.titleMedium
        )
        AttackEventSequenceItem(null) {
            Text(
                text = "War End",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .background(
                        color = LocalClashColors.current.clashNegative.copy(alpha = .7f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }

        attacks.forEachIndexed { index, item ->
            AttackEventBodyItem(
                attack = item,
                nameByTags = nameByTags,
                membersMapPosition = membersMapPosition,
                isOpponentAttack = opponentTags.contains(item.attackerTag)
            )
        }

        AttackEventSequenceItem(null) {
            Text(
                text = "War Start",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .background(
                        color = LocalClashColors.current.clashPositive.copy(alpha = .7f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }

}

@Composable
private fun AttackEventBodyItem(
    attack: Attack,
    nameByTags: HashMap<String, String>,
    membersMapPosition: HashMap<String, Int?>,
    isOpponentAttack: Boolean
) {
    AttackEventSequenceItem(number = attack.order ?: 0) {
        val direction = when (isOpponentAttack) {
            true -> LocalLayoutDirection provides LayoutDirection.Rtl
            false -> LocalLayoutDirection provides LayoutDirection.Ltr
        }

        val arrowSide = when (isOpponentAttack) {
            true -> ArrowSide.Left
            false -> ArrowSide.Right
        }

        CompositionLocalProvider(direction) {
            ClashCardItem {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .graphicsLayer { clip = false }
                            .fillMaxWidth(.60f)
                            .background(
                                MaterialTheme.colorScheme.primary.copy(alpha = .1f),
                                shape = calloutRectWithArrow(
                                    arrowSide = arrowSide,
                                    arrowHeight = 64.dp,
                                    arrowWidth = 14.dp
                                )
                            )
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.size(30.dp).graphicsLayer {
                                if (isOpponentAttack) {
                                    rotationY = 180f
                                }
                            },
                            painter = painterResource(Res.drawable.ic_sward),
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            text = "${membersMapPosition[attack.attackerTag]}. ${nameByTags[attack.attackerTag].orEmpty()}",
                            textAlign = if (isOpponentAttack) TextAlign.End else TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium.copy(
                                textDirection = TextDirection.Ltr
                            )
                        )
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${attack.destructionPercentage.toString()}%",
                                style = MaterialTheme.typography.labelMedium
                            )
                            ClashAttackStars(attack.stars)
                        }
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = if (isOpponentAttack) TextAlign.Start else TextAlign.End,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelMedium.copy(
                            textDirection = TextDirection.Ltr
                        ),
                        text = "${membersMapPosition[attack.defenderTag]}. ${nameByTags[attack.defenderTag].orEmpty()}"
                    )
                }
            }
        }
    }
}

@Composable
private fun AttackEventSequenceItem(number: Int?, content: @Composable () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        number?.let {
            Box(
                modifier = Modifier
                    .height(78.dp),
                contentAlignment = Alignment.Center
            ) {
                // center the divider behind the bubble
                VerticalDivider(
                    modifier = Modifier
                        .align(Alignment.Center) // ⬅️ important
                        .fillMaxHeight()
                        .width(2.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                // Number bubble (true centered text)
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = .9f),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        style = MaterialTheme.typography.labelLarge,
                        text = if (number > 0) number.toString() else "",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        }
        content()
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
                    modifier = Modifier.padding(bottom = 8.dp)
                        .padding(horizontal = 12.dp),
                    text = "VS",
                    color = Color(0xFF8B6508),
                    style = MaterialTheme.typography.titleLarge.copy(
                        shadow = Shadow(color = Color.Black, blurRadius = 2f)
                    )
                )
                Spacer(Modifier.size(18.dp))
                Text(text = war.teamSize.toString(), style = MaterialTheme.typography.labelLarge)
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
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = clan.badgeUrls?.medium,
            modifier = Modifier.size(56.dp),
            contentDescription = clan.name
        )
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

        if (showAttackStats) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClashChip(text = clan.attacks.toString(), trailingImage = Res.drawable.ic_sward)
                Spacer(Modifier.size(12.dp))
                ClashChip(text = clan.stars.toString(), trailingImage = Res.drawable.ic_star)
            }
        }
    }
}

@Composable
@Preview
private fun ClanWarSummaryInfoPreview() {
    ClashTheme {
        ClanWarSummaryInfo(
            war = CurrentWarResponse(
                state = WarState.IN_WAR,
                clan = FakeClanDetailItem,
                opponent = FakeClanDetailItem
            )
        )
    }
}