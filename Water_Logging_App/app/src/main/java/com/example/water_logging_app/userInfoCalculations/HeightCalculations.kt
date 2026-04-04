package com.example.water_logging_app.userInfoCalculations

import com.example.water_logging_app.preferenceData.domain.modelData.enums.UnitMeasurementType

/*
* This is just a simple helper function we use to convert cm/in into meters/feet and pass it as a string
*
* Used in the ui for UserDataPage
*/
fun heightCalculations(
    unitSystem : String,
    height : Int
) : String {
    if(unitSystem == UnitMeasurementType.Imperial.name) {
        val feet = height / 12
        val inches = height % 12

        return "$feet' $inches\""
    }

    val meters = height / 100
    val centimeters = height % 100

    return "${meters}m ${centimeters}cm"
}