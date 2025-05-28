package com.clash.market.models
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val tag: String,
    val name: String,
    val townHallLevel: Int,
    val townHallWeaponLevel: Int? = null,
    val expLevel: Int,
    val trophies: Int,
    val bestTrophies: Int,
    val warStars: Int,
    val attackWins: Int,
    val defenseWins: Int,
    val builderHallLevel: Int? = null,
    val versusTrophies: Int? = null,
    val bestVersusTrophies: Int? = null,
    val versusBattleWins: Int? = null,
    val role: String? = null,
    val warPreference: String? = null,
    val donations: Int,
    val donationsReceived: Int,
    val clan: ClanDetail? = null,
    val league: League? = null,
    val achievements: List<Achievement> = emptyList(),
    val troops: List<MeasureUnit> = emptyList(),
    val heroes: List<MeasureUnit> = emptyList(),
    val spells: List<MeasureUnit> = emptyList(),
    val labels: List<Label> = emptyList()
)

@Serializable
data class ClanDetail(
    val tag: String,
    val name: String,
//    val type: String,
    val description: String? = null,
    val location: Location? = null,
    val badgeUrls: BadgeUrls,
    val clanLevel: Int,
//    val clanPoints: Int,
//    val clanBuilderBasePoints: Int,
//    val clanVersusPoints: Int,
//    val requiredTrophies: Int,
    val warFrequency: String? = null,
    val warWinStreak: Int? = null,
    val warWins: Int? = null,
    val warTies: Int? = null,
    val warLosses: Int? = null,
//    val isWarLogPublic: Boolean,
//    val members: Int,
    val memberList: List<Player>? = null,
    val labels: List<Label>? = null,
    val requiredVersusTrophies: Int? = null,
    val requiredTownhallLevel: Int? = null,
)

@Serializable
data class BadgeUrls(
    val small: String,
    val large: String,
    val medium: String
)

@Serializable
data class Location(
    val id: Int,
    val name: String,
    val isCountry: Boolean? = null,
    val countryCode: String? = null
)


@Serializable
data class League(
    val id: Int,
    val name: String,
    val iconUrls: IconUrls
)

@Serializable
data class IconUrls(
    val small: String? = null,
    val tiny: String? = null,
    val medium: String? = null
)

@Serializable
data class Achievement(
    val name: String,
    val stars: Int,
    val value: Int,
    val target: Int,
    val info: String,
    val completionInfo: String? = null,
    val village: String
)

@Serializable
data class MeasureUnit(
    val name: String,
    val level: Int,
    val maxLevel: Int,
    val village: String
)

@Serializable
data class Label(
    val id: Int,
    val name: String,
    val iconUrls: IconUrls
)