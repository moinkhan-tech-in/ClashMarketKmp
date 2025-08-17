package com.clash.market.components.clash

import PinnedCenterRowDynamic
import SideArrangement
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.HorizontalDivider
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
import centerPinned
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_shield
import clashmarket.composeapp.generated.resources.ic_star
import clashmarket.composeapp.generated.resources.ic_sward
import clashmarket.composeapp.generated.resources.ic_wall_breaker_barrel
import coil3.compose.AsyncImage
import com.clash.market.ClashTheme
import com.clash.market.components.ClashCard
import com.clash.market.components.ClashCardItem
import com.clash.market.components.ClashChip
import com.clash.market.components.ClashTripleInfoRowCard
import com.clash.market.components.widgets.AnimatedTimeText
import com.clash.market.components.widgets.ClashAttackStars
import com.clash.market.components.widgets.ClashImage
import com.clash.market.components.widgets.ClashImageSpec
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
import com.clash.market.utils.formatPercent
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.max

@Composable
internal fun ClanWarSummaryInfo(
    war: CurrentWarResponse,
    onClanClick: (ClanDetail) -> Unit,
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
                ClanWarCard(
                    war = war,
                    showAttackStats = war.state != WarState.PREPARATION,
                    onClanClick = onClanClick
                )
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
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            war.result != null -> {
                ClanWarCard(
                    war = war,
                    showAttackStats = false,
                    onClanClick = onClanClick
                )
            }

            else -> {}
        }
    }
}

@Composable
internal fun ClanWarAttacksLog(
    modifier: Modifier = Modifier,
    attacks: List<Attack>,
    nameByTags: HashMap<String, String>,
    clanTags: HashSet<String>,
    opponentTags: HashSet<String>,
    membersMapPosition: HashMap<String, Int?>
) {
    Column(modifier = modifier) {
        AttackEventStatusBodyItem(
            text = "War Ended",
            showTopDivider = false,
            bodyBackgroundColor = LocalClashColors.current.clashNegative.copy(alpha = .7f),
            indicatorBackgroundColor = LocalClashColors.current.clashNegative.copy(alpha = .7f)
        )

        attacks.forEachIndexed { index, item ->
            AttackEventBodyItem(
                attack = item,
                nameByTags = nameByTags,
                membersMapPosition = membersMapPosition,
                isOpponentAttack = opponentTags.contains(item.attackerTag)
            )
        }

        AttackEventStatusBodyItem(
            text = "War Started",
            showBottomDivider = false,
            indicatorBackgroundColor = LocalClashColors.current.clashPositive.copy(alpha = .7f),
            bodyBackgroundColor = LocalClashColors.current.clashPositive.copy(alpha = .7f)
        )
    }

}

@Composable
private fun AttackEventStatusBodyItem(
    text: String,
    bodyBackgroundColor: Color,
    showTopDivider: Boolean = true,
    showBottomDivider: Boolean = true,
    indicatorBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    imageSpec: ClashImageSpec? = null
) {
    AttackEventSequenceItem(
        modifier = Modifier.height(64.dp),
        showTopDivider = showTopDivider,
        showBottomDivider = showBottomDivider,
        indicatorBackgroundColor = indicatorBackgroundColor,
        imageSpec = imageSpec
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White,
            modifier = Modifier
                .padding(vertical = 12.dp)
                .background(
                    color = bodyBackgroundColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun AttackEventBodyItem(
    attack: Attack,
    nameByTags: HashMap<String, String>,
    membersMapPosition: HashMap<String, Int?>,
    isOpponentAttack: Boolean
) {
    AttackEventSequenceItem(
        modifier = Modifier.height(94.dp),
        number = attack.order ?: 0
    ) {
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
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .graphicsLayer { clip = false }
                            .fillMaxWidth(.60f)
                            .background(
                                color = when((attack.stars ?: 0) <= 0) {
                                    true -> LocalClashColors.current.clashNegative
                                        .copy(alpha = max(attack.destructionPercentage?.div(200f) ?: .1f, .1f))
                                    false -> LocalClashColors.current.clashPositive
                                        .copy(alpha = max(attack.destructionPercentage?.div(200f) ?: .1f, .1f))
                                },
                                shape = calloutRectWithArrow(
                                    arrowSide = arrowSide,
                                    arrowHeight = 64.dp,
                                    arrowWidth = 14.dp
                                )
                            )
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            ClashChip(
                                leadImageModifier = Modifier.size(24.dp).graphicsLayer {
                                    if (isOpponentAttack) { rotationY = 180f }
                                },
                                leadingImage = Res.drawable.ic_sward,
                                text = "Attacker"
                            )
                            Text(
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                text = "${membersMapPosition[attack.attackerTag]}. ${nameByTags[attack.attackerTag].orEmpty()}",
                                textAlign = if (isOpponentAttack) TextAlign.End else TextAlign.Start,
                                style = MaterialTheme.typography.labelMedium.copy(textDirection = TextDirection.Ltr)
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = attack.destructionPercentage?.toDouble().formatPercent(),
                                style = MaterialTheme.typography.labelMedium
                            )
                            ClashAttackStars(attack.stars)
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f).padding(all = 12.dp),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        ClashChip(
                            text = "Defender",
                            trailingImage = Res.drawable.ic_shield
                        )
                        Text(
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
}

@Composable
private fun AttackEventSequenceItem(
    modifier: Modifier = Modifier,
    number: Int = 0,
    indicatorBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    imageSpec: ClashImageSpec? = null,
    showTopDivider: Boolean = true,
    showBottomDivider: Boolean = true,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier.heightIn(max = 94.dp, min = 28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (showTopDivider) {
                VerticalDivider(
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                Spacer(Modifier.weight(1f))
            }

            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        color = indicatorBackgroundColor,
                        shape = RoundedCornerShape(12.dp),
                    ).border(1.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (number > 0) {
                    Text(
                        style = MaterialTheme.typography.labelSmall,
                        text = if (number > 0) "#$number" else "",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else if (imageSpec != null) {
                    ClashImage(modifier = Modifier.size(24.dp), image = imageSpec)
                }
            }

            if (showBottomDivider) {
                VerticalDivider(
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                Spacer(Modifier.weight(1f))
            }
        }
        HorizontalDivider(Modifier.width(12.dp))
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
    onClanClick: (ClanDetail) -> Unit,
    showAttackStats: Boolean = true
) {
    Column {
        PinnedCenterRowDynamic(
            modifier = Modifier.fillMaxWidth(),
            sideArrangement = SideArrangement.SpaceEvenly
        ) {
            ClanCard(
                clan = war.clan,
                showAttackStats = showAttackStats,
                onClick = { onClanClick(war.clan) })
            Column(
                modifier = Modifier.centerPinned().padding(top = 16.dp),
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
            ClanCard(
                clan = war.opponent,
                showAttackStats = showAttackStats,
                onClick = { onClanClick(war.opponent) })
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
    onClick: () -> Unit,
    showAttackStats: Boolean = true
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = clan.badgeUrls?.medium,
            modifier = Modifier.size(56.dp).clickable(onClick = onClick),
            contentDescription = clan.name
        )
        Text(
            modifier = Modifier.basicMarquee(),
            text = clan.name.orEmpty(),
            maxLines = 1,
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
            ),
            onClanClick = {}
        )
    }
}