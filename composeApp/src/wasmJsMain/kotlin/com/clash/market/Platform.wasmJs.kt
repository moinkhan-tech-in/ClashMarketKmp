package com.clash.market

import androidx.compose.runtime.Composable
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.Js
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"

    override val isAndroid: Boolean = false
}

actual fun getPlatform(): Platform = WasmPlatform()

actual fun provideEngine(): HttpClientEngineFactory<*> = Js

actual val ioDispatcher: CoroutineDispatcher = Dispatchers.Default

@Composable
actual fun BackPressHandler(enabled: Boolean, onBack: () -> Unit) {
    // No-op or implement custom logic if needed
}