package com.clash.market

import io.ktor.client.engine.HttpClientEngineFactory

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun provideEngine(): HttpClientEngineFactory<*>