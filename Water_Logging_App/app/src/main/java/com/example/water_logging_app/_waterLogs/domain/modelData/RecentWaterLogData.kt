package com.example.water_logging_app._waterLogs.domain.modelData

data class RecentWaterLogData(
    val isLoading : Boolean = true,
    val error : String? = null,

    val recentWaterLogs : ArrayDeque<Int> = ArrayDeque()
)
