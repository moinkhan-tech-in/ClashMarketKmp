package com.clash.market.ui.contents.playerdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.components.ResultStateCrossFade
import com.clash.market.components.clash.ClanInfo
import com.clash.market.components.clash.PlayerAchievementInfo
import com.clash.market.components.clash.PlayerInfo
import com.clash.market.components.clash.PlayerItemsInfoFlowRow
import com.clash.market.theme.ClashFont
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun PlayerDetailContent(
    playerTag: String,
    viewModel: PlayerDetailContentViewModel = getKoin().get<PlayerDetailContentViewModel>()
) {
    val playerSearchState = viewModel.playerSearchState.collectAsStateWithLifecycle()
    LaunchedEffect(playerTag) { viewModel.fetchPlayer(playerTag) }
    ResultStateCrossFade(
        resultState = playerSearchState.value,
        idealContent = {
            Text(
                modifier = Modifier.padding(top = 120.dp),
                text = "Chief, Start searching for a player.",
                fontFamily = ClashFont
            )
        }
    ) { result ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(minSize = 300.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(
                top = 12.dp, bottom = 56.dp,
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

            item {
                PlayerAchievementInfo(result.achievements)
            }
        }
    }
}