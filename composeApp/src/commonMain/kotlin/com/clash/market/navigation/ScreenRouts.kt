package com.clash.market.navigation

import kotlinx.serialization.Serializable

sealed interface ScreenRouts {

    @Serializable
    data class PlayerDetail(val tag: String) : ScreenRouts

    @Serializable
    data class ClanDetail(val tag: String) : ScreenRouts

    @Serializable
    object Home : ScreenRouts

    @Serializable
    object Dashboard : ScreenRouts

    @Serializable
    object Search : ScreenRouts

    @Serializable
    object Clan : ScreenRouts

    @Serializable
    object War : ScreenRouts

    @Serializable
    object More : ScreenRouts
}