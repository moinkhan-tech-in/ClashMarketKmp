package com.clash.market.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

sealed class ClashPreferenceKeys<T>(val key: Preferences.Key<T>) {
    object ProfilePlayer: ClashPreferenceKeys<String>(stringPreferencesKey("clash.profile.player"))
    object ProfileClan: ClashPreferenceKeys<String>(stringPreferencesKey("clash.profile.clan"))
    object IsInClan: ClashPreferenceKeys<Boolean>(booleanPreferencesKey("clash.profile.isInClan"))
    object IsProfileEnterSkipped: ClashPreferenceKeys<Boolean>(booleanPreferencesKey("clash.profile.isProfileEnterSkipped"))
}

class PreferenceManager(
    private val preference: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend fun <T> save(prefKey: ClashPreferenceKeys<T>, value: T) {
        withContext(dispatcher) {
            preference.edit { it[prefKey.key] = value }
        }
    }

    suspend fun <T> getValue(prefKey: ClashPreferenceKeys<T>, default: T? = null): T? {
        return withContext(dispatcher) {
            preference.data.map { it[prefKey.key] ?: default }
        }.first()
    }

    suspend fun <R> getValueAsFlow(prefKey: ClashPreferenceKeys<R>, default: R? = null): Flow<R?> {
        return withContext(dispatcher) {
            preference.data.map { it[prefKey.key] ?: default }
        }
    }
}