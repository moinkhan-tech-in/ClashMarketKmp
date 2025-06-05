package com.clash.market.network.data.repositories

import com.clash.market.models.FakePlayer
import com.clash.market.models.Player
import io.ktor.client.HttpClient

interface PlayerRepository {
    suspend fun getPlayerDetails(playerTag: String): Player
}

class PlayerRepositoryImpl(
    private val client: HttpClient
) : PlayerRepository {

    override suspend fun getPlayerDetails(playerTag: String): Player {
        return FakePlayer
//        return client.get("proxy/players/${playerTag.encodeURLPath()}").body()
    }
}