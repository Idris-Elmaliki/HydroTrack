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
    private var _signUpData = MutableStateFlow(UserPreferenceData())
    val signUpData : StateFlow<UserPreferenceData> = _signUpData.asStateFlow()

    init {
        getUserData()
    }

    // this won't be posted in the ui, and instead it is only for checking!
    fun getUserData() {
        viewModelScope.launch {
            try {
                val userData = repo.getUserPreference()

                if(userData != null) {
                    _signUpData.update { data ->
                        data.copy(
                            isLoading = false
                        )
                    }
                }
                else {
                    throw Exception("No data found")
                }
            }
            catch (e : Exception) {
                _signUpData.update { data ->
                    data.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    fun addUserData(
        userData : UserPreferenceData
    ) {
        viewModelScope.launch {
            try {
                _signUpData.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                repo.insertUserPreference(
                    UserPreferenceData(
                        name = userData.name,
                        age = userData.age,
                        gender = userData.gender,
                        height = userData.height,
                        weight = userData.weight,
                        dailyGoal = userData.dailyGoal,
                        isMetric = userData.isMetric,
                    )
                )

                _signUpData.update { data ->
                    data.copy(
                        isLoading = false,

                        name = userData.name,
                        age = userData.age,
                        gender = userData.gender,
                        height = userData.height,
                        weight = userData.weight,
                        dailyGoal = userData.dailyGoal,
                        isMetric = userData.isMetric,
                    )
                }
            } catch (e: Exception) {
                _signUpData.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }
}