package com.example.water_logging_app.ui.homepage.viewModel.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app._waterLogStreak.data.DailyStreakDataStoreManager
import com.example.water_logging_app._waterLogStreak.domain.DailyStreakData
import com.example.water_logging_app._waterLogs.data.local.repository.WaterLogRepositoryImpl
import com.example.water_logging_app._waterLogs.domain.modelData.WaterLogData
import com.example.water_logging_app.time.TimeConversion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
                dataStore.getMaxDailyStreak(),
                dataStore.getLastStreakUpdateDate()
            ) { current, max, date ->
                var currentStreak = current

                if (date != null) {
                    val today = LocalDate.now()
                    if (today.isAfter(date.plusDays(1))) {
                        currentStreak = 0
                    }
                }

                DailyStreakData(
                    currentDailyStreak = currentStreak,
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

    fun updateDailyStreakData() {
        viewModelScope.launch(Dispatchers.IO) {
            var todayWaterLog : List<WaterLogData> = listOf()

            repo.getWaterDataByDate(LocalDate.now().toString()).collect {
                todayWaterLog = it
            }

            val currentData = dailyStreak.value

            Log.d("Profile", "todayWaterLog.waterInfoList.isEmpty() : ${todayWaterLog.isEmpty()}")

            if(todayWaterLog.isNotEmpty() && !streakAlreadyUpdatedToday()) {
                Log.d("Profile", "In the if statement")

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
            else if(todayWaterLog.isEmpty() && streakAlreadyUpdatedToday()) {
                Log.d("Profile", "In the else if statement")

                val newStreak = if(currentData.currentDailyStreak - 1 > 0) { currentData.currentDailyStreak - 1 }
                                else { 0 }

                val latestDate = repo.getLatestWaterLogDate().first()

                _dailyStreak.update { data ->
                    data.copy(
                        currentDailyStreak = newStreak,
                        maxDailyStreak = if (currentData.currentDailyStreak == currentData.maxDailyStreak) {
                            newStreak
                        } else {
                            currentData.maxDailyStreak
                        },
                        lastUpdatedDay = latestDate?.let { s ->
                            TimeConversion.getLocalDateFromLocalDateTimeV(
                                TimeConversion.getLocalDateTimeFromStringR(s)
                            )
                        },
                        isLoading = false
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