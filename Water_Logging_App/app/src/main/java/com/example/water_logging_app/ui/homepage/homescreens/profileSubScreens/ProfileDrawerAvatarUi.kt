package com.example.water_logging_app.ui.homepage.homescreens.profileSubScreens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import com.example.water_logging_app.R
import com.example.water_logging_app.photoPicker.domain.modelData.PhotoData
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.ui.theme.Aquamarine
import com.example.water_logging_app.ui.theme.BrilliantAzure
import com.example.water_logging_app.ui.theme.poppins


@Composable
fun ProfileDrawerAvatarUi(
    modifier: Modifier,
    userData: UserPreferenceData,
    isEditMode: Boolean,
    newPfpPath: String,
    onPfpClick: (PhotoData) -> Unit,
    newUserNamesData: UserPreferenceData,
    onNameChanges: (UserPreferenceData) -> Unit,
) {
    val singlePhotoPickerLaunch = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            onPfpClick(
                PhotoData(
                    filePath = uri.toString()
                )
            )
        }
    )

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.container_padding))
            .padding(horizontal = dimensionResource(R.dimen.container_padding)),
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
                .border(
                    width = dimensionResource(R.dimen.BorderStroke),
                    color = BrilliantAzure,
                    shape = CircleShape
                )
        ) {
            AsyncImage(
                model = newPfpPath,
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
                    .then(
                        if (isEditMode) Modifier.alpha(0.6f) else Modifier
                    )
            )
            if (isEditMode) {
                Box(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.NavIconSize))
                        .clip(CircleShape)
                        .clickable(
                            onClick = {
                                singlePhotoPickerLaunch.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                        )
                        .background(Aquamarine),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(R.dimen.mini_text_padding))
                    )
                }
            }
        }

        if (isEditMode) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.text_padding)),
                value = newUserNamesData.userName,
                onValueChange = { data ->
                    if(data.all { it.isLetterOrDigit() } || data.all { it.isHighSurrogate() })
                    { onNameChanges(
                        UserPreferenceData(
                            userName = data,
                            firstName = newUserNamesData.firstName,
                            lastName = newUserNamesData.lastName
                        )
                    )
                    }
                },
                label = { Text("Username") },
                shape = MaterialTheme.shapes.medium,
                singleLine = true
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.text_padding))
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = newUserNamesData.firstName,
                    onValueChange = { data ->
                        if (data.all { it.isLetter() }) {
                            onNameChanges(
                                UserPreferenceData(
                                    userName = newUserNamesData.userName,
                                    firstName = data,
                                    lastName = newUserNamesData.lastName
                                )
                            )
                        }
                    },
                    label = { Text("First name") },
                    shape = MaterialTheme.shapes.medium,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = newUserNamesData.lastName,
                    onValueChange = { data ->
                        if (data.all { it.isLetter()}) {
                            onNameChanges(
                                UserPreferenceData(
                                    userName = newUserNamesData.userName,
                                    firstName = newUserNamesData.firstName,
                                    lastName = data
                                )
                            )
                        }
                    },
                    label = { Text("Last name") },
                    shape = MaterialTheme.shapes.medium,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
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