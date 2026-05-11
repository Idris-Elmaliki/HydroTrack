package com.example.water_logging_app.ui.homepage.homescreens

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
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.water_logging_app.R
import com.example.water_logging_app.photoPicker.domain.modelData.PhotoData
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.preferenceData.domain.modelData.enums.Genders
import com.example.water_logging_app.preferenceData.domain.modelData.enums.UnitMeasurementType
import com.example.water_logging_app.ui.homepage.viewModel.ROUserDataViewModel
import com.example.water_logging_app.ui.theme.Aquamarine
import com.example.water_logging_app.ui.theme.BrilliantAzure
import com.example.water_logging_app.ui.theme.poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenUi(
    userDataVM : ROUserDataViewModel,
    onDismiss : () -> Unit
) {
    val userData by userDataVM.userData.collectAsStateWithLifecycle()
    val pfpData by userDataVM.profilePicture.collectAsStateWithLifecycle()

    var isInEditMode by rememberSaveable { mutableStateOf(false) }

    ModalDrawerSheet {
        ProfileDrawerHeaderUi(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.text_padding),
                    vertical = dimensionResource(R.dimen.mini_text_padding)
                ),
            isEditMode = isInEditMode,
            onClose = onDismiss,
            onToggleEdit = {
                isInEditMode = !isInEditMode
            }
        )

        HorizontalDivider()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.container_padding))
                .verticalScroll(rememberScrollState())
        ) {
            ProfileDrawerPfpUi(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.container_padding)),
                userData = userData,
                pfpData = pfpData,
                isEditMode = isInEditMode,
                onPfpClick = { }
            )

            ProfileDrawerMeasurementsUi(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.container_padding)),
                userData = userData,
                isEditMode = isInEditMode
            )

            ProfileDrawerPreferencesUi(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.container_padding)),
                userData = userData,
                isEditMode = isInEditMode
            )
        }

    }
}

