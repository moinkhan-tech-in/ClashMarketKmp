package com.clash.market.models

enum class WarFrequency(val value: String) {
    ALWAYS("always"),
    MORE_THAN_ONCE_PER_WEEK("moreThanOncePerWeek"),
    ONCE_PER_WEEK("oncePerWeek"),
    LESS_THAN_ONCE_PER_WEEK("lessThanOncePerWeek"),
    NEVER("never"),
    UNKNOWN("unknown");

    override fun toString() = value
}