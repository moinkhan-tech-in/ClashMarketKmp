package com.clash.market.network.data.repositories

import com.clash.market.models.ClanDetail
import com.clash.market.models.Label
import com.clash.market.models.WarFrequency
import com.clash.market.models.dtos.ClanSearchResponse
import com.clash.market.models.dtos.ClashResponse
import com.clash.market.models.dtos.CurrentWarResponse
import com.clash.market.models.dtos.WarLeagueGroupResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.encodeURLPath

private const val DefaultSearchClanLimit = 5

interface ClanRepository {

    suspend fun getClanDetails(tag: String): ClanDetail

    suspend fun getCurrentWar(tag: String): CurrentWarResponse

    suspend fun getWarLeagueGroup(tag: String): WarLeagueGroupResponse

    suspend fun getWarLeagueWar(warTag: String): CurrentWarResponse

    suspend fun getClanWarLogs(tag: String, limit: Int = 20): ClashResponse<CurrentWarResponse>

    suspend fun searchClan(
        name: String,
        warFrequency: WarFrequency? = null,
        locationId: Int? = null,
        minMembers: Int? = null,
        maxMembers: Int? = null,
        minClanPoints: Int? = null,
        minClanLevel: Int? = null,
        limit: Int? = null,
        labels: List<Label>? = null,
        after: String? = null,
        before: String? = null
    ): ClanSearchResponse
}

class ClanRepositoryImpl(
    private val client: HttpClient
) : ClanRepository {

    override suspend fun getClanDetails(tag: String): ClanDetail {
        return client.get("/v1/clans/${tag.encodeURLPath()}").body()
    }

    override suspend fun getCurrentWar(tag: String): CurrentWarResponse {
        return client.get("/v1/clans/${tag.encodeURLPath()}/currentwar").body()
    }

    override suspend fun getWarLeagueGroup(tag: String): WarLeagueGroupResponse {
        return client.get("/v1/clans/${tag.encodeURLPath()}/currentwar/leaguegroup").body()
    }

    override suspend fun getWarLeagueWar(warTag: String): CurrentWarResponse {
        return client.get("/v1/clanwarleagues/wars/${warTag.encodeURLPath()}").body()
    }

    override suspend fun getClanWarLogs(tag: String, limit: Int): ClashResponse<CurrentWarResponse> {
        return client.get("/v1/clans/${tag.encodeURLPath()}/warlog").body()
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
        labels: List<Label>?,
        after: String?,
        before: String?
    ): ClanSearchResponse {
        return client.get("proxy/clans") {
            parameter("name", name)
            parameter("limit", DefaultSearchClanLimit)
            warFrequency?.let { parameter("warFrequency", it.serializeValue()) }
            locationId?.let { parameter("locationId", it) }
            minMembers?.let { parameter("minMembers", it) }
            maxMembers?.let { parameter("maxMembers", it) }
            minClanPoints?.let { parameter("minClanPoints", it) }
            minClanLevel?.let { parameter("minClanLevel", it) }
            labels.takeIf { labels ->
                labels?.isNotEmpty() == true
            }?.let { labels ->
                parameter("labelIds", labels.map { it.id }.joinToString(","))
            }
            limit?.let { parameter("limit", it) }
            after?.let { parameter("after", it) }
            before?.let { parameter("before", it) }
        }.body()
    }
}