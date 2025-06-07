package com.clash.market.models.dtos

import com.clash.market.models.ClanDetail
import com.clash.market.models.FakeClanDetailItem
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

val FakeClanSearchResponse = ClanSearchResponse(
    items = listOf(
        FakeClanDetailItem,
        FakeClanDetailItem,
        FakeClanDetailItem,
        FakeClanDetailItem
    )
)