package com.clash.market.models.dtos

import com.clash.market.models.ClanDetail
import com.clash.market.models.WarState
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWarResponse(
    val state: WarState,
    val teamSize: Int? = null,
    val clan: ClanDetail,
    val opponent: ClanDetail
)