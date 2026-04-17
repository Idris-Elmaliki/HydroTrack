package com.example.water_logging_app.ui.signUpPage.screens.subscreens.animations

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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import com.example.water_logging_app.R

@Composable
fun DotLoadingAnimation() {
    val dots : List<Int> = listOf(3, 2, 1, 0)

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