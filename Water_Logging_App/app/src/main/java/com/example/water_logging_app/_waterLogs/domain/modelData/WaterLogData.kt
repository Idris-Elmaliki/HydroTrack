package com.example.water_logging_app._waterLogs.domain.modelData

import java.time.LocalDateTime

data class WaterLogData(
    val id: Long? = null, // I need to include id to stop duplication
    val amountOfWater : Int,
    val timeOfInput : LocalDateTime,
)