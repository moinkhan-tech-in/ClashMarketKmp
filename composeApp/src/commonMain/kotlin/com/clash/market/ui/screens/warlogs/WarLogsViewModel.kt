package com.clash.market.ui.screens.warlogs

import com.clash.market.base.BaseViewModel
import com.clash.market.base.ResultState
import com.clash.market.models.dtos.CurrentWarResponse
import com.clash.market.network.data.repositories.ClanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class WarLogsViewModel(
    val clanRepository: ClanRepository
) : BaseViewModel() {

    private val _warLogsState = MutableStateFlow<ResultState<List<CurrentWarResponse>>>(ResultState.Ideal)
    val warLogsState = _warLogsState

    fun fetchWarLogs(tag: String) {
        launchIO {
            _warLogsState.update { ResultState.Loading }

            try {
                val warLogs = clanRepository.getClanWarLogs(tag)
                _warLogsState.update { ResultState.Success(warLogs.items.filter { it.result != null }) }
            } catch (e: Exception) {
                _warLogsState.update { ResultState.Error(e.message) }
            }
        }
    }
}