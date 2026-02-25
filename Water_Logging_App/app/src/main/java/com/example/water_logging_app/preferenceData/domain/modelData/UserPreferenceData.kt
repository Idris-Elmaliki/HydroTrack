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
    val age : Int = 0,
    val gender : String = Genders.MALE.name,
    val height : Float = 0.0f,
    val weight : Float = 0.0f,
    val dailyGoal : Long = 0L,
    val isMetric : Boolean = true,
)