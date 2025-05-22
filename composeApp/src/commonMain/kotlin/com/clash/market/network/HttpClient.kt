package com.clash.market.network

import com.clash.market.provideEngine
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


val httpClient = HttpClient(provideEngine()) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }

    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.BODY
    }

    defaultRequest {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }

    install(DefaultRequest) {
        url {
            protocol = URLProtocol.HTTP
            host = "18.217.229.6:8080"
            path("proxy")
        }
    }
}
