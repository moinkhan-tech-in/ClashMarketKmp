package com.clash.market.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.clash.market.components.BottomNavItem
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.dialogs.SingleInputDialog
import com.clash.market.ui.screens.dashboard.DashboardScreen
import com.clash.market.ui.screens.myclan.MyClanScreen
import com.clash.market.ui.screens.rankings.RankingsScreen
import com.clash.market.ui.screens.search.SearchScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.mp.KoinPlatform.getKoin

val bottomNavItems = listOf(
    BottomNavItem(ScreenRouts.Dashboard, Icons.Default.Dashboard, null, null, "Home"),
    BottomNavItem(ScreenRouts.MyClan, Icons.Default.Group, null, null, "My Clan"),
    BottomNavItem(ScreenRouts.Search, Icons.Default.Search, null, null, "Search"),
    BottomNavItem(ScreenRouts.Rankings, Icons.Default.Leaderboard, null, null, "Rankings"),
    BottomNavItem(ScreenRouts.More, Icons.Default.Settings, null, null, "Profile"),
)

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = getKoin().get<HomeViewModel>(),
    onNavigate: (ScreenRouts) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreenContent(
        uiState = uiState,
        onNavigate = onNavigate,
        onSavePlayerTag = viewModel::onPlayerTagSave
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onSavePlayerTag: (String) -> Unit,
    onNavigate: (ScreenRouts) -> Unit
) {
    val childNavController = rememberNavController()
    if (uiState.showPlayerInputDialog) {
        SingleInputDialog(
            text = "Identify Yourself, Chief!",
            positiveButtonClick = onSavePlayerTag,
            hint = "#YourTagHere",
            positiveButtonText = "Submit"
        )
    }
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = childNavController,
        startDestination = ScreenRouts.Dashboard
    ) {
        composable<ScreenRouts.Dashboard> {
            DashboardScreen(onBottomBarNavigate = { childNavController.navigate(it) })
        }

        composable<ScreenRouts.Search> {
            SearchScreen(
                onNavigate = onNavigate,
                onBottomBarNavigate = { childNavController.navigate(it) }
            )
        }

        composable<ScreenRouts.MyClan> {
            MyClanScreen(
                playerClan = uiState.playerClan,
                onNavigate = onNavigate,
                onBottomBarNavigate = { childNavController.navigate(it) }
            )
        }

        composable<ScreenRouts.Rankings> {
            RankingsScreen(
                onBottomBarNavigate = { childNavController.navigate(it) }
            )
        }

        composable<ScreenRouts.More> {
            Text("More")
        }
    }
}

@Composable
@Preview
private fun HomeScreenContentPreview() {
    HomeScreenContent(
        uiState = HomeUiState(),
        onSavePlayerTag = {},
        onNavigate = {}
    )
}