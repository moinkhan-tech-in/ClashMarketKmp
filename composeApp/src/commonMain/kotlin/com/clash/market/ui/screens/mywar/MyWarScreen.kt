package com.clash.market.ui.screens.mywar

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_builder_sleeping
import clashmarket.composeapp.generated.resources.ic_wall_breaker_barrel
import com.clash.market.components.ClashChipLight
import com.clash.market.components.ClashLoadingIndicator
import com.clash.market.components.clash.ClanCurrentWarInfo
import com.clash.market.components.clash.ClashMessageInfo
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.screens.home.HomeScreenScaffold
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MyWarScreen(
    viewModel: MyWarViewModel = koinViewModel<MyWarViewModel>(),
    onBottomBarNavigate: (ScreenRouts) -> Unit = {},
    onNavigate: (ScreenRouts) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MyWarContent(
        uiState = uiState,
        onNavigate = onNavigate,
        onBottomBarNavigate = onBottomBarNavigate
    )
}

@Composable
private fun MyWarContent(
    uiState: MyWarUiState,
    onBottomBarNavigate: (ScreenRouts) -> Unit,
    onNavigate: (ScreenRouts) -> Unit = {}
) {
    val title = remember(uiState) {
        when (uiState) {
            is MyWarUiState.LeagueWar -> "League War"
            is MyWarUiState.Loading -> ""
            else -> null
        }
    }
    HomeScreenScaffold(
        currentRoute = ScreenRouts.MyWar,
        overrideTitle = title,
        onBottomBarNavigate = onBottomBarNavigate,
        onNavigate = onNavigate,
        ignoreStatusBarAlphaChange = uiState is MyWarUiState.LeagueWar,
        topBarAction = {
            if (uiState is MyWarUiState.LeagueWar) {
                val season = uiState.warLeagueGroupResponse.season
                if (season.isNullOrEmpty().not()) {
                    ClashChipLight("Season: $season", trailingIcon = null, onClick = {})
                }
            }
        }
    ) { innerPadding ->

        Crossfade(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            targetState = uiState
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                when (uiState) {
                    is MyWarUiState.Error -> {}

                    MyWarUiState.Ideal -> {}

                    is MyWarUiState.LeagueWar -> {
                        MyWarLeagueWarContent(
                            warLeagueGroupResponse = uiState.warLeagueGroupResponse,
                            onPlayerClick = { onNavigate(ScreenRouts.PlayerDetail(it)) },
                            onClanClick = { onNavigate(ScreenRouts.ClanDetail(it)) },
                            onRoundClick = { onNavigate(it) }
                        )
                    }

                    MyWarUiState.Loading -> {
                        ClashLoadingIndicator()
                    }

                    MyWarUiState.NoProfileLinked -> {
                        ClashMessageInfo(
                            icon = Res.drawable.ic_builder_sleeping,
                            message = "Link your village to view your profile, clan wars and more.",
                            btnText = "Link My Village!",
                            onClick = { onNavigate(ScreenRouts.EnterProfile) }
                        )
                    }

                    MyWarUiState.NotInClan -> {
                        ClashMessageInfo(
                            icon = Res.drawable.ic_builder_sleeping,
                            message = "Link your village to view your profile, clan wars and more.",
                            btnText = "Link My Village!",
                            onClick = { onNavigate(ScreenRouts.EnterProfile) }
                        )
                    }

                    MyWarUiState.NotInWar -> {
                        ClashMessageInfo(
                            icon = Res.drawable.ic_wall_breaker_barrel,
                            message = "Your clan hasn’t entered the war zone… for now."
                        )
                    }

                    is MyWarUiState.StandardWar -> {
                        ClanCurrentWarInfo(uiState.warResult)
                    }
                }
            }
        }

        /*LazyVerticalStaggeredGrid(
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
            }
        }*/
    }
}