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
    val clanLevel: Int? = null,
    val clanCapital: ClanCapital? = null,
    val clanCapitalPoints: Int? = null,
    val capitalLeague: League? = null,
    val chatLanguage: Language? = null,
    val warLeague: League? = null,
    val warFrequency: WarFrequency? = null,
    val warWinStreak: Int? = null,
    val warWins: Int? = null,
    val warTies: Int? = null,
    val members: MembersField? = null,
    val warLosses: Int? = null,
    val memberList: List<Player>? = null,
    val labels: List<Label>? = null,
    val requiredVersusTrophies: Int? = null,
    val expEarned: Int? = null,
    val requiredTrophies: Int? = null,
    val requiredTownhallLevel: Int? = null,
    val attacks: Int? = null,
    val stars: Int? = null,
    val destructionPercentage: Float? = null,
) {

    fun getMemberCount(): Int {
        return when (val m = members) {
            is MembersField.Count -> m.total
            is MembersField.List -> m.members.size
            null -> 0
        }
    }
}

val FakeClanDetailItem = ClanDetail(
    tag = "#2G3G34FE",
    name = "Avengers",
    description = "Always do your both attacks in clan war, otherwise you will be kicked out. Even if we are behind in stars like 10 vs 100, do your attacks even with one troop! If you are not sure which target, ask from higher rank players in the chat, they will help",
    clanLevel = 12,
    members = MembersField.Count(12),
    type = "open",
    stars = 12,
    attacks = 10,
    warTies = 10,
    warWins = 10,
    warLosses = 10,
    expEarned = 200,
    destructionPercentage = 90.12f,
    badgeUrls = BadgeUrls(
        small = "https://api-assets.clashofclans.com/badges/200/pyrF7jEkUBWNgthO_NBKRYs2rg9sMRHBIpLwwSC18og.png",
        medium = "https://api-assets.clashofclans.com/badges/200/pyrF7jEkUBWNgthO_NBKRYs2rg9sMRHBIpLwwSC18og.png",
        large = "https://api-assets.clashofclans.com/badges/200/pyrF7jEkUBWNgthO_NBKRYs2rg9sMRHBIpLwwSC18og.png"
    ),
    requiredTrophies = 10,
    location = Location(
        name = "India",
        isCountry = true,
        countryCode = "IN",
        id = 1
    )
)