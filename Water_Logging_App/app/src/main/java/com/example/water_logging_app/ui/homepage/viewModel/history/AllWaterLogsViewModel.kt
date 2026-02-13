package com.example.water_logging_app.ui.homepage.viewModel.history

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
import javax.inject.Inject

@HiltViewModel
class AllWaterLogsViewModel @Inject constructor(
    private val repo : WaterLogRepositoryImpl
) : ViewModel() {
    private var _allWaterLogs = MutableStateFlow(WaterLogDataList())
    val allWaterLogs : StateFlow<WaterLogDataList> = _allWaterLogs.asStateFlow()

    val timeConversion = TimeConversion

    init {
        loadAllWaterLogsASC()
    }

    fun loadAllWaterLogsASC() {
        viewModelScope.launch {
            try {
                val waterLogs : List<WaterLogData> = repo.getWaterDataASC()

                val map : MutableMap<LocalDate, MutableList<WaterLogData>> = mutableMapOf()

                for(log in waterLogs) {
                    val key = timeConversion.getLocalDateFromLocalDateTimeV(log.timeOfInput)
                    map.getOrPut(key) { mutableListOf() }.add(log)
                }

                _allWaterLogs.update { data ->
                    data.copy(
                        isLoading = false,
                        waterInfoList = map
                    )
                }
            }
            catch (e: Exception) {
                _allWaterLogs.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun loadAllWaterLogsDSC() {
        viewModelScope.launch {
            try {
                _allWaterLogs.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                val waterLogs : List<WaterLogData> = repo.getWaterDataDSC()

                val map : MutableMap<LocalDate, MutableList<WaterLogData>> = mutableMapOf()

                for(log in waterLogs) {
                    val key = timeConversion.getLocalDateFromLocalDateTimeV(log.timeOfInput)
                    map.getOrPut(key) { mutableListOf() }.add(log)
                }

                _allWaterLogs.update { data ->
                    data.copy(
                        isLoading = false,
                        waterInfoList = map
                    )
                }
            }
            catch(e :Exception) {
                _allWaterLogs.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }
}