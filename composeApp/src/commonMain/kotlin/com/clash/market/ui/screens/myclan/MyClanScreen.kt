package com.clash.market.ui.screens.myclan

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import clashmarket.composeapp.generated.resources.Res
import clashmarket.composeapp.generated.resources.ic_builder_seating
import clashmarket.composeapp.generated.resources.ic_clan_castle
import com.clash.market.components.ClashChipLight
import com.clash.market.components.clash.ClashMessageInfo
import com.clash.market.navigation.ScreenRouts
import com.clash.market.openClanLink
import com.clash.market.openClashLink
import com.clash.market.ui.contents.clandetail.ClanDetailContent
import com.clash.market.ui.contents.clandetail.ClanDetailContentViewModel
import com.clash.market.ui.screens.home.HomeScreenScaffold
import com.clash.market.ui.screens.home.PlayerClanStatus
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

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
                        openClashLink(openClanLink(it.tag))
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
                val viewModel: ClanDetailContentViewModel = koinViewModel { parametersOf(playerClan.tag) }
                val clanResultState by viewModel.clanDetailState.collectAsStateWithLifecycle()
                ClanDetailContent(
                    topPadding = innerPadding.calculateTopPadding() + 12.dp,
                    clanDetailState = clanResultState,
                    onNavigate = onNavigate,
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