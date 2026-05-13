package com.example.water_logging_app.ui.homepage.homescreens.profileSubScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExposedDropdownMenuAnchorType
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
import com.example.water_logging_app.preferenceData.domain.modelData.enums.UnitMeasurementType

private enum class PreferenceList {
    Measurement,
    DailyGoal
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDrawerPreferencesUi(
    modifier: Modifier,
    userData: UserPreferenceData,
    isEditMode: Boolean,
    userPreferenceList : List<String>,
    onPreferenceChange : (UserPreferenceData) -> Unit,
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
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                    value = userPreferenceList[PreferenceList.Measurement.ordinal],
                    onValueChange = { data ->
                        onPreferenceChange(
                            UserPreferenceData(
                                unitOfMeasurement = data,
                                dailyGoal = userPreferenceList[PreferenceList.DailyGoal.ordinal].toLong()
                            )
                        )
                    },
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
                                onPreferenceChange(
                                    UserPreferenceData(
                                        unitOfMeasurement = type.name,
                                        dailyGoal = userPreferenceList[PreferenceList.DailyGoal.ordinal].toLong()
                                    )
                                )
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
                value = userPreferenceList[PreferenceList.DailyGoal.ordinal],
                onValueChange = { data ->
                    if (data.all { it.isDigit() }) {
                        onPreferenceChange(
                            UserPreferenceData(
                                unitOfMeasurement = userPreferenceList[PreferenceList.Measurement.ordinal],
                                dailyGoal = if(data.isEmpty()) 0L else data.toLong()
                            )
                        )
                    }
                },
                label = { Text("Daily Goal") },
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                suffix = {
                    Text(
                        text = if (userPreferenceList[PreferenceList.Measurement.ordinal] == UnitMeasurementType.Metric.name) "ml" else "oz"
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
