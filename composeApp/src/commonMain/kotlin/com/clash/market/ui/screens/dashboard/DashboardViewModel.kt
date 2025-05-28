package com.clash.market.ui.screens.dashboard

import com.clash.market.base.BaseViewModel
import com.clash.market.local.datastore.ClashPreferenceKeys
import com.clash.market.local.datastore.PreferenceManager
import com.clash.market.network.data.repositories.ClanRepository
import com.clash.market.network.data.repositories.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    val playerRepository: PlayerRepository,
    val clanRepository: ClanRepository,
    val preferenceManager: PreferenceManager
): BaseViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchSavedPlayerTag()
    }

    private fun fetchSavedPlayerTag() {
        viewModelScope.launch {
            preferenceManager.getValueAsFlow(ClashPreferenceKeys.ProfilePlayer)
                .collectLatest {
                    if (it.isEmpty()) {

                    } else {
                        fetchDashboardData(it)
                    }
                }
        }
    }

    private fun fetchDashboardData(playerTag: String) {
        viewModelScope.launch {
            val player = playerRepository.getPlayerDetails(playerTag)
            _uiState.update { it.copy(player = player) }
        }
    }
}