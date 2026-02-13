package com.example.water_logging_app._waterLogs.domain.modelData

import java.time.LocalDateTime

data class WaterLogData(
    // will probably change the data in the future (as in add more)
    val amountOfWater : Int,
    val measurementType : String,
    val timeOfInput : LocalDateTime,
)