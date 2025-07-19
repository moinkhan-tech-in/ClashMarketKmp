package com.clash.market.ui.screens.enterprofile

import com.clash.market.base.BaseViewModel
import com.clash.market.base.ResultState
import com.clash.market.local.datastore.ClashPreferenceKeys
import com.clash.market.local.datastore.PreferenceManager
import com.clash.market.models.Player
import com.clash.market.network.data.repositories.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EnterProfileViewModel(
    private val preferenceManager: PreferenceManager,
    private val playerRepository: PlayerRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(EnterProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val cachedPlayer = mutableMapOf<String, Player>()

    fun onPlayerTagSubmitted(tag: String) {

        cachedPlayer[tag]?.let { player ->
            _uiState.update { it.copy(playerResultState = ResultState.Success(player)) }
            return
        }

        launchIO {
            runCatching {
                _uiState.update { it.copy(playerResultState = ResultState.Loading) }
                playerRepository.getPlayerDetails(tag)
            }.onSuccess { player ->
                cachedPlayer.put(tag, player)
                _uiState.update { it.copy(playerResultState = ResultState.Success(player)) }
            }.onFailure { error ->
                _uiState.update { it.copy(playerResultState = ResultState.Error(error.message)) }
            }
        }
    }

    fun onSaveTagIfAvailable() {
        if (uiState.value.playerResultState is ResultState.Success) {
            val player = (uiState.value.playerResultState as ResultState.Success<Player>).data
            viewModelScope.launch {
                preferenceManager.save(ClashPreferenceKeys.ProfilePlayer, player.tag)

                player.clan?.tag?.let {
                    preferenceManager.save(ClashPreferenceKeys.IsInClan, true)
                    preferenceManager.save(ClashPreferenceKeys.ProfileClan, player.clan.tag)
                } ?: run {
                    preferenceManager.save(ClashPreferenceKeys.IsInClan, false)
                }
            }
        }
    }

    fun onPlayerTagChanged(newTag: String) {
        if (uiState.value.playerResultState is ResultState.Success) {
            val player = (uiState.value.playerResultState as ResultState.Success<Player>).data
            if (newTag != player.tag) {
                _uiState.update { it.copy(playerResultState = ResultState.Ideal) }
            }
        }

    }

    fun onSkipClick() {
        viewModelScope.launch {
            preferenceManager.save(
                prefKey = ClashPreferenceKeys.IsProfileEnterSkipped,
                value = true
            )
        }
    }

    fun onProfileVerification() {}
}