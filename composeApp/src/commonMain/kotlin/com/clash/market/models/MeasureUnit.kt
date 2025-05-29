package com.clash.market.models

import kotlinx.serialization.Serializable

@Serializable
data class MeasureUnit(
    val name: String,
    val level: Int,
    val maxLevel: Int,
    val village: String
)
