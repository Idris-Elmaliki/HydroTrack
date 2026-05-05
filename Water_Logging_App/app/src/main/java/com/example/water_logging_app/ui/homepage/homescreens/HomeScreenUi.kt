package com.example.water_logging_app.ui.homepage.homescreens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.water_logging_app.R
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.ui.homepage.viewModel.home.ROUserDataViewModel
import com.example.water_logging_app.ui.homepage.viewModel.home.TodayWaterLogViewModel
import com.example.water_logging_app.ui.theme.BrilliantAzure
import com.example.water_logging_app.ui.theme.poppins
import java.time.LocalTime

/*
* What I have so far is just the shell for the home page ui!
*
* I will need to gather the data from the SQLite database to import proper data into the UI.
* This is for things like:
*  - The user's name
*  - Daily water intake Goal
*  - Their progress so far today
*  - The actual additions for that day
*
* There is only more I will need to include for the ui and soon the viewModel & database.
*/

private const val ALPHA_AMOUNT = 0.6f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier : Modifier,
    todayWaterLogVM: TodayWaterLogViewModel,
    userDataVM : ROUserDataViewModel,
) {
    val todayWLData by todayWaterLogVM.todayWaterLogs.collectAsStateWithLifecycle()
    val userData by userDataVM.userData.collectAsStateWithLifecycle()
    val pfpData by userDataVM.profilePicture.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    GreetUserText(
                        modifier = Modifier
                            .fillMaxWidth(),
                        userData = userData
                    )
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.pfpNavIconSize))
                            .clip(CircleShape)
                            .border(
                                width = dimensionResource(R.dimen.BorderStroke),
                                color = BrilliantAzure,
                                shape = CircleShape
                            )
                            .clickable(onClick = {}),
                    ) {
                        AsyncImage(
                            model = pfpData.filePath.toUri(),
                            contentDescription = null,
                            placeholder = painterResource(R.drawable.default_pfp_icon),
                            error = painterResource(R.drawable.default_pfp_icon),
                            fallback = painterResource(R.drawable.default_pfp_icon), // If model is null
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.pfpNavIconSize))
                        )
                    }
                },
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.container_padding)),
            )
        },
        modifier = modifier
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.container_padding))
                .padding(innerpadding)
        ) {

        }
    }
}

@Composable
private fun GreetUserText(
    modifier : Modifier,
    userData : UserPreferenceData
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = when {
                (LocalTime.now().hour in 5..< 12) -> { stringResource(R.string.Good_Morning) }
                (LocalTime.now().hour in 12..19) -> { stringResource(R.string.Good_Afternoon) }
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

@Composable
private fun LoggingStreakUi(
    modifier : Modifier,

) {

}