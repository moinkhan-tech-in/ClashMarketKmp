package com.clash.market.ui.screens.search

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.base.ResultState
import com.clash.market.components.ClashChipLight
import com.clash.market.components.ClashTab
import com.clash.market.components.ClashTabs
import com.clash.market.models.ClanDetail
import com.clash.market.models.Label
import com.clash.market.models.Location
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.screens.home.HomeScreenScaffold
import com.clash.market.ui.screens.search.tabs.SearchClanContent
import com.clash.market.ui.screens.search.tabs.SearchPlayerContent
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

private val tabs = listOf(
    ClashTab(0, "Clans"),
    ClashTab(1, "Player")
)

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onBottomBarNavigate: (ScreenRouts) -> Unit,
    onNavigate: (ScreenRouts) -> Unit
) {
    val clanSearchState by viewModel.clanSearchState.collectAsStateWithLifecycle()
    val clanLabelsState by viewModel.clanLabels.collectAsStateWithLifecycle()
    val clanFilterOptions by viewModel.clanFilterOptions.collectAsStateWithLifecycle()
    val locations by viewModel.locations.collectAsStateWithLifecycle()

    SearchScreenContent(
        clanSearchState = clanSearchState,
        clanLabelsState = clanLabelsState,
        clanFilterOptions = clanFilterOptions,
        locations = locations,
        onNavigate = onNavigate,
        onClanSearchQuery = viewModel::searchClans,
        onBottomBarNavigate = onBottomBarNavigate,
        onFilterApply = viewModel::applyFilter
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenContent(
    clanSearchState: ResultState<List<ClanDetail>>,
    clanLabelsState: List<Label>,
    clanFilterOptions: ClanFilterOptions,
    locations: List<Location>,
    onBottomBarNavigate: (ScreenRouts) -> Unit,
    onNavigate: (ScreenRouts) -> Unit = {},
    onClanSearchQuery: (String) -> Unit = {},
    onFilterApply: (ClanFilterOptions) -> Unit
) {
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    HomeScreenScaffold(
        currentRoute = ScreenRouts.Search,
        onBottomBarNavigate = onBottomBarNavigate,
        ignoreStatusBarAlphaChange = true,
        topBarAction = {
            Crossfade(selectedTabIndex) {
                when (it) {
                    0 -> {
                        var showSearchOptions by remember { mutableStateOf(false) }
                        ClashChipLight(
                            text = "Filter",
                            onClick = { showSearchOptions = showSearchOptions.not() }
                        )
                        if (showSearchOptions) {
                            SearchClanFilterOptionsSheet(
                                show = showSearchOptions,
                                clanFilterOptions = clanFilterOptions,
                                locations = locations,
                                clanLabelsState = clanLabelsState,
                                onDismiss = { showSearchOptions = false },
                                onFilterApply = onFilterApply
                            )
                        }
                    }
                    1 -> {}
                }
            }
        }
    ) { innerPadding ->

        ClashTabs(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it.index }
        ) {
            when (it) {
                0 -> SearchClanContent(
                    clanSearchState = clanSearchState,
                    onQuerySubmit = onClanSearchQuery,
                    onNavigate = onNavigate
                )

                1 -> SearchPlayerContent()
            }
        }
    }
}

@Composable
@Preview
private fun SearchScreenContentPreview() {
    SearchScreenContent(
        clanSearchState = ResultState.Ideal,
        onBottomBarNavigate = {},
        clanLabelsState = emptyList(),
        locations = emptyList(),
        clanFilterOptions = ClanFilterOptions(),
        onFilterApply = {},

    )
}