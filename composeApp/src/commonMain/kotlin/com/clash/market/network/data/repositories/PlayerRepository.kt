package com.clash.market.network.data.repositories

import com.clash.market.models.Player
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodeURLPath

interface PlayerRepository {
    suspend fun getPlayerDetails(playerTag: String): Player
}

class PlayerRepositoryImpl(
    private val client: HttpClient
) : PlayerRepository {

    override suspend fun getPlayerDetails(playerTag: String): Player {
        return client.get("proxy/players/${playerTag.encodeURLPath()}").body()
    }
}