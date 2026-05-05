package com.example.water_logging_app._waterLogStreak.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyStreakDataStoreManager @Inject constructor(
    @param:ApplicationContext private val context : Context
) {
    private val Context.dataStore by preferencesDataStore("dailyStreak")

    fun getCurrentDailyStreak() : Flow<Int> {
        return context.dataStore.data.map { data ->
            data[currentDailyStreak] ?: 0
        }
    }

    suspend fun setCurrentDailyStreak(newCurrentStreak : Int)  {
        context.dataStore.edit { data ->
            data[currentDailyStreak] = newCurrentStreak
        }
    }

    fun getMaxDailyStreak() : Flow<Int> {
        return context.dataStore.data.map { data ->
            data[maxDailyStreak] ?: 0
        }
    }

    suspend fun setMaxDailyStreak(newMaxStreak : Int) {
        context.dataStore.edit { data ->
            data[currentDailyStreak] = newMaxStreak
        }
    }

    companion object {
        val currentDailyStreak = intPreferencesKey("current_daily_streak")

        val maxDailyStreak = intPreferencesKey("max_daily_streak")
    }
}