package com.clash.market.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.base.ResultState
import com.clash.market.components.ResultStateCrossFade
import com.clash.market.components.clash.ClanCurrentWarInfo
import com.clash.market.components.clash.ClanInfo
import com.clash.market.components.clash.ClashLinkVillageMessage
import com.clash.market.components.clash.PlayerAchievementInfo
import com.clash.market.components.clash.PlayerInfo
import com.clash.market.models.Player
import com.clash.market.models.dtos.CurrentWarResponse
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.screens.home.HomeScreenScaffold
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel(),
    onBottomBarNavigate: (ScreenRouts) -> Unit,
    onNavigate: (ScreenRouts) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DashboardScreenContent(
        uiState = uiState,
        onBottomBarNavigate = onBottomBarNavigate,
        onNavigate = onNavigate
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenContent(
    uiState: DashboardUiState,
    onBottomBarNavigate: (ScreenRouts) -> Unit,
    onNavigate: (ScreenRouts) -> Unit
) {
    HomeScreenScaffold(
        currentRoute = ScreenRouts.Dashboard,
        onBottomBarNavigate = onBottomBarNavigate
    ) { innerPadding ->

        when (uiState.playerProfileState) {
            is ProfileState.Linked -> {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(minSize = 300.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalItemSpacing = 4.dp,
                    contentPadding = PaddingValues(
                        top = innerPadding.calculateTopPadding() + 12.dp,
                        bottom = 56.dp,
                        start = 12.dp, end = 12.dp
                    )
                ) {
                    item {
                        PlayerInfoStateUi(uiState.playerProfileState.player)
                    }

                    item {
                        ClanInfoStateUi(uiState.playerProfileState.player)
                    }

                    item {
                        CurrentWarStateUi(uiState.currentWar)
                    }

                    item(span = StaggeredGridItemSpan.FullLine) {
                        if (uiState.playerProfileState.player is ResultState.Success) {
                            PlayerAchievementInfo(uiState.playerProfileState.player.data.achievements)
                        }
                    }
                }
            }

            ProfileState.NotLinked -> {
                ClashLinkVillageMessage(
                    onLinkClick = {
                        onNavigate(ScreenRouts.EnterProfile)

                    }
                )
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
        it.clan?.let { clan -> ClanInfo(clan = clan) }
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