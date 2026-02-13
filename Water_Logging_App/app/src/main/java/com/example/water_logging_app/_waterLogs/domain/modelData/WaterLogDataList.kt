package com.example.water_logging_app._waterLogs.domain.modelData

import java.time.LocalDate

data class WaterLogDataList(
    val isLoading : Boolean = true,
    val error : String? = null,

    val waterInfoList : Map<LocalDate, MutableList<WaterLogData>> = mapOf()
)