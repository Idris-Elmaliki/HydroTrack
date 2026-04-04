package com.example.water_logging_app.userInfoCalculations

import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.preferenceData.domain.modelData.enums.ActivityLevel
import com.example.water_logging_app.preferenceData.domain.modelData.enums.Genders


// complete the calculations later!


fun dailyGoalCalculation(
    userData : UserPreferenceData
) : Long {
    var dailyGoal : Float = (userData.weight * 30f) // we multiply weight (in kg) by 30

    dailyGoal += when(userData.gender) {
        Genders.Male.name -> 250
        else -> 0
    }

    val age = userData.age.toInt()

    when {
        (age < 18) -> dailyGoal += 150f
        (age < 30) -> dailyGoal += 200f
        (age < 55)-> dailyGoal += 0f
        else -> dailyGoal -= 100f
    }

    when(userData.activityLevel) {
        ActivityLevel.CouchPotato.name -> dailyGoal *= 1f
        ActivityLevel.Walker.name -> dailyGoal *= 1.1f
        ActivityLevel.GymBro.name -> dailyGoal *= 1.2f
        ActivityLevel.Athlete.name -> dailyGoal *= 1.5f
    }

    return dailyGoal.toLong()
}