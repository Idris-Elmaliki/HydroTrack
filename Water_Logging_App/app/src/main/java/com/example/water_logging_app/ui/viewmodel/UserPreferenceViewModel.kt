package com.example.water_logging_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app.preferenceData.data.repository.UserPreferenceRepositoryImpl
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class UserPreferenceViewModel(
    val preferenceRepository : UserPreferenceRepositoryImpl
) : ViewModel() {
    var _preferenceState = MutableStateFlow(UserPreferenceData())

    val uiState : StateFlow<UserPreferenceData> = _preferenceState.asStateFlow()

    init {
        loadUserPreferenceInfo()
    }

    fun loadUserPreferenceInfo() {
        viewModelScope.launch {
            try {


                _preferenceState.update { currentState ->
                    currentState.copy(
                        name = name.await(),
                        dailyGoal = dailyGoal.await(),
                        preferredMeasurement = preferredMeasurement.await()
                    )
                }

            } catch (e: Exception) {
                _preferenceState.update { currentState ->
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

                _preferenceState.update { currentState ->
                    currentState.copy(
                        name = usersName,
                        dailyGoal = usersDailyGoal,
                        preferredMeasurement = usersPreferredMeasurement
                    )
                }
            }
            catch (e : Exception) {
                _preferenceState.update { currentState ->
                    currentState.copy(
                        error = e.message
                    )
                }
            }
        }
    }
}


