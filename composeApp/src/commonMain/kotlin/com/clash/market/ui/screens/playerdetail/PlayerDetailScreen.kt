package com.clash.market.ui.screens.playerdetail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.clash.market.components.clash.ClashScaffold
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.contents.playerdetail.PlayerDetailContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerDetailScreen(
    playerDetailRoute: ScreenRouts.PlayerDetail,
    onBackClick: () -> Unit
) {
    ClashScaffold(
        title = playerDetailRoute.name ?: playerDetailRoute.tag,
        onBackClick = onBackClick
    ) {
        PlayerDetailContent(
            topPadding = it.calculateTopPadding() + 12.dp,
            playerTag = playerDetailRoute.tag
        )
    }
}