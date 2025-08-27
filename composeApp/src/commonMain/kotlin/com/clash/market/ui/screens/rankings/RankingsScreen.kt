package com.clash.market.ui.screens.rankings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_chevron_down
import clashmarket.composeapp.generated.resources.ic_nav_logo
import com.clash.market.base.ResultState
import com.clash.market.components.ClashChipLight
import com.clash.market.components.ResultStateLazyGrid
import com.clash.market.components.clash.ClashLocationsSheet
import com.clash.market.components.clash.ClashScaffold
import com.clash.market.components.clash.TopBuilderBaseClanItem
import com.clash.market.components.clash.TopBuilderBasePlayerItem
import com.clash.market.components.clash.TopClanItem
import com.clash.market.components.clash.TopPlayerItem
import com.clash.market.components.widgets.tabs.ClashScrollableTabs
import com.clash.market.components.widgets.tabs.ClashTab
import com.clash.market.navigation.ScreenRouts
import org.koin.compose.viewmodel.koinViewModel

internal val tabs = listOf(
    ClashTab(index = 0, title = "Top Players"),
    ClashTab(index = 1, title = "Top Clans"),
    ClashTab(index = 2, title = "Builder Players"),
    ClashTab(index = 3, title = "Builder Clans")
)

@Composable
fun RankingsScreen(
    viewModel: RankingsViewModel = koinViewModel(),
    onNavigate: (ScreenRouts) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RankingsScreenContent(
        uiState = uiState,
        onNavigate = onNavigate,
        onEvent = viewModel::onUiEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RankingsScreenContent(
    uiState: RankingUiState,
    onNavigate: (ScreenRouts) -> Unit,
    onEvent: (RankingUiEvent) -> Unit
) {
    var showLocationSheet by remember { mutableStateOf(false) }

    if (uiState.locations is ResultState.Success) {
        ClashLocationsSheet(
            show = showLocationSheet,
            locations = uiState.locations.data,
            onItemClick = { onEvent(RankingUiEvent.LocationChange(it)) },
            onDismiss = { showLocationSheet = false }
        )
    }

    ClashScaffold(
        title = "Ranking",
        ignoreStatusBarAlphaChange = true,
        navigationIcon = Res.drawable.ic_nav_logo,
        onNavigationIconClick = { onNavigate(ScreenRouts.MyProfile) },
        topBarAction = {
            if (uiState.locations is ResultState.Success) {
                ClashChipLight(
                    text = uiState.selectedLocation.name,
                    trailingIcon = Res.drawable.ic_chevron_down,
                    onClick = { showLocationSheet = true }
                )
            }
        }
    ) { innerPadding ->

        var selectedTabIndex by rememberSaveable { mutableStateOf(0) }

        LaunchedEffect(selectedTabIndex) {
            onEvent(RankingUiEvent.TabChange(selectedTabIndex))
        }

        val paddingValues = PaddingValues(
            top = 12.dp,
            start = innerPadding.calculateStartPadding(LocalLayoutDirection.current) + 12.dp,
            end = innerPadding.calculateEndPadding(LocalLayoutDirection.current) + 12.dp,
            bottom = innerPadding.calculateBottomPadding()
        )

        ClashScrollableTabs(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it.index }
        ) {
            when (it) {
                0 -> {
                    ResultStateLazyGrid(
                        paddingValues = paddingValues,
                        resultState = uiState.topPlayers
                    ) { players ->
                        items(players, key = { it.tag }) { player ->
                            TopPlayerItem(
                                player = player,
                                onClick = { onNavigate(ScreenRouts.PlayerDetail(player.tag)) }
                            )
                        }
                    }
                }

                1 -> {
                    ResultStateLazyGrid(
                        paddingValues = paddingValues,
                        resultState = uiState.topClans
                    ) { clans ->
                        items(clans, key = { it.tag ?: it.name ?: "" }) { clan ->
                            TopClanItem(
                                clan = clan,
                                onClick = { onNavigate(ScreenRouts.ClanDetail(clan.tag.orEmpty())) }
                            )
                        }
                    }
                }

                2 -> {
                    ResultStateLazyGrid(
                        paddingValues = paddingValues,
                        resultState = uiState.topBuilderPlayers
                    ) { players ->
                        items(players, key = { it.tag }) { player ->
                            TopBuilderBasePlayerItem(
                                player = player,
                                onClick = { onNavigate(ScreenRouts.PlayerDetail(player.tag)) }
                            )
                        }
                    }
                }

                3 -> {
                    ResultStateLazyGrid(
                        paddingValues = paddingValues,
                        resultState = uiState.topBuilderClans
                    ) { clans ->
                        items(clans, key = { it.tag ?: it.name ?: "" }) { clan ->
                            TopBuilderBaseClanItem(
                                clan = clan,
                                onClick = { onNavigate(ScreenRouts.ClanDetail(clan.tag.orEmpty())) }
                            )
                        }
                    }
                }
            }
        }
    }
}