@Composable
private fun ProfileDrawerHeaderUi(
    modifier: Modifier,
    isEditMode: Boolean,
    onClose: () -> Unit,
    onToggleEdit: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                onToggleEdit() // we turn off editing
                onClose()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null
            )
        }

        Text(
            text = stringResource(R.string.Profile),
            style = MaterialTheme.typography.displaySmall,
            color = Aquamarine,
        )

        TextButton(
            onClick = onToggleEdit
        ) {
            Text(
                text =
                    if (isEditMode) { "Save" }
                    else { "Edit" },
                color = Aquamarine,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun ProfileDrawerPfpUi(
    modifier: Modifier,
    userData: UserPreferenceData,
    pfpData: PhotoData,
    isEditMode: Boolean,
    onPfpClick: () -> Unit
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.container_padding),
            ),
        text = stringResource(R.string.Avatar),
        style = MaterialTheme.typography.headlineSmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
    )

    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.container_padding),
            )
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.container_padding))
                .size(dimensionResource(R.dimen.pfpUpdatedIconSize))
                .clip(CircleShape)
                .border(
                    width = dimensionResource(R.dimen.BorderStroke),
                    color = BrilliantAzure,
                    shape = CircleShape
                )
        ) {
            AsyncImage(
                model = pfpData.filePath.toUri(),
                contentDescription = null,
                placeholder = painterResource(R.drawable.default_pfp_icon),
                error = painterResource(R.drawable.default_pfp_icon),
                fallback = painterResource(R.drawable.default_pfp_icon),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.pfpUpdatedIconSize))
                    .clip(CircleShape)
                    .border(
                        width = dimensionResource(R.dimen.BorderStroke),
                        color = Aquamarine,
                        shape = CircleShape
                    )
                    .clickable(enabled = isEditMode) { onPfpClick() }
                    .then(
                        if (isEditMode) Modifier.alpha(0.6f) else Modifier
                    )
            )
            if (isEditMode) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Aquamarine),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(14.dp)
                    )
                }
            }
        }
        if (isEditMode) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.text_padding)),
                value = userData.userName,
                onValueChange = { },
                label = { Text("Username") },
                shape = MaterialTheme.shapes.medium,
                singleLine = true
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.text_padding))
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = userData.firstName,
                    onValueChange = { },
                    label = { Text("First name") },
                    shape = MaterialTheme.shapes.medium,
                    singleLine = true
                )
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = userData.lastName,
                    onValueChange = { },
                    label = { Text("Last name") },
                    shape = MaterialTheme.shapes.medium,
                    singleLine = true
                )
            }
        } else {
            Text(
                text = userData.userName,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppins
                )
            )
            Text(
                text = "${userData.firstName} ${userData.lastName}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileDrawerMeasurementsUi(
    modifier: Modifier,
    userData: UserPreferenceData,
    isEditMode: Boolean
) {
    val isMetric = userData.unitOfMeasurement == UnitMeasurementType.Metric.name
    val weightUnit = if (isMetric) "kg" else "lbs"
    val heightUnit = if (isMetric) "cm" else "ft"

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.container_padding),
            ),
        text = stringResource(R.string.Measurements),
        style = MaterialTheme.typography.headlineSmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
    )

    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.container_padding),
            )
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var genderExpanded by rememberSaveable { mutableStateOf(false) }

        if (isEditMode) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimensionResource(R.dimen.container_padding),
                        vertical = dimensionResource(R.dimen.text_padding)
                    ),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.text_padding))
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = userData.weight.toInt().toString(),
                    onValueChange = { },
                    label = { Text("Weight") },
                    shape = MaterialTheme.shapes.medium,
                    singleLine = true,
                    suffix = { Text(weightUnit) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = userData.height.toInt().toString(),
                    onValueChange = { },
                    label = { Text("Height") },
                    shape = MaterialTheme.shapes.medium,
                    singleLine = true,
                    suffix = { Text(heightUnit) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.container_padding)),
                value = userData.age,
                onValueChange = { },
                label = { Text("Age") },
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            ExposedDropdownMenuBox(
                expanded = genderExpanded,
                onExpandedChange = { genderExpanded = !genderExpanded }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(R.dimen.container_padding)),
                    value = userData.gender ?: Genders.Male.name,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Gender") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded)
                    },
                    shape = MaterialTheme.shapes.medium
                )

                ExposedDropdownMenu(
                    expanded = genderExpanded,
                    onDismissRequest = { genderExpanded = false }
                ) {
                    Genders.entries.forEach { gender ->
                        DropdownMenuItem(
                            text = { Text(gender.name) },
                            onClick = {
                                // call your vm update function here
                                genderExpanded = false
                            }
                        )
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.container_padding)),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${userData.weight.toInt()} $weightUnit",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Weight",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${userData.height.toInt()} $heightUnit",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Height",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = userData.age,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Age",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = userData.gender ?: "Male",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Gender",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileDrawerPreferencesUi(
    modifier: Modifier,
    userData: UserPreferenceData,
    isEditMode: Boolean
) {
    var measurementExpanded by rememberSaveable { mutableStateOf(false) }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.container_padding),
            ),
        text = stringResource(R.string.Preferences),
        style = MaterialTheme.typography.headlineSmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
    )

    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.container_padding),
            )
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isEditMode) {
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimensionResource(R.dimen.container_padding),
                        vertical = dimensionResource(R.dimen.text_padding)
                    ),
                expanded = measurementExpanded,
                onExpandedChange = { measurementExpanded = !measurementExpanded }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = userData.unitOfMeasurement.toString(),
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Measurement Type") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = measurementExpanded)
                    },
                    shape = MaterialTheme.shapes.medium
                )

                ExposedDropdownMenu(
                    expanded = measurementExpanded,
                    onDismissRequest = { measurementExpanded = false }
                ) {
                    UnitMeasurementType.entries.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type.name) },
                            onClick = {
                                // call your vm update function here
                                measurementExpanded = false
                            }
                        )
                    }
                }
            }

            // Daily Goal
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.container_padding)),
                value = userData.dailyGoal.toString(),
                onValueChange = { },
                label = { Text("Daily Goal") },
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                suffix = {
                    Text(
                        text = if (userData.unitOfMeasurement == UnitMeasurementType.Metric.name) "ml" else "oz"
                    )
                }
            )
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.container_padding)),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = userData.unitOfMeasurement.toString(),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Measurement",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (userData.unitOfMeasurement == UnitMeasurementType.Metric.name) {
                            "${userData.dailyGoal.toFloat() / 1000f}L"
                        } else {
                            "${userData.dailyGoal} oz"
                        },
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Daily Goal",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}