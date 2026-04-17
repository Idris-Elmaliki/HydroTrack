package com.example.water_logging_app.ui.signUpPage.screens

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.LoadingScreen
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.animations.DotLoadingAnimation
import com.example.water_logging_app.ui.signUpPage.viewModels.parent.SignUpViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun MainLoadingScreenUi(
    modifier : Modifier = Modifier,
    signUpViewModel: SignUpViewModel,
    toHomeScreen : () -> Unit, // takes you to the home page
    toSignUpScreen : () -> Unit // takes you to the sign-up page
) {
    val data by signUpViewModel.signUpData.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        while(signUpViewModel.signUpData.value.isLoading) {
            delay(1000)
        }

        Log.d("MainLoadingScreenUi", "MainLoadingScreenUi: error=${data.error}, user=${data.userName}")
    }

    LoadingScreen(
        modifier = modifier,
        currentUi = {
            Spacer(modifier = Modifier.padding(bottom = dimensionResource(R.dimen.extra_container_padding)))

            DotLoadingAnimation()

            Spacer(modifier = Modifier.padding(bottom = dimensionResource(R.dimen.extra_container_padding)))

            RandomTextPrompt()
        }
    )

    LaunchedEffect(data.error) {
        delay(1000)
        if (data.error != null) {
            toSignUpScreen()
        }
        else {
            toHomeScreen()
        }
    }
}

@Composable
private fun RandomTextPrompt() {
    val textList : List<Int> = listOf(
        R.string.loading_text1,
        R.string.loading_text2,
        R.string.loading_text3,
        R.string.loading_text4,
    )

    val rand : Int = Random.nextInt(textList.size)

    Text(
        text = stringResource(textList[rand]),
        style = MaterialTheme.typography.bodyLarge.copy(
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .padding(dimensionResource(R.dimen.container_padding))
    )
}