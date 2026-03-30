package com.example.water_logging_app.preferenceData.domain.modelData

/*
* All the data is null since I want to include an option where the user doesn't need to give the app any data!
*
* Plus this ensures that we don't need to include any default arguments within the viewModels :)
*/
data class UserPreferenceData(
    val error : String? = null,
    val isLoading : Boolean = true,

    val firstName : String = "",
    val lastName : String = "",
    val userName : String = "",
    val age : String = "",
    val gender : String? = null,
    val height : Float = 0.0f,
    val weight : Float = 0.0f,
    val unitOfMeasurement : String? = null,
    val activityLevel : String? = null,
    val dailyGoal : Long = 0L,
)