package com.clash.market.ui.screens.search

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.base.ResultState
import com.clash.market.components.ClashTab
import com.clash.market.components.ClashTabs
import com.clash.market.models.ClanDetail
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.screens.search.tabs.SearchClanContent
import com.clash.market.ui.screens.search.tabs.SearchPlayerContent
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.mp.KoinPlatform.getKoin

private val tabs = listOf<ClashTab>(
    ClashTab(0, "Player"),
    ClashTab(1, "Clan")
)

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = getKoin().get<SearchViewModel>(),
    onNavigate: (ScreenRouts) -> Unit
) {
    val clanSearchState by viewModel.clanSearchState.collectAsStateWithLifecycle()
    SearchScreenContent(
        clanSearchState = clanSearchState,
        onNavigate = onNavigate,
        onClanSearchQuery = viewModel::searchClans
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenContent(
    clanSearchState: ResultState<List<ClanDetail>>,
    onNavigate: (ScreenRouts) -> Unit = {},
    onClanSearchQuery: (String) -> Unit = {}
) {
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    ClashTabs(
        tabs = tabs,
        selectedTabIndex = selectedTabIndex,
        onTabSelected = { selectedTabIndex = it.index }
    ) {
        when (it) {
            0 -> SearchPlayerContent()

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
    SearchScreenContent(clanSearchState = ResultState.Ideal)
}