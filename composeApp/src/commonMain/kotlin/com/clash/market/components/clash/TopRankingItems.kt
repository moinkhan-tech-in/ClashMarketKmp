package com.clash.market.components.clash

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.clash.market.components.ClashTooltipBox
import com.clash.market.copyToClipboard
import com.clash.market.models.ClanDetail
import com.clash.market.models.FakeClanDetailItem
import com.clash.market.models.FakePlayer
import com.clash.market.models.Player
import com.clash.market.theme.ClashFont
import com.clash.market.theme.ClashTypography
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TopPlayerItem(
    player: Player,
    onClick: () -> Unit
) {
    ClashCard(onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                modifier = Modifier.width(38.dp),
                text = "#${player.rank.toString()}",
                textAlign = TextAlign.Center,
                fontFamily = ClashFont,
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 10.sp,
                    maxFontSize = 18.sp
                )
            )

            AsyncImage(model = player.league?.iconUrls?.medium, contentDescription = null, modifier = Modifier.size(44.dp))

            PlayerExp(player.expLevel.toString(), size = 44.dp, textSize = 16.sp)

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(player.name, style = ClashTypography.bodyMedium)
                Text(player.clan?.name.orEmpty(), style = ClashTypography.bodyMedium)
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
    ClashCard(onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                modifier = Modifier.width(38.dp),
                text = "#${player.rank.toString()}",
                textAlign = TextAlign.Center,
                fontFamily = ClashFont,
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 10.sp,
                    maxFontSize = 18.sp
                )
            )
            AsyncImage(model = player.league?.iconUrls?.medium, contentDescription = null, modifier = Modifier.size(44.dp))

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(player.name, style = ClashTypography.bodyMedium)
                Text(player.clan?.name.orEmpty(), style = ClashTypography.bodyMedium)
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
        title = "${clan.name} (Lv. ${clan.clanLevel})",
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                modifier = Modifier.width(38.dp),
                text = "#${clan.rank.toString()}",
                textAlign = TextAlign.Center,
                fontFamily = ClashFont,
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 10.sp,
                    maxFontSize = 18.sp
                )
            )

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
                    text = clan.tag.orEmpty(),
                    fontFamily = ClashFont,
                    modifier = Modifier.clickable {
                        copyToClipboard("Clan Tag", clan.tag.orEmpty())
                    }
                )
                ClashTooltipBox(tooltipText = "Total Members") {
                    ClashLabel(
                        leadingImage = Icons.Default.Group,
                        label = "${clan.getMemberCount()}/50"
                    )
                }
            }

            ClashChipColumn(topImage = Res.drawable.ic_trophy, text = "${clan.clanPoints}")
        }
    }
}


@Composable
fun TopBuilderBaseClanItem(
    clan: ClanDetail,
    onClick: () -> Unit
) {
    ClashCard(
        title = "${clan.name} (Lv. ${clan.clanLevel})",
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                modifier = Modifier.width(38.dp),
                text = "#${clan.rank.toString()}",
                textAlign = TextAlign.Center,
                fontFamily = ClashFont,
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 10.sp,
                    maxFontSize = 18.sp
                )
            )

            AsyncImage(
                modifier = Modifier.size(52.dp),
                model = clan.badgeUrls?.medium.orEmpty(),
                contentDescription = null
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = clan.tag.orEmpty(),
                    fontFamily = ClashFont,
                    modifier = Modifier.clickable {
                        copyToClipboard("Clan Tag", clan.tag.orEmpty())
                    }
                )
                ClashTooltipBox(tooltipText = "Total Members") {
                    ClashLabel(
                        leadingImage = Icons.Default.Group,
                        label = "${clan.getMemberCount()}/50"
                    )
                }
            }

            ClashChipColumn(topImage = Res.drawable.ic_bb_Trophy, text = "${clan.clanBuilderBasePoints}")
        }
    }
}

@Composable
@Preview
fun TopPlayerItemPreview() {
    ClashTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TopPlayerItem(FakePlayer, onClick = {})
            TopBuilderBasePlayerItem(FakePlayer, onClick = {})
            TopClanItem(FakeClanDetailItem, onClick = {})
            TopBuilderBaseClanItem(FakeClanDetailItem, onClick = {})
        }
    }
}