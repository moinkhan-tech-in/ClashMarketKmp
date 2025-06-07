package com.clash.market.ui.screens.playerdetail

import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
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
    Scaffold(
        topBar = {
            ClashTopBar(
                title = playerDetailRoute.name ?: playerDetailRoute.tag,
                navigationIcon = Res.drawable.ic_back,
                onBackClick = onBackClick
            )
        }
    ) {
        PlayerDetailContent(playerTag = playerDetailRoute.tag)
    }
}