package com.example.water_logging_app._waterLogStreak.domain

data class DailyStreakData(
    val isLoading : Boolean = true,
    val error : String? = null,

    val currentDailyStreak : Int = 0,
    val maxDailyStreak : Int = 0
)
