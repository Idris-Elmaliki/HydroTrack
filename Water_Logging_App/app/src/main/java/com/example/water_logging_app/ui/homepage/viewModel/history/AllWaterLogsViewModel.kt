package com.example.water_logging_app.ui.homepage.viewModel.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app._waterLogs.data.local.repository.WaterLogRepositoryImpl
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
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AllWaterLogsViewModel @Inject constructor(
    private val repo : WaterLogRepositoryImpl
) : ViewModel() {
    private var _allWaterLogs = MutableStateFlow(WaterLogDataList())
    val allWaterLogs : StateFlow<WaterLogDataList> = _allWaterLogs.asStateFlow()

    init {
        loadAllWaterLogsASC()
    }

    fun loadAllWaterLogsASC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _allWaterLogs.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                repo.getWaterDataASC().map { list ->
                    val map = mutableMapOf<LocalDate, MutableList<WaterLogData>>()
                    list.forEach { data ->
                        val currentKey = TimeConversion.getLocalDateFromLocalDateTimeV(data.timeOfInput)

                        map.getOrPut(currentKey) {mutableListOf()}.add(data)
                    }
                    map // we are essentially returning the map, so .collect{} can get it!
                }.collect { map ->
                    _allWaterLogs.update { data ->
                        data.copy(
                            waterInfoList = map,
                            isLoading = false
                        )
                    }
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _allWaterLogs.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                repo.getWaterDataDSC().map { list ->
                    val map = mutableMapOf<LocalDate, MutableList<WaterLogData>>()
                    list.forEach { data ->
                        val currentKey = TimeConversion.getLocalDateFromLocalDateTimeV(data.timeOfInput)

                        map.getOrPut(currentKey) {mutableListOf()}.add(data)
                    }
                    map // we are essentially returning the map, so .collect{} can get it!
                }.collect { map ->
                    _allWaterLogs.update { data ->
                        data.copy(
                            waterInfoList = map,
                            isLoading = false
                        )
                    }
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