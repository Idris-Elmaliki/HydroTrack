package com.example.water_logging_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.theme.Cyan
import kotlinx.coroutines.delay
import java.text.DateFormat
import java.util.Calendar


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
const val ALPHA_AMOUNT = 0.6f

@Composable
fun HomeScreen(
    onButtonClick : () -> Unit,
) {
    Scaffold(
        topBar = {
            GreetingText()
        }
    ) { innerpadding ->
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(innerpadding)
                .fillMaxWidth()
        ) {
            CurrentGoalCard(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.icon_padding))
            )
            CurrentGoalAmountMatched(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.icon_padding))
            )
        }
        AddNewDate(
            onButtonClick,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.container_padding))
                .fillMaxHeight()
        )
    }
}

@Composable
private fun CurrentGoalCard(
    modifier : Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.container_padding))
        ) {
            Text(
                text = stringResource(R.string.Target),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .alpha(ALPHA_AMOUNT)
            )
            Spacer(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(R.dimen.container_padding))
            )
            // Placeholder (will need to query the data from the Sqlite database)
            Text(
                text = "1000ml",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Composable
private fun CurrentGoalAmountMatched(
    modifier : Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.container_padding))
        ) {
            Text(
                text = stringResource(R.string.Amount_of_water_drank),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .alpha(ALPHA_AMOUNT)
            )
            Spacer(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(R.dimen.container_padding))
            )

            // Placeholder (will need to query the data from the Sqlite database)
            Text(
                text = "50%",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(R.string.Goal_amount_reached),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.text_padding))
                    .alpha(0.7f)
            )
        }
    }
}

@Composable
private fun AddNewDate(
    onButtonClick : () -> Unit,
    modifier : Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.Empty_Log_Description),
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .alpha(ALPHA_AMOUNT)
                .padding(dimensionResource(R.dimen.text_padding))
        )
        Spacer(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.container_padding))
        )
        IconButton(
            onClick = onButtonClick,
            modifier = Modifier
                .background(
                    color = Cyan,
                    shape = MaterialTheme.shapes.extraLarge,
                )
                .size(dimensionResource(R.dimen.text_bubble_size))
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = null,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.text_bubble_size))
                    .padding(dimensionResource(R.dimen.icon_padding)),
            )
        }
    }
}


@Composable
private fun GreetingText() {
    var time by remember { mutableStateOf(currentTime()) }
    var date by remember { mutableStateOf(currentDate()) }

    LaunchedEffect(Unit) {
        while(true) {
            if(date != currentDate()) {
                date = currentDate()
            }
            delay(3_600_000)
        }
    }

    LaunchedEffect(Unit) {
        while(true) {
            if(time != currentDate()) {
                time = currentDate()
            }
            delay(600_000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = dimensionResource(R.dimen.container_padding)
            )
    ) {
        Text(
            text = stringResource(R.string.Good_Morning),
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .alpha(ALPHA_AMOUNT)
                .padding(bottom = dimensionResource(R.dimen.text_padding))
        )
        Text(
            text = "Idris Elmaliki",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Start
        )
    }
}

private fun currentDate() : String {
    val date = Calendar.getInstance().time
    val dateFormat = DateFormat.getDateInstance().format(date)

    return dateFormat
}

private fun currentTime() : String {
    val time = Calendar.getInstance().time
    val timeFormat = DateFormat.getTimeInstance().format(time)

    return timeFormat
}

