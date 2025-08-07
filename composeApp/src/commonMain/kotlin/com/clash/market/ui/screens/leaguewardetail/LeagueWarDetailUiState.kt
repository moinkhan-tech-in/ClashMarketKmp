package com.clash.market.ui.screens.leaguewardetail

import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.clash.market.base.ResultState
import com.clash.market.models.dtos.CurrentWarResponse

data class LeagueWarDetailUiState(
    val leagueWarDetailByTag: SnapshotStateMap<String, ResultState<CurrentWarResponse>> = SnapshotStateMap()
)