package com.clash.market.ui.contents.wardetail

import androidx.compose.runtime.Composable
import com.clash.market.base.ResultState
import com.clash.market.components.ResultStateLazyGrid
import com.clash.market.components.clash.ClanWarAttacksInfo
import com.clash.market.components.clash.ClanWarSummaryInfo
import com.clash.market.models.dtos.CurrentWarResponse

@Composable
fun ClanWarDetailContent(warState: ResultState<CurrentWarResponse>) {
    ResultStateLazyGrid(
        resultState = warState,
        idealContent = {}
    ) {
        item {
            ClanWarSummaryInfo(it)
        }

        item {
            ClanWarAttacksInfo(
                attacks = it.getAttackEvents(),
                nameByTags = it.nameByTags,
                clanTags = it.clanMembersTag,
                opponentTags = it.opponentMembersTag,
                membersMapPosition = it.membersMapPosition,
            )
        }
    }
}