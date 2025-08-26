package com.clash.market.ui.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.clash.market.components.ClashBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenScaffold(
    childNavController: NavController,
    content: @Composable (PaddingValues) -> Unit,
) {
    val bottomAppBarScrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    val currentNavItem = remember { mutableStateOf(bottomNavItems.first()) }

    val navBackStackEntry by childNavController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination
    LaunchedEffect(destination) {
        bottomNavItems.firstOrNull { destination?.hasRoute(it.route::class) == true }?.let {
            currentNavItem.value = it
        }
    }

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
            .nestedScroll(bottomAppBarScrollBehavior.nestedScrollConnection),
        bottomBar = {
            ClashBottomBar(
                items = bottomNavItems,
                currentNavItem = currentNavItem.value,
                scrollBehaviour = bottomAppBarScrollBehavior,
                onItemClick = {
                    currentNavItem.value = it
                    childNavController.navigate(it.route)
                }
            )
        },
        content = content
    )
}