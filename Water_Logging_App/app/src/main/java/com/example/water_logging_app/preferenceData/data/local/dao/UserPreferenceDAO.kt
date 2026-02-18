package com.example.water_logging_app.preferenceData.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.water_logging_app.preferenceData.data.local.entity.UserPreferenceEntity

@Dao
interface UserPreferenceDAO {
    @Upsert
    suspend fun insertUserPreference(userPreference : UserPreferenceEntity)

    @Delete
    suspend fun deleteUserPreference(userPreference : UserPreferenceEntity)

    // I changed this function to pass UserPreferenceData? in order to have O(1) null check instead of O(n)
    @Query("SELECT * FROM user_preference_table")
    suspend fun getUserPreference() : UserPreferenceEntity?
}