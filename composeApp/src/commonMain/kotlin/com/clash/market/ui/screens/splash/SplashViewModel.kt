package com.clash.market.ui.screens.splash

import com.clash.market.base.BaseViewModel
import com.clash.market.local.datastore.ClashPreferenceKeys
import com.clash.market.local.datastore.PreferenceManager
import com.clash.market.navigation.ScreenRouts
import com.clash.market.network.data.repositories.MetadataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SplashViewModel(
    private val metadataRepository: MetadataRepository,
    private val preferenceManager: PreferenceManager,
) : BaseViewModel() {

    private val _isReady = MutableStateFlow<InitialLaunch>(InitialLaunch.None)
    val isReady: StateFlow<InitialLaunch> = _isReady

    init {
        launchIO {
            runCatching { metadataRepository.getLeagues() }
            runCatching { metadataRepository.getLocations() }
            runCatching { metadataRepository.getPlayerLabels() }
            runCatching { metadataRepository.getClanLabels() }

            val hasPlayerId = preferenceManager.getValue(ClashPreferenceKeys.ProfilePlayer).isNullOrEmpty().not()
            val isProfileEnterSkipped = preferenceManager.getValue(ClashPreferenceKeys.IsProfileEnterSkipped)

            if (hasPlayerId) {
                _isReady.value = InitialLaunch.NavigateTo(ScreenRouts.Home)
            } else {
                if (isProfileEnterSkipped == true) {
                    _isReady.value = InitialLaunch.NavigateTo(ScreenRouts.Home)
                } else {
                    _isReady.value = InitialLaunch.NavigateTo(ScreenRouts.EnterProfile)
                }
            }
        }
    }
}

sealed class InitialLaunch {
    data object None : InitialLaunch()
    data class NavigateTo(val route: ScreenRouts): InitialLaunch()
}