package com.clash.market.ui.screens.search

import com.clash.market.base.BaseViewModel
import com.clash.market.base.ResultState
import com.clash.market.models.ClanDetail
import com.clash.market.models.Label
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

    private val _clanLabels = MutableStateFlow<ResultState<List<Label>>>(ResultState.Ideal)
    val clanLabels = _clanLabels

    init {
        launchIO {
            val labels = metadataRepository.getClanLabels()
            try {
                _clanLabels.update { ResultState.Success(labels.items) }
            } catch (e: Exception) {}
        }
    }

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
}