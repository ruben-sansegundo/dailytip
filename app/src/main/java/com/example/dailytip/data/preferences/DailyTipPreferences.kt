package com.example.dailytip.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "daily_tip_prefs")

class DailyTipPreferences(private val context: Context) {

    companion object {
        val KEY_CACHED_DATE = stringPreferencesKey("cached_date")
        val KEY_CACHED_TIP_ID = longPreferencesKey("cached_tip_id")
    }

    val cachedDate: Flow<String?> = context.dataStore.data.map { it[KEY_CACHED_DATE] }
    val cachedTipId: Flow<Long?> = context.dataStore.data.map { it[KEY_CACHED_TIP_ID] }

    suspend fun saveDailyTip(date: String, tipId: Long) {
        context.dataStore.edit { prefs ->
            prefs[KEY_CACHED_DATE] = date
            prefs[KEY_CACHED_TIP_ID] = tipId
        }
    }
}
