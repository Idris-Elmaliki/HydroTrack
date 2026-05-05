package com.example.water_logging_app._waterLogs.domain.modelData

data class TodayWaterDataList(
    val isLoading : Boolean = true,
    val error : String? = null,

    val waterInfoList : List<WaterLogData> = mutableListOf()
)
