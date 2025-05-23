package com.clash.market

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.Js

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

actual fun provideEngine(): HttpClientEngineFactory<*> = Js