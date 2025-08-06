package com.clash.market.ui.screens.dashboard

import com.clash.market.base.BaseViewModel
import com.clash.market.base.ResultState
import com.clash.market.local.datastore.ClashPreferenceKeys
import com.clash.market.local.datastore.PreferenceManager
import com.clash.market.network.data.repositories.MetadataRepository
import com.clash.market.network.data.repositories.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update

class DashboardViewModel(
    val playerRepository: PlayerRepository,
    val preferenceManager: PreferenceManager,
    val metadataRepository: MetadataRepository
): BaseViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchSavedPlayerTag()
    }

    private fun fetchSavedPlayerTag() {
        launchIO {
            preferenceManager.getValueAsFlow(ClashPreferenceKeys.ProfilePlayer)
                .collectLatest { playerTag ->
                    if (playerTag.isNullOrEmpty()) {
                        _uiState.update { it.copy(playerProfileState = ProfileState.NotLinked) }
                    } else {
                        fetchDashboardData(playerTag)
                    }
                }
        }
    }

    private fun fetchDashboardData(playerTag: String) {
        launchIO {
            _uiState.update { it.copy(playerProfileState = ProfileState.Linked(playerTag,ResultState.Loading)) }

            try {
                val player = playerRepository.getPlayerDetails(playerTag)
                _uiState.update { it.copy(playerProfileState = ProfileState.Linked(playerTag,ResultState.Success(player))) }

                fetchPlayerLeagues()

                player.clan?.tag?.let {
                    preferenceManager.save(ClashPreferenceKeys.IsInClan, true)
                    preferenceManager.save(ClashPreferenceKeys.ProfileClan, it)
                } ?: run {
                    preferenceManager.save(ClashPreferenceKeys.IsInClan, false)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(playerProfileState = ProfileState.Linked(playerTag,ResultState.Error(e.message))) }
            }
        }
    }

    private fun fetchPlayerLeagues() {
        launchIO {
            val playerLeagues = metadataRepository.getLeagues()
            _uiState.update { it.copy(playerLeagues = playerLeagues.items) }
        }
    }
}