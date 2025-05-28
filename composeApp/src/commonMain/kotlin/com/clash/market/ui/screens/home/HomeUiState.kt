package com.clash.market.ui.screens.home

import com.clash.market.models.Player

data class HomeUiState(
    val player: Player? = null,
    val showPlayerInputDialog: Boolean = false
)