package com.example.water_logging_app.ui.signUpPage.screens

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.water_logging_app.R
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.ui.signUpPage.viewModel.SignUpViewModel
import com.example.water_logging_app.ui.theme.Aquamarine
import com.example.water_logging_app.ui.theme.MistyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersNamePageUi(
    modifier : Modifier, // just passes in .fillMaxSize()
    signUpVM : SignUpViewModel,
    currentNavAction : () -> Unit,
) {
    val signUpData = signUpVM.signUpData.collectAsStateWithLifecycle()

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
            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.container_padding))
                    .fillMaxWidth(),
            ) {
                Row(
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
                    IconButton(
                        onClick = {
                            currentNavAction()
                        }
                    ) {
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
        },
        modifier = modifier
    ) { innerpadding ->
       UserNamesUi(
           modifier = Modifier
               .padding(dimensionResource(R.dimen.container_padding))
               .padding(innerpadding),
           signUpVM = signUpVM,
           signUpData = signUpData.value,
       )
    }
}

@Composable
private fun UserNamesUi(
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
                    color = MaterialTheme.colorScheme.onBackground,
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



        OutlinedTextField(
            value = signUpData.firstName,
            textStyle = MaterialTheme.typography.bodyLarge,
            onValueChange = { data ->
                signUpVM.updateUserProfile(
                    firstName = data
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.FirstName).trim(), // it worked, I don't know how
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
            },
            singleLine = true,
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MistyBlue,
                unfocusedContainerColor = MistyBlue
            ),
            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.container_padding),
                    bottom = dimensionResource(R.dimen.container_padding)
                )
                .fillMaxWidth(),
        )

        OutlinedTextField(
            value = signUpData.lastName,
            textStyle = MaterialTheme.typography.bodyLarge,
            onValueChange = { data ->
                signUpVM.updateUserProfile(
                    lastName = data
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.lastName),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            singleLine = true,
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MistyBlue,
                unfocusedContainerColor = MistyBlue
            ),
            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.container_padding),
                    bottom = dimensionResource(R.dimen.container_padding)
                )
                .fillMaxWidth(),
        )

        OutlinedTextField(
            value = signUpData.userName,
            textStyle = MaterialTheme.typography.bodyLarge,
            onValueChange = { data ->
                signUpVM.updateUserProfile(
                    userName = data
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.UserName),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
            },
            singleLine = true,
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MistyBlue,
                unfocusedContainerColor = MistyBlue
            ),
            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.container_padding),
                    bottom = dimensionResource(R.dimen.container_padding)
                )
                .fillMaxWidth(),
        )
    }
}