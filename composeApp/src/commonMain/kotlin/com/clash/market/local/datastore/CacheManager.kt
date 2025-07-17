package com.clash.market.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
data class CachedWrapper<T>(
    val data: T,
    val timestamp: Long
)

object CacheKeys {
    const val LEAGUES = "leagues"
    const val LOCATIONS = "locations"
    const val PLAYER_LABELS = "playerLabels"
    const val CLAN_LABELS = "clanLabels"
    const val PLAYER_PROFILE = "playerProfile"
}

object CacheExpiry {
    const val ONE_DAY = 86_400_000L
    const val SEVEN_DAYS = 604_800_000L
    const val FIFTEEN_DAYS = 1_296_000_000L
    const val THIRTY_DAYS = 2_592_000_000L

    val cacheExpiryMap = mapOf(
        CacheKeys.LEAGUES to SEVEN_DAYS,
        CacheKeys.LOCATIONS to THIRTY_DAYS,
        CacheKeys.PLAYER_LABELS to FIFTEEN_DAYS,
        CacheKeys.CLAN_LABELS to FIFTEEN_DAYS,
        CacheKeys.PLAYER_PROFILE to 60_000L * 5
    )
}

class CacheManager(val dataStore: DataStore<Preferences>, val json: Json) {

    suspend inline fun <reified T> save(key: String, data: T) {
        val wrapper = CachedWrapper(data, Clock.System.now().toEpochMilliseconds())
        val serialized = json.encodeToString(cachedWrapperSerializer<T>(), wrapper)
        dataStore.edit { it[stringPreferencesKey(key)] = serialized }
    }

    suspend inline fun <reified T> get(
        key: String,
        maxAgeMillis: Long
    ): T? {
        val prefs = dataStore.data.first()
        val raw = prefs[stringPreferencesKey(key)] ?: return null

        val wrapper = json.decodeFromString(cachedWrapperSerializer<T>(), raw)
        val now = Clock.System.now().toEpochMilliseconds()

        return (if (now - wrapper.timestamp <= maxAgeMillis) wrapper.data else null) as T?
    }

    suspend fun clear(key: String) {
        dataStore.edit { it.remove(stringPreferencesKey(key)) }
    }
}

inline fun <reified T> cachedWrapperSerializer(): KSerializer<CachedWrapper<T>> {
    val dataSerializer = serializer<T>()
    return CachedWrapper.serializer(dataSerializer)
}