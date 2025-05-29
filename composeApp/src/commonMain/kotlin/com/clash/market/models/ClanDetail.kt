package com.clash.market.models

import kotlinx.serialization.Serializable

@Serializable
data class ClanDetail(
    val tag: String,
    val name: String,
    val type: String? = null,
    val description: String? = null,
    val location: Location? = null,
    val badgeUrls: BadgeUrls? = null,
    val clanLevel: Int,
//    val clanPoints: Int,
//    val clanBuilderBasePoints: Int,
//    val clanVersusPoints: Int,
//    val requiredTrophies: Int,
    val warFrequency: String? = null,
    val warWinStreak: Int? = null,
    val warWins: Int? = null,
    val warTies: Int? = null,
    val members: Int? = null,
    val warLosses: Int? = null,
//    val isWarLogPublic: Boolean,
//    val members: Int,
    val memberList: List<Player>? = null,
    val labels: List<Label>? = null,
    val requiredVersusTrophies: Int? = null,
    val requiredTownhallLevel: Int? = null,
)
