package com.clash.market.models.dtos

import kotlinx.serialization.Serializable

@Serializable
data class VerifyTokenRequest(
    val token: String
)

@Serializable
data class VerifyTokenResponse(
    val tag: String,
    val token: String,
    val status: String
)