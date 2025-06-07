package com.clash.market.ui.screens.home

data class HomeUiState(
    val showPlayerInputDialog: Boolean = false,
    val playerClan: PlayerClanStatus = PlayerClanStatus.Unknown
)

sealed class PlayerClanStatus {
    data class EnrolledInClan(val tag: String): PlayerClanStatus()
    object NotEnrolledInClan: PlayerClanStatus()
    object Unknown: PlayerClanStatus()
}