package com.clash.market.models

import kotlinx.serialization.Serializable

@Serializable
data class BadgeUrls(
    val small: String,
    val large: String,
    val medium: String
)
