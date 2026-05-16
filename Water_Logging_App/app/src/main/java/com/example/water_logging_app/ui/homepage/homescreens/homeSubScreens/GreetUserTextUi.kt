package com.example.water_logging_app.ui.homepage.homescreens.homeSubScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.water_logging_app.R
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.ui.theme.poppins
import java.time.LocalTime

private const val ALPHA_AMOUNT = 0.7f

@Composable
fun GreetUserTextUi(
    modifier : Modifier,
    userData : UserPreferenceData
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = when {
                (LocalTime.now().hour in 5..< 12) -> { stringResource(R.string.Good_Morning) }
                (LocalTime.now().hour in 12.. 19) -> { stringResource(R.string.Good_Afternoon) }
                else -> { stringResource(R.string.Good_Evening) }
            },
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .alpha(ALPHA_AMOUNT)
                .padding(bottom = dimensionResource(R.dimen.mini_text_padding))
        )
        Text(
            text = userData.userName,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = poppins,
                fontWeight = FontWeight.Bold
            ),
        )
    }
}