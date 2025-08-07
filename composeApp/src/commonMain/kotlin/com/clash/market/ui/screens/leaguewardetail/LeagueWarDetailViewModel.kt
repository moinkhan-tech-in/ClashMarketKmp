package com.clash.market.ui.screens.leaguewardetail

import com.clash.market.base.BaseViewModel
import com.clash.market.base.ResultState
import com.clash.market.navigation.ScreenRouts
import com.clash.market.network.data.repositories.ClanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LeagueWarDetailViewModel(
    val clanRepository: ClanRepository,
    val leagueWarDetailRoute: ScreenRouts.LeagueWarDetail
): BaseViewModel() {

    private val _uiState = MutableStateFlow(LeagueWarDetailUiState())
    val uiState: StateFlow<LeagueWarDetailUiState> = _uiState

    fun fetchWarDetailByTag(warTag: String) {
        val warState = uiState.value.leagueWarDetailByTag[warTag]
        if (warState != null && warState is ResultState.Success) {
            return
        }

        launchIO {
            runCatching {
                _uiState.value.leagueWarDetailByTag.put(warTag, ResultState.Loading)
                clanRepository.getWarLeagueWar(warTag)
            }.onSuccess { response ->
                _uiState.value.leagueWarDetailByTag.put(warTag, ResultState.Success(response))
            }.onFailure { exception ->
                _uiState.value.leagueWarDetailByTag.put(warTag, ResultState.Error(exception.message))
            }
        }
    }
}