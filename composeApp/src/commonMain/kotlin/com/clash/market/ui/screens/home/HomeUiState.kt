package com.clash.market.ui.screens.home

data class HomeUiState(
    val playerClan: PlayerClanStatus = PlayerClanStatus.NotLinkedProfile
)

sealed class PlayerClanStatus {
    data class EnrolledInClan(val tag: String): PlayerClanStatus()
    object NotEnrolledInClan: PlayerClanStatus()
    object NotLinkedProfile: PlayerClanStatus()
}