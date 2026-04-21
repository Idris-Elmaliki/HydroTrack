package com.example.water_logging_app.ui.viewModel

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

/*
* This viewModel is what we use to control the loading screen!
*
* It has the same logic as before, if we get null then we navigate to the SignUpScreen
* Else we go to the homePage!
*
* The only difference is now this logic is in a dedicated viewModel!
*/
@HiltViewModel
class SplashScreenViewModel @Inject constructor(
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