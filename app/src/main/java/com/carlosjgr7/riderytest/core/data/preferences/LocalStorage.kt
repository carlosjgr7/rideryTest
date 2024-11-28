package com.carlosjgr7.riderytest.core.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStorage @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ReadWrite<Any> {

    override suspend fun save(key: String, value: Any?) {
        dataStore.edit { preferences ->
            when (value) {
                is String -> preferences[stringPreferencesKey(key)] = value
                is Int -> preferences[intPreferencesKey(key)] = value
                is Boolean -> preferences[booleanPreferencesKey(key)] = value
                null -> preferences.remove(stringPreferencesKey(key))
                else -> throw IllegalArgumentException("Unsupported type")
            }
        }
    }

    override suspend fun read(key: String): Flow<Any?> {
        return dataStore.data.map { preferences ->
            when {
                preferences.contains(stringPreferencesKey(key))
                    -> preferences[stringPreferencesKey(key)]

                preferences.contains(intPreferencesKey(key))
                    -> preferences[intPreferencesKey(key)]

                preferences.contains(booleanPreferencesKey(key))
                    -> preferences[booleanPreferencesKey(key)]

                else -> null
            }
        }
    }
}