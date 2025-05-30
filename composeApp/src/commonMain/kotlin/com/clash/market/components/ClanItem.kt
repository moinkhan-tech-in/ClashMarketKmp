package com.clash.market.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_trophy
import coil3.compose.AsyncImage
import com.clash.market.models.ClanDetail
import com.clash.market.models.FakeClanDetailItem
import com.clash.market.theme.ClashFont
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun ClanListItem(
    clanDetail: ClanDetail,
    onClick: () -> Unit = {}
) {
    ClashCard(
        modifier = Modifier.fillMaxWidth(),
        title = "${clanDetail.name} (Lv ${clanDetail.clanLevel})",
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
                    Text(text = clanDetail.tag.orEmpty(), fontFamily = ClashFont)
                    ClashTooltipBox(tooltipText = "Total Members") {
                        ClashLabel(
                            leadingImage = Icons.Default.Group,
                            label = "${clanDetail.members}/50"
                        )
                    }
                }
                clanDetail.labels?.let { ClashLabelFlowRow(it) }
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
