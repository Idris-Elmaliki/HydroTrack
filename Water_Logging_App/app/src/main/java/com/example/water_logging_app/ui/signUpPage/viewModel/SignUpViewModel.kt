package com.example.water_logging_app.ui.signUpPage.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app.preferenceData.data.repository.UserPreferenceRepositoryImpl
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repo : UserPreferenceRepositoryImpl
) : ViewModel() {
    private var _signUpData = MutableStateFlow(UserPreferenceData(
        name = "",
        dailyGoal = 0,
        preferredMeasurement = ""
    ))
    val signUpData : StateFlow<UserPreferenceData> = _signUpData.asStateFlow()


    fun addUserData(
        userData : UserPreferenceData
    ) {
        viewModelScope.launch {
            try {
                repo.insertUserPreference(
                    UserPreferenceData(
                        name = userData.name,
                        dailyGoal = userData.dailyGoal,
                        preferredMeasurement = userData.preferredMeasurement
                    )
                )

                _signUpData.update { data ->
                    data.copy(
                        name = userData.name,
                        dailyGoal = userData.dailyGoal,
                        preferredMeasurement = userData.preferredMeasurement
                    )
                }
            } catch (e: Exception) {
                _signUpData.update { data ->
                    data.copy(
                        error = e.message.toString()
                    )
                }
            }
        }
    }
}