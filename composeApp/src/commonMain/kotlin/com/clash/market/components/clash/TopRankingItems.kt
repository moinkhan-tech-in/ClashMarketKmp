package com.clash.market.components.clash

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_bb_Trophy
import clashmarket.composeapp.generated.resources.ic_trophy
import coil3.compose.AsyncImage
import com.clash.market.ClashTheme
import com.clash.market.components.ClashCard
import com.clash.market.components.ClashChipColumn
import com.clash.market.components.ClashLabel
import com.clash.market.components.widgets.ClashTooltipBox
import com.clash.market.copyToClipboard
import com.clash.market.models.ClanDetail
import com.clash.market.models.FakeClanDetailItem
import com.clash.market.models.FakePlayer
import com.clash.market.models.Player
import com.clash.market.theme.ClashTypography
import com.clash.market.theme.LocalClashColors
import com.clash.market.utils.glowingBorder
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TopPlayerItem(
    player: Player,
    onClick: () -> Unit
) {

    ClashCard(
        modifier = getGlowingBorderModifier(player.rank),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RankLabel(player.rank, player.previousRank)

            AsyncImage(model = player.league?.iconUrls?.medium, contentDescription = null, modifier = Modifier.size(40.dp))

            PlayerExp(player.expLevel.toString(), size = 40.dp, textSize = 14.sp)

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    modifier = Modifier.clickable { copyToClipboard("PlayerTag", player.tag) },
                    text = player.name,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(player.clan?.name.orEmpty(), style = MaterialTheme.typography.labelMedium)
            }

            ClashChipColumn(topImage = Res.drawable.ic_trophy, text = "${player.trophies}")
        }
    }
}

@Composable
fun TopBuilderBasePlayerItem(
    player: Player,
    onClick: () -> Unit
) {
    ClashCard(modifier = getGlowingBorderModifier(player.rank)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RankLabel(player.rank, player.previousRank)

            AsyncImage(model = player.league?.iconUrls?.medium, contentDescription = null, modifier = Modifier.size(44.dp))

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(player.name, style = ClashTypography.labelMedium)
                Text(player.clan?.name.orEmpty(), style = ClashTypography.labelSmall)
            }

            ClashChipColumn(topImage = Res.drawable.ic_bb_Trophy, text = "${player.builderBaseTrophies}")
        }
    }
}

@Composable
fun TopClanItem(
    clan: ClanDetail,
    onClick: () -> Unit
) {
    ClashCard(
        modifier = getGlowingBorderModifier(clan.rank),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ClanDetailSection(clan)
            ClashChipColumn(topImage = Res.drawable.ic_trophy, text = "${clan.clanPoints}")
        }
    }
}

@Composable
private fun getGlowingBorderModifier(rank: Int?): Modifier {
    return when (rank) {
        1 -> Modifier.glowingBorder(color = LocalClashColors.current.ClashGold)
        2 -> Modifier.glowingBorder(color = LocalClashColors.current.ClashSilver)
        3 -> Modifier.glowingBorder(color = LocalClashColors.current.ClashBronze)
        else -> Modifier
    }
}


@Composable
fun TopBuilderBaseClanItem(
    clan: ClanDetail,
    onClick: () -> Unit
) {
    ClashCard(
        modifier = getGlowingBorderModifier(clan.rank),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ClanDetailSection(clan)
            ClashChipColumn(topImage = Res.drawable.ic_bb_Trophy, text = "${clan.clanBuilderBasePoints}")
        }
    }
}

@Composable
private fun RowScope.ClanDetailSection(clan: ClanDetail) {

    RankLabel(clan.rank, clan.previousRank)

    AsyncImage(
        modifier = Modifier.size(52.dp),
        model = clan.badgeUrls?.medium.orEmpty(),
        contentDescription = null
    )

    Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = "${clan.name} (Lv. ${clan.clanLevel})",
            style = MaterialTheme.typography.labelMedium
        )
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            ClashTooltipBox(tooltipText = "Total Members") {
                ClashLabel(
                    leadingImage = Icons.Default.Group,
                    label = clan.getMemberCount().toString()
                )
            }
            Text(
                text = clan.tag.orEmpty(),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun RankLabel(rank: Int?, previousRank: Int?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier.width(34.dp),
            text = "#${rank.toString()}",
            textAlign = TextAlign.Center,
            maxLines = 1,
            style = MaterialTheme.typography.labelLarge
        )

        val textColor = when {
            (rank ?: 0) < (previousRank ?: rank ?: 0) -> LocalClashColors.current.clashPositive
            (rank ?: 0) > (previousRank ?: rank ?: 0) -> LocalClashColors.current.clashNegative
            else -> Color.Black
        }

        val rankText = when {
            (rank ?: 0) < (previousRank ?: rank ?: 0) -> "${previousRank.toString()}"
            (rank ?: 0) > (previousRank ?: rank ?: 0) -> "${previousRank.toString()}"
            else -> "${previousRank.toString()}"
        }

        Text(
            text = rankText,
            textAlign = TextAlign.Center,
            maxLines = 1,
            style = MaterialTheme.typography.labelSmall,
            color = textColor
        )
    }
}

@Composable
@Preview
fun TopPlayerItemPreview() {
    ClashTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TopPlayerItem(FakePlayer.copy(rank = 100), onClick = {})
            TopBuilderBasePlayerItem(FakePlayer.copy(previousRank = 10), onClick = {})
            TopClanItem(FakeClanDetailItem, onClick = {})
            TopBuilderBaseClanItem(FakeClanDetailItem, onClick = {})
        }
    }
}