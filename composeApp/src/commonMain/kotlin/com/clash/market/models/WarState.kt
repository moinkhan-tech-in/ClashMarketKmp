package com.clash.market.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class WarState(val readableName: String) {
    @SerialName("notInWar")
    NOT_IN_WAR("Not in war"),

    @SerialName("preparation")
    PREPARATION("Preparation"),

    @SerialName("inWar")
    IN_WAR("In War"),

    @SerialName("warEnded")
    WAR_ENDED("War Ended"),

    @SerialName("ended")
    ENDED("War Ended");

    override fun toString(): String = readableName
}