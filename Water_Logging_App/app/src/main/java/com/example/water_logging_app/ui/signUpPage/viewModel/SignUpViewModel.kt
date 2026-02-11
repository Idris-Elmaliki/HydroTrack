package com.example.water_logging_app.ui.signUpPage.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app.preferenceData.data.repository.UserPreferenceRepositoryImpl
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repo : UserPreferenceRepositoryImpl
) : ViewModel() {
    private var _signUpData = MutableStateFlow(UserPreferenceData())
    val signUpData : StateFlow<UserPreferenceData> = _signUpData.asStateFlow()

    fun addUserData() {
        viewModelScope.launch {
            try {

            } catch (e: Exception) {

            }
        }
    }
}