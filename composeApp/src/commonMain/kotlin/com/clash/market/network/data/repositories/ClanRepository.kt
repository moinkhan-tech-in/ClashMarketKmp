package com.clash.market.network.data.repositories

import com.clash.market.models.ClanDetail
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodeURLPath

interface ClanRepository {
    suspend fun getClanDetails(tag: String): ClanDetail
}

class ClanRepositoryImpl(
    private val client: HttpClient
) : ClanRepository {

    override suspend fun getClanDetails(tag: String): ClanDetail {
        return client.get("proxy/clans/${tag.encodeURLPath()}").body()
    }
}