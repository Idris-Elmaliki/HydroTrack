package com.example.water_logging_app.ui.homepage.homescreens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.water_logging_app.R
import com.example.water_logging_app.photoPicker.domain.modelData.PhotoData
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.ui.homepage.homescreens.profileSubScreens.ProfileDrawerAvatarUi
import com.example.water_logging_app.ui.homepage.homescreens.profileSubScreens.ProfileDrawerHeaderUi
import com.example.water_logging_app.ui.homepage.homescreens.profileSubScreens.ProfileDrawerMeasurementsUi
import com.example.water_logging_app.ui.homepage.homescreens.profileSubScreens.ProfileDrawerPreferencesUi
import com.example.water_logging_app.ui.homepage.viewModel.profile.UserDataViewModel
import com.example.water_logging_app.ui.subscreens.alerts.ConfirmationAlertDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenUi(
    userDataVM : UserDataViewModel,
    onDismiss : () -> Unit
) {
    val userData by userDataVM.userData.collectAsStateWithLifecycle()

    val editedUserData by userDataVM.editedUserData.collectAsStateWithLifecycle()
    val pfpData by userDataVM.profilePicture.collectAsStateWithLifecycle()

    var isInEditMode by rememberSaveable      { mutableStateOf(false) }
    var hasChanges by rememberSaveable        { mutableStateOf(false) }
    var showConfirmDialog by rememberSaveable { mutableStateOf(false) }

    var newPfpFilePath by rememberSaveable(pfpData.filePath) { mutableStateOf(pfpData.filePath) }

    val updateData = {
        Log.d("Profile", "Time to reset the edited data")
        userDataVM.resetEditUserData()
    }

    ModalDrawerSheet {
        ProfileDrawerHeaderUi(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.text_padding),
                    vertical = dimensionResource(R.dimen.mini_text_padding)
                ),
            isEditMode = isInEditMode,
            onClose = {
                if (isInEditMode && hasChanges) {
                    showConfirmDialog = true
                }

                if(!showConfirmDialog) {
                    isInEditMode = false
                    onDismiss()
                }
            },
            onToggleEdit = {
                if (isInEditMode) {
                    if (hasChanges) {
                        showConfirmDialog = true  // show confirm dialog to save
                    } else {
                        isInEditMode = false  // no changes, just exit edit mode
                    }
                } else {
                    isInEditMode = true  // enter edit mode
                }
            }
        )

        HorizontalDivider()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(R.dimen.container_padding))
                .verticalScroll(rememberScrollState())
        ) {
            ProfileDrawerAvatarUi(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = dimensionResource(R.dimen.container_padding),
                        horizontal = dimensionResource(R.dimen.container_padding)
                    ),
                userData = userData,
                isEditMode = isInEditMode,
                newPfpPath = newPfpFilePath,
                onPfpClick = {
                    if(it.filePath != pfpData.filePath) {
                        hasChanges = true
                    }
                    newPfpFilePath = it.filePath
                },
                newUserNamesData = UserPreferenceData(
                    userName = editedUserData.userName,
                    firstName = editedUserData.firstName,
                    lastName = editedUserData.lastName,
                ),
                onNameChanges = {
                    if (it.userName != userData.userName ||
                        it.firstName != userData.firstName ||
                        it.lastName != userData.lastName
                    ) {
                        hasChanges = true
                    }

                    userDataVM.updateEditedUserData(
                        userName = it.userName,
                        firstName = it.firstName,
                        lastName = it.lastName,
                    )
                }
            )

            ProfileDrawerPreferencesUi(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = dimensionResource(R.dimen.container_padding),
                    ),
                userData = userData,
                isEditMode = isInEditMode,
                newUserPreferenceData = UserPreferenceData(
                    dailyGoal = editedUserData.dailyGoal,
                    unitOfMeasurement = editedUserData.unitOfMeasurement
                ),
                onPreferenceChange = {
                    if (it.dailyGoal != userData.dailyGoal ||
                        it.unitOfMeasurement != userData.unitOfMeasurement
                    ) {
                        hasChanges = true
                    }

                    userDataVM.updateEditedUserData(
                        unitOfMeasurement = it.unitOfMeasurement,
                        dailyGoal = it.dailyGoal
                    )
                }
            )

            ProfileDrawerMeasurementsUi(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.container_padding)),
                userData = userData,
                isEditMode = isInEditMode,
                newUserMeasurementData = UserPreferenceData(
                    weight = editedUserData.weight,
                    height = editedUserData.height,
                    age = editedUserData.age,
                    gender = editedUserData.gender,
                ),
                onMeasurementChange = {
                    if (it.weight != userData.weight ||
                        it.height != userData.height ||
                        it.age != userData.age ||
                        it.gender != userData.gender
                    ) {
                        hasChanges = true
                    }

                    userDataVM.updateEditedUserData(
                        weight = it.weight,
                        height = it.height,
                        age = it.age,
                        gender = it.gender
                    )
                }
            )
        }
    }

    if(showConfirmDialog) {
        ConfirmationAlertDialog(
            onDismiss = {
                showConfirmDialog = false
                hasChanges = false

                updateData()
            },
            onContinuation = {
                userDataVM.uploadNewUpdatedData()

                userDataVM.uploadNewUpdatedFilePath(
                    PhotoData(filePath = newPfpFilePath)
                )

                hasChanges = false
                showConfirmDialog = false
                isInEditMode = false
            }
        )
    }
}