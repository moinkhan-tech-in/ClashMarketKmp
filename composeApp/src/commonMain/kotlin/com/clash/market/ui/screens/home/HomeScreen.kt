package com.clash.market.ui.screens.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.clash.market.components.BottomNavItem
import com.clash.market.components.ClashBottomBar
import com.clash.market.components.ClashTopBar
import com.clash.market.navigation.ScreenRouts
import com.clash.market.theme.ClashFont
import com.clash.market.ui.dialogs.SingleInputDialog
import com.clash.market.ui.screens.dashboard.DashboardScreen
import com.clash.market.ui.screens.search.SearchScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.mp.KoinPlatform.getKoin

val bottomNavItems = listOf(
    BottomNavItem(ScreenRouts.Dashboard, Icons.Default.Dashboard, "Home"),
    BottomNavItem(ScreenRouts.Clan, Icons.Default.Group, "Clan"),
    BottomNavItem(ScreenRouts.Search, Icons.Default.Search, "Search"),
    BottomNavItem(ScreenRouts.War, Icons.Default.Shield, "War"),
    BottomNavItem(ScreenRouts.More, Icons.Default.Settings, "Profile"),
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

    val bottomAppBarScrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    val topBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val childNavController = rememberNavController()
    var currentRoute by remember { mutableStateOf<ScreenRouts>(ScreenRouts.Dashboard) }
    var showFloatingOption by remember { mutableStateOf(false) }

    val title = remember(currentRoute) {
        when (currentRoute) {
            ScreenRouts.Dashboard -> "Dashboard"
            ScreenRouts.Search -> "Search"
            ScreenRouts.War -> "War"
            ScreenRouts.More -> "More"
            ScreenRouts.Clan -> "Clan"
            else -> ""
        }
    }

    if (uiState.showPlayerInputDialog) {
        SingleInputDialog(
            text = "Identify Yourself, Chief!",
            positiveButtonClick = onSavePlayerTag,
            hint = "#YourTagHere",
            positiveButtonText = "Submit"
        )
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection)
            .nestedScroll(bottomAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            ClashTopBar(
                title = title,
                scrollBehaviour = topBarScrollBehavior
            )
        },
        bottomBar = {
            ClashBottomBar(
                items = bottomNavItems,
                currentRoute = currentRoute,
                scrollBehaviour = bottomAppBarScrollBehavior,
                onItemSelected = {
                    currentRoute = it
                    childNavController.navigate(it)
                }
            )
        },
        floatingActionButton = {
            if (showFloatingOption) {
                ExtendedFloatingActionButton(
                    onClick = {},
                    contentColor = Color.White,
                    containerColor = Color(0xFF2C2C2C),
                ) {
                    Text(text = "Options", fontFamily = ClashFont)
                    Spacer(Modifier.size(8.dp))
                    Icon(Icons.AutoMirrored.Default.Sort, contentDescription = "Search Options")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = childNavController,
            startDestination = ScreenRouts.Dashboard
        ) {
            composable<ScreenRouts.Dashboard> {
                DashboardScreen()
            }

            composable<ScreenRouts.Search> {
                SearchScreen(
                    onNavigate = onNavigate
                )
            }

            composable<ScreenRouts.Clan> {
                Text("Clan")
            }

            composable<ScreenRouts.War> {
                Text("War")
            }

            composable<ScreenRouts.More> {
                Text("More")
            }
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