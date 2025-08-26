package com.clash.market.ui.screens.clandetail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.components.ClashChipLight
import com.clash.market.components.clash.ClashScaffold
import com.clash.market.navigation.ScreenRouts
import com.clash.market.openClanLink
import com.clash.market.openClashLink
import com.clash.market.ui.contents.clandetail.ClanDetailContent
import com.clash.market.ui.contents.clandetail.ClanDetailContentViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClanDetailScreen(
    clanDetailRoute: ScreenRouts.ClanDetail,
    viewModel: ClanDetailContentViewModel = koinViewModel { parametersOf(clanDetailRoute.tag) },
    onNavigate: (ScreenRouts) -> Unit,
    onBackClick: () -> Unit
) {
    val clanResultState by viewModel.clanDetailState.collectAsStateWithLifecycle()
    ClashScaffold(
        title = clanDetailRoute.tag,
        onNavigationIconClick = onBackClick,
        topBarAction = {
            ClashChipLight("Open in Game") {
                openClashLink(openClanLink(clanDetailRoute.tag))
            }
        }
    ) {
        ClanDetailContent(
            topPadding = it.calculateTopPadding() + 12.dp,
            onNavigate = onNavigate,
            clanDetailState = clanResultState
        )
    }
}

