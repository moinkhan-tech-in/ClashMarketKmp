package com.clash.market.ui.screens.enterprofile

import com.clash.market.base.ResultState
import com.clash.market.models.Player

data class EnterProfileUiState(
    val playerResultState: ResultState<Player> = ResultState.Ideal
)