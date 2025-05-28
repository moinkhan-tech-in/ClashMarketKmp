package com.clash.market.ui.screens

import com.clash.market.models.Player

data class HomeUiState(
    val player: Player? = null,
    val showPlayerInputDialog: Boolean = false
)