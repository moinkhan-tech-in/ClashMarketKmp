package com.clash.market.ui.screens.clandetail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.clash.market.components.clash.ClashScaffold
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.contents.clandetail.ClanDetailContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClanDetailScreen(
    clanDetailRoute: ScreenRouts.ClanDetail,
    onNavigate: (ScreenRouts) -> Unit,
    onBackClick: () -> Unit
) {
    ClashScaffold(
        title = clanDetailRoute.tag,
        onBackClick = onBackClick
    ) {
        ClanDetailContent(
            topPadding = it.calculateTopPadding() + 12.dp,
            clanTag = clanDetailRoute.tag,
            onNavigate = onNavigate
        )
    }
}

