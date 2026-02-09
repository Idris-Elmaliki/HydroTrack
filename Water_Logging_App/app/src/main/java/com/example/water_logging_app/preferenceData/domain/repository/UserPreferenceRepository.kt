package com.example.water_logging_app.preferenceData.domain.repository

import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData

interface UserPreferenceRepository {
    suspend fun insertUserPreference(userPreference: UserPreferenceData)

    suspend fun deleteUserPreference(userPreference: UserPreferenceData)

    suspend fun getUserPreference() : UserPreferenceData
}