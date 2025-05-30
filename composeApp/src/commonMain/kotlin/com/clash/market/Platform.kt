package com.clash.market

import io.ktor.client.engine.HttpClientEngineFactory
import kotlinx.coroutines.CoroutineDispatcher

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun provideEngine(): HttpClientEngineFactory<*>

expect val ioDispatcher: CoroutineDispatcher

expect fun openClashPlayer(tag: String)

expect fun openClashClan(tag: String)