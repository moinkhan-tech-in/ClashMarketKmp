package com.clash.market.models

import kotlinx.serialization.Serializable

@Serializable
data class ClanCapital(
    val capitalHallLevel: Int? = null,
    val districts: List<ClanCapitalDistrict>? = null
)
