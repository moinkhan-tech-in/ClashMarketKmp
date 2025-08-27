package com.clash.market.ui.screens.dashboard

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_builder_sleeping
import clashmarket.composeapp.generated.resources.ic_nav_logo
import com.clash.market.components.ClashChipLight
import com.clash.market.components.ResultStateLazyGrid
import com.clash.market.components.clash.ClanInfo
import com.clash.market.components.clash.ClashMessageInfo
import com.clash.market.components.clash.ClashScaffold
import com.clash.market.components.clash.PlayerAchievementInfo
import com.clash.market.components.clash.PlayerInfo
import com.clash.market.navigation.ScreenRouts
import com.clash.market.openClashLink
import com.clash.market.openPlayerLink
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel(),
    onNavigate: (ScreenRouts) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DashboardScreenContent(
        uiState = uiState,
        onNavigate = onNavigate
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenContent(
    uiState: DashboardUiState,
    onNavigate: (ScreenRouts) -> Unit
) {
    ClashScaffold(
        title = "Home",
        navigationIcon = Res.drawable.ic_nav_logo,
        onNavigationIconClick = { onNavigate(ScreenRouts.MyProfile) },
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
                ResultStateLazyGrid(
                    paddingValues = PaddingValues(
                        top = innerPadding.calculateTopPadding() + 12.dp,
                        bottom = innerPadding.calculateBottomPadding() + 56.dp,
                        start = innerPadding.calculateStartPadding(LocalLayoutDirection.current) + 12.dp,
                        end = innerPadding.calculateEndPadding(LocalLayoutDirection.current) + 12.dp
                    ),
                    resultState = uiState.playerProfileState.player
                ) {
                    item {
                        PlayerInfo(it)
                    }

                    item {
                        it.clan?.let { clan -> ClanInfo(clan) }
                    }

                    item(span = StaggeredGridItemSpan.FullLine) {
                        PlayerAchievementInfo(it.achievements)
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