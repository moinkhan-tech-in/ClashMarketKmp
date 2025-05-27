package com.clash.market.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

sealed class ClashPreferenceKeys(val key: String) {
    object ProfilePlayer: ClashPreferenceKeys("clash.profile.player")
}

class PreferenceManager(
    private val preference: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend fun save(prefKey: ClashPreferenceKeys, value: String) {
        withContext(dispatcher) {
            preference.edit { it[stringPreferencesKey(prefKey.key)] = value }
        }
    }

    suspend fun getValue(prefKey: ClashPreferenceKeys, default: String = ""): String {
        return withContext(dispatcher) {
            preference.data.map { it[stringPreferencesKey(prefKey.key)] ?: default }
        }.first()
    }

    suspend fun getValueAsFlow(prefKey: ClashPreferenceKeys, default: String = ""): Flow<String> {
        return withContext(dispatcher) {
            preference.data.map { it[stringPreferencesKey(prefKey.key)] ?: default }
        }
    }
}