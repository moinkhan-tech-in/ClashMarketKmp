package com.clash.market.models

import kotlinx.serialization.Serializable

@Serializable
data class ClanDetail(
    val tag: String? = null,
    val name: String? = null,
    val type: String? = null,
    val description: String? = null,
    val location: Location? = null,
    val badgeUrls: BadgeUrls? = null,
    val clanLevel: Int,
    val chatLanguage: Language? = null,
    val warLeague: League? = null,
//    val clanPoints: Int,
//    val clanBuilderBasePoints: Int,
//    val clanVersusPoints: Int,
//    val requiredTrophies: Int,
    val warFrequency: WarFrequency? = null,
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
    val requiredTrophies: Int? = null,
    val requiredTownhallLevel: Int? = null,
)

val FakeClanDetailItem = ClanDetail(
    tag = "#2G3G34FE",
    name = "Avengers",
    clanLevel = 12,
    members = 12,
    type = "open",
    requiredTrophies = 10,
    location = Location(
        name = "India",
        isCountry = true,
        countryCode = "IN",
        id = 1
    )
)