package com.clash.market.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.clash.market.local.datastore.PreferenceManager
import com.clash.market.local.datastore.createDataStore
import org.koin.dsl.module

val preferenceModule = module {
    single<DataStore<Preferences>> { createDataStore() }
    single { PreferenceManager(get()) }
}