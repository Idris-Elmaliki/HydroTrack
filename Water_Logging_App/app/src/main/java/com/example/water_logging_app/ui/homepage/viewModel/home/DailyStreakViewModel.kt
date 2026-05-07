package com.example.water_logging_app.ui.homepage.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app._waterLogStreak.data.DailyStreakDataStoreManager
import com.example.water_logging_app._waterLogStreak.domain.DailyStreakData
import com.example.water_logging_app._waterLogs.data.local.repository.WaterLogRepositoryImpl
import com.example.water_logging_app._waterLogs.domain.modelData.WaterLogData
import com.example.water_logging_app._waterLogs.domain.modelData.WaterLogDataList
import com.example.water_logging_app.time.TimeConversion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DailyStreakViewModel @Inject constructor(
    private val dataStore : DailyStreakDataStoreManager,
    private val repo : WaterLogRepositoryImpl
) : ViewModel() {
    private var _dailyStreak = MutableStateFlow(DailyStreakData())
    val dailyStreak = _dailyStreak.asStateFlow()

    init {
        loadDailyStreakData()
    }

    private fun loadDailyStreakData() {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                dataStore.getCurrentDailyStreak(),
                dataStore.getMaxDailyStreak()
            ) { current, max ->
                DailyStreakData(
                    currentDailyStreak = current,
                    maxDailyStreak = max
                )
            }.collect { streakData ->
                _dailyStreak.value = streakData
            }
        }
    }
}