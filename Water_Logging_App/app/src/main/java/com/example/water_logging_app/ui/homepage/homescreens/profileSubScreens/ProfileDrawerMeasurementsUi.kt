package com.example.water_logging_app.ui.homepage.homescreens.profileSubScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.example.water_logging_app.R
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.preferenceData.domain.modelData.enums.Genders
import com.example.water_logging_app.preferenceData.domain.modelData.enums.UnitMeasurementType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDrawerMeasurementsUi(
    modifier: Modifier,
    userData: UserPreferenceData,
    isEditMode: Boolean,
    newUserMeasurementData : UserPreferenceData,
    onMeasurementChange : (UserPreferenceData) -> Unit
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
                    value = newUserMeasurementData.weight.toString(),
                    onValueChange = { data ->
                        if (data.isEmpty() || data.all { it.isDigit() }) {
                            onMeasurementChange(
                                UserPreferenceData(
                                    weight = if (data.isEmpty()) 0f else data.toFloat(),
                                    height = newUserMeasurementData.height,
                                    age = newUserMeasurementData.age,
                                    gender = newUserMeasurementData.gender
                                )
                            )
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.Weight)
                        )
                    },
                    shape = MaterialTheme.shapes.medium,
                    singleLine = true,
                    suffix = { Text(weightUnit) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = newUserMeasurementData.age,
                    onValueChange = { data ->
                        if (data.isEmpty() || data.all { it.isDigit() }) {
                            onMeasurementChange(
                                UserPreferenceData(
                                    weight = newUserMeasurementData.weight,
                                    height = if (data.isEmpty()) 0f else data.toFloat(),
                                    age = newUserMeasurementData.age,
                                    gender = newUserMeasurementData.gender
                                )
                            )
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.Height)
                        )
                    },
                    shape = MaterialTheme.shapes.medium,
                    singleLine = true,
                    suffix = {
                        Text(heightUnit)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.container_padding)),
                value = newUserMeasurementData.age,
                onValueChange = { data ->
                    if (data.all { it.isDigit() }) {
                        onMeasurementChange(
                            UserPreferenceData(
                                weight = newUserMeasurementData.weight,
                                height = newUserMeasurementData.height,
                                age = data,
                                gender = newUserMeasurementData.gender
                            )
                        )
                    }
                },
                label = {
                    Text(
                        text = stringResource(R.string.Age)
                    )
                },
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            ExposedDropdownMenuBox(
                expanded = genderExpanded,
                onExpandedChange = {
                    genderExpanded = !genderExpanded
                }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        .padding(horizontal = dimensionResource(R.dimen.container_padding)),
                    value = newUserMeasurementData.gender ?: UnitMeasurementType.Metric.name,
                    onValueChange = { data ->
                        onMeasurementChange(
                            UserPreferenceData(
                                weight = newUserMeasurementData.weight,
                                height = newUserMeasurementData.height,
                                age = newUserMeasurementData.age,
                                gender = data
                            )
                        )
                    },
                    readOnly = true,
                    label = {
                        Text(
                            text = stringResource(R.string.Gender)
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded)
                    },
                    shape = MaterialTheme.shapes.medium
                )
                ExposedDropdownMenu(
                    expanded = genderExpanded,
                    onDismissRequest = { genderExpanded = !genderExpanded }
                ) {
                    Genders.entries.forEach { gender ->
                        DropdownMenuItem(
                            text = { Text(gender.name) },
                            onClick = {
                                onMeasurementChange(
                                    UserPreferenceData(
                                        weight = newUserMeasurementData.weight,
                                        height = newUserMeasurementData.height,
                                        age = newUserMeasurementData.age,
                                        gender = gender.name
                                    )
                                )
                                genderExpanded = !genderExpanded
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

