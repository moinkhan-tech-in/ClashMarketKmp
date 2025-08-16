package com.clash.market.ui.screens.leaguewardetail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.base.ResultState
import com.clash.market.components.ClashChipLight
import com.clash.market.components.clash.ClashScaffold
import com.clash.market.components.widgets.tabs.ClashScrollableTabs
import com.clash.market.components.widgets.tabs.ClashTab
import com.clash.market.models.ClanDetail
import com.clash.market.models.Player
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.contents.wardetail.ClanWarDetailContent

@Composable
fun LeagueWarDetailScreen(
    viewModel: LeagueWarDetailViewModel,
    onNavigate: (ScreenRouts) -> Unit,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LeagueWarDetailContent(
        uiState = uiState,
        leagueWarDetail = viewModel.leagueWarDetailRoute,
        onTabChange = {
            viewModel.fetchWarDetailByTag(
                viewModel.leagueWarDetailRoute.warTags.getOrNull(
                    it
                ).orEmpty()
            )
        },
        onBackClick = onBackClick,
        onPlayerClick = { onNavigate(ScreenRouts.PlayerDetail(it.tag)) },
        onClanClick = { onNavigate(ScreenRouts.ClanDetail(it.tag.orEmpty())) }
    )
}

@Composable
private fun LeagueWarDetailContent(
    uiState: LeagueWarDetailUiState,
    leagueWarDetail: ScreenRouts.LeagueWarDetail,
    onTabChange: (Int) -> Unit,
    onPlayerClick: (Player) -> Unit,
    onClanClick: (ClanDetail) -> Unit,
    onBackClick: () -> Unit
) {
    ClashScaffold(
        title = leagueWarDetail.title,
        onBackClick = onBackClick,
        ignoreStatusBarAlphaChange = true,
        topBarAction = {
            ClashChipLight("Season: ${leagueWarDetail.season}", trailingIcon = null, onClick = {})
        }
    ) { innerPadding ->

        var selectedTabIndex by remember { mutableStateOf(0) }
        LaunchedEffect(selectedTabIndex) { onTabChange(selectedTabIndex) }

        ClashScrollableTabs(
            modifier = Modifier.fillMaxWidth().padding(top = innerPadding.calculateTopPadding()),
            tabs = leagueWarDetail.warTags.mapIndexed { index, string ->
                ClashTab(
                    index = index,
                    title = "Match ${index + 1}",
                )
            },
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it.index }
        ) {
            ClanWarDetailContent(
                warState = uiState.leagueWarDetailByTag.getOrPut(
                    key = leagueWarDetail.warTags.getOrNull(selectedTabIndex).orEmpty(),
                    defaultValue = { ResultState.Loading }
                ),
                onPlayerClick = onPlayerClick,
                onClanClick = onClanClick
            )
        }
    }
}

