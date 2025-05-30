package com.clash.market.ui.screens.clandetail

import com.clash.market.base.BaseViewModel
import com.clash.market.base.ResultState
import com.clash.market.models.ClanDetail
import com.clash.market.network.data.repositories.ClanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ClanDetailViewModel(
    private val clanRepository: ClanRepository
) : BaseViewModel() {

    private val _clanDetailState = MutableStateFlow<ResultState<ClanDetail>>(ResultState.Ideal)
    val clanDetailState: StateFlow<ResultState<ClanDetail>> = _clanDetailState.asStateFlow()

    fun fetchClanDetail(tag: String) {
        launchIO {
            _clanDetailState.update { ResultState.Loading }
            try {
                val clanDetail = clanRepository.getClanDetails(tag)
                _clanDetailState.update { ResultState.Success(clanDetail) }
            } catch (e: Exception) {
                _clanDetailState.update { ResultState.Error(e.message) }
            }
        }
    }
}