package com.clash.market.ui.screens.myclan

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_builder_seating
import clashmarket.composeapp.generated.resources.ic_clan_castle
import com.clash.market.components.ClashChipLight
import com.clash.market.components.clash.ClashMessageInfo
import com.clash.market.getOpenClanLink
import com.clash.market.navigation.ScreenRouts
import com.clash.market.openClashLink
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
        onBottomBarNavigate = onBottomBarNavigate,
        topBarAction = {
            Crossfade(playerClan) {
                if (it is PlayerClanStatus.EnrolledInClan) {
                    ClashChipLight(text = "Open in Game") {
                        openClashLink(getOpenClanLink(it.tag))
                    }
                }
            }
        }
    ) { innerPadding ->
        when (playerClan) {
            PlayerClanStatus.NotLinkedProfile -> {
                ClashMessageInfo(
                    icon = Res.drawable.ic_builder_seating,
                    btnText = "Link My Village!",
                    message = "Link your village to view your clan details.",
                    onClick = { onNavigate(ScreenRouts.EnterProfile) }
                )
            }

            is PlayerClanStatus.EnrolledInClan -> {
                ClanDetailContent(
                    topPadding = innerPadding.calculateTopPadding() + 12.dp,
                    clanTag = playerClan.tag,
                    onNavigate = onNavigate
                )
            }

            PlayerClanStatus.NotEnrolledInClan -> {
                ClashMessageInfo(
                    icon = Res.drawable.ic_clan_castle,
                    message = "Looks like you’re not in a Clan yet, Chief! Join one to see all details here.",
                )
            }
        }
    }
}