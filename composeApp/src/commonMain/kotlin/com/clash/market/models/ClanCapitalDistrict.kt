package com.clash.market.models

import kotlinx.serialization.Serializable

@Serializable
data class ClanCapitalDistrict(
    val id: Int? = null,
    val name: String? = null,
    val districtHallLevel: Int? = null
)
