package com.clash.market.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

expect fun createDataStore(): DataStore<Preferences>

internal const val dataStoreFileName = "clash.preferences_pb"

private lateinit var dataStore: DataStore<Preferences>

fun getDataStore(producePath: () -> String): DataStore<Preferences> =
    if (::dataStore.isInitialized) {
        dataStore
    } else {
        PreferenceDataStoreFactory.createWithPath(produceFile = { producePath().toPath() })
            .also { dataStore = it }
    }
