package com.example.water_logging_app._waterLogs.domain.modelData

data class WaterLogData(
    // will probably change the data in the future
    val amountOfWater : Int,
    val measurementType : String,
    val timeOfInput : String,
)