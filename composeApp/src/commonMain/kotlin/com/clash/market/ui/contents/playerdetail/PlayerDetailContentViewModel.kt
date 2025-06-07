package com.clash.market.ui.contents.playerdetail

import com.clash.market.base.BaseViewModel
import com.clash.market.base.ResultState
import com.clash.market.models.Player
import com.clash.market.network.data.repositories.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class PlayerDetailContentViewModel(
    val playerRepository: PlayerRepository
): BaseViewModel() {

    private val _playerSearchState = MutableStateFlow<ResultState<Player>>(ResultState.Ideal)
    val playerSearchState = _playerSearchState

    fun fetchPlayer(playerTag: String) {
        if (playerTag.length < 10) return

        launchIO {
            _playerSearchState.update { ResultState.Loading }

            try {
                val player = playerRepository.getPlayerDetails(playerTag = playerTag)
                _playerSearchState.update { ResultState.Success(player) }
            } catch (e: Exception) {
                _playerSearchState.update { ResultState.Error(e.message) }
            }
        }
    }
}