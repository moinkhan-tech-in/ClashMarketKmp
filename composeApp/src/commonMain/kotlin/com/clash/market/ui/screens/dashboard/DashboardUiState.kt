package com.clash.market.ui.screens.dashboard

import com.clash.market.models.ClanDetail
import com.clash.market.models.Player

data class DashboardUiState(
    val player: Player? = null,
    val clanDetail: ClanDetail? = null
)