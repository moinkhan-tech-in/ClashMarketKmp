package com.clash.market.ui.screens.search

import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.base.ResultState
import com.clash.market.components.ClashTab
import com.clash.market.components.ClashTabs
import com.clash.market.components.ClashTopBar
import com.clash.market.models.ClanDetail
import com.clash.market.ui.screens.search.tabs.SearchClanContent
import com.clash.market.ui.screens.search.tabs.SearchPlayerContent
import org.koin.mp.KoinPlatform.getKoin

private val tabs = listOf<ClashTab>(
    ClashTab("Player"),
    ClashTab("Clan")
)

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = getKoin().get<SearchViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val clanSearchState by viewModel.clanSearchState.collectAsStateWithLifecycle()

    SearchScreenContent(
        uiState = uiState,
        clanSearchState = clanSearchState,
        onClanSearchQuery = viewModel::searchClans
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenContent(
    uiState: SearchUiState,
    clanSearchState: ResultState<List<ClanDetail>>,
    onClanSearchQuery: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
            ClashTopBar(
                title = "Search",
                topAppBarScrollBehaviour = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        var selectedTab by remember { mutableStateOf(tabs[0]) }
        ClashTabs(
            tabs = tabs,
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        ) {
            when (it) {
                0 -> {
                    SearchPlayerContent()
                }
                1 -> {
                    SearchClanContent(
                        clanSearchState = clanSearchState,
                        onClanSearchQuery = onClanSearchQuery
                    )
                }
            }
        }
    }
}