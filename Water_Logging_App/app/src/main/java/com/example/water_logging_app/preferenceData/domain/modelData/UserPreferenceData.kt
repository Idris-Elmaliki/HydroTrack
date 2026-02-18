package com.example.water_logging_app.preferenceData.domain.modelData

/*
* All the data is null since I want to include an option where the user doesn't need to give the app any data!
*
* Plus this ensures that we don't need to include any default arguments within the viewModels :)
*/
data class UserPreferenceData(
    val error : String? = null,
    val isLoading : Boolean = true,

    val name : String? = null,
    val age : Int? = null,
    val gender : String? = null,
    val height : Float? = null,
    val weight : Float? = null,
    val dailyGoal : Long? = null,
    val isMetric : Boolean? = null,
)