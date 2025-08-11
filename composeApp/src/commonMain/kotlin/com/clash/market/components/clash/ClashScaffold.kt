package com.clash.market.components.clash

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
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_back
import com.clash.market.components.ClashTopBar
import org.jetbrains.compose.resources.DrawableResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClashScaffold(
    title: String,
    navigationIcon: DrawableResource? = Res.drawable.ic_back,
    topBarAction: @Composable RowScope.() -> Unit = {},
    onBackClick: (() -> Unit)? = null,
    ignoreStatusBarAlphaChange: Boolean = false,
    content: @Composable (PaddingValues) -> Unit
) {

    val topBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val isTopBarVisible by remember {
        derivedStateOf { topBarScrollBehavior.state.collapsedFraction < 1f }
    }

    val alpha by animateFloatAsState(
        if (isTopBarVisible || ignoreStatusBarAlphaChange) 1f else .5f
    )
    
    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
        topBar = {
            ClashTopBar(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = alpha))
                    .statusBarsPadding(),
                title = title,
                navigationIcon = navigationIcon,
                actions = topBarAction,
                onBackClick = onBackClick,
                scrollBehaviour = topBarScrollBehavior
            )
        },
        content = content
    )
}