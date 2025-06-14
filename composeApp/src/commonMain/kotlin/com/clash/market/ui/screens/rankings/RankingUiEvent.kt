package com.clash.market.ui.screens.rankings

import com.clash.market.models.Location

sealed interface RankingUiEvent {
    object FetchTopPlayers: RankingUiEvent
    object FetchTopClans: RankingUiEvent
    object FetchBuilderBaseTopPlayers: RankingUiEvent
    object FetchBuilderBaseTopClans: RankingUiEvent
    data class LocationChange(val location: Location): RankingUiEvent
}