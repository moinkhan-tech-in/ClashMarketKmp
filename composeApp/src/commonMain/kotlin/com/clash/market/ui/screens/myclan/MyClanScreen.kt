package com.clash.market.ui.screens.myclan

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.clash.market.components.clash.ClashLinkVillageMessage
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
        when (playerClan) {
            PlayerClanStatus.NotLinkedProfile -> {
                ClashLinkVillageMessage(
                    onLinkClick = { onNavigate(ScreenRouts.EnterProfile) }
                )
            }

            is PlayerClanStatus.EnrolledInClan -> {
                ClanDetailContent(
                    topPadding = innerPadding.calculateTopPadding() + 12.dp,
                    clanTag = playerClan.tag,
                    onNavigate = onNavigate
                )
            }

            PlayerClanStatus.NotEnrolledInClan -> {}
        }
    }
}