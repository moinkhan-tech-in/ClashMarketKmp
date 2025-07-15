package com.clash.market.ui.screens.rankings

import com.clash.market.models.Location

sealed interface RankingUiEvent {
    data class LocationChange(val location: Location): RankingUiEvent
    class TabChange(val selectedTabIndex: Int): RankingUiEvent
}