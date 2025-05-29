
package com.clash.market.models

import kotlinx.serialization.Serializable

@Serializable
data class IconUrls(
    val small: String? = null,
    val tiny: String? = null,
    val medium: String? = null
)
