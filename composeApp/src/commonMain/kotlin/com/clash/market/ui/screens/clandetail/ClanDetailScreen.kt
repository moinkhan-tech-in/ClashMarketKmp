package com.clash.market.ui.screens.clandetail

import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.clash.market.components.ClashTopBar
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.contents.clandetail.ClanDetailContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClanDetailScreen(
    clanDetailRoute: ScreenRouts.ClanDetail,
    onNavigate: (ScreenRouts) -> Unit
) {
    Scaffold(
        topBar = { ClashTopBar(title = clanDetailRoute.tag) }
    ) {
        ClanDetailContent(
            clanTag = clanDetailRoute.tag,
            onNavigate = onNavigate
        )
    }
}

