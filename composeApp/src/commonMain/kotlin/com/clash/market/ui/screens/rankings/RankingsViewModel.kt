package com.clash.market.ui.screens.rankings

import com.clash.market.base.BaseViewModel
import com.clash.market.base.ResultState
import com.clash.market.models.ClanDetail
import com.clash.market.models.GlobalLocationId
import com.clash.market.models.Player
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

    private val topPlayersByLocation: HashMap<String, List<Player>> = hashMapOf()
    private val topClansByLocation: HashMap<String,List<ClanDetail>> = hashMapOf()
    private val topBuilderPlayersByLocation: HashMap<String,List<Player>> = hashMapOf()
    private val topBuilderClansByLocation: HashMap<String, List<ClanDetail>> = hashMapOf()

    init {
        fetchLocations()
    }

    fun onUiEvent(event: RankingUiEvent) {
        when (event) {
            is RankingUiEvent.TabChange -> {
                _uiState.update { it.copy(selectedTabIndex = event.selectedTabIndex) }
                refreshData()
            }
            is RankingUiEvent.LocationChange -> {
                _uiState.update { it.copy(selectedLocation = event.location) }
                refreshData()
            }
        }
    }

    private fun refreshData() {
        val locationId = uiState.value.selectedLocation.id ?: GlobalLocationId
        when (uiState.value.selectedTabIndex) {
            0 -> fetchTopPlayers(locationId.toString())
            1 -> fetchTopClans(locationId.toString())
            2 -> fetchBuilderBaseTopPlayers(locationId.toString())
            3 -> fetchBuilderBaseTopClans(locationId.toString())
        }
    }

    private fun fetchTopPlayers(locationId: String) = launchIO {
        topPlayersByLocation[locationId]?.let { players ->
            _uiState.update { it.copy(topPlayers = ResultState.Success(players)) }
            return@launchIO
        }

        _uiState.update { it.copy(topPlayers = ResultState.Loading) }
        runCatching {
            rankingRepository.getTopPlayers(locationId).items
        }.onSuccess { players ->
            _uiState.update { it.copy(topPlayers = ResultState.Success(players)) }
            topPlayersByLocation[locationId] = players
        }.onFailure { error ->
            _uiState.update { it.copy(topPlayers = ResultState.Error(error.message)) }
        }
    }

    private fun fetchTopClans(locationId: String) = launchIO {
        topClansByLocation[locationId]?.let { clans ->
            _uiState.update { it.copy(topClans = ResultState.Success(clans)) }
            return@launchIO
        }

        _uiState.update { it.copy(topClans = ResultState.Loading) }
        runCatching {
            rankingRepository.getTopClans(locationId).items
        }.onSuccess { clans ->
            _uiState.update { it.copy(topClans = ResultState.Success(clans)) }
            topClansByLocation[locationId] = clans
        }.onFailure { error ->
            _uiState.update { it.copy(topClans = ResultState.Error(error.message)) }
        }
    }

    private fun fetchBuilderBaseTopPlayers(locationId: String) = launchIO {
        topBuilderPlayersByLocation[locationId]?.let { players ->
            _uiState.update { it.copy(topBuilderPlayers = ResultState.Success(players)) }
            return@launchIO
        }

        _uiState.update { it.copy(topBuilderPlayers = ResultState.Loading) }
        runCatching {
            rankingRepository.getTopBuilderBasePlayers(locationId).items
        }.onSuccess { players ->
            _uiState.update { it.copy(topBuilderPlayers = ResultState.Success(players)) }
            topBuilderPlayersByLocation[locationId] = players
        }.onFailure { error ->
            _uiState.update { it.copy(topBuilderPlayers = ResultState.Error(error.message)) }
        }
    }

    private fun fetchBuilderBaseTopClans(locationId: String) = launchIO {
        topBuilderClansByLocation[locationId]?.let { clans ->
            _uiState.update { it.copy(topBuilderClans = ResultState.Success(clans)) }
            return@launchIO
        }

        _uiState.update { it.copy(topBuilderClans = ResultState.Loading) }
        runCatching {
            rankingRepository.getTopBuilderBaseClans(locationId).items
        }.onSuccess { clans ->
            _uiState.update { it.copy(topBuilderClans = ResultState.Success(clans)) }
            topBuilderClansByLocation[locationId] = clans
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