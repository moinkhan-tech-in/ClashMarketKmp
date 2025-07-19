package com.clash.market.ui.screens.home

import com.clash.market.base.BaseViewModel
import com.clash.market.local.datastore.ClashPreferenceKeys
import com.clash.market.local.datastore.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val preferenceManager: PreferenceManager,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeProfile()
    }

    private fun observeProfile() {
        launchIO {
            preferenceManager.getValueAsFlow(ClashPreferenceKeys.ProfilePlayer)
                .collectLatest {
                    if (it.isNullOrEmpty()) {
                        _uiState.update { it.copy(playerClan = PlayerClanStatus.NotLinkedProfile) }
                    } else {
                        observePlayerClanStatus()
                    }
                }
        }
    }

    private fun observePlayerClanStatus() {
        launchIO {
            val isInClan = preferenceManager.getValue(ClashPreferenceKeys.IsInClan)
            val clanTag = preferenceManager.getValue(ClashPreferenceKeys.ProfileClan)
            if (isInClan == true) {
                _uiState.update { it.copy(playerClan = PlayerClanStatus.EnrolledInClan(clanTag.orEmpty())) }
            } else {
                _uiState.update { it.copy(playerClan = PlayerClanStatus.NotEnrolledInClan) }
            }
        }
    }
}