package com.clash.market.models.dtos

import kotlinx.serialization.Serializable

@Serializable
data class MetadataResponse<T>(
    val items: List<T> = listOf<T>()
)