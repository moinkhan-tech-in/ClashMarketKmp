package com.clash.market.models.dtos

import com.clash.market.models.ClanDetail
import kotlinx.serialization.Serializable

@Serializable
data class ClanSearchResponse(
    val items: List<ClanDetail>? = null,
    val paging: Paging? = null
)

@Serializable
data class Paging(
    val cursors: Cursors
)

@Serializable
data class Cursors(
    val after: String? = null
)