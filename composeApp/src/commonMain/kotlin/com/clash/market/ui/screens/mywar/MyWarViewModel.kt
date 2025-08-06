package com.clash.market.ui.screens.mywar

import com.clash.market.base.BaseViewModel
import com.clash.market.local.datastore.ClanState
import com.clash.market.local.datastore.PreferenceManager
import com.clash.market.local.datastore.ProfileState
import com.clash.market.models.WarState
import com.clash.market.network.data.repositories.ClanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update

class MyWarViewModel(
    val clanRepository: ClanRepository,
    val preferenceManager: PreferenceManager
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<MyWarUiState>(MyWarUiState.Ideal)
    val uiState = _uiState.asStateFlow()

    init {
        fetchProfileClan()
    }

    private fun fetchProfileClan() {
        launchIO {
            preferenceManager.getProfileStateAsFlow()
                .collectLatest { profileState ->
                    when (profileState) {
                        is ProfileState.Linked -> {
                            when (profileState.clanState) {
                                is ClanState.InClan -> {
                                    fetchCurrentWar(profileState.clanState.tag)
                                }

                                is ClanState.NotInClan -> {
                                    _uiState.update { MyWarUiState.NotInClan }
                                }
                            }
                        }

                        ProfileState.NotLinked -> {
                            _uiState.update { MyWarUiState.NoProfileLinked }
                        }
                    }
                }
        }
    }

    private fun fetchCurrentWar(tag: String) {
        launchIO {
            _uiState.update { MyWarUiState.Loading }

            try {
                val war = clanRepository.getCurrentWar(tag)
                _uiState.update { MyWarUiState.StandardWar(war) }

                if (war.state == WarState.NOT_IN_WAR) {
                    _uiState.update { MyWarUiState.NotInWar }
                    fetchClanWarLeague(tag)
                }
            } catch (e: Exception) {
                _uiState.update { MyWarUiState.Error(e.message.orEmpty()) }
            }
        }
    }

    private fun fetchClanWarLeague(tag: String) {
        launchIO {
            runCatching {
                _uiState.update { MyWarUiState.Loading }
                clanRepository.getWarLeagueGroup(tag)
            }.onSuccess { warLeague ->
                _uiState.update { MyWarUiState.LeagueWar(warLeague) }
            }.onFailure { exception ->
                _uiState.update { MyWarUiState.Error(exception.message.orEmpty()) }
            }
        }
    }

    private fun fetchWarByTags(warTags: List<String>) {
        launchIO {

        }
    }
}