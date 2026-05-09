package com.example.water_logging_app._waterLogStreak.domain

import java.time.LocalDate

data class DailyStreakData(
    val isLoading : Boolean = true,
    val error : String? = null,

    val currentDailyStreak : Int = 0,
    val maxDailyStreak : Int = 0,
    val lastUpdatedDay : LocalDate? = null
)
