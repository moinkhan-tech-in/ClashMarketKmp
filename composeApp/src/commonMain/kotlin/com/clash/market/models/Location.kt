package com.clash.market.models

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val id: Int?,
    val name: String,
    val isCountry: Boolean? = null,
    val countryCode: String? = null
)

val WorldWideLocation = Location(
    id = null,
    name = "WorldWide",
    isCountry = true,
    countryCode = "Worldwide"
)

val GlobalLocationId = "global"