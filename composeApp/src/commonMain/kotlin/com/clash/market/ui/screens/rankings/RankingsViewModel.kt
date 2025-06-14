package com.clash.market.ui.screens.rankings

import com.clash.market.base.BaseViewModel
import com.clash.market.base.ResultState
import com.clash.market.models.GlobalLocationId
import com.clash.market.network.data.repositories.MetadataRepository
import com.clash.market.network.data.repositories.RankingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RankingsViewModel(
    val rankingRepository: RankingRepository,
    val metadataRepository: MetadataRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(RankingUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchLocations()
    }

    fun onUiEvent(event: RankingUiEvent) {
        val locationId = uiState.value.selectedLocation.id ?: GlobalLocationId
        when (event) {
            RankingUiEvent.FetchTopPlayers -> fetchTopPlayers(locationId.toString())
            RankingUiEvent.FetchTopClans -> fetchTopClans(locationId.toString())
            RankingUiEvent.FetchBuilderBaseTopPlayers -> fetchBuilderBaseTopPlayers(locationId.toString())
            RankingUiEvent.FetchBuilderBaseTopClans -> fetchBuilderBaseTopClans(locationId.toString())
            is RankingUiEvent.LocationChange -> {
                _uiState.update { it.copy(selectedLocation = event.location) }
            }
        }
    }

    private fun fetchTopPlayers(locationId: String) = launchIO {
        _uiState.update { it.copy(topPlayers = ResultState.Loading) }
        runCatching {
            rankingRepository.getTopPlayers(locationId).items
        }.onSuccess { players ->
            _uiState.update { it.copy(topPlayers = ResultState.Success(players)) }
        }.onFailure { error ->
            _uiState.update { it.copy(topPlayers = ResultState.Error(error.message)) }
        }
    }

    private fun fetchTopClans(locationId: String) = launchIO {
        _uiState.update { it.copy(topClans = ResultState.Loading) }
        runCatching {
            rankingRepository.getTopClans(locationId).items
        }.onSuccess { clans ->
            _uiState.update { it.copy(topClans = ResultState.Success(clans)) }
        }.onFailure { error ->
            _uiState.update { it.copy(topClans = ResultState.Error(error.message)) }
        }
    }

    private fun fetchBuilderBaseTopPlayers(locationId: String) = launchIO {
        _uiState.update { it.copy(topBuilderPlayers = ResultState.Loading) }
        runCatching {
            rankingRepository.getTopBuilderBasePlayers(locationId).items
        }.onSuccess { players ->
            _uiState.update { it.copy(topBuilderPlayers = ResultState.Success(players)) }
        }.onFailure { error ->
            _uiState.update { it.copy(topBuilderPlayers = ResultState.Error(error.message)) }
        }
    }

    private fun fetchBuilderBaseTopClans(locationId: String) = launchIO {
        _uiState.update { it.copy(topBuilderClans = ResultState.Loading) }
        runCatching {
            rankingRepository.getTopBuilderBaseClans(locationId).items
        }.onSuccess { clans ->
            _uiState.update { it.copy(topBuilderClans = ResultState.Success(clans)) }
        }.onFailure { error ->
            _uiState.update { it.copy(topBuilderClans = ResultState.Error(error.message)) }
        }
    }

    private fun fetchLocations() = launchIO {
        _uiState.update { it.copy(locations = ResultState.Loading) }
        runCatching {
            metadataRepository.getLocations()
                .items.filter { it.isCountry == true }
                .sortedBy { it.name }
        }.onSuccess { locations ->
            _uiState.update { it.copy(locations = ResultState.Success(locations)) }
        }.onFailure { error ->
            _uiState.update { it.copy(locations = ResultState.Error(error.message)) }
        }
    }
}