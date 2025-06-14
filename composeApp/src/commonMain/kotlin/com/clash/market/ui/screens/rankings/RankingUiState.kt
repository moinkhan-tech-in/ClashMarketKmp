package com.clash.market.ui.screens.rankings

import com.clash.market.base.ResultState
import com.clash.market.models.ClanDetail
import com.clash.market.models.Location
import com.clash.market.models.Player
import com.clash.market.models.WorldWideLocation

data class RankingUiState(
    val topPlayers: ResultState<List<Player>> = ResultState.Ideal,
    val topClans: ResultState<List<ClanDetail>> = ResultState.Ideal,
    val topBuilderPlayers: ResultState<List<Player>> = ResultState.Ideal,
    val topBuilderClans: ResultState<List<ClanDetail>> = ResultState.Ideal,
    val locations: ResultState<List<Location>> = ResultState.Ideal,
    val selectedLocation: Location = WorldWideLocation
)