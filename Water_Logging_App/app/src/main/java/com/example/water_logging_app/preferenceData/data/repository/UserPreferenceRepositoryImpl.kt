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

    /*
    * I changed this function to pass UserPreferenceData? in order to have O(1) null check instead of O(n)
    * What I mean is that since I am using this function in both before and after sign Up, I have to check whether the table is empty
    *
    * So instead of looping through each variable in UserPreferenceData, I can use the exception thrown when I call an empty table row!
    */
    override suspend fun getUserPreference(): UserPreferenceData? {
        return dao.getUserPreference()?.toUserPreferenceData()
    }
}

private fun UserPreferenceEntity.toUserPreferenceData() : UserPreferenceData {
    return UserPreferenceData(
        name = name,
        age = age,
        gender = gender,
        height = height,
        weight = weight,
        dailyGoal = dailyGoal,
        isMetric = isMetric
    )
}

private fun UserPreferenceData.toUserPreferenceEntity() : UserPreferenceEntity {
    return UserPreferenceEntity(
        name = name?: "User",
        age = age?: -1,
        gender = gender?: "Not specified",
        height = height?: -1f,
        weight = weight?: -1f,
        dailyGoal = dailyGoal?: -1,
        isMetric = isMetric?: true
    )
}
