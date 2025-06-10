package com.clash.market.models.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ClashResponse<T>(
    val items: List<T> = emptyList<T>(),
    val paging: Paging? = null
)
