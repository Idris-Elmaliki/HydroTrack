package com.example.water_logging_app.ui.homepage.homescreens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.water_logging_app.R
import com.example.water_logging_app.photoPicker.domain.modelData.PhotoData
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.preferenceData.domain.modelData.enums.Genders
import com.example.water_logging_app.preferenceData.domain.modelData.enums.UnitMeasurementType
import com.example.water_logging_app.ui.homepage.homescreens.profileSubScreens.ProfileDrawerAvatarUi
import com.example.water_logging_app.ui.homepage.homescreens.profileSubScreens.ProfileDrawerHeaderUi
import com.example.water_logging_app.ui.homepage.homescreens.profileSubScreens.ProfileDrawerMeasurementsUi
import com.example.water_logging_app.ui.homepage.homescreens.profileSubScreens.ProfileDrawerPreferencesUi
import com.example.water_logging_app.ui.homepage.viewModel.settings.UserDataViewModel
import com.example.water_logging_app.ui.subscreens.alerts.ConfirmationAlertDialog
import com.example.water_logging_app.ui.theme.Aquamarine
import com.example.water_logging_app.ui.theme.BrilliantAzure
import com.example.water_logging_app.ui.theme.poppins
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenUi(
    userDataVM : UserDataViewModel,
    onDismiss : () -> Unit
) {
    val userData by userDataVM.userData.collectAsStateWithLifecycle()
    val pfpData by userDataVM.profilePicture.collectAsStateWithLifecycle()

    var isInEditMode by rememberSaveable { mutableStateOf(false) }
    Log.d("Profile", "isInEditMode: $isInEditMode")

    var hasChanges by rememberSaveable { mutableStateOf(false) }

    var showConfirmDialog by rememberSaveable { mutableStateOf(false) }

    var newPfpFilePath by rememberSaveable(pfpData.filePath) { mutableStateOf(pfpData.filePath) }

    var newUserName by rememberSaveable(userData.userName) { mutableStateOf(userData.userName) }
    var newFirstName by rememberSaveable(userData.firstName) { mutableStateOf(userData.firstName) }
    var newLastName by rememberSaveable(userData.lastName) { mutableStateOf(userData.lastName) }

    var newWeight by rememberSaveable(userData.weight) { mutableStateOf(userData.weight.toInt().toString()) }
    var newHeight by rememberSaveable(userData.height) { mutableStateOf(userData.height.toInt().toString()) }
    var newAge by rememberSaveable(userData.age) { mutableStateOf(userData.age) }
    var newGender by rememberSaveable(userData.gender) { mutableStateOf(userData.gender ?: Genders.Male.name) }

    var newUnitType by rememberSaveable(userData.unitOfMeasurement) { mutableStateOf(userData.unitOfMeasurement ?: UnitMeasurementType.Metric.name) }
    var newDailyGoal by rememberSaveable(userData.dailyGoal) { mutableStateOf(userData.dailyGoal.toString()) }

    val resetData = {
        newPfpFilePath = pfpData.filePath
        newUserName = userData.userName
        newFirstName = userData.firstName
        newLastName = userData.lastName
        newWeight = userData.weight.toInt().toString()
        newHeight = userData.height.toInt().toString()
        newAge = userData.age
        newGender = userData.gender ?: Genders.Male.name
        newUnitType = userData.unitOfMeasurement ?: UnitMeasurementType.Metric.name
        newDailyGoal = userData.dailyGoal.toString()
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
                    showConfirmDialog = false
                }
                isInEditMode = false
                resetData()
                onDismiss()
            },
            onToggleEdit = {
                if (isInEditMode && hasChanges) {
                    showConfirmDialog = !showConfirmDialog
                }
                else {
                    isInEditMode = !isInEditMode
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
                pfpData = pfpData,
                isEditMode = isInEditMode,
                pfpPath = newPfpFilePath,
                onPfpClick = {
                    if(it.filePath != pfpData.filePath) {
                        hasChanges = true
                    }
                    newPfpFilePath = it.filePath
                },
                listOfNames = listOf(newUserName, newFirstName, newLastName),
                onNameChanges = {
                    if (it.userName != userData.userName ||
                        it.firstName != userData.firstName ||
                        it.lastName != userData.lastName
                    ) {
                        hasChanges = true
                    }

                    Log.d("Profile", "Before update: $newUserName")

                    newUserName = it.userName
                    newFirstName = it.firstName
                    newLastName = it.lastName

                    Log.d("Profile", "After update: $newUserName")
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
                userPreferenceList = listOf(newUnitType, newDailyGoal),
                onPreferenceChange = {
                    if (it.dailyGoal != userData.dailyGoal ||
                        it.unitOfMeasurement != userData.unitOfMeasurement
                    ) {
                        hasChanges = true
                    }

                    newUnitType = it.unitOfMeasurement ?: UnitMeasurementType.Metric.name
                    newDailyGoal = it.dailyGoal.toString()
                },
            )

            ProfileDrawerMeasurementsUi(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.container_padding)),
                userData = userData,
                isEditMode = isInEditMode,
                userMeasurementList = listOf(newWeight, newHeight, newAge, newGender),
                onMeasurementChange = {
                    if (it.weight != userData.weight ||
                        it.height != userData.height ||
                        it.age != userData.age ||
                        it.gender != userData.gender
                    ) {
                        hasChanges = true
                    }

                    newWeight = it.weight.toString()
                    newHeight = it.height.toString()
                    newAge = it.age
                    newGender = it.gender ?: Genders.Male.name
                }
            )
        }
    }

    val coroutineScope = rememberCoroutineScope()
    if(showConfirmDialog) {
        ConfirmationAlertDialog(
            onDismiss = {
                showConfirmDialog = !showConfirmDialog
                resetData()

                hasChanges = false
            },
            onContinuation = {
                isInEditMode = false
                // I need to push everything to their respected vms first, THEN call the repos
                // Even though the VM gets updated, the ui still doesn't update...
                coroutineScope.launch {
                    userDataVM.onUserDataChange(
                        UserPreferenceData(
                            userName = newUserName,
                            firstName = newFirstName,
                            lastName = newLastName,
                            weight = newWeight.toFloatOrNull() ?: userData.weight,
                            height = newHeight.toFloatOrNull() ?: userData.height,
                            age = newAge.ifBlank { userData.age },
                            gender = newGender,
                            unitOfMeasurement = newUnitType,
                            dailyGoal = newDailyGoal.toLongOrNull() ?: userData.dailyGoal,
                        )
                    )

                    userDataVM.onFilePathChange(
                        PhotoData(filePath = newPfpFilePath)
                    )
                    userDataVM.uploadUpdatedData()
                    userDataVM.uploadUpdatedFilePath()

                    hasChanges = false
                    showConfirmDialog = false
                }
            }
        )
    }
}