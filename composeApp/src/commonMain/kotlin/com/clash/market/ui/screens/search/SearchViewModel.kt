package com.clash.market.ui.screens.search

import com.clash.market.base.BaseViewModel
import com.clash.market.base.ResultState
import com.clash.market.local.datastore.PreferenceManager
import com.clash.market.models.ClanDetail
import com.clash.market.network.data.repositories.ClanRepository
import com.clash.market.network.data.repositories.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchViewModel(
    val playerRepository: PlayerRepository,
    val clanRepository: ClanRepository,
    val preferenceManager: PreferenceManager
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private val _clanSearchState = MutableStateFlow<ResultState<List<ClanDetail>>>(ResultState.Ideal)
    val clanSearchState = _clanSearchState

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