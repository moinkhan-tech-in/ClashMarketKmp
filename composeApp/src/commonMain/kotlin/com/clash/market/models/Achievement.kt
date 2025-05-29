package com.clash.market.models

import kotlinx.serialization.Serializable

@Serializable
data class Achievement(
    val name: String,
    val stars: Int,
    val value: Int,
    val target: Int,
    val info: String,
    val completionInfo: String? = null,
    val village: String
)
