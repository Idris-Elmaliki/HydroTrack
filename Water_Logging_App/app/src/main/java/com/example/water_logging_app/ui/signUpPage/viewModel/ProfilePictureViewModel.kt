package com.example.water_logging_app.ui.signUpPage.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app.photoPicker.domain.PhotoData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfilePictureViewModel @Inject constructor() : ViewModel() {
    private val _profilePictureUri = MutableStateFlow(PhotoData())
    val profilePictureUri: StateFlow<PhotoData> = _profilePictureUri.asStateFlow()

    fun updateProfilePicture(uri: String?) {

        viewModelScope.launch {
            try {
                _profilePictureUri.update { data ->
                    data.copy(
                        uri = uri ?: ""
                    )
                }
            }
            catch (e: Exception) {
                _profilePictureUri.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }
}