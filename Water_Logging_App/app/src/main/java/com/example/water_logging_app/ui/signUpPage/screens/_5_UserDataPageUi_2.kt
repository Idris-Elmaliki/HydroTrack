package com.example.water_logging_app.ui.signUpPage.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.water_logging_app.R
import com.example.water_logging_app.preferenceData.domain.modelData.UnitMeasurementType
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.ShowErrorDialogUi
import com.example.water_logging_app.ui.signUpPage.viewModel.SignUpViewModel
import com.example.water_logging_app.ui.theme.Aquamarine
import com.example.water_logging_app.ui.theme.BrilliantAzure
import com.example.water_logging_app.ui.theme.MistyBlue
import com.example.water_logging_app.ui.theme.poppins
import com.example.water_logging_app.userInfoCalculations.heightCalculations
import kotlinx.coroutines.launch


/*
* This page is a continuation of the profile creation process, we collect the rest of the data here!
*
*   1. The users Age
*   2. What measurement system the user uses (metric/imperial)
*   3. The users height
*   4. The users weight
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

    var checkForError by rememberSaveable { mutableStateOf(false) } // for error dialog
    var checkForConfirmation by rememberSaveable { mutableStateOf(false) } // for confirmation dialog

    var errorList by rememberSaveable { mutableStateOf(emptyList<String>()) }

    if(checkForError) {
        if(!errorList.isEmpty()) {
            ShowErrorDialogUi(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(R.dimen.extra_container_padding),
                        bottom = dimensionResource(R.dimen.extra_container_padding),
                        start = dimensionResource(R.dimen.container_padding),
                        end = dimensionResource(R.dimen.container_padding)
                    ),
                errorList = errorList,
                onDismiss = {
                    checkForError = !checkForError
                }
            )
        }
        else {
            checkForError = false
            checkForConfirmation = true
        }
    }

    val coroutineScope = rememberCoroutineScope()

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
                    .padding(
                        start = dimensionResource(R.dimen.container_padding),
                        end = dimensionResource(R.dimen.container_padding)
                    )
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .navigationBarsPadding()
                    .fillMaxWidth()
            ) {
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
        },
        modifier = modifier
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .padding(innerpadding)
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(R.dimen.container_padding)),
        ) {
            UsersAgeUi(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.container_padding)),
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
            ConfirmButtonUi(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(R.dimen.extra_container_padding),
                        start = dimensionResource(R.dimen.container_padding),
                        end = dimensionResource(R.dimen.container_padding)
                    ),
                currentVMAction = {
                    coroutineScope.launch {
                        errorList = signUpVM.checkIfDataIsComplete2()
                    }
                },
                currentNavAction = {
                    checkForError  = !checkForError
                },
            )
        }
    }

    if(checkForConfirmation) {
        ShowConfirmDialogUi(
            modifier = Modifier,
            onDismiss = {
                checkForConfirmation = false
            },
            currentNavAction = currentNavAction
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowConfirmDialogUi(
    modifier : Modifier,
    onDismiss : () -> Unit,
    currentNavAction: () -> Unit
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.container_padding)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.Confirm),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppins
                    ),
                    modifier = Modifier
                        .padding(top = dimensionResource(R.dimen.text_padding))
                )
                Text(
                    text = stringResource(R.string.ConfirmMessage),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(top = dimensionResource(R.dimen.text_padding), bottom = dimensionResource(R.dimen.text_padding))
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        shape = CircleShape,
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.Cancel).trim(),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .padding(dimensionResource(R.dimen.text_padding))
                        )
                    }

                    TextButton(
                        shape = CircleShape,
                        onClick = {
                            currentNavAction()
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.Confirm).trim(),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .padding(dimensionResource(R.dimen.text_padding)),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UsersAgeUi(
    modifier : Modifier,
    signUpVM : SignUpViewModel,
    signUpData : UserPreferenceData
) {
    val coroutineScope = rememberCoroutineScope()
    val regexFilter = Regex("^[0-9]+$") // this regex checks that the inputted string is only

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        OutlinedTextField(
            value = signUpData.age,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Black,
                textAlign = TextAlign.Center
            ),
            onValueChange = { data ->
                coroutineScope.launch {
                    if(data.contains(regexFilter)) {
                        signUpVM.updateUserData(
                            age = data
                        )
                    }
                    else if(data.isEmpty()) {
                        signUpVM.updateUserData(
                            age = ""
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            label = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.Age),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "*",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Aquamarine,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                focusedContainerColor = MistyBlue,
                unfocusedContainerColor = MistyBlue,
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
    val coroutineScope = rememberCoroutineScope()

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
                            coroutineScope.launch {
                                signUpVM.updateUserData(
                                    unitSystem = unit
                                )
                            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UsersMeasurementsUi(
    modifier : Modifier,
    signUpVM : SignUpViewModel,
    signUpData : UserPreferenceData
) {
    val coroutineScope = rememberCoroutineScope()

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
        val sliderColors = SliderDefaults.colors()

        var heightVal by rememberSaveable { mutableIntStateOf(signUpData.height.toInt()) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.mini_text_padding))
        ) {
            Text(
                text = stringResource(R.string.Height),
                style = MaterialTheme.typography.labelMedium.copy(
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
            thumb = {
                Box(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.NavIconSize))
                        .background(sliderColors.thumbColor, CircleShape)
                ){}
            },
            onValueChange = { data ->
                coroutineScope.launch {
                    signUpVM.updateUserData(
                        height = data
                    )
                }
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

        var weightVal by rememberSaveable { mutableIntStateOf(signUpData.weight.toInt()) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.mini_text_padding))
        ) {
            Text(
                text = stringResource(R.string.Weight),
                style = MaterialTheme.typography.labelMedium.copy(
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
            thumb = {
                Box(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.NavIconSize))
                        .background(sliderColors.thumbColor, CircleShape)
                ){}
            },
            onValueChange = { data ->
                coroutineScope.launch {
                    signUpVM.updateUserData(
                        weight = data
                    )
                }
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

@Composable
private fun ConfirmButtonUi(
    modifier : Modifier,
    currentVMAction : () -> Unit,
    currentNavAction: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(R.dimen.text_padding),
                end = dimensionResource(R.dimen.text_padding)
            )
            .height(dimensionResource(R.dimen.ClickableCardHeight))
            .shadow(
                elevation = dimensionResource(R.dimen.card_shadow_elevation),
                clip = true,
                spotColor = Aquamarine,
                ambientColor = Aquamarine,
                shape = MaterialTheme.shapes.small
            ),
        colors = CardDefaults.cardColors(
            containerColor = Aquamarine
        ),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            // will set up a system similar to UserNamePage
            coroutineScope.launch {
                currentVMAction()
            }
            currentNavAction()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.Finish),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}