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

val FakePlayer = Player(
    tag = "#2GYCPJJY2",
    name = "MSquare",
    role = "coLeader",
    trophies = 1234,
    expLevel = 236,
    bestTrophies = 4000,
    warStars = 1,
    attackWins = 1,
    defenseWins = 2,
    donations = 12,
    donationsReceived = 123,
    clan = FakeClanDetailItem,
    townHallLevel = 17
)

