package com.clash.market.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.clash.market.components.ClashBottomBar
import com.clash.market.components.ClashTopBar
import com.clash.market.navigation.ScreenRouts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenScaffold(
    currentRoute: ScreenRouts,
    additionalTopContent: @Composable () -> Unit= {},
    onBottomBarNavigate: (ScreenRouts) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val topBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val bottomAppBarScrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection)
            .nestedScroll(bottomAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            Column {
                ClashTopBar(
                    title = "Dashboard",
                    scrollBehaviour = topBarScrollBehavior
                )
                additionalTopContent()
            }
        },
        bottomBar = {
            ClashBottomBar(
                items = bottomNavItems,
                currentRoute = currentRoute,
                scrollBehaviour = bottomAppBarScrollBehavior,
                onItemSelected = { onBottomBarNavigate(it) }
            )
        },
        content = content
    )
}