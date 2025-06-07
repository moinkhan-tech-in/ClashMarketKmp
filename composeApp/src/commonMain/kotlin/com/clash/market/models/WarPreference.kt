package com.clash.market.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class WarPreference(val readableName: String) {
    @SerialName("in")
    IN("Included"),

    @SerialName("out")
    OUT("Not Participating");

    override fun toString(): String = readableName
}