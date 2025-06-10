package com.clash.market.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class VillageType(val readableName: String) {
    @SerialName("home")
    HOME("Home Village"),

    @SerialName("builderBase")
    BUILDER_BASE("Builder Base"),

    @SerialName("clanCapital")
    CLAN_CAPITAL("Clan Capital");

    override fun toString(): String = readableName
}