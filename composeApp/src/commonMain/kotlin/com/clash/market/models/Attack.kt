package com.clash.market.models

import kotlinx.serialization.Serializable

@Serializable
data class Attack(
    val attackerTag: String? = null,
    val defenderTag: String? = null,
    val stars: Int? = null,
    val destructionPercentage: Float? = null,
    val order: Int? = null,
    val duration: Int? = null
)