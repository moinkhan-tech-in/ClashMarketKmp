package com.clash.market.ui.screens.search

import com.clash.market.base.BaseViewModel
import com.clash.market.base.ResultState
import com.clash.market.models.ClanDetail
import com.clash.market.models.Label
import com.clash.market.models.Location
import com.clash.market.network.data.repositories.ClanRepository
import com.clash.market.network.data.repositories.MetadataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SearchViewModel(
    private val clanRepository: ClanRepository,
    private val metadataRepository: MetadataRepository
) : BaseViewModel() {

    private val _clanSearchState = MutableStateFlow<ResultState<List<ClanDetail>>>(ResultState.Ideal)
    val clanSearchState = _clanSearchState

    private val _clanLabels = MutableStateFlow<List<Label>>(emptyList())
    val clanLabels = _clanLabels

    private val _locations = MutableStateFlow<List<Location>>(emptyList())
    val locations = _locations

    private val _clanFilterOptions = MutableStateFlow(ClanFilterOptions())
    val clanFilterOptions = _clanFilterOptions

    private var searchQuery: String = ""

    init {
        launchIO {
            try {
                val labels = metadataRepository.getClanLabels()
                _clanLabels.update { labels.items }

                val locations = metadataRepository.getLocations()
                    .items.filter { it.isCountry == true }
                    .sortedBy { it.name }

                _locations.update { locations }
            } catch (e: Exception) {}
        }
    }

    fun searchClans(query: String) {
        if (query.trim().length < 3) return

        this.searchQuery = query
        val filterOptions = _clanFilterOptions.value

        launchIO {
            _clanSearchState.update { ResultState.Loading }
            try {
                val clans = clanRepository.searchClan(
                    name = query,
                    warFrequency = filterOptions.warFrequency,
                    minMembers = filterOptions.minMember,
                    maxMembers = filterOptions.maxMember,
                    minClanPoints = filterOptions.minClanPoint,
                    minClanLevel = filterOptions.clanLevel,
                    locationId = filterOptions.location?.id,
                    labels = filterOptions.labels
                )
                _clanSearchState.update { ResultState.Success(clans.items.orEmpty()) }
            } catch (e: Exception) {
                _clanSearchState.update { ResultState.Error(e.message) }
            }
        }
    }

    fun applyFilter(filterOptions: ClanFilterOptions) {
        _clanFilterOptions.update { filterOptions }
        searchClans(searchQuery)
    }
}