package com.clash.market.ui.screens.dashboard

import androidx.compose.animation.Crossfade
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
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_builder_sleeping
import com.clash.market.base.ResultState
import com.clash.market.components.ClashChipLight
import com.clash.market.components.ResultStateCrossFade
import com.clash.market.components.clash.ClanInfo
import com.clash.market.components.clash.ClashMessageInfo
import com.clash.market.components.clash.PlayerAchievementInfo
import com.clash.market.components.clash.PlayerInfo
import com.clash.market.models.Player
import com.clash.market.navigation.ScreenRouts
import com.clash.market.openClashLink
import com.clash.market.openPlayerLink
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
        onBottomBarNavigate = onBottomBarNavigate,
        onNavigate = onNavigate,
        topBarAction = {
            Crossfade(uiState.playerProfileState) {
                if (it is ProfileState.Linked) {
                    ClashChipLight(text = "Open in Game") {
                        openClashLink(openPlayerLink(it.tag))
                    }
                }
            }
        }
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

                    item(span = StaggeredGridItemSpan.FullLine) {
                        if (uiState.playerProfileState.player is ResultState.Success) {
                            PlayerAchievementInfo(uiState.playerProfileState.player.data.achievements)
                        }
                    }
                }
            }

            ProfileState.NotLinked -> {
                ClashMessageInfo(
                    icon = Res.drawable.ic_builder_sleeping,
                    message = "Link your village to view your profile, clan wars and more.",
                    btnText = "Link My Village!",
                    onClick = { onNavigate(ScreenRouts.EnterProfile) }
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