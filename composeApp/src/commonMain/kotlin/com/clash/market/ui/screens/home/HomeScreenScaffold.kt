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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.clash.market.components.BottomNavItem
import com.clash.market.components.ClashBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenScaffold(
    currentNavItem: BottomNavItem,
    onCurrentNavItemChange: (BottomNavItem) -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    val bottomAppBarScrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
            .nestedScroll(bottomAppBarScrollBehavior.nestedScrollConnection),
        bottomBar = {
            ClashBottomBar(
                items = bottomNavItems,
                currentNavItem = currentNavItem,
                scrollBehaviour = bottomAppBarScrollBehavior,
                onItemClick = { onCurrentNavItemChange(it) }
            )
        },
        content = content
    )
}