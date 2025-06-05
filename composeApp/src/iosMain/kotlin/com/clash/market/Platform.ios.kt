package com.clash.market

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
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun provideEngine(): HttpClientEngineFactory<*> = Darwin

actual val ioDispatcher: CoroutineDispatcher = Dispatchers.Default


actual fun openClashPlayer(tag: String) {
    val url = NSURL.URLWithString(OpenPlayerLink + tag.removePrefix("#"))!!
    UIApplication.sharedApplication.openURL(url)
}

actual fun openClashClan(tag: String) {
    val url = NSURL.URLWithString(OpenClanLink + tag.removePrefix("#"))!!
    UIApplication.sharedApplication.openURL(url)
}

actual fun copyToClipboard(label: String, text: String) {
    UIPasteboard.generalPasteboard.string = text
}