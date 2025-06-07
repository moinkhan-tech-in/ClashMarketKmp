package com.clash.market.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PlayerRole(val readableName: String) {
    @SerialName("leader")
    LEADER("Leader"),

    @SerialName("coLeader")
    CO_LEADER("Co-Leader"),

    @SerialName("admin")
    ADMIN("Elder"),

    @SerialName("member")
    MEMBER("Member");

    override fun toString(): String = readableName
}