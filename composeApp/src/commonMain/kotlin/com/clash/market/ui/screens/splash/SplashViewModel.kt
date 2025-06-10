package com.clash.market.ui.screens.splash

import com.clash.market.base.BaseViewModel
import com.clash.market.network.data.repositories.MetadataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SplashViewModel(
    private val metadataRepository: MetadataRepository
) : BaseViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady

    init {
        launchIO {
            runCatching { metadataRepository.getLeagues() }
            runCatching { metadataRepository.getLocations() }
            runCatching { metadataRepository.getPlayerLabels() }
            runCatching { metadataRepository.getClanLabels() }
            _isReady.value = true
        }
    }
}