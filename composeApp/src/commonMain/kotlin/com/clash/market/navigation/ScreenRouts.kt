package com.clash.market.navigation

import kotlinx.serialization.Serializable

sealed interface ScreenRouts {

    @Serializable
    object Splash : ScreenRouts

    @Serializable
    object Onboarding : ScreenRouts

    @Serializable
    data class PlayerDetail(
        val tag: String,
        val name: String? = null
    ) : ScreenRouts

    @Serializable
    data class ClanDetail(val tag: String) : ScreenRouts

    @Serializable
    object Home : ScreenRouts

    @Serializable
    object Dashboard : ScreenRouts

    @Serializable
    object Search : ScreenRouts

    @Serializable
    object MyClan : ScreenRouts

    @Serializable
    object Rankings : ScreenRouts

    @Serializable
    data class WarLogs(
        val tag: String,
        val name: String
    ) : ScreenRouts

    @Serializable
    object MyWar : ScreenRouts

    @Serializable
    object MyProfile : ScreenRouts

    @Serializable
    data object EnterProfile: ScreenRouts

    @Serializable
    data class LeagueWarDetail(
        val title: String,
        val season: String,
        val warTags: List<String>
    ): ScreenRouts
}