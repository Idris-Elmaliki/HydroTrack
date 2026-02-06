package com.example.water_logging_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app.preferenceData.data.repository.PreferenceRepository
import com.example.water_logging_app.ui.viewmodel.dataclasses.UserPreferenceData
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserPreferenceViewModel(
    val preferenceRepository : PreferenceRepository
) : ViewModel() {
    var _uiState = MutableStateFlow(UserPreferenceData())

    val uiState : StateFlow<UserPreferenceData> = _uiState.asStateFlow()

    init {
        loadUserPreferenceInfo()
    }

    fun loadUserPreferenceInfo() {
        viewModelScope.launch {
            try {
                val name = async {  preferenceRepository.getName() }
                val dailyGoal = async { preferenceRepository.getDailyGoal() }
                val preferredMeasurement = async { preferenceRepository.getPreferredMeasurement() }

                _uiState.update { currentState ->
                    currentState.copy(
                        name = name.await(),
                        dailyGoal = dailyGoal.await(),
                        preferredMeasurement = preferredMeasurement.await()
                    )
                }

            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun insertUsersInfo(
        usersName : String,
        usersDailyGoal : Long,
        usersPreferredMeasurement : String
    ) {
        viewModelScope.launch {
            try {
                preferenceRepository.insertUsersData(
                    usersName = usersName,
                    usersDailyGoal = usersDailyGoal,
                    usersPreferredMeasurement = usersPreferredMeasurement
                )

                _uiState.update { currentState ->
                    currentState.copy(
                        name = usersName,
                        dailyGoal = usersDailyGoal,
                        preferredMeasurement = usersPreferredMeasurement
                    )
                }
            }
            catch (e : Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        error = e.message
                    )
                }
            }
        }
    }
}


