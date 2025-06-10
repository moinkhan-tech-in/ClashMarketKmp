package com.clash.market.network.data.repositories

import com.clash.market.local.datastore.CacheExpiry.cacheExpiryMap
import com.clash.market.local.datastore.CacheKeys.CLAN_LABELS
import com.clash.market.local.datastore.CacheKeys.LEAGUES
import com.clash.market.local.datastore.CacheKeys.LOCATIONS
import com.clash.market.local.datastore.CacheKeys.PLAYER_LABELS
import com.clash.market.local.datastore.CacheManager
import com.clash.market.models.Label
import com.clash.market.models.League
import com.clash.market.models.Location
import com.clash.market.models.dtos.MetadataResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


interface MetadataRepository {
    suspend fun getLeagues(): MetadataResponse<League>
    suspend fun getLocations(): MetadataResponse<Location>
    suspend fun getPlayerLabels(): MetadataResponse<Label>
    suspend fun getClanLabels(): MetadataResponse<Label>
}

class MetadataRepositoryImpl(
    private val client: HttpClient,
    private val cacheManager: CacheManager
) : MetadataRepository {

    override suspend fun getLeagues(): MetadataResponse<League> {
        return cacheManager.get<MetadataResponse<League>>(LEAGUES, cacheExpiryMap[LEAGUES]!!)
            ?: client.get("proxy/leagues").body<MetadataResponse<League>>()
                .also { cacheManager.save(LEAGUES, it) }
    }

    override suspend fun getLocations(): MetadataResponse<Location> {
        return cacheManager.get<MetadataResponse<Location>>(LOCATIONS, cacheExpiryMap[LOCATIONS]!!)
            ?: client.get("proxy/locations").body<MetadataResponse<Location>>()
                .also { cacheManager.save(LOCATIONS, it) }
    }

    override suspend fun getPlayerLabels(): MetadataResponse<Label> {
        return cacheManager.get<MetadataResponse<Label>>(PLAYER_LABELS, cacheExpiryMap[PLAYER_LABELS]!!)
            ?: client.get("proxy/labels/players").body<MetadataResponse<Label>>()
                .also { cacheManager.save(PLAYER_LABELS, it) }
    }

    override suspend fun getClanLabels(): MetadataResponse<Label> {
        return cacheManager.get<MetadataResponse<Label>>(CLAN_LABELS, cacheExpiryMap[CLAN_LABELS]!!)
            ?: client.get("proxy/labels/clans").body<MetadataResponse<Label>>()
                .also { cacheManager.save(CLAN_LABELS, it) }
    }
}