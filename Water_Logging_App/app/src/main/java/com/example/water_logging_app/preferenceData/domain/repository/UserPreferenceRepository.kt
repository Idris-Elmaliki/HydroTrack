package com.example.water_logging_app.preferenceData.domain.repository

import com.example.water_logging_app.preferenceData.data.local.entity.UserPreferenceEntity

interface UserPreferenceRepository {
    suspend fun insertUserPreference(userPreference: UserPreferenceEntity)

    suspend fun deleteUserPreference()

    suspend fun getUserPreference() : UserPreferenceEntity
}