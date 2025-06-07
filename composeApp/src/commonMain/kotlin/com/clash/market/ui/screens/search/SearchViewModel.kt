package com.clash.market.ui.screens.search

import com.clash.market.base.BaseViewModel
import com.clash.market.base.ResultState
import com.clash.market.models.ClanDetail
import com.clash.market.models.Player
import com.clash.market.network.data.repositories.ClanRepository
import com.clash.market.network.data.repositories.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SearchViewModel(
    val playerRepository: PlayerRepository,
    val clanRepository: ClanRepository
) : BaseViewModel() {

    private val _clanSearchState = MutableStateFlow<ResultState<List<ClanDetail>>>(ResultState.Ideal)
    val clanSearchState = _clanSearchState

    private val _playerSearchState = MutableStateFlow<ResultState<Player>>(ResultState.Ideal)
    val playerSearchState = _playerSearchState

    fun searchClans(query: String) {
        if (query.trim().length < 3) return

        launchIO {
            _clanSearchState.update { ResultState.Loading }

            try {
                val clans = clanRepository.searchClan(name = query)
                _clanSearchState.update { ResultState.Success(clans.items.orEmpty()) }
            } catch (e: Exception) {
                _clanSearchState.update { ResultState.Error(e.message) }
            }
        }
    }

    fun searchPlayer(playerTag: String) {
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