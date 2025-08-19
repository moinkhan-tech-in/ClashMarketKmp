package com.clash.market.network.data.repositories

import com.clash.market.models.ClanDetail
import com.clash.market.models.Player
import com.clash.market.models.dtos.ClashResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface RankingRepository {
    suspend fun getTopPlayers(locationId: String): ClashResponse<Player>
    suspend fun getTopClans(locationId: String): ClashResponse<ClanDetail>
    suspend fun getTopBuilderBasePlayers(locationId: String): ClashResponse<Player>
    suspend fun getTopBuilderBaseClans(locationId: String): ClashResponse<ClanDetail>
}

class RankingRepositoryImpl(
    private val client: HttpClient
) : RankingRepository {

    override suspend fun getTopPlayers(locationId: String): ClashResponse<Player> {
        return client.get("/v1/locations/$locationId/rankings/players").body()
    }

    override suspend fun getTopClans(locationId: String): ClashResponse<ClanDetail> {
        return client.get("/v1/locations/$locationId/rankings/clans").body()
    }

    override suspend fun getTopBuilderBaseClans(locationId: String): ClashResponse<ClanDetail> {
        return client.get("/v1/locations/$locationId/rankings/clans-builder-base").body()
    }

    override suspend fun getTopBuilderBasePlayers(locationId: String): ClashResponse<Player> {
        return client.get("/v1/locations/$locationId/rankings/players-builder-base").body()
    }
}