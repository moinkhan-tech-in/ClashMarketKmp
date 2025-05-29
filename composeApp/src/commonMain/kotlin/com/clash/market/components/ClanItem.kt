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
import coil3.compose.AsyncImage
import com.clash.market.models.ClanDetail
import com.clash.market.theme.ClashFont
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun ClanListItem(clanDetail: ClanDetail) {
    ClashCard(
        modifier = Modifier.fillMaxWidth(),
        title = "${clanDetail.name} (Level ${clanDetail.clanLevel})"
    ) {
        Row {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    modifier = Modifier.size(56.dp),
                    model = clanDetail.badgeUrls?.small.orEmpty(),
                    contentDescription = null
                )
                ClashTooltipBox(tooltipText = "Total Members") {
                    ClashLabel(
                        leadingImage = Icons.Default.Group,
                        label = clanDetail.members.toString()
                    )
                }
            }
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = clanDetail.tag, fontFamily = ClashFont)
                clanDetail.labels?.let { ClashLabelFlowRow(it) }
            }
        }
    }
}

private val FakeClanDetailItem = ClanDetail(
    tag = "#2G3G34FE",
    name = "Avengers",
    clanLevel = 12,
    members = 12,
    type = "open"
)

@Composable
@Preview
private fun ClanItemPreview() {
    ClanListItem(FakeClanDetailItem)
}
