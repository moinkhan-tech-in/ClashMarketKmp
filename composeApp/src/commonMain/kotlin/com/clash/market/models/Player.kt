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
    val clan: Clan? = null,
    val league: League? = null,
    val achievements: List<Achievement> = emptyList(),
    val troops: List<MeasureUnit> = emptyList(),
    val heroes: List<MeasureUnit> = emptyList(),
    val spells: List<MeasureUnit> = emptyList(),
    val labels: List<Label> = emptyList()
)

@Serializable
data class Clan(
    val tag: String,
    val name: String,
    val clanLevel: Int,
    val badgeUrls: BadgeUrls
)

@Serializable
data class BadgeUrls(
    val small: String,
    val large: String,
    val medium: String
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