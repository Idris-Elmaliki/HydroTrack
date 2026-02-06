package com.example.water_logging_app.preferenceData.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User_Preference_Table")
data class UserPreferenceEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long? = null,

    val name : String,
    val dailyGoal : Long,
    val preferredMeasurement : String
)