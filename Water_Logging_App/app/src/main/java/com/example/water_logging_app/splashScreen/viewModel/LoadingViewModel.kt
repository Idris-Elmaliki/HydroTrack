package com.example.water_logging_app.splashScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app.preferenceData.data.repository.UserPreferenceRepositoryImpl
import com.example.water_logging_app.splashScreen.domain.LoadingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val repo : UserPreferenceRepositoryImpl
) : ViewModel() {
    private var _isReady = MutableStateFlow(LoadingData())
    val isReady = _isReady.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val repoData = repo.getUserPreference() ?: throw Exception("User data is empty!")

                _isReady.update { data ->
                    data.copy(
                        isReady = true
                    )
                }
            }
            catch (e : Exception) {
                _isReady.update { data ->
                    data.copy(
                        error = e.message,
                        isReady = true
                    )
                }
            }
        }
    }
}