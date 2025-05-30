package com.clash.market.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class WarFrequency(val displayName: String) {
    @SerialName("always")
    ALWAYS("Always"),

    @SerialName("moreThanOncePerWeek")
    MORE_THAN_ONCE_PER_WEEK("More than once per week"),

    @SerialName("oncePerWeek")
    ONCE_PER_WEEK("Once per week"),

    @SerialName("lessThanOncePerWeek")
    LESS_THAN_ONCE_PER_WEEK("Less than once per week"),

    @SerialName("never")
    NEVER("Never"),

    @SerialName("unknown")
    UNKNOWN("Unknown");

    override fun toString(): String = displayName
}