package com.example.water_logging_app.ui.signUpPage.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.water_logging_app.R
import com.example.water_logging_app.preferenceData.domain.modelData.UnitMeasurementType
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.ui.signUpPage.viewModel.SignUpViewModel
import com.example.water_logging_app.ui.theme.Aquamarine
import com.example.water_logging_app.ui.theme.BrilliantAzure
import com.example.water_logging_app.ui.theme.MistyBlue
import com.example.water_logging_app.userInfoCalculations.heightCalculations


/*
* This page is a continuation of the profile creation process, we collect the rest of the data here!
*
*   1. What measurement system the user uses (metric/imperial)
*   2. The users height
*   3. The users weight
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDataPageUi2(
    modifier : Modifier,
    signUpVM : SignUpViewModel,
    previousNavAction : () -> Unit,
    currentNavAction : () -> Unit,
) {
    val signUpData by signUpVM.signUpData.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(R.string.CreateProfile),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = stringResource(R.string.TellAboutYourself),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                },
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.container_padding))
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(R.dimen.text_padding),
                        end = dimensionResource(R.dimen.text_padding),
                        bottom = dimensionResource(R.dimen.text_padding)
                    )
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(R.dimen.container_padding),
                            end = dimensionResource(R.dimen.container_padding)
                        )
                        .padding(
                            start = dimensionResource(R.dimen.text_padding),
                            end = dimensionResource(R.dimen.text_padding)
                        )
                        .height(dimensionResource(R.dimen.ClickableCardHeight)),
                    colors = CardDefaults.cardColors(
                        containerColor = Aquamarine
                    ),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        // will set up a system similar to UserNamePage
                        // currentNavAction()
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.Finish),
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        Row(
                            modifier = Modifier
                                .padding(dimensionResource(R.dimen.text_padding))
                                .clip(CircleShape)
                                .clickable(
                                    onClick = {
                                        previousNavAction()
                                    }
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                tint = Aquamarine,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = dimensionResource(R.dimen.mini_text_padding))
                            )
                            Text(
                                text = stringResource(R.string.GoBack),
                                color = Aquamarine,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                )
            }
        },
        modifier = modifier
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.container_padding))
                .padding(innerpadding),
        ) {
            UsersAgeUi(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.container_padding))
                    .fillMaxWidth(),
                signUpVM = signUpVM,
                signUpData = signUpData
            )
            UsersUnitSystemUi(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.container_padding)),
                signUpVM = signUpVM,
                signUpData = signUpData
            )
            UsersMeasurementsUi(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.container_padding)),
                signUpVM = signUpVM,
                signUpData = signUpData
            )
        }
    }
}

@Composable
private fun UsersAgeUi(
    modifier : Modifier,
    signUpVM : SignUpViewModel,
    signUpData : UserPreferenceData
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        TextField(
            value = signUpData.age.toString(),
            onValueChange = { data ->
                if(data.isEmpty()) {
                    signUpVM.updateUserData(
                        age = 0
                    )
                }
                else {
                    data.toIntOrNull()?.let { num ->
                        signUpVM.updateUserData(age = num)
                    }
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            label = {
                Text(
                    text = stringResource(R.string.Age),
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MistyBlue,
                unfocusedContainerColor = MistyBlue,
                focusedTextColor = BrilliantAzure,
                unfocusedTextColor = BrilliantAzure
            ),
        )
    }
}

@Composable
private fun UsersUnitSystemUi(
    modifier : Modifier,
    signUpVM : SignUpViewModel,
    signUpData : UserPreferenceData
) {
    val unitSystems = listOf(
        UnitMeasurementType.Metric.name,
        UnitMeasurementType.Imperial.name
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.UnitSystem),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .padding(bottom = dimensionResource(R.dimen.mini_text_padding))
            )
            Text(
                text = "*",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            unitSystems.forEach { unit ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = unit,
                        style = MaterialTheme.typography.labelSmall
                    )
                    RadioButton(
                        selected = unit == signUpData.unitOfMeasurement,
                        onClick = {
                            signUpVM.updateUserData(
                                unitSystem = unit
                            )
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Aquamarine,
                            unselectedColor = Aquamarine
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun UsersMeasurementsUi(
    modifier : Modifier,
    signUpVM : SignUpViewModel,
    signUpData : UserPreferenceData
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(dimensionResource(R.dimen.text_padding))
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.Measurements),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
            )
        )
    }
    Column(
        modifier = modifier
    ) {
        var heightVal by rememberSaveable { mutableIntStateOf(0) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.mini_text_padding))
        ) {
            Text(
                text = stringResource(R.string.Height),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                ),
            )
            Text(
                text = "*",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
        Slider(
            value = signUpData.height,
            onValueChange = { data ->
                signUpVM.updateUserData(
                    height = data
                )
                heightVal = data.toInt()
            },
            valueRange = if(signUpData.unitOfMeasurement == UnitMeasurementType.Metric.name) {
                0f..250f
            }
            else {
                0f..100f
            },
            enabled = signUpData.unitOfMeasurement != null
        )
        if(signUpData.unitOfMeasurement != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.container_padding)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (signUpData.unitOfMeasurement == UnitMeasurementType.Metric.name) {
                        "$heightVal cm"
                    } else {
                        "$heightVal in"
                    },
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .weight(1f)
                )
                Text(
                    text = heightCalculations(
                        unitSystem = signUpData.unitOfMeasurement,
                        height = heightVal
                    ),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }

        var weightVal by rememberSaveable { mutableIntStateOf(0) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.mini_text_padding))
        ) {
            Text(
                text = stringResource(R.string.Weight),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                ),
            )
            Text(
                text = "*",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
        Slider(
            value = signUpData.weight,
            onValueChange = { data ->
                signUpVM.updateUserData(
                    weight = data
                )
                weightVal = data.toInt()
            },
            valueRange = if(signUpData.unitOfMeasurement == UnitMeasurementType.Metric.name) {
                0f..180f
            }
            else {
                0f..400f
            },
            enabled = signUpData.unitOfMeasurement != null
        )
        if(signUpData.unitOfMeasurement != null) {
            Text(
                text = if (signUpData.unitOfMeasurement == UnitMeasurementType.Metric.name) {
                    "$weightVal kg"
                } else {
                    "$weightVal lbs"
                },
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}
