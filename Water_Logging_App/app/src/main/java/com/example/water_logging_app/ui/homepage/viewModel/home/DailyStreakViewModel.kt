package com.example.water_logging_app.ui.homepage.viewModel.home

import android.util.Log
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

    fun updateDailyStreakData() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentData = dailyStreak.value

            val newStreak = currentData.currentDailyStreak + 1

            _dailyStreak.update { data ->
                data.copy(
                    currentDailyStreak = newStreak,
                    maxDailyStreak =
                        if(newStreak > currentData.maxDailyStreak) { newStreak }
                        else { currentData.maxDailyStreak }
                )
            }
        }
    }

    fun uploadDailyStreakData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _dailyStreak.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                val newData = dailyStreak.value

                dataStore.setCurrentDailyStreak(newData.currentDailyStreak)

                if(newData.currentDailyStreak == newData.maxDailyStreak) {
                    dataStore.setMaxDailyStreak(newData.maxDailyStreak)
                }

                _dailyStreak.update { data ->
                    data.copy(
                        isLoading = false
                    )
                }
            }
            catch (e : Exception) {
                _dailyStreak.update { data ->
                    Log.e("StreakVM", e.message, e)
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }
}