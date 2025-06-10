package com.clash.market.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.clash.market.local.datastore.CacheManager
import com.clash.market.local.datastore.PreferenceManager
import com.clash.market.local.datastore.createDataStore
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val preferenceModule = module {
    single<DataStore<Preferences>> { createDataStore() }
    single { PreferenceManager(get()) }
    single {
        CacheManager(get(), Json {
            ignoreUnknownKeys = true
        })
    }
}