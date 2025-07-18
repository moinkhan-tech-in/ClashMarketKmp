package com.clash.market.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.clash.market.components.BottomNavItem
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.screens.dashboard.DashboardScreen
import com.clash.market.ui.screens.myclan.MyClanScreen
import com.clash.market.ui.screens.profile.ProfileScreen
import com.clash.market.ui.screens.rankings.RankingsScreen
import com.clash.market.ui.screens.search.SearchScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

val bottomNavItems = listOf(
    BottomNavItem(ScreenRouts.Dashboard, Icons.Default.Dashboard, null, null, "Home"),
    BottomNavItem(ScreenRouts.MyClan, Icons.Default.Group, null, null, "My Clan"),
    BottomNavItem(ScreenRouts.Search, Icons.Default.Search, null, null, "Search"),
    BottomNavItem(ScreenRouts.Rankings, Icons.Default.Leaderboard, null, null, "Rankings"),
    BottomNavItem(ScreenRouts.MyProfile, Icons.Default.Settings, null, null, "Profile"),
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
    val childNavController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = childNavController,
        startDestination = ScreenRouts.Search
    ) {
        composable<ScreenRouts.Dashboard> {
            DashboardScreen(
                onNavigate = onNavigate,
                onBottomBarNavigate = { childNavController.navigate(it) }
            )
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
                onNavigate = onNavigate,
                onBottomBarNavigate = { childNavController.navigate(it) }
            )
        }

        composable<ScreenRouts.MyProfile> {
            ProfileScreen(
                onNavigate = onNavigate,
                onBottomBarNavigate = { childNavController.navigate(it) }
            )
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