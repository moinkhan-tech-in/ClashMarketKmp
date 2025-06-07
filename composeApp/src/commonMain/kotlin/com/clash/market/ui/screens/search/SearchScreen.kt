package com.clash.market.ui.screens.search

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.base.ResultState
import com.clash.market.components.ClashTab
import com.clash.market.components.ClashTabs
import com.clash.market.models.ClanDetail
import com.clash.market.models.Player
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.screens.search.tabs.SearchClanContent
import com.clash.market.ui.screens.search.tabs.SearchPlayerContent
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.mp.KoinPlatform.getKoin

private val tabs = listOf<ClashTab>(
    ClashTab("Player"),
    ClashTab("Clan")
)

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = getKoin().get<SearchViewModel>(),
    onNavigate: (ScreenRouts) -> Unit
) {
    val clanSearchState by viewModel.clanSearchState.collectAsStateWithLifecycle()
    val playerSearchState by viewModel.playerSearchState.collectAsStateWithLifecycle()

    SearchScreenContent(
        clanSearchState = clanSearchState,
        playerSearchState = playerSearchState,
        onNavigate = onNavigate,
        onClanSearchQuery = viewModel::searchClans,
        onPlayerSearchQuery = viewModel::searchPlayer
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenContent(
    clanSearchState: ResultState<List<ClanDetail>>,
    playerSearchState: ResultState<Player>,
    onNavigate: (ScreenRouts) -> Unit = {},
    onClanSearchQuery: (String) -> Unit = {},
    onPlayerSearchQuery: (String) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(tabs[0]) }
    ClashTabs(
        tabs = tabs,
        selectedTab = selectedTab,
        onTabSelected = { selectedTab = it }
    ) {
        when (it) {
            0 -> SearchPlayerContent(
                playerSearchState = playerSearchState,
                onQuerySubmit = onPlayerSearchQuery
            )

            1 -> SearchClanContent(
                clanSearchState = clanSearchState,
                onQuerySubmit = onClanSearchQuery,
                onNavigate = onNavigate
            )
        }
    }
}

@Composable
@Preview
private fun SearchScreenContentPreview() {
    SearchScreenContent(
        clanSearchState = ResultState.Ideal,
        playerSearchState = ResultState.Ideal
    )
}