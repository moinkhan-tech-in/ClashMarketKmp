package com.clash.market.ui.screens

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.clash.market.components.BottomNavItem
import com.clash.market.components.ClashBottomBar
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.dialogs.SingleInputDialog
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
        onSavePlayerTag = viewModel::onPlayerTagSave,

    )
}

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onSavePlayerTag: (String) -> Unit,
    onNavigate: (ScreenRouts) -> Unit
) {

    val childNavController = rememberNavController()
    var currentRoute by remember { mutableStateOf<ScreenRouts>(ScreenRouts.Dashboard) }

    if (uiState.showPlayerInputDialog) {
        SingleInputDialog(
            text = "Identify Yourself, Chief!",
            positiveButtonClick = onSavePlayerTag,
            hint = "#YourTagHere",
            positiveButtonText = "Submit"
        )
    }

    Scaffold(
        bottomBar = {
            ClashBottomBar(
                items = bottomNavItems,
                currentRoute = currentRoute,
                onItemSelected = {
                    currentRoute = it
                    childNavController.navigate(it)
                }
            )
        }
    ) {
        NavHost(childNavController, startDestination = ScreenRouts.Dashboard) {
            composable<ScreenRouts.Dashboard> {
                Text("Search")
            }

            composable<ScreenRouts.Search> {
                Text("Search")
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