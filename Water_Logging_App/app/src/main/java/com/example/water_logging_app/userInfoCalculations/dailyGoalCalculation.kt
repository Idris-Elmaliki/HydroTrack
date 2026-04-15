package com.example.water_logging_app.userInfoCalculations

import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.preferenceData.domain.modelData.enums.ActivityLevel
import com.example.water_logging_app.preferenceData.domain.modelData.enums.Genders
import com.example.water_logging_app.preferenceData.domain.modelData.enums.UnitMeasurementType

fun dailyGoalCalculation(
    userData : UserPreferenceData
) : Long {
    var dailyGoal : Float = (userData.weight)

    dailyGoal *= 30f

    when(userData.activityLevel) {
        ActivityLevel.CouchPotato.name -> dailyGoal *= 1f
        ActivityLevel.Walker.name -> dailyGoal *= 1.01f
        ActivityLevel.GymBro.name -> dailyGoal *= 1.05f
        ActivityLevel.Athlete.name -> dailyGoal *= 1.1f
    }

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

    if(userData.unitOfMeasurement == UnitMeasurementType.Imperial.name) {
        dailyGoal /= 29.5735f
        return ((dailyGoal.toLong()) / 10L) * 10L
    }

    return ((dailyGoal.toLong()) / 100L) * 100L
}