package com.clash.market

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"

    override val isAndroid: Boolean = true
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun provideEngine(): HttpClientEngineFactory<*> = OkHttp

actual val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

actual fun openClashLink(url: String) {
    ContextUtils.dataStoreApplicationContext?.let {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        it.startActivity(intent)
    }
}

actual fun copyToClipboard(label: String, text: String) {
    ContextUtils.dataStoreApplicationContext?.let {
        val clipboard = it.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(it, "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}

@Composable
actual fun BackPressHandler(enabled: Boolean, onBack: () -> Unit) {
    BackHandler(enabled = enabled, onBack = onBack)
}