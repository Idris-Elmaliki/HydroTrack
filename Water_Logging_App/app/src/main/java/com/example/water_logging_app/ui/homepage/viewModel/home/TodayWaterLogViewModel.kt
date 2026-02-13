package com.example.water_logging_app.ui.homepage.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app._waterLogs.data.repository.WaterLogRepositoryImpl
import com.example.water_logging_app._waterLogs.domain.modelData.WaterLogData
import com.example.water_logging_app._waterLogs.domain.modelData.WaterLogDataList
import com.example.water_logging_app.time.TimeConversion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TodayWaterLogViewModel @Inject constructor(
    private val repo : WaterLogRepositoryImpl
) : ViewModel() {
    private var _todayWaterLogs = MutableStateFlow(WaterLogDataList())
    var todayWaterLogs : StateFlow<WaterLogDataList> = _todayWaterLogs.asStateFlow()

    val timeConversion = TimeConversion

    init {
        loadTodayWaterLogs()
    }

    fun loadTodayWaterLogs() {
        viewModelScope.launch {
            try {
                val waterLogs : List<WaterLogData> = repo.getWaterDataByDate(LocalDate.now().toString())

                val map : MutableMap<LocalDate, MutableList<WaterLogData>> = mutableMapOf()

                for(log in waterLogs) {
                    val key = timeConversion.getLocalDateFromLocalDateTimeV(log.timeOfInput)
                    map.getOrPut(key) { mutableListOf() }.add(log)
                }

                _todayWaterLogs.update { data ->
                    data.copy(
                        isLoading = false,
                        waterInfoList = map
                    )
                }
            }
            catch (e : Exception) {
                _todayWaterLogs.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun insertWaterLogData(
        waterAmount : Int,
        measurementType : String
    ) {
        viewModelScope.launch {
            try {
                val waterLog = WaterLogData(
                    waterAmount,
                    measurementType,
                    LocalDateTime.now()
                )

                val updatedMap = _todayWaterLogs.value.waterInfoList.toMutableMap()
                val key = timeConversion.getLocalDateFromLocalDateTimeV(waterLog.timeOfInput)

                updatedMap.getOrPut(key) { mutableListOf() }.add(waterLog)

                repo.upsertLoggedWaterData(waterLog)

                _todayWaterLogs.update { data ->
                    data.copy(
                        waterInfoList = updatedMap
                    )
                }
            }
            catch (e : Exception) {
                _todayWaterLogs.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }
}
