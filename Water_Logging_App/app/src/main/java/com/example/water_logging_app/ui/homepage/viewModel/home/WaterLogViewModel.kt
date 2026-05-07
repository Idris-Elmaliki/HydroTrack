package com.example.water_logging_app.ui.homepage.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.water_logging_app._waterLogs.data.local.repository.WaterLogRepositoryImpl
import com.example.water_logging_app._waterLogs.domain.modelData.RecentWaterLogData
import com.example.water_logging_app._waterLogs.domain.modelData.TodayWaterDataList
import com.example.water_logging_app._waterLogs.domain.modelData.WaterLogData
import com.example.water_logging_app._waterLogs.domain.modelData.WaterLogDataList
import com.example.water_logging_app.time.TimeConversion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * ViewModel responsible for managing water logging data and state for the Home Screen.
 * It handles loading today's logs, weekly summaries, and recent unique intake amounts.
 */
@HiltViewModel
class WaterLogViewModel @Inject constructor(
    private val repo : WaterLogRepositoryImpl
) : ViewModel() {
    // StateFlow for today's water logs
    private var _todayWaterLogs = MutableStateFlow(TodayWaterDataList())
    val todayWaterLogs : StateFlow<TodayWaterDataList> = _todayWaterLogs.asStateFlow()

    // StateFlow for recently used water intake amounts (for quick selection)
    private var _recentWaterLogs = MutableStateFlow(RecentWaterLogData())
    val recentWaterLogs = _recentWaterLogs.asStateFlow()

    // StateFlow for the weekly water intake breakdown
    private var _weeklyWaterLog = MutableStateFlow(WaterLogDataList())
    val weeklyWaterLog = _weeklyWaterLog.asStateFlow()

    init {
        // Initialize data loading on ViewModel creation
        loadTodayWaterLogs()
        loadWeeklyWaterLog()
        loadTotalWaterLogs()
    }

    /**
     * Loads water logs for the current date from the repository.
     */
    private fun loadTodayWaterLogs() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.getWaterDataByDate(
                    TimeConversion.getStringFromLocalDateV(LocalDate.now())
                ).collect { list ->
                    _todayWaterLogs.update { data ->
                        data.copy(
                            isLoading = false,
                            waterInfoList = list.toMutableList()
                        )
                    }
                }
            }
            catch (e : Exception) {
                _todayWaterLogs.update { data ->
                    data.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    /**
     * Loads water logs for the current week (Monday to today) and groups them by date.
     */
    private fun loadWeeklyWaterLog() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.getWeeklyLoggedWaterData(
                    startDate = TimeConversion.getStringFromLocalDateV(
                        LocalDate.now().with(DayOfWeek.MONDAY)
                    ),
                    endDate = TimeConversion.getStringFromLocalDateV(
                        LocalDate.now()
                    ),
                ).map { list ->
                    // Group the flat list of logs into a map keyed by LocalDate
                    val map =
                        mutableMapOf<LocalDate, MutableList<WaterLogData>>()

                    list.forEach { data ->
                        val currentKey =
                            TimeConversion.getLocalDateFromLocalDateTimeV(data.timeOfInput)

                        map.getOrPut(currentKey) { mutableListOf() }.add(data)
                    }

                    map
                }.collect { map ->
                    _weeklyWaterLog.update { data ->
                        data.copy(
                            waterInfoList = map,
                            isLoading = false
                        )
                    }
                }
            }
            catch (e : Exception) {
                _weeklyWaterLog.update { data ->
                    data.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    /**
     * Fetches historical logs and extracts the 3 most recent unique water amounts.
     * This provides the "recent" buttons for quick logging.
     */
    private fun loadTotalWaterLogs() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val map = repo.getWaterDataDSC()

                map.map { list ->
                    val seen = mutableSetOf<Int>()
                    val result = ArrayDeque<Int>()

                    for (log in list) {
                        if (result.size == 3)
                            break

                        if (seen.add(log.amountOfWater)) {
                            result.add(log.amountOfWater)
                        }
                    }
                    result
                }.collect { data ->
                    _recentWaterLogs.update {
                        it.copy(
                            isLoading = false,
                            recentWaterLogs = data
                        )
                    }
                }
            }
            catch (e : Exception) {
                _recentWaterLogs.update { data ->
                    data.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    /**
     * Updates the UI state with a new water log entry. 
     * Note: This only updates the local state; call [insertWaterLogData] to persist.
     */
    fun updateWaterLogData(
        waterAmount : Int,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val newLog = WaterLogData(
                waterAmount,
                LocalDateTime.now()
            )

            // Update today's log list in state
            val newList : MutableList<WaterLogData> = _todayWaterLogs.value.waterInfoList.toMutableList()
            newList.add(newLog)

            _todayWaterLogs.update { data ->
                data.copy(
                    waterInfoList = newList.toList()
                )
            }

            // Update weekly map in state
            val currentWeeklyMap = _weeklyWaterLog.value.waterInfoList.toMutableMap()
            val todayDate = LocalDate.now()
            val todayWeeklyList = currentWeeklyMap[todayDate]?.toMutableList() ?: mutableListOf()
            todayWeeklyList.add(newLog)
            currentWeeklyMap[todayDate] = todayWeeklyList

            _weeklyWaterLog.update { data ->
                data.copy(
                    waterInfoList = currentWeeklyMap.toMap()
                )
            }

            // Update recent amounts if this is a new unique amount
            val updatedWaterLog = _recentWaterLogs.value.recentWaterLogs

            if(!updatedWaterLog.contains(waterAmount)) {
                updatedWaterLog.addFirst(waterAmount)

                if(updatedWaterLog.size > 3)
                    updatedWaterLog.removeLast()

                _recentWaterLogs.update { data ->
                    data.copy(
                        recentWaterLogs = updatedWaterLog
                    )
                }
            }
        }
    }

    /**
     * Persists the current state of today's water logs to the database.
     * Intended to be called during lifecycle events like OnStop().
     */
    fun insertWaterLogData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _todayWaterLogs.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                repo.upsertLoggedWaterData(
                    _todayWaterLogs.value.waterInfoList
                )

                _todayWaterLogs.update { data ->
                    data.copy(
                        isLoading = false
                    )
                }
            }
            catch (e : Exception) {
                _todayWaterLogs.update { data ->
                    data.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }
}
