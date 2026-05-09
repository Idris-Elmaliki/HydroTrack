package com.example.water_logging_app.ui.homepage.viewModel.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app._waterLogStreak.data.DailyStreakDataStoreManager
import com.example.water_logging_app._waterLogStreak.domain.DailyStreakData
import com.example.water_logging_app._waterLogs.domain.modelData.TodayWaterDataList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
                dataStore.getMaxDailyStreak(),
                dataStore.getLastStreakUpdateDate()
            ) { current, max, date ->
                DailyStreakData(
                    currentDailyStreak = current,
                    maxDailyStreak = max,
                    lastUpdatedDay = date
                )
            }.collect { streakData ->
                _dailyStreak.value = streakData
            }
        }
    }

    private fun streakAlreadyUpdatedToday(): Boolean {
        val lastUpdatedDate = _dailyStreak.value.lastUpdatedDay
        return lastUpdatedDate == LocalDate.now()
    }

    fun updateDailyStreakData(
        todayWaterLog : TodayWaterDataList
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if(todayWaterLog.waterInfoList.isNotEmpty() && !streakAlreadyUpdatedToday()) {
                val currentData = dailyStreak.value

                val newStreak = currentData.currentDailyStreak + 1

                _dailyStreak.update { data ->
                    data.copy(
                        currentDailyStreak = newStreak,
                        maxDailyStreak =
                            if (newStreak > currentData.maxDailyStreak) {
                                newStreak
                            } else {
                                currentData.maxDailyStreak
                            },
                        lastUpdatedDay = LocalDate.now()
                    )
                }
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

                newData.lastUpdatedDay?.let { newDate ->
                    dataStore.setLastStreakUpdateDate(newDate)
                }

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
                        error = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }
}