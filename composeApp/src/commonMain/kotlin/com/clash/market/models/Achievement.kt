package com.clash.market.models

import kotlinx.serialization.Serializable

@Serializable
data class Achievement(
    val name: String,
    val stars: Int,
    val value: Int,
    val target: Int,
    val info: String,
    val completionInfo: String? = null,
    val village: VillageType? = null
)

val FakeAchievement = Achievement(
    name = "Friend in Need",
    stars = 3,
    value = 50000,
    target = 5000,
    info = "Donate 5,000 housing space worth of reinforcements to Clan mates.",
    completionInfo = "Total donations: 50,000",
    village = VillageType.HOME
)