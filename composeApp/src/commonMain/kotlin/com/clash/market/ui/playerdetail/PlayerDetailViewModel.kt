package com.clash.market.ui.playerdetail

import com.clash.market.base.BaseViewModel
import com.clash.market.network.data.repositories.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayerDetailViewModel(
    private val playerRepository: PlayerRepository
): BaseViewModel() {

    private val _uiState = MutableStateFlow(PlayerDetailUiState())
    val uiState: StateFlow<PlayerDetailUiState> = _uiState.asStateFlow()

    fun fetchPlayerDetail(tag: String) {
        viewModelScope.launch {
            val player = playerRepository.getPlayerDetails(tag)
            _uiState.update { it.copy(player = player) }
        }
    }
}