package com.example.water_logging_app.preferenceData.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User_Preference_Table")
data class UserPreferenceEntity(
    @PrimaryKey
    val id : Int = 1,

    val firstName : String,
    val lastName : String,
    val userName : String ,
    val age : Int,
    val gender : String,
    val height : Float,
    val weight : Float,
    val activityLevel : String,
    val dailyGoal : Long,
    val unitOfMeasurement : String,
)