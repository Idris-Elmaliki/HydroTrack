package com.example.water_logging_app.userInfoCalculations

import com.example.water_logging_app.preferenceData.domain.modelData.enums.UnitMeasurementType

/*
* These two functions are helper functions
*
* They convert any imperial height/weight back to metric
*
* This ensures that all the data stays consistent (metric!) under the hood
*/

fun heightMetricConversion(
    unitSystem : String,
    height : Float
) : Float {
    if(unitSystem == UnitMeasurementType.Imperial.name) {
        return height * 2.54f
    }

    return height
}

fun weightMetricConversion(
    unitSystem : String,
    weight : Float
) : Float {
    if(unitSystem == UnitMeasurementType.Imperial.name) {
        return weight * 2.2f
    }

    return weight
}
