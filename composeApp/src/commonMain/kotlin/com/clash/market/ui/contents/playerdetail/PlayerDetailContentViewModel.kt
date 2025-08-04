package com.clash.market.ui.contents.playerdetail

import com.clash.market.base.BaseViewModel
import com.clash.market.base.ResultState
import com.clash.market.models.Player
import com.clash.market.network.data.repositories.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class PlayerDetailContentViewModel(
    val playerRepository: PlayerRepository,
    val playerTag: String
): BaseViewModel() {

    private val _playerResultState = MutableStateFlow<ResultState<Player>>(ResultState.Ideal)
    val playerSearchState = _playerResultState

    init {
        fetchPlayer(playerTag)
    }

    fun fetchPlayer(playerTag: String) {
        if (playerTag.length <= 8) return

        launchIO {
            _playerResultState.update { ResultState.Loading }

            try {
                val player = playerRepository.getPlayerDetails(playerTag = playerTag)
                _playerResultState.update { ResultState.Success(player) }
            } catch (e: Exception) {
                _playerResultState.update { ResultState.Error(e.message) }
            }
        }
    }
}