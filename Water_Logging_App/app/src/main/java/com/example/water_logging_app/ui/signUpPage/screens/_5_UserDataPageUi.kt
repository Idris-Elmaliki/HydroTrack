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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.signUpPage.viewModel.SignUpViewModel
import com.example.water_logging_app.ui.theme.Aquamarine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDataPageUi(
    modifier : Modifier,
    signUpVM : SignUpViewModel,
    previousNavAction : () -> Unit,
    currentNavAction : () -> Unit,
) {
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
                    },
                    /*
                    actions = {
                        Row(
                            modifier = Modifier
                                .padding(dimensionResource(R.dimen.text_padding))
                                .clip(CircleShape)
                                .clickable(
                                    onClick = {
                                        currentNavAction()
                                    }
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.Finish),
                                color = Aquamarine,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .padding(end = dimensionResource(R.dimen.mini_text_padding))
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                tint = Aquamarine,
                                contentDescription = null,
                            )
                        }
                    },
                    */
                )
            }
        },
        modifier = modifier
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .padding()
                .padding(innerpadding)
        ) { }
    }
}