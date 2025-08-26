package com.clash.market.ui.screens.playerdetail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.components.ClashChipLight
import com.clash.market.components.clash.ClashScaffold
import com.clash.market.navigation.ScreenRouts
import com.clash.market.openClashLink
import com.clash.market.openPlayerLink
import com.clash.market.ui.contents.playerdetail.PlayerDetailContent
import com.clash.market.ui.contents.playerdetail.PlayerDetailContentViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerDetailScreen(
    playerDetailRoute: ScreenRouts.PlayerDetail,
    playerDetailContentViewModel: PlayerDetailContentViewModel = koinViewModel { parametersOf(playerDetailRoute.tag) },
    onBackClick: () -> Unit
) {
    val playerResultState by playerDetailContentViewModel.playerSearchState.collectAsStateWithLifecycle()
    ClashScaffold(
        title = playerDetailRoute.name ?: playerDetailRoute.tag,
        onNavigationIconClick = onBackClick,
        topBarAction = {
            ClashChipLight("Open in Game") {
                openClashLink(openPlayerLink(playerDetailRoute.tag))
            }
        }
    ) {
        PlayerDetailContent(
            topPadding = it.calculateTopPadding() + 12.dp,
            playerResultState = playerResultState
        )
    }
}