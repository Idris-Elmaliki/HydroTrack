package com.example.water_logging_app.preferenceData.data.repository

import android.content.Context
import androidx.room.Room
import com.example.water_logging_app.preferenceData.data.local.database.UserPreferenceDatabase
import com.example.water_logging_app.preferenceData.data.local.entity.UserPreferenceEntity

class PreferenceRepository(
    private val repoContext : Context
) {
    private val preferenceDatabase : UserPreferenceDatabase by lazy {
        Room.databaseBuilder(
            context = repoContext.applicationContext,
            UserPreferenceDatabase::class.java,
            name = "user_preference_database"
        ).build()
    }

    private val preferenceDao = preferenceDatabase.userPreferenceDao()

    suspend fun getName() : String {
        return preferenceDao.getUserName()
    }

    suspend fun getDailyGoal() : Long {
        return preferenceDao.getUsersDailyGoal()
    }

    suspend fun getPreferredMeasurement() : String {
        return preferenceDao.getPreferredMeasurement()
    }

    suspend fun insertUsersData(
        usersName : String,
        usersDailyGoal : Long,
        usersPreferredMeasurement : String
    ) {
        preferenceDao.insertUserPreference(
            userPreference = UserPreferenceEntity(
                name = usersName,
                dailyGoal = usersDailyGoal,
                preferredMeasurement = usersPreferredMeasurement
            )
        )
    }
}