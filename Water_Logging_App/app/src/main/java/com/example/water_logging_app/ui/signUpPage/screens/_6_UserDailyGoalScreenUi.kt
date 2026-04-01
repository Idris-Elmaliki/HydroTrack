package com.example.water_logging_app.ui.signUpPage.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.LoadingScreen
import com.example.water_logging_app.ui.signUpPage.viewModels.parent.SignUpViewModel
import kotlinx.coroutines.delay

@Composable
fun UserDailyGoalScreen(
    modifier : Modifier,
    signUpVM : SignUpViewModel,
    currentNavAction : () -> Unit,
) {
    var showLoadingScreen by rememberSaveable { mutableStateOf(true) }

    var currentProgress by rememberSaveable { mutableFloatStateOf(0f) }
    var isLoaded by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isLoaded) {
        if(isLoaded)
            showLoadingScreen = false
    }

    if(showLoadingScreen) {
        LoadingScreen(
            modifier = modifier,
        ) {
            Spacer(
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.extra_container_padding))
            )

            LaunchedEffect(Unit) {
                loadProgress { progress ->
                    currentProgress = progress
                }
                isLoaded = true
            }

            LoadingBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = dimensionResource(R.dimen.extra_container_padding),
                        end = dimensionResource(R.dimen.extra_container_padding)
                    ),
                currentProgress = currentProgress
            )

            Spacer(
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.container_padding))
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(
                        start = dimensionResource(R.dimen.container_padding),
                        end = dimensionResource(R.dimen.container_padding)
                    )
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.CalculatingRecommendedGoal),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
    else {
        Text(
            text = "Hello World",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

private suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(100)
    }
}
@Composable
private fun LoadingBar(
    modifier : Modifier,
    currentProgress : Float,
) {
    LinearProgressIndicator(
        modifier = modifier,
        progress = { currentProgress },
        trackColor = MaterialTheme.colorScheme.background,
        gapSize = 4.dp
    )
}