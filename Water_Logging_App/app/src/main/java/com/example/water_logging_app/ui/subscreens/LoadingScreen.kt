package com.example.water_logging_app.ui.subscreens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.theme.BrilliantAzure

/*
* This is the loadingScreen that is used within the sign-up process
*/

@Composable
fun LoadingScreen(
    modifier : Modifier,
    currentUi : @Composable () -> Unit
) {
    Surface (
        color = BrilliantAzure, // will be changed soon! (Should be MaterialTheme.colorScheme.SurfaceVariant)
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.container_padding))
        ) {
            Text(
                text = stringResource(R.string.HydroTrack),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(
                        bottom = dimensionResource(R.dimen.container_padding)
                    )
            )
            Image(
                painter = painterResource(R.drawable.dropicon),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier
                    .size(
                        width = 157.dp,
                        height = 171.dp
                    )
            )
            currentUi()
        }

        Box {
            Image(
                painter = painterResource(R.drawable.watervector1),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
            Image(
                painter = painterResource(R.drawable.watervector2),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
        }
    }
}