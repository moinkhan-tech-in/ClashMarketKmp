package com.clash.market.ui.screens.playerdetail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_back
import com.clash.market.components.ClashTopBar
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.contents.playerdetail.PlayerDetailContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerDetailScreen(
    playerDetailRoute: ScreenRouts.PlayerDetail,
    onBackClick: () -> Unit
) {
    val topBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val isTopBarVisible by remember {
        derivedStateOf { topBarScrollBehavior.state.collapsedFraction < 1f }
    }

    val alpha by animateFloatAsState(if (isTopBarVisible) 1f else .5f)

    Scaffold(
        containerColor = Color.White,
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
        topBar = {
            ClashTopBar(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = alpha))
                    .statusBarsPadding(),
                title = playerDetailRoute.name ?: playerDetailRoute.tag,
                navigationIcon = Res.drawable.ic_back,
                onBackClick = onBackClick,
                scrollBehaviour = topBarScrollBehavior
            )
        }
    ) {
        PlayerDetailContent(
            topPadding = it.calculateTopPadding() + 12.dp,
            playerTag = playerDetailRoute.tag
        )
    }
}