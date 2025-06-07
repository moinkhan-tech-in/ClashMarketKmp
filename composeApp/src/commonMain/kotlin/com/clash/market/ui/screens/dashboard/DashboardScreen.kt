package com.clash.market.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.base.ResultState
import com.clash.market.components.ResultStateCrossFade
import com.clash.market.components.clash.ClanCurrentWarInfo
import com.clash.market.components.clash.ClanInfo
import com.clash.market.components.clash.PlayerAchievementInfo
import com.clash.market.components.clash.PlayerInfo
import com.clash.market.models.Player
import com.clash.market.models.dtos.CurrentWarResponse
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = getKoin().get<DashboardViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DashboardScreenContent(uiState = uiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenContent(uiState: DashboardUiState) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 300.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(
            top = 12.dp,
            start = 12.dp, end = 12.dp,
            bottom = 56.dp
        )
    ) {
        item {
            PlayerInfoStateUi(uiState.player)
        }

        item {
            ClanInfoStateUi(uiState.player)
        }

        item {
            CurrentWarStateUi(uiState.currentWar)
        }

        if (uiState.player is ResultState.Success) {
            item {
                PlayerAchievementInfo(uiState.player.data.achievements)
            }
        }
    }
}

@Composable
private fun PlayerInfoStateUi(player: ResultState<Player>) {
    ResultStateCrossFade(
        resultState = player,
        idealContent = {}
    ) {
        PlayerInfo(
            player = it,
            onEdit = {}
        )
    }
}

@Composable
private fun ClanInfoStateUi(player: ResultState<Player>) {
    ResultStateCrossFade(
        resultState = player,
        idealContent = {}
    ) {
        ClanInfo(
            name = it.clan?.name.orEmpty(),
            tag = it.clan?.tag.orEmpty(),
            clanImage = it.clan?.badgeUrls?.small.orEmpty(),
            onShare = {

            }
        )
    }
}

@Composable
private fun CurrentWarStateUi(currentWar: ResultState<CurrentWarResponse>) {
    ResultStateCrossFade(
        resultState = currentWar,
        idealContent = {}
    ) {
        ClanCurrentWarInfo(it)
    }
}