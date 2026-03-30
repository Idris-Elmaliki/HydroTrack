package com.example.water_logging_app.ui.signUpPage.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.water_logging_app.R
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.preferenceData.domain.modelData.enums.ActivityLevel
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.ShowErrorDialogUi
import com.example.water_logging_app.ui.signUpPage.viewModel.SignUpViewModel
import com.example.water_logging_app.ui.theme.Aquamarine
import kotlinx.coroutines.launch

@Composable
fun UserDataPageUi2(
    modifier : Modifier,
    signUpVM : SignUpViewModel,
    previousNavAction : () -> Unit,
    currentNavAction : () -> Unit,
) {
    val signUpData by signUpVM.signUpData.collectAsStateWithLifecycle()

    var checkForError by rememberSaveable { mutableStateOf(false) }
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
            currentNavAction()
        }
    }

    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier,
        bottomBar = {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .navigationBarsPadding()
                    .fillMaxWidth()
            ) {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                Row(
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.text_padding))
                        .clip(MaterialTheme.shapes.medium)
                        .clickable(
                            onClick = {
                                coroutineScope.launch {
                                    errorList = signUpVM.checkIfDataIsComplete1()
                                }
                                checkForError = !checkForError
                            }
                        ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.Next),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Aquamarine,
                        modifier = Modifier
                            .padding(end = dimensionResource(R.dimen.mini_text_padding))
                    )
                    Icon(
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.NavIconSize)),
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Aquamarine
                    )
                }
            }
        }
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .padding(innerpadding)
                .padding(all = dimensionResource(R.dimen.container_padding))
        ) {
            Text(
                text = stringResource(R.string.CreateProfile),
                style = MaterialTheme.typography.titleSmall
            )
            ChooseActivityLevelUi(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(R.dimen.container_padding)
                    ),
                signUpVM = signUpVM,
                signUpData = signUpData
            )
        }
    }
}

/*
* Four levels:
    * potato couch
    * walker
    * gym bro
    * athlete
*/

data class ActivityLevelData(
    val activityLevel : String,
    val levelImage : Int,
    val description : Int
)

val ActivityLevelList = listOf(
    ActivityLevelData(
        ActivityLevel.CouchPotato.name,
        R.drawable.couch_potato,
        R.string.CouchPotatoDescription
    ),
    ActivityLevelData(
        ActivityLevel.Walker.name,
        R.drawable.walker,
        R.string.WalkerDescription
    ),
    ActivityLevelData(
        ActivityLevel.GymBro.name,
        R.drawable.gym_bro,
        R.string.GymBroDescription
    ),
    ActivityLevelData(
        ActivityLevel.Athlete.name,
        R.drawable.athlete,
        R.string.AthleteDescription
    )
)

@Composable
private fun ChooseActivityLevelUi(
    modifier : Modifier,
    signUpVM : SignUpViewModel,
    signUpData : UserPreferenceData,
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
    ) {
        ActivityLevelList.forEach { level ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = level.activityLevel == signUpData.activityLevel,
                    onClick = {
                        coroutineScope.launch {
                            signUpVM.updateUserData(
                                activityLevel = level.activityLevel
                            )
                        }
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Aquamarine,
                        unselectedColor = Aquamarine
                    )
                )
                Image(
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.text_padding)),
                    painter = painterResource(id = level.levelImage),
                    contentDescription = null,
                )
                Column(
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.text_padding)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = level.activityLevel,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = stringResource(id = level.description),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}