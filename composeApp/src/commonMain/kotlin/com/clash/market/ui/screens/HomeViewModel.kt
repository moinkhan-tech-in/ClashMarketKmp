package com.clash.market.ui.screens

import com.clash.market.base.BaseViewModel
import com.clash.market.local.datastore.ClashPreferenceKeys
import com.clash.market.local.datastore.PreferenceManager
import com.clash.market.network.data.repositories.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val playerRepository: PlayerRepository,
    private val preferenceManager: PreferenceManager,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchPlayerDetail()
    }

    fun onPlayerTagSave(tag: String) {
        viewModelScope.launch { preferenceManager.save(ClashPreferenceKeys.ProfilePlayer, tag) }
    }

    fun fetchPlayerDetail() {
        viewModelScope.launch {
            preferenceManager.getValueAsFlow(ClashPreferenceKeys.ProfilePlayer)
                .collectLatest {
                    if (it.isEmpty()) {
                        _uiState.update { it.copy(showPlayerInputDialog = true) }
                    } else {
                        val player = playerRepository.getPlayerDetails(it)
                        _uiState.update { it.copy(player = player) }
                    }
                }
        }
    }
}