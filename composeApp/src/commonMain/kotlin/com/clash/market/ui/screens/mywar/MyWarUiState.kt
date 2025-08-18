package com.clash.market.ui.screens.mywar

import com.clash.market.models.dtos.CurrentWarResponse
import com.clash.market.models.dtos.WarLeagueGroupResponse

sealed class MyWarUiState {

    data object Ideal: MyWarUiState()

    data object Loading: MyWarUiState()
    data object NoProfileLinked: MyWarUiState()
    data object NotInClan: MyWarUiState()

    data object NotInWar: MyWarUiState()
    data class StandardWar(val warResult: CurrentWarResponse): MyWarUiState()

    data object PrivateWarLog: MyWarUiState()

    data class LeagueWar(val warLeagueGroupResponse: WarLeagueGroupResponse): MyWarUiState()

    data class Error(val message: String): MyWarUiState()
}