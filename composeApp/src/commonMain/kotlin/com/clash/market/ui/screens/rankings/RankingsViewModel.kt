package com.clash.market.ui.screens.rankings

import com.clash.market.base.BaseViewModel
import com.clash.market.base.ResultState
import com.clash.market.models.ClanDetail
import com.clash.market.models.Location
import com.clash.market.models.Player
import com.clash.market.network.data.repositories.RankingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RankingsViewModel(
    val rankingRepository: RankingRepository
) : BaseViewModel() {

    private val _topPlayerState = MutableStateFlow<ResultState<List<Player>>>(ResultState.Ideal)
    val topPlayerState = _topPlayerState.asStateFlow()

    private val _topClanState = MutableStateFlow<ResultState<List<ClanDetail>>>(ResultState.Ideal)
    val topClanState = _topClanState.asStateFlow()

    private val _topBuilderBasePlayerState = MutableStateFlow<ResultState<List<Player>>>(ResultState.Ideal)
    val topBuilderBasePlayerState = _topBuilderBasePlayerState.asStateFlow()

    private val _topBuilderBaseClanState = MutableStateFlow<ResultState<List<ClanDetail>>>(ResultState.Ideal)
    val topBuilderBaseClanState = _topBuilderBaseClanState.asStateFlow()

    private val _locationState = MutableStateFlow<ResultState<List<Location>>>(ResultState.Ideal)
    val locationState = _locationState.asStateFlow()

    init {
        fetchTopPlayers()
        fetchTopClans()
        fetchBuilderBaseTopPlayers()
        fetchBuilderBaseTopClans()
    }

    private fun fetchTopPlayers() {
        launchIO {
            _topPlayerState.update { ResultState.Loading }
            try {
                val response = rankingRepository.getTopPlayers("32000010")
                _topPlayerState.update { ResultState.Success(response.items) }
            } catch (e: Exception) {
                _topPlayerState.update { ResultState.Error(e.message) }
            }
        }
    }

    private fun fetchTopClans() {
        launchIO {
            _topClanState.update { ResultState.Loading }
            try {
                val response = rankingRepository.getTopClans("32000010")
                _topClanState.update { ResultState.Success(response.items) }
            } catch (e: Exception) {
                _topClanState.update { ResultState.Error(e.message) }
            }
        }
    }

    private fun fetchBuilderBaseTopPlayers() {
        launchIO {
            _topBuilderBasePlayerState.update { ResultState.Loading }
            try {
                val response = rankingRepository.getTopBuilderBasePlayers("32000010")
                _topBuilderBasePlayerState.update { ResultState.Success(response.items) }
            } catch (e: Exception) {
                _topBuilderBasePlayerState.update { ResultState.Error(e.message) }
            }
        }
    }

    private fun fetchBuilderBaseTopClans() {
        launchIO {
            _topBuilderBaseClanState.update { ResultState.Loading }
            try {
                val response = rankingRepository.getTopBuilderBaseClans("32000010")
                _topBuilderBaseClanState.update { ResultState.Success(response.items) }
            } catch (e: Exception) {
                _topBuilderBaseClanState.update { ResultState.Error(e.message) }
            }
        }
    }
}