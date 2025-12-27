package com.example.water_logging_app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.water_logging_app.data.local.entity.UserPreferenceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPreferenceDAO {
    @Upsert
    suspend fun insertUserPreference(userPreference : UserPreferenceEntity)

    @Delete
    suspend fun deleteUserPreference(userPreference : UserPreferenceEntity)

    @Query("SELECT preferredMeasurement FROM user_preference_table LIMIT 1")
    suspend fun getPreferredMeasurement() : String

    @Query("SELECT dailyGoal FROM user_preference_table WHERE dailyGoal")
    suspend fun getUsersDailyGoal() : Long
}