package com.example.water_logging_app.ui.signUpPage.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import androidx.compose.material3.ShapeDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.water_logging_app.R
import com.example.water_logging_app.preferenceData.domain.modelData.Genders
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.ShowErrorDialogUi
import com.example.water_logging_app.ui.signUpPage.viewModel.SignUpViewModel
import com.example.water_logging_app.ui.theme.Aquamarine
import com.example.water_logging_app.ui.theme.BrilliantAzure
import com.example.water_logging_app.ui.theme.MistyBlue
import kotlinx.coroutines.launch

/*
* Hopefully the name is self-explanatory but this is the ui for the first page of the profile creation process
*
* Here we are gathering 5 things:
*   1. A profile picture
*   2. The users first name
*   3. The users last name
*   4. A username for the users profile
*   5. The users gender
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersDataPageUi1(
    modifier : Modifier, // just passes in .fillMaxSize()
    signUpVM : SignUpViewModel,
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
        },
        modifier = modifier
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(R.dimen.container_padding))
                .padding(innerpadding),
        ) {
            UserProfileUi(
                modifier = Modifier
                    .padding(
                        dimensionResource(R.dimen.container_padding))
                    .fillMaxWidth(),
                signUpVM = signUpVM,
                signUpData = signUpData,
            )
            UserNameTextFieldsUi(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(R.dimen.container_padding),
                        bottom = dimensionResource(R.dimen.container_padding)
                    )
                    .fillMaxWidth(),
                signUpVM = signUpVM,
                signUpData = signUpData
            )

            GenderSelectionUi(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.container_padding),
                        end = dimensionResource(R.dimen.container_padding),
                        top = dimensionResource(R.dimen.container_padding)
                    ),
                viewModel = signUpVM,
                signUpData = signUpData
            )
        }
    }
}

@Composable
private fun UserProfileUi(
    modifier : Modifier,
    signUpVM: SignUpViewModel,
    signUpData : UserPreferenceData
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(
                start = dimensionResource(R.dimen.container_padding),
                end = dimensionResource(R.dimen.container_padding)
            ),
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.extra_mini_text_padding))
                .size(dimensionResource(R.dimen.pfpUpdateIconSize))
                .clip(CircleShape)
                .border(
                    width = dimensionResource(R.dimen.BorderStroke),
                    color = BrilliantAzure,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                contentScale = ContentScale.Crop,
                painter = painterResource(R.drawable.default_pfp_icon),
                contentDescription = null,
            )
        }
        Card(
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.container_padding)),
            colors = CardDefaults.cardColors(
                containerColor = Aquamarine
            ),
            shape = ShapeDefaults.Large,
            onClick = {}
        ) {
            Text(
                text = stringResource(R.string.EditProfilePicture),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.mini_text_padding))
            )
        }


    }
}

@Composable
private fun UserNameTextFieldsUi(
    modifier : Modifier,
    signUpVM: SignUpViewModel,
    signUpData : UserPreferenceData
) {
    val outlinedColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Aquamarine,
        unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
        focusedContainerColor = MistyBlue,
        unfocusedContainerColor = MistyBlue,
        unfocusedTextColor = BrilliantAzure
    )

    val coroutineScope = rememberCoroutineScope()

    Column {
        Text(
            text = stringResource(R.string.Names),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.container_padding),
                    top = dimensionResource(R.dimen.container_padding),
                    bottom = dimensionResource(R.dimen.container_padding)
                )
        )

        OutlinedTextField(
            value = signUpData.firstName,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Black
            ),
            onValueChange = { data ->
                coroutineScope.launch {
                    signUpVM.updateUserProfile(
                        firstName = data
                    )
                }
            },
            label = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.FirstName).trim(), // don't ask why this works
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
            shape = MaterialTheme.shapes.medium,
            colors = outlinedColors,
            modifier = modifier

        )

        OutlinedTextField(
            value = signUpData.lastName,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Black
            ),
            onValueChange = { data ->
                coroutineScope.launch {
                    signUpVM.updateUserProfile(
                        lastName = data
                    )
                }
            },
            label = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.lastName),
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
            shape = MaterialTheme.shapes.medium,
            colors = outlinedColors,
            modifier = modifier
        )

        OutlinedTextField(
            value = signUpData.userName,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Black
            ),
            onValueChange = { data ->
                coroutineScope.launch {
                    signUpVM.updateUserProfile(
                        userName = data
                    )
                }
            },
            label = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.UserName),
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
            shape = MaterialTheme.shapes.medium,
            colors = outlinedColors,
            modifier = modifier,
        )
    }
}
@Composable
private fun GenderSelectionUi(
    modifier : Modifier,
    viewModel: SignUpViewModel,
    signUpData : UserPreferenceData
) {
    val genders = listOf(
        Genders.Male.name,
        Genders.Female.name
    )

    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.Gender),
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
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            genders.forEach { gender ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(
                        selected = gender == signUpData.gender,
                        onClick = {
                            viewModel.updateUserProfile(
                                gender = gender
                            )
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Aquamarine,
                            unselectedColor = Aquamarine
                        )
                    )
                    Text(
                        text = gender,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}