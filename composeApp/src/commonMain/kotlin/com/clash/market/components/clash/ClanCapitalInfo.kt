package com.clash.market.components.clash

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_capital_trophy
import com.clash.market.components.ClashCard
import com.clash.market.components.ClashChip
import com.clash.market.components.ClashInfoRowCard
import com.clash.market.models.ClanCapital
import com.clash.market.models.League

@Composable
fun ClanCapitalInfo(
    points: Int,
    league: League? = null,
    detail: ClanCapital
) {
    ClashCard(
        title = "Clan Capital (Lv. ${detail.capitalHallLevel})",
        topEndContent = {
            ClashChip(
                trailingImage = Res.drawable.ic_capital_trophy,
                text = "Trophy: $points",
                trailingImageModifier = Modifier.size(30.dp, 36.dp)
            )
        }
    ) {
        val infoList = detail.districts?.map {
            Pair(
                it.name.orEmpty(),
                it.districtHallLevel.toString()
            )
        }.orEmpty()

        if (infoList.isNotEmpty()) {
            ClashInfoRowCard(infoList)
        }
    }
}