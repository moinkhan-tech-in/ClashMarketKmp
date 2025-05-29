package com.clash.market.models

import kotlinx.serialization.Serializable

@Serializable
data class Label(
    val id: Int,
    val name: String,
    val iconUrls: IconUrls
)