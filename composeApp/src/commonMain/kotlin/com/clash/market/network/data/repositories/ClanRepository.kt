package com.clash.market.network.data.repositories

import com.clash.market.models.ClanDetail
import com.clash.market.models.WarFrequency
import com.clash.market.models.dtos.ClanSearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.encodeURLPath

private val DefaultSearchClanLimit = 50

interface ClanRepository {
    suspend fun getClanDetails(tag: String): ClanDetail

    suspend fun searchClan(
        name: String,
        warFrequency: WarFrequency? = null,
        locationId: Int? = null,
        minMembers: Int? = null,
        maxMembers: Int? = null,
        minClanPoints: Int? = null,
        minClanLevel: Int? = null,
        limit: Int? = null,
        after: String? = null,
        before: String? = null
    ): ClanSearchResponse
}

class ClanRepositoryImpl(
    private val client: HttpClient
) : ClanRepository {

    override suspend fun getClanDetails(tag: String): ClanDetail {
        return client.get("proxy/clans/${tag.encodeURLPath()}").body()
    }

    override suspend fun searchClan(
        name: String,
        warFrequency: WarFrequency?,
        locationId: Int?,
        minMembers: Int?,
        maxMembers: Int?,
        minClanPoints: Int?,
        minClanLevel: Int?,
        limit: Int?,
        after: String?,
        before: String?
    ): ClanSearchResponse {
        return client.get("proxy/clans") {
            parameter("name", name)
            parameter("limit", DefaultSearchClanLimit)
            warFrequency?.let { parameter("warFrequency", it.toString()) }
            locationId?.let { parameter("locationId", it) }
            minMembers?.let { parameter("minMembers", it) }
            maxMembers?.let { parameter("maxMembers", it) }
            minClanPoints?.let { parameter("minClanPoints", it) }
            minClanLevel?.let { parameter("minClanLevel", it) }
            limit?.let { parameter("limit", it) }
            after?.let { parameter("after", it) }
            before?.let { parameter("before", it) }
        }.body()
    }
}