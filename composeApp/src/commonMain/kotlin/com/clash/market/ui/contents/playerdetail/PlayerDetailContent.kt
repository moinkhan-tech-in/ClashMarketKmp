package com.clash.market.ui.contents.playerdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_builder_direction
import com.clash.market.base.ResultState
import com.clash.market.components.ResultStateCrossFade
import com.clash.market.components.clash.ClanInfo
import com.clash.market.components.clash.ClashMessageInfo
import com.clash.market.components.clash.PlayerAchievementInfo
import com.clash.market.components.clash.PlayerInfo
import com.clash.market.components.clash.PlayerItemsInfoFlowRow
import com.clash.market.models.Player

@Composable
fun PlayerDetailContent(
    playerResultState: ResultState<Player>,
    topPadding: Dp = 12.dp
) {
    ResultStateCrossFade(
        resultState = playerResultState,
        topPadding = topPadding + 80.dp,
        idealContent = {
            ClashMessageInfo(
                icon = Res.drawable.ic_builder_direction,
                message = "Looking for a warrior, Chief? Type in their tag!",
            )
        }
    ) { result ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(minSize = 300.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalItemSpacing = 4.dp,
            contentPadding = PaddingValues(
                top = topPadding, bottom = 56.dp,
                start = 12.dp, end = 12.dp
            )
        ) {
            item {
                PlayerInfo(player = result)
            }

            item {
                if (result.clan != null) {
                    ClanInfo(result.clan)
                }
            }

            item {
                PlayerItemsInfoFlowRow(
                    title = "Troops",
                    playerItems = result.troops
                )
            }

            item {
                PlayerItemsInfoFlowRow(
                    title = "Spells",
                    playerItems = result.spells
                )
            }

            item {
                PlayerItemsInfoFlowRow(
                    title = "Hero",
                    playerItems = result.heroes
                )
            }

            item {
                PlayerItemsInfoFlowRow(
                    title = "Hero Equipment",
                    playerItems = result.heroEquipment
                )
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                PlayerAchievementInfo(result.achievements)
            }
        }
    }
}