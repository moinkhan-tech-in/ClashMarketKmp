package com.clash.market.ui.screens.mywar

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_builder_angry
import clashmarket.composeapp.generated.resources.ic_builder_sleeping
import clashmarket.composeapp.generated.resources.ic_wall_breaker_barrel
import com.clash.market.base.ResultState
import com.clash.market.components.ClashChipLight
import com.clash.market.components.ClashLoadingIndicator
import com.clash.market.components.clash.ClashMessageInfo
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.contents.wardetail.ClanWarDetailContent
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
            is MyWarUiState.LeagueWar -> "League War Details"
            is MyWarUiState.StandardWar -> "War Details"
            is MyWarUiState.Loading -> ""
            else -> null
        }
    }
    HomeScreenScaffold(
        currentRoute = ScreenRouts.MyWar,
        overrideTitle = title,
        onBottomBarNavigate = onBottomBarNavigate,
        onNavigate = onNavigate,
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
            modifier = Modifier.fillMaxSize(),
            targetState = uiState
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                when (uiState) {
                    is MyWarUiState.Error -> {}

                    MyWarUiState.Ideal -> {}

                    is MyWarUiState.LeagueWar -> {
                        MyWarLeagueWarContent(
                            innerPaddingValues = innerPadding,
                            warLeagueGroupResponse = uiState.warLeagueGroupResponse,
                            onPlayerClick = { onNavigate(ScreenRouts.PlayerDetail(it)) },
                            onClanClick = { onNavigate(ScreenRouts.ClanDetail(it)) },
                            onRoundClick = { onNavigate(it) }
                        )
                    }

                    MyWarUiState.Loading -> {
                        ClashLoadingIndicator(modifier = Modifier.padding(top = innerPadding.calculateTopPadding() + 100.dp))
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

                    MyWarUiState.PrivateWarLog -> {
                        ClashMessageInfo(
                            icon = Res.drawable.ic_builder_angry,
                            message = "Looks like this clan’s war log is private, Chief. No war details can be shown right now."
                        )
                    }

                    is MyWarUiState.StandardWar -> {
                        ClanWarDetailContent(
                            paddingValues = PaddingValues(
                                top = innerPadding.calculateTopPadding() + 12.dp,
                                bottom = innerPadding.calculateBottomPadding() + 56.dp,
                                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current) + 12.dp,
                                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current) + 12.dp
                            ),
                            warState = ResultState.Success(uiState.warResult),
                            onPlayerClick = { onNavigate(ScreenRouts.PlayerDetail(it.tag)) },
                            onClanClick = { onNavigate(ScreenRouts.ClanDetail(it.tag.orEmpty())) }
                        )
                    }
                }
            }
        }
    }
}