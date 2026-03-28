package com.example.water_logging_app.ui.signUpPage.viewModel

import android.util.Log
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

    fun updateUserProfile(
        firstName : String? = null,
        lastName : String? = null,
        userName : String? = null,
        gender : String? = null,
    ) {
        viewModelScope.launch {
            try {
                _signUpData.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                _signUpData.update { data ->
                    data.copy(
                        isLoading = false,
                        firstName = firstName ?: data.firstName,
                        lastName = lastName ?: data.lastName,
                        userName = userName ?: data.userName,
                        gender = gender ?: data.gender
                    )
                }
            }
            catch (e : Exception) {
                _signUpData.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun updateUserData(
        unitSystem : String? = null,
        age : String? = null,
        height : Float? = null,
        weight : Float? = null,
    ) {
        viewModelScope.launch {
            try {
                _signUpData.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                _signUpData.update { data ->
                    data.copy(
                        isLoading = false,
                        unitOfMeasurement = unitSystem ?: data.unitOfMeasurement,
                        age = age ?: data.age,
                        height = height ?: data.height,
                        weight = weight ?: data.weight
                    )
                }
            }
            catch (e : Exception) {
                _signUpData.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun syncLimitWithMetric(
        upperLimits: Pair<Float, Float>, // height, weight
    ) {
        viewModelScope.launch {
            try {
                _signUpData.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                val info = _signUpData.value

                _signUpData.update { data ->
                    data.copy(
                        isLoading = false,
                        height = if(info.height > upperLimits.first) {
                            upperLimits.first
                        } else {
                            info.height
                        },
                        weight = if(info.weight > upperLimits.second) {
                            upperLimits.second
                        } else {
                            info.weight
                        }
                    )
                }
            }
            catch (e : Exception) {
                _signUpData.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun checkIfDataIsComplete1() : List<String> { // for screen 1 (UserNameScreen)
        val errorList = mutableListOf<String>()
        Log.d("Testing", "First Name is: ${_signUpData.value.firstName}")

        viewModelScope.launch {
            try {
                _signUpData.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                if (_signUpData.value.firstName.isBlank()) {
                    errorList.add("First Name is Empty.")
                }
                if (_signUpData.value.lastName.isBlank()) {
                    errorList.add("Last Name is Empty.")
                }
                if (_signUpData.value.userName.isBlank()) {
                    errorList.add("User Name is Empty.")
                }
                if (_signUpData.value.gender == null) {
                    errorList.add("Gender is Empty.")
                }

                if (errorList.isNotEmpty()) {
                    throw Exception("Missing Data Found")
                } else {
                    _signUpData.update { data ->
                        data.copy(
                            isLoading = false,
                            error = null
                        )
                    }
                }
            } catch (e: Exception) {
                _signUpData.update { data ->
                    data.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }

        return errorList
    }

    fun checkIfDataIsComplete2() : List<String> { // for screen 2 (UserDataScreen)
        val errorList = mutableListOf<String>()
        viewModelScope.launch {
            try {
                _signUpData.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                if(_signUpData.value.unitOfMeasurement == null) {
                    errorList.add("User hasn't chosen a unit system.")
                }
                if(_signUpData.value.age.isEmpty() || _signUpData.value.age.toIntOrNull() == 0) {
                    errorList.add("Age is invalid.")
                }
                if(_signUpData.value.height == 0.0f) {
                    errorList.add("A height hasn't been added.")
                }
                if(_signUpData.value.weight == 0.0f) {
                    errorList.add("A weight hasn't been added.")
                }

                if (errorList.isNotEmpty()) {
                    throw Exception("Missing Data Found")
                } else {
                    _signUpData.update { data ->
                        data.copy(
                            isLoading = false,
                            error = null
                        )
                    }
                }
            } catch (e: Exception) {
                _signUpData.update { data ->
                    data.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }

        return errorList
    }

    fun uploadUserData() {
        viewModelScope.launch {
            try {
                _signUpData.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                val info = _signUpData.value

                repo.insertUserPreference(
                    UserPreferenceData(
                        firstName = info.firstName,
                        lastName = info.lastName,
                        userName = info.userName,
                        age = info.age,
                        gender = info.gender,
                        height = info.height,
                        weight = info.weight,
                        unitOfMeasurement = info.unitOfMeasurement,
                        dailyGoal = TODO() // this needs to be calculated (we need exercise data)
                    )
                )
            }
            catch (e : Exception) {
                _signUpData.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }
}