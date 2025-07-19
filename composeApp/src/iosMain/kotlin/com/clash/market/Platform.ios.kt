package com.clash.market

import androidx.compose.runtime.Composable
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice
import platform.UIKit.UIPasteboard

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    override val isAndroid: Boolean = false
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun provideEngine(): HttpClientEngineFactory<*> = Darwin

actual val ioDispatcher: CoroutineDispatcher = Dispatchers.Default

actual fun openClashLink(url: String) {
    NSURL.URLWithString(url)?.let {
        UIApplication.sharedApplication.openURL(it)
    }
}

actual fun copyToClipboard(label: String, text: String) {
    UIPasteboard.generalPasteboard.string = text
}

@Composable
actual fun BackPressHandler(enabled: Boolean, onBack: () -> Unit) {
    // No-op or implement custom logic if needed
}