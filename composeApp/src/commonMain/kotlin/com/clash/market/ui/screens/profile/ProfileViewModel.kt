package com.clash.market.ui.screens.profile

import com.clash.market.base.BaseViewModel
import com.clash.market.local.datastore.ClashPreferenceKeys
import com.clash.market.local.datastore.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update

class ProfileViewModel(
    private val preferenceManager: PreferenceManager,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        syncInitData()
    }

    private fun syncInitData() {
        launchIO {
            preferenceManager.getValueAsFlow(prefKey = ClashPreferenceKeys.ProfilePlayer)
                .collectLatest { tag ->
                    _uiState.update { it.copy(linkedProfile = tag) }
                }
        }
    }

    fun unLinkProfile() {
        launchIO {
            preferenceManager.save(prefKey = ClashPreferenceKeys.ProfilePlayer, "")
            preferenceManager.save(prefKey = ClashPreferenceKeys.ProfileClan, "")
        }
    }
}