package com.clash.market

import androidx.compose.runtime.Composable
import io.ktor.client.engine.HttpClientEngineFactory
import kotlinx.coroutines.CoroutineDispatcher

interface Platform {
    val name: String
    val isAndroid: Boolean
}

expect fun getPlatform(): Platform

expect fun provideEngine(): HttpClientEngineFactory<*>

expect val ioDispatcher: CoroutineDispatcher

expect fun openClashLink(url: String)

expect fun copyToClipboard(label: String, text: String)

@Composable
expect fun BackPressHandler(enabled: Boolean = true, onBack: () -> Unit)