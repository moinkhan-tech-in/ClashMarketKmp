package com.clash.market.ui.screens.playerdetail

import com.clash.market.models.Player

data class PlayerDetailUiState(
    val player: Player? = null,
    val showPlayerInputDialog: Boolean = false
)