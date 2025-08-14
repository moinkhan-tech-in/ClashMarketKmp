package com.clash.market.models

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val tag: String,
    val name: String,
    val townHallLevel: Int? = null,
    val townhallLevel: Int? = null,
    val townHallWeaponLevel: Int? = null,
    val expLevel: Int? = null,
    val trophies: Int? = null,
    val builderBaseTrophies: Int? = null,
    val bestTrophies: Int? = null,
    val warStars: Int? = null,
    val attackWins: Int? = null,
    val defenseWins: Int? = null,
    val builderHallLevel: Int? = null,
    val versusTrophies: Int? = null,
    val bestVersusTrophies: Int? = null,
    val versusBattleWins: Int? = null,
    val role: PlayerRole? = null,
    val rank: Int? = null,
    val previousRank: Int? = null,
    val warPreference: WarPreference? = null,
    val donations: Int? = null,
    val donationsReceived: Int? = null,
    val clan: ClanDetail? = null,
    val league: League? = null,
    val mapPosition: Int? = null,
    val opponentAttacks: Int? = null,
    val bestOpponentAttack: Attack? = null,
    val attacks: List<Attack> = emptyList(),
    val achievements: List<Achievement> = emptyList(),
    val troops: List<PlayerItem> = emptyList(),
    val heroes: List<PlayerItem> = emptyList(),
    val heroEquipment: List<PlayerItem> = emptyList(),
    val spells: List<PlayerItem> = emptyList(),
    val labels: List<Label> = emptyList()
)

val FakePlayer = Player(
    tag = "#2GYCPJJY2",
    name = "MSquare",
    role = PlayerRole.CO_LEADER,
    rank = 1,
    previousRank = 4,
    warPreference = WarPreference.IN,
    trophies = 1234,
    expLevel = 236,
    bestTrophies = 4000,
    warStars = 1,
    attackWins = 1,
    league = League(
        id = 1000,
        name = "Unranked",
        iconUrls = IconUrls(
            small = "https://api-assets.clashofclans.com/leagues/72/e--YMyIexEQQhE4imLoJcwhYn6Uy8KqlgyY3_kFV6t4.png"
        )
    ),
    defenseWins = 2,
    donations = 12,
    donationsReceived = 123,
    clan = FakeClanDetailItem,
    townHallLevel = 17,
    troops = listOf(
        FakePlayerItem, FakePlayerItem, FakePlayerItem, FakePlayerItem, FakePlayerItem
    ),
    spells = listOf(
        FakePlayerItem, FakePlayerItem, FakePlayerItem, FakePlayerItem, FakePlayerItem
    ),
    heroes = listOf(
        FakePlayerItem, FakePlayerItem, FakePlayerItem, FakePlayerItem, FakePlayerItem
    ),
    heroEquipment = listOf(
        FakePlayerItem, FakePlayerItem, FakePlayerItem, FakePlayerItem, FakePlayerItem
    )
)

