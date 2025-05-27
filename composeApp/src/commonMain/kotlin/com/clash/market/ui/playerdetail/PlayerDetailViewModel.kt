package com.clash.market.ui.playerdetail

import com.clash.market.base.BaseViewModel
import com.clash.market.local.datastore.PreferenceManager
import com.clash.market.network.data.repositories.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayerDetailViewModel(
    private val playerRepository: PlayerRepository,
    private val preferenceManager: PreferenceManager,
): BaseViewModel() {

    private val _uiState = MutableStateFlow(PlayerDetailUiState())
    val uiState: StateFlow<PlayerDetailUiState> = _uiState.asStateFlow()

    fun fetchPlayerDetail(tag: String) {
        viewModelScope.launch {
            preferenceManager.save("player", tag)
            val player = playerRepository.getPlayerDetails(preferenceManager.getValue("player", "#2GYCPJJY2"))
            _uiState.update { it.copy(player = player) }
        }
    }
}