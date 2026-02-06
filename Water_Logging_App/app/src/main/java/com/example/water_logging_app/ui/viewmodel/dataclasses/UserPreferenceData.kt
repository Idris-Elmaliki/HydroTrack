package com.example.water_logging_app.ui.viewmodel.dataclasses

data class UserPreferenceData(
    val error : String? = null,
    val name : String? = null,
    val dailyGoal : Long? = null,
    val preferredMeasurement : String? = null
)
