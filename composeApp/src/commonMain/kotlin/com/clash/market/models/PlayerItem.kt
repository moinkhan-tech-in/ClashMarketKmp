package com.clash.market.models

import kotlinx.serialization.Serializable

@Serializable
data class PlayerItem(
    val name: String,
    val level: Int,
    val maxLevel: Int,
    val village: VillageType? = null
)

val FakePlayerItem = PlayerItem(
    name = "Archer",
    level = 10,
    maxLevel = 15,
    village = VillageType.HOME
)