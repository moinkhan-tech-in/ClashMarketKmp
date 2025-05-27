package com.clash.market.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.clash.market.ContextUtils
import kotlinx.coroutines.runBlocking

actual fun createDataStore(): DataStore<Preferences> {
    return runBlocking {
        getDataStore(producePath = {
            ContextUtils.dataStoreApplicationContext!!.filesDir.resolve(
                dataStoreFileName
            ).absolutePath
        })
    }
}