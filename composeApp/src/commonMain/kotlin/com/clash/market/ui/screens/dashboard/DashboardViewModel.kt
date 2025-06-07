package com.clash.market.ui.screens.dashboard

import com.clash.market.base.BaseViewModel
import com.clash.market.base.ResultState
import com.clash.market.local.datastore.ClashPreferenceKeys
import com.clash.market.local.datastore.PreferenceManager
import com.clash.market.network.data.repositories.ClanRepository
import com.clash.market.network.data.repositories.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update

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
        launchIO {
            preferenceManager.getValueAsFlow(ClashPreferenceKeys.ProfilePlayer)
                .filterNotNull()
                .collectLatest {
                    if (it.isEmpty()) {

                    } else {
                        fetchDashboardData(it)
                    }
                }
        }
    }

    private fun fetchDashboardData(playerTag: String) {
        launchIO {
            _uiState.update { it.copy(player = ResultState.Loading) }

            try {
                val player = playerRepository.getPlayerDetails(playerTag)
                _uiState.update { it.copy(player = ResultState.Success(player)) }

                player.clan?.tag?.let {
                    preferenceManager.save(ClashPreferenceKeys.IsInClan, true)
                    preferenceManager.save(ClashPreferenceKeys.ProfileClan, it)
                    fetchCurrentWar(it)
                } ?: run {
                    preferenceManager.save(ClashPreferenceKeys.IsInClan, false)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(player = ResultState.Error(e.message)) }
            }
        }
    }

    private fun fetchCurrentWar(tag: String) {
        launchIO {
            _uiState.update { it.copy(currentWar = ResultState.Loading) }

            try {
                val war = clanRepository.getCurrentWar("#2YVGPYQ98")
                _uiState.update { it.copy(currentWar = ResultState.Success(war)) }

            } catch (e: Exception) {
                _uiState.update { it.copy(currentWar = ResultState.Error(e.message)) }
            }
        }
    }
}