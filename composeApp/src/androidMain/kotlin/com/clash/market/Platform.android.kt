package com.clash.market

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.core.net.toUri
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun provideEngine(): HttpClientEngineFactory<*> = OkHttp

actual val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

actual fun openClashPlayer(tag: String) {
    val context = ContextUtils.dataStoreApplicationContext
    context?.let {
        val uri = (OpenPlayerLink + Uri.encode(tag.removePrefix("#"))).toUri()
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Clash of Clans not installed", Toast.LENGTH_SHORT).show()
        }
    }
}

actual fun openClashClan(tag: String) {
    val context = ContextUtils.dataStoreApplicationContext
    context?.let {
        val uri = (OpenClanLink + tag).toUri()
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Clash of Clans not installed", Toast.LENGTH_SHORT).show()
        }
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