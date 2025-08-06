package com.clash.market.models.dtos

import com.clash.market.models.ClanDetail
import com.clash.market.models.WarState

@kotlinx.serialization.Serializable
data class WarLeagueGroupResponse(
    val tag: String? = null,
    val state: WarState? = null,
    val season: String? = null,
    val clans: List<ClanDetail>? = null,
    val rounds: List<Round>? = null
)

@kotlinx.serialization.Serializable
data class Round(val warTags: List<String>)