package com.example.water_logging_app.ui.signUpPage.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.theme.Aquamarine
import com.example.water_logging_app.ui.theme.MetallicGray

@Composable
fun BeginSignUpPageUi(
    modifier : Modifier,
    currentNavAction : () -> Unit
) {
    Surface(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(
                all = dimensionResource(R.dimen.container_padding)
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.CreateAnAccount),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(
                    bottom = dimensionResource(R.dimen.extra_container_padding)
                )
            )

            Spacer(
                modifier = Modifier.padding(
                    bottom = dimensionResource(R.dimen.container_padding)
                )
            )

            // change the color to using MaterialTheme!
            Text(
                text = stringResource(R.string.FirstTimeHere),
                style = MaterialTheme.typography.labelMedium,
                color = MetallicGray,
                modifier = Modifier.padding(
                    bottom = dimensionResource(R.dimen.extra_mini_text_padding)
                )
            )

            Text(
                text = stringResource(R.string.CreateToContinue),
                style = MaterialTheme.typography.labelSmall,
                color = MetallicGray,
                modifier = Modifier.padding(
                    bottom = dimensionResource(R.dimen.extra_container_padding)
                )
            )

            Spacer(
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.container_padding),
                    bottom = dimensionResource(R.dimen.container_padding)
                )
            )

            Card(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(R.dimen.container_padding),
                        end = dimensionResource(R.dimen.container_padding)
                    )
                    .height(dimensionResource(R.dimen.ClickableCardHeight))
                    .shadow(
                        elevation = dimensionResource(R.dimen.card_shadow_elevation),
                        clip = true,
                        spotColor = Aquamarine,
                        ambientColor = Aquamarine,
                        shape = MaterialTheme.shapes.small
                    )
                    .fillMaxWidth(),
                onClick = { currentNavAction() },
                colors = CardDefaults.cardColors(
                    containerColor = Aquamarine,
                ),
                shape = MaterialTheme.shapes.small,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        // fix soon (this is a bruteforce method to get the text aligned)
                        modifier = Modifier
                            .padding(top = dimensionResource(R.dimen.text_padding)),
                        text = stringResource(R.string.CreateAccount),
                        style = MaterialTheme.typography.labelMedium.copy(
                            lineHeight = MaterialTheme.typography.labelMedium.fontSize, // sets line height to font size
                            shadow = Shadow(
                                color = if (isSystemInDarkTheme())
                                    MaterialTheme.colorScheme.onBackground
                                else
                                    MaterialTheme.colorScheme.background,
                                offset = Offset(x = 0f, y = 4f),
                                blurRadius = 4f
                            )
                        )
                    )
                }
            }
        }
    }
}