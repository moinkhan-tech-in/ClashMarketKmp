package com.clash.market.ui.screens.search

import com.clash.market.base.ResultState
import com.clash.market.models.ClanDetail

data class SearchUiState(
    val clanSearchState: ResultState<List<ClanDetail>> = ResultState.Ideal
)