package com.clash.market.ui.screens.home

import com.clash.market.base.BaseViewModel
import com.clash.market.local.datastore.ClashPreferenceKeys
import com.clash.market.local.datastore.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val preferenceManager: PreferenceManager,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observePlayerClanStatus()
    }

    private fun observePlayerClanStatus() {
        launchIO {
            preferenceManager.getValueAsFlow(ClashPreferenceKeys.IsInClan)
                .filterNotNull()
                .collectLatest {
                    if (it) {
                        val clanTag = preferenceManager.getValue(ClashPreferenceKeys.ProfileClan)
                        clanTag?.let { tag ->
                            _uiState.update { it.copy(playerClan = PlayerClanStatus.EnrolledInClan(tag)) }
                        } ?: {
                            _uiState.update { it.copy(playerClan = PlayerClanStatus.Unknown) }
                        }
                    } else {
                        _uiState.update { it.copy(playerClan = PlayerClanStatus.NotEnrolledInClan) }
                    }
                }
        }
    }

    fun onPlayerTagSave(tag: String) {
        viewModelScope.launch { preferenceManager.save(ClashPreferenceKeys.ProfilePlayer, tag) }
    }
}