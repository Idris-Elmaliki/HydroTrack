package com.example.water_logging_app.ui.signUpPage.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.LoadingScreen
import com.example.water_logging_app.ui.signUpPage.viewModels.parent.SignUpViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun MainLoadingScreenUi(
    modifier : Modifier = Modifier,
    signUpViewModel: SignUpViewModel,
    toHomeScreen : () -> Unit, // takes you to the home page
    toSignUpScreen : () -> Unit // takes you to the sign-up page
) {
    LaunchedEffect(Unit) {
        while(signUpViewModel.signUpData.value.isLoading) {
            delay(1000)
        }

        if(signUpViewModel.signUpData.value.error != null) {
            toSignUpScreen()
        }
        else {
            toHomeScreen()
        }
    }

    LoadingScreen(
        modifier = modifier,
        currentUi = {
            Spacer(modifier = Modifier.padding(bottom = dimensionResource(R.dimen.extra_container_padding)))

            LoadingAnimation()

            Spacer(modifier = Modifier.padding(bottom = dimensionResource(R.dimen.extra_container_padding)))

            RandomTextPrompt()
        }
    )
}

@Composable
private fun LoadingAnimation() {
    val dots : List<Int> = listOf(4, 3, 2, 1, 0)

    val transition = rememberInfiniteTransition(label = "Bouncing_Dots")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.icon_padding)),
    ) {
        dots.forEach { index ->
            val offset by transition.animateFloat(
                initialValue = 0f,
                targetValue = dimensionResource(R.dimen.jumpingValue).value,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 1000,
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(
                        offsetMillis = index * 300,
                        offsetType = StartOffsetType.FastForward
                    ),
                ),
                label = "Bouncing_Dots",
            )

            Box(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.loadingDotSize))
                    .graphicsLayer {
                        translationY = offset // Applies the animated height
                    }
                    .background(color = MaterialTheme.colorScheme.onBackground, shape = MaterialTheme.shapes.extraLarge)
            )
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
            .padding(
                top = dimensionResource(R.dimen.container_padding),
                start = dimensionResource(R.dimen.container_padding),
                end = dimensionResource(R.dimen.container_padding),
                bottom = dimensionResource(R.dimen.container_padding)
            )

    )
}