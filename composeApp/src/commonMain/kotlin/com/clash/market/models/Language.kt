package com.clash.market.models

import kotlinx.serialization.Serializable

@Serializable
data class Language(
    val id: Int,
    val name: String,
    val isCountry: Boolean? = null,
    val languageCode: String? = null
)
