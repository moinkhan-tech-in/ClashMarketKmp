package com.clash.market.ui.screens.dashboard

import com.clash.market.base.ResultState
import com.clash.market.models.ClanDetail
import com.clash.market.models.League
import com.clash.market.models.Player
import com.clash.market.models.dtos.CurrentWarResponse

data class DashboardUiState(
    val playerProfileState: ProfileState = ProfileState.NotLinked,
    val clanDetail: ResultState<ClanDetail> = ResultState.Ideal,
    val currentWar: ResultState<CurrentWarResponse> = ResultState.Ideal,
    val playerLeagues: List<League> = emptyList(),
    val playerLeagueId: Int = -1
)

sealed class ProfileState {
    data object NotLinked: ProfileState()
    data class Linked(val tag: String, val player: ResultState<Player>): ProfileState()
}