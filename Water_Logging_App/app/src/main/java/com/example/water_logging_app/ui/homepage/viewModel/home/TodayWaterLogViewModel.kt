package com.example.water_logging_app.ui.homepage.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app._waterLogs.data.local.repository.WaterLogRepositoryImpl
import com.example.water_logging_app._waterLogs.domain.modelData.TodayWaterDataList
import com.example.water_logging_app._waterLogs.domain.modelData.WaterLogData
import com.example.water_logging_app.time.TimeConversion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private var _todayWaterLogs = MutableStateFlow(TodayWaterDataList())
    var todayWaterLogs : StateFlow<TodayWaterDataList> = _todayWaterLogs.asStateFlow()

    val timeConversion = TimeConversion

    init {
        loadTodayWaterLogs()
    }

    fun loadTodayWaterLogs() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.getWaterDataByDate(
                    timeConversion.getStringFromLocalDateV(LocalDate.now())
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
                        error = e.message
                    )
                }
            }
        }
    }

    fun updateWaterLogData(
        waterAmount : Int,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val newList : MutableList<WaterLogData> = _todayWaterLogs.value.waterInfoList.toMutableList()

            newList.add(
                WaterLogData(
                    waterAmount,
                    LocalDateTime.now()
                )
            )

            _todayWaterLogs.update { data ->
                data.copy(
                    waterInfoList = newList.toList()
                )
            }
        }
    }

    // we will be using OnStop()!
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
