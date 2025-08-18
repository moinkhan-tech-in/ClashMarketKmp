package com.clash.market.ui.contents.wardetail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_battle
import com.clash.market.base.ResultState
import com.clash.market.components.ResultStateLazyGrid
import com.clash.market.components.clash.ClanWarAttacksLog
import com.clash.market.components.clash.ClanWarSummaryInfo
import com.clash.market.components.widgets.ClashImageSpec
import com.clash.market.components.widgets.tabs.ClashButtonTabs
import com.clash.market.components.widgets.tabs.ClashTab
import com.clash.market.models.ClanDetail
import com.clash.market.models.Player
import com.clash.market.models.dtos.CurrentWarResponse

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ClanWarDetailContent(
    warState: ResultState<CurrentWarResponse>,
    paddingValues: PaddingValues = PaddingValues(),
    onPlayerClick: (Player) -> Unit,
    onClanClick: (ClanDetail) -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(1) }
    ResultStateLazyGrid(
        paddingValues = paddingValues,
        resultState = warState,
        idealContent = {}
    ) { war ->

        item {
            ClanWarSummaryInfo(
                war = war,
                onClanClick = onClanClick
            )
        }

        item {
            ClashButtonTabs(
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                tabs = listOf(
                    ClashTab(
                        index = 0,
                        title = war.clan?.name.orEmpty()
                    ),
                    ClashTab(
                        index = 1,
                        leadingImage = ClashImageSpec.Res(Res.drawable.ic_battle)
                    ),
                    ClashTab(
                        index = 2,
                        title = war.opponent?.name.orEmpty()
                    )
                ),
                selectedTabIndex = selectedIndex,
                onTabSelected = { selectedIndex = it.index },
            ) {
                when (selectedIndex) {
                    0 -> {

                        val clanAttacks = remember(war) {
                            war.clan?.members?.safeMembers()?.sortedBy { it.mapPosition }
                        }

                        PlayerWarAttackList(
                            players = clanAttacks.orEmpty(),
                            nameByTags = war.nameByTags,
                            onPlayerClick = onPlayerClick,
                        )
                    }

                    1 -> {
                        ClanWarAttacksLog(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            attacks = war.getAttackEvents(),
                            warState = war.state,
                            nameByTags = war.nameByTags,
                            clanTags = war.clanMembersTag,
                            opponentTags = war.opponentMembersTag,
                            membersMapPosition = war.membersMapPosition
                        )
                    }

                    2 -> {

                        val clanAttacks = remember(war) {
                            war.opponent?.members?.safeMembers()?.sortedBy { it.mapPosition }
                        }

                        PlayerWarAttackList(
                            players = clanAttacks.orEmpty(),
                            nameByTags = war.nameByTags,
                            onPlayerClick = onPlayerClick
                        )
                    }
                }
            }
        }
    }
}