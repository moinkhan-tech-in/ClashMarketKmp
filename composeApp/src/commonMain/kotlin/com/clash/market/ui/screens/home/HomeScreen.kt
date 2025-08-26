package com.clash.market.ui.screens.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.BackPressHandler
import com.clash.market.components.BottomNavItem
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.screens.dashboard.DashboardScreen
import com.clash.market.ui.screens.dashboard.DashboardViewModel
import com.clash.market.ui.screens.myclan.MyClanScreen
import com.clash.market.ui.screens.mywar.MyWarScreen
import com.clash.market.ui.screens.mywar.MyWarViewModel
import com.clash.market.ui.screens.rankings.RankingsScreen
import com.clash.market.ui.screens.rankings.RankingsViewModel
import com.clash.market.ui.screens.search.SearchScreen
import com.clash.market.ui.screens.search.SearchViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

val bottomNavItems = listOf(
    BottomNavItem(ScreenRouts.Dashboard, Icons.Default.Dashboard, null, null, "Home"),
    BottomNavItem(ScreenRouts.MyClan, Icons.Default.Group, null, null, "Clan"),
    BottomNavItem(ScreenRouts.Search, Icons.Default.Search, null, null, "Search"),
    BottomNavItem(ScreenRouts.Rankings, Icons.Default.Leaderboard, null, null, "Rankings"),
    BottomNavItem(ScreenRouts.MyWar, Icons.Default.Security, null, null, "War"),
)

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigate: (ScreenRouts) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreenContent(
        uiState = uiState,
        onNavigate = onNavigate
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onNavigate: (ScreenRouts) -> Unit
) {
    val scope = rememberCoroutineScope()

    val currentNavItem = remember { mutableStateOf(bottomNavItems.first()) }

    val dashboardViewModel = koinViewModel<DashboardViewModel>()
    val searchViewModel = koinViewModel<SearchViewModel>()
    val rankingsViewModel = koinViewModel<RankingsViewModel>()
    val myWarViewModel = koinViewModel<MyWarViewModel>()

    val pagerState = rememberPagerState { bottomNavItems.size }
    val currentPage = pagerState.currentPage
    LaunchedEffect(currentPage) { currentNavItem.value = bottomNavItems[currentPage] }

    BackPressHandler(currentPage != 0) {
        if (currentPage != 0) {
            scope.launch {
                pagerState.animateScrollToPage (0, animationSpec = tween(500))
                currentNavItem.value = bottomNavItems[0]
            }
        }
    }

    HomeScreenScaffold(
        onCurrentNavItemChange = {
            scope.launch {
                pagerState.animateScrollToPage(bottomNavItems.indexOf(it), animationSpec = tween(500))
                currentNavItem.value = it
            }
        },
        currentNavItem = currentNavItem.value
    ) {

        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> {
                    DashboardScreen(
                        viewModel = dashboardViewModel,
                        onNavigate = onNavigate
                    )
                }

                1 -> {
                    MyClanScreen(
                        playerClan = uiState.playerClan,
                        onNavigate = onNavigate
                    )
                }

                2 -> {
                    SearchScreen(
                        viewModel = searchViewModel,
                        onNavigate = onNavigate
                    )
                }

                3 -> {
                    RankingsScreen(
                        viewModel = rankingsViewModel,
                        onNavigate = onNavigate
                    )
                }

                4 -> {
                    MyWarScreen(
                        viewModel = myWarViewModel,
                        onNavigate = onNavigate
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun HomeScreenContentPreview() {
    HomeScreenContent(
        uiState = HomeUiState(),
        onNavigate = {}
    )
}