package com.example.water_logging_app.preferenceData.data.repository

import com.example.water_logging_app.preferenceData.data.local.dao.UserPreferenceDAO
import com.example.water_logging_app.preferenceData.data.local.entity.UserPreferenceEntity
import com.example.water_logging_app.preferenceData.domain.repository.UserPreferenceRepository

class UserPreferenceRepositoryImpl(
    private val dao : UserPreferenceDAO
) : UserPreferenceRepository {
    override suspend fun insertUserPreference(userPreference: UserPreferenceEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUserPreference() {
        TODO("Not yet implemented")
    }

    override suspend fun getUserPreference(): UserPreferenceEntity {
        TODO("Not yet implemented")
    }
}