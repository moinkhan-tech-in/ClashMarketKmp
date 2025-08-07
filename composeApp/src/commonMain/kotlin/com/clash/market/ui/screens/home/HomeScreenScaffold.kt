package com.clash.market.ui.screens.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.clash.market.components.ClashBottomBar
import com.clash.market.components.ClashTopBar
import com.clash.market.navigation.ScreenRouts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenScaffold(
    currentRoute: ScreenRouts,
    overrideTitle: String? = null,
    onBottomBarNavigate: (ScreenRouts) -> Unit,
    onNavigate: (ScreenRouts) -> Unit,
    topBarAction: @Composable RowScope.() -> Unit = {},
    ignoreStatusBarAlphaChange: Boolean = false,
    content: @Composable (PaddingValues) -> Unit,
) {
    val topBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val bottomAppBarScrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()

    val isTopBarVisible by remember {
        derivedStateOf { topBarScrollBehavior.state.collapsedFraction < 1f }
    }

    val alpha by animateFloatAsState(
        if (isTopBarVisible || ignoreStatusBarAlphaChange) 1f else .5f
    )

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection)
            .nestedScroll(bottomAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            ClashTopBar(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = alpha))
                    .statusBarsPadding(),
                title = overrideTitle ?: bottomNavItems.find { it.route == currentRoute }?.label.orEmpty(),
                scrollBehaviour = topBarScrollBehavior,
                actions = topBarAction,
                onBackClick = { onNavigate(ScreenRouts.MyProfile) }
            )
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