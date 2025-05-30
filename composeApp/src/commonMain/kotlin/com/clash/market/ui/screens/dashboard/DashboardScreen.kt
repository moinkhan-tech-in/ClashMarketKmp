package com.clash.market.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clash.market.base.ResultState
import com.clash.market.components.ResultStateCrossFade
import com.clash.market.models.Player
import com.clash.market.models.dtos.CurrentWarResponse
import com.clash.market.ui.screens.dashboard.components.ClanCurrentWarInfo
import com.clash.market.ui.screens.dashboard.components.ClanInfo
import com.clash.market.ui.screens.dashboard.components.PlayerInfo
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = getKoin().get<DashboardViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DashboardScreenContent(uiState = uiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenContent(uiState: DashboardUiState) {
    Column(Modifier.fillMaxSize()) {
        PlayerAndClanInfoStateUi(uiState.player)
        CurrentWarStateUi(uiState.currentWar)
    }
}

@Composable
private fun PlayerAndClanInfoStateUi(player: ResultState<Player>) {
    ResultStateCrossFade(
        resultState = player,
        idealContent = {}
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Spacer(modifier = Modifier.size(8.dp))
            PlayerInfo(
                player = it,
                onEdit = {

                }
            )

            ClanInfo(
                name = it.clan?.name.orEmpty(),
                tag = it.clan?.tag.orEmpty(),
                clanImage = it.clan?.badgeUrls?.small.orEmpty(),
                onShare = {

                }
            )
        }
    }
}

@Composable
private fun CurrentWarStateUi(currentWar: ResultState<CurrentWarResponse>) {
    ResultStateCrossFade(
        resultState = currentWar,
        idealContent = {}
    ) {
        ClanCurrentWarInfo(it)
    }
}