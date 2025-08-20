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
    object IsOnboardingShown: ClashPreferenceKeys<Boolean>(booleanPreferencesKey("clash.profile.isOnboardingShown"))
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

    suspend fun getProfileStateAsFlow(): Flow<ProfileState> {
        return getValueAsFlow(ClashPreferenceKeys.ProfilePlayer)
            .map {
                if (it.isNullOrEmpty()) {
                    ProfileState.NotLinked
                } else {
                    val clanTag = getValue(ClashPreferenceKeys.ProfileClan)
                    if (clanTag.isNullOrEmpty().not()) {
                        return@map ProfileState.Linked(it, ClanState.InClan(clanTag))
                    } else {
                        return@map ProfileState.Linked(it, ClanState.NotInClan)
                    }
                }
            }
    }
}

sealed class ProfileState {
    data object NotLinked: ProfileState()
    data class Linked(val tag: String, val clanState: ClanState): ProfileState()
}

sealed class ClanState {
    data class InClan(val tag: String): ClanState()
    data object NotInClan: ClanState()
}