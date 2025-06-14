package com.clash.market.ui.screens.myclan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.clash.market.navigation.ScreenRouts
import com.clash.market.ui.contents.clandetail.ClanDetailContent
import com.clash.market.ui.screens.home.HomeScreenScaffold
import com.clash.market.ui.screens.home.PlayerClanStatus

@Composable
fun MyClanScreen(
    playerClan: PlayerClanStatus,
    onBottomBarNavigate: (ScreenRouts) -> Unit,
    onNavigate: (ScreenRouts) -> Unit
) {
    HomeScreenScaffold(
        currentRoute = ScreenRouts.MyClan,
        onBottomBarNavigate = onBottomBarNavigate
    ) { innerPadding ->

        Box(Modifier.padding(innerPadding).fillMaxSize()) {

            when (playerClan) {
                PlayerClanStatus.Unknown -> {}

                is PlayerClanStatus.EnrolledInClan -> {
                    ClanDetailContent(
                        clanTag = playerClan.tag,
                        onNavigate = onNavigate
                    )
                }

                PlayerClanStatus.NotEnrolledInClan -> {}
            }
        }
    }
}