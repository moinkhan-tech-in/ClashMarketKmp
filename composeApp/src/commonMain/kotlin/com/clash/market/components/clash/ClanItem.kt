package com.clash.market.components.clash

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_trophy
import coil3.compose.AsyncImage
import com.clash.market.components.ClashCard
import com.clash.market.components.ClashChip
import com.clash.market.components.ClashInfoRowCard
import com.clash.market.components.ClashLabel
import com.clash.market.components.ClashLabelFlowRow
import com.clash.market.components.ClashTooltipBox
import com.clash.market.copyToClipboard
import com.clash.market.models.ClanDetail
import com.clash.market.models.FakeClanDetailItem
import com.clash.market.theme.ClashFont
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun ClanListItem(
    clanDetail: ClanDetail,
    onClick: () -> Unit = {},
    onWarLogsClick: () -> Unit = {}
) {
    ClashCard(
        modifier = Modifier.fillMaxWidth(),
        title = "${clanDetail.name} (Lv. ${clanDetail.clanLevel})",
        onClick = onClick,
        topEndContent = {
            clanDetail.requiredTrophies?.let {
                ClashChip(
                    text = "Required: ${clanDetail.requiredTrophies}",
                    trailingImage = Res.drawable.ic_trophy
                )
            }
        }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    modifier = Modifier.size(56.dp),
                    model = clanDetail.badgeUrls?.small.orEmpty(),
                    contentDescription = null
                )
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp).weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = clanDetail.tag.orEmpty(),
                        fontFamily = ClashFont,
                        modifier = Modifier.clickable {
                            copyToClipboard("Clan Tag", clanDetail.tag.orEmpty())
                        }
                    )
                    ClashTooltipBox(tooltipText = "Total Members") {
                        ClashLabel(
                            leadingImage = Icons.Default.Group,
                            label = "${clanDetail.getMemberCount()}/50"
                        )
                    }
                }
                clanDetail.labels?.let { ClashLabelFlowRow(it) }
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
                    .clickable { onWarLogsClick() }
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("War Stats")
                    Text("Tap to see war logs >>", color = Color(0xFF388E3C))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Wins: ${clanDetail.warWins}")
                    Text(text = "Losses: ${clanDetail.warLosses}")
                    Text(text = "Ties: ${clanDetail.warTies}")
                }

            }


            val data = listOf(
                "Location" to clanDetail.location?.name.orEmpty(),
                "War League" to clanDetail.warLeague?.name.orEmpty(),
                "War Frequency" to clanDetail.warFrequency?.displayName.orEmpty()
            )
            ClashInfoRowCard(infoList = data, Modifier.fillMaxWidth(1f))
        }
    }
}

@Composable
@Preview
private fun ClanItemPreview() {
    ClanListItem(FakeClanDetailItem)
}
