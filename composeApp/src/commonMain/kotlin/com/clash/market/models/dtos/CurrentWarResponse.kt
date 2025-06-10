package com.clash.market.models.dtos

import com.clash.market.models.ClanDetail
import com.clash.market.models.FakeClanDetailItem
import com.clash.market.models.WarState
import com.clash.market.utils.getTimeAgoLabel
import com.clash.market.utils.getTimeRemainingLabel
import com.clash.market.utils.parseWarTime
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWarResponse(
    val state: WarState? = null,
    val result: String? = null,
    val teamSize: Int? = null,
    val startTime: String? = null,
    val endTime: String? = null,
    val preparationStartTime: String? = null,
    val attacksPerMember: Int? = null,
    val clan: ClanDetail,
    val opponent: ClanDetail
) {


    fun getPreparationTime(): String {
        val startTime = parseWarTime(startTime.orEmpty())
        return getTimeRemainingLabel(startTime) // → "3h 15m"
    }

    fun getWarEndTime(): String {
        val endTime = parseWarTime(endTime.orEmpty())
        return getTimeRemainingLabel(endTime) // → "18h 42m"
    }

    fun getWarEndedTime(): String {
        return getTimeAgoLabel(parseWarTime(endTime.orEmpty()))
    }

    fun getDetailsForWarCard(): List<Triple<String, String, String>> {
        if (state == null || state == WarState.PREPARATION) return emptyList()

        return arrayListOf<Triple<String, String, String>>().apply {

            clan.destructionPercentage?.let {
                add(
                    Triple(
                        clan.destructionPercentage.toString(),
                        "Destruction",
                        opponent.destructionPercentage.toString()
                    )
                )

                clan.expEarned?.let {
                    add(
                        Triple(
                            it.toString(),
                            "Exp Earned",
                            opponent.expEarned.toString()
                        )
                    )
                }
            }
        }
    }
}

val FakeCurrentWarResponse = CurrentWarResponse(
    state = WarState.IN_WAR,
    teamSize = 30,
    clan = FakeClanDetailItem,
    opponent = FakeClanDetailItem,
    preparationStartTime = "20250606T080000.000Z",
    startTime = "20250607T080000.000Z",
    endTime = "20250608T080000.000Z"
)