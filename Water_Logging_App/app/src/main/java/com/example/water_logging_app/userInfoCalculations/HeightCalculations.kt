package com.example.water_logging_app.userInfoCalculations

/*
* This is just a simple helper function we use to convert cm/in into meters/feet and pass it as a string
*
* Used in the UserDataPageUi_2.kt + more (maybe)
*/
fun heightCalculations(
    isMetric : Boolean,
    height : Int
) : String {
    if(!isMetric) {
        val feet = height / 12
        val inches = height % 12

        return "$feet' $inches\""
    }

    val meters = height / 100
    val centimeters = height % 100

    return "${meters}m ${centimeters}cm"
}