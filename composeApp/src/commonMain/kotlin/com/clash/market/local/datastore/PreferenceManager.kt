package com.clash.market.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PreferenceManager(
    private val preference: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend fun save(key: String, value: String) {
        withContext(dispatcher) {
            preference.edit { it[stringPreferencesKey(key)] = value }
        }
    }

    suspend fun getValue(key: String, default: String = ""): String {
        return withContext(dispatcher) {
            preference.data.map { it[stringPreferencesKey(key)] ?: default }
        }.first()
    }
}