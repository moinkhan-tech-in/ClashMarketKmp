package com.clash.market.ui.screens.dashboard

import com.clash.market.base.ResultState
import com.clash.market.models.ClanDetail
import com.clash.market.models.Player
import com.clash.market.models.dtos.CurrentWarResponse

data class DashboardUiState(
    val player: ResultState<Player> = ResultState.Ideal,
    val clanDetail: ResultState<ClanDetail> = ResultState.Ideal,
    val currentWar: ResultState<CurrentWarResponse> = ResultState.Ideal
)