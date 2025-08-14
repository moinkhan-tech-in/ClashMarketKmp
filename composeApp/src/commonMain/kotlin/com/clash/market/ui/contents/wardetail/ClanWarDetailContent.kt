package com.clash.market.ui.contents.wardetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clash.market.base.ResultState
import com.clash.market.components.ResultStateLazyGrid
import com.clash.market.components.clash.ClanWarAttacksInfo
import com.clash.market.components.clash.ClanWarSummaryInfo
import com.clash.market.components.widgets.tabs.ClashButtonTabs
import com.clash.market.components.widgets.tabs.ClashTab
import com.clash.market.models.dtos.CurrentWarResponse

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ClanWarDetailContent(warState: ResultState<CurrentWarResponse>) {
    var selectedIndex by remember { mutableIntStateOf(1) }
    ResultStateLazyGrid(
        resultState = warState,
        idealContent = {}
    ) { war ->

        item {
            ClanWarSummaryInfo(war)
        }

        item {
            ClashButtonTabs(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 4.dp),
                tabs = listOf(
                    ClashTab(index = 0, title = war.clan.name.orEmpty()),
                    ClashTab(index = 1, title = "Events"),
                    ClashTab(index = 2, title = war.opponent.name.orEmpty())
                ),
                selectedTabIndex = selectedIndex,
                onTabSelected = { selectedIndex = it.index },
            ) {
                when (selectedIndex) {
                    0 -> {

                        val clanAttacks = remember(war) {
                            war.clan.members?.safeMembers()?.sortedBy { it.mapPosition }
                        }

                        Column {
                            PlayerWarAttackList(
                                players = clanAttacks.orEmpty(),
                                nameByTags = war.nameByTags
                            )
                        }
                    }

                    1 -> {
                        ClanWarAttacksInfo(
                            attacks = war.getAttackEvents(),
                            nameByTags = war.nameByTags,
                            clanTags = war.clanMembersTag,
                            opponentTags = war.opponentMembersTag,
                            membersMapPosition = war.membersMapPosition,
                        )
                    }

                    2 -> {

                        val clanAttacks = remember(war) {
                            war.opponent.members?.safeMembers()?.sortedBy { it.mapPosition }
                        }

                        Column {
                            PlayerWarAttackList(
                                players = clanAttacks.orEmpty(),
                                nameByTags = war.nameByTags
                            )
                        }
                    }
                }
            }
        }
    }
}