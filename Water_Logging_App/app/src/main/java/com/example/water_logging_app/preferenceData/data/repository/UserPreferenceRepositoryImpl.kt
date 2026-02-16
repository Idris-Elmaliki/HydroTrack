package com.example.water_logging_app.preferenceData.data.repository

import com.example.water_logging_app.preferenceData.data.local.dao.UserPreferenceDAO
import com.example.water_logging_app.preferenceData.data.local.entity.UserPreferenceEntity
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.preferenceData.domain.repository.UserPreferenceRepository
import javax.inject.Inject

class UserPreferenceRepositoryImpl @Inject constructor(
    private val dao : UserPreferenceDAO
) : UserPreferenceRepository {
    override suspend fun insertUserPreference(userPreference: UserPreferenceData) {
        dao.insertUserPreference(userPreference.toUserPreferenceEntity())
    }

    override suspend fun deleteUserPreference(userPreference: UserPreferenceData) {
        dao.deleteUserPreference(userPreference.toUserPreferenceEntity())
    }

    override suspend fun getUserPreference(): UserPreferenceData {
        return dao.getUserPreference().toUserPreferenceData()
    }
}

private fun UserPreferenceEntity.toUserPreferenceData() : UserPreferenceData {
    return UserPreferenceData(
        name = name,
        dailyGoal = dailyGoal,
        preferredMeasurement = preferredMeasurement,
    )
}

private fun UserPreferenceData.toUserPreferenceEntity() : UserPreferenceEntity {
    return UserPreferenceEntity(
        name = name!!,
        dailyGoal = dailyGoal!!,
        preferredMeasurement = preferredMeasurement!!,
    )
}