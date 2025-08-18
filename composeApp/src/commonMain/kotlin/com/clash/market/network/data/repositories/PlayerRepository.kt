package com.clash.market.network.data.repositories

import com.clash.market.models.Player
import com.clash.market.models.dtos.VerifyTokenRequest
import com.clash.market.models.dtos.VerifyTokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.encodeURLPath

interface PlayerRepository {
    suspend fun getPlayerDetails(playerTag: String): Player
    suspend fun verifyToken(playerTag: String, token: String): VerifyTokenResponse
}

class PlayerRepositoryImpl(
    private val client: HttpClient
) : PlayerRepository {

    override suspend fun getPlayerDetails(playerTag: String): Player {
        return client.get("v1/players/${playerTag.encodeURLPath()}").body()
    }

    override suspend fun verifyToken(playerTag: String, token: String): VerifyTokenResponse {
        return client.post("proxy/players/${playerTag.encodeURLPath()}/verifytoken") {
            contentType(ContentType.Application.Json)
            setBody(VerifyTokenRequest(token))
        }.body()
    }
}