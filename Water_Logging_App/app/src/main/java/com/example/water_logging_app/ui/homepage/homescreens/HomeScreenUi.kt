package com.example.water_logging_app.ui.homepage.homescreens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.water_logging_app.R
import com.example.water_logging_app._waterLogs.domain.modelData.TodayWaterDataList
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.preferenceData.domain.modelData.enums.UnitMeasurementType
import com.example.water_logging_app.ui.homepage.viewModel.home.DailyStreakViewModel
import com.example.water_logging_app.ui.homepage.viewModel.home.ROUserDataViewModel
import com.example.water_logging_app.ui.homepage.viewModel.home.TodayWaterLogViewModel
import com.example.water_logging_app.ui.theme.Aquamarine
import com.example.water_logging_app.ui.theme.BrilliantAzure
import com.example.water_logging_app.ui.theme.averiaSerifLibre
import com.example.water_logging_app.ui.theme.poppins
import java.time.DayOfWeek
import java.time.LocalDate
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
    dailyStreakVM : DailyStreakViewModel
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
                            .clickable(onClick = {

                            }),
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
                .padding(
                    dimensionResource(R.dimen.container_padding))
                .padding(innerpadding)
                .verticalScroll(rememberScrollState())
        ) {
            LoggingStreakUi(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = dimensionResource(R.dimen.BorderStroke),
                        color = BrilliantAzure,
                        shape = MaterialTheme.shapes.medium
                    )
                    .shadow(
                        elevation = dimensionResource(R.dimen.card_shadow_elevation),
                        clip = true,
                        spotColor = Aquamarine,
                        ambientColor = Aquamarine,
                        shape = MaterialTheme.shapes.small
                    ),
                dailyStreakVM = dailyStreakVM
            )
            Spacer(
                modifier = Modifier
                    .height(dimensionResource(R.dimen.container_padding))
            )
            DailyGoalBarUi(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = dimensionResource(R.dimen.BorderStroke),
                        color = BrilliantAzure,
                        shape = MaterialTheme.shapes.medium
                    )
                    .shadow(
                        elevation = dimensionResource(R.dimen.card_shadow_elevation),
                        clip = true,
                        spotColor = Aquamarine,
                        ambientColor = Aquamarine,
                        shape = MaterialTheme.shapes.small
                    ),
                todayWLData = todayWLData,
                userData = userData
            )
            Spacer(
                modifier = Modifier
                    .height(dimensionResource(R.dimen.container_padding))
            )
            TodayWaterLogsUi(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = dimensionResource(R.dimen.BorderStroke),
                        color = BrilliantAzure,
                        shape = MaterialTheme.shapes.medium
                    )
                    .shadow(
                        elevation = dimensionResource(R.dimen.card_shadow_elevation),
                        clip = true,
                        spotColor = Aquamarine,
                        ambientColor = Aquamarine,
                        shape = MaterialTheme.shapes.small
                    ),
                todayWLData = todayWLData,
            )
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
    dailyStreakVM : DailyStreakViewModel
) {
    val streakData by dailyStreakVM.dailyStreak.collectAsStateWithLifecycle()

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        shape = MaterialTheme.shapes.medium,
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.text_padding))
        ) {
            val (
                title,
                streakCount,
                subTitle,
                weekDates,
                weekStreak
            ) = createRefs()

            Text(
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                },
                text = stringResource(R.string.LoggingStreak),
                style = MaterialTheme.typography.bodyLarge
            )

            Row(
                modifier = Modifier.constrainAs(streakCount) {
                    top.linkTo(parent.top, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(end = dimensionResource(R.dimen.mini_text_padding)),
                    imageVector = Icons.Filled.LocalFireDepartment,
                    contentDescription = null,
                )
                Text(
                    text = "${streakData.currentDailyStreak} days",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
            }


            Text(
                modifier = Modifier.constrainAs(subTitle) {
                    top.linkTo(title.bottom, margin = 12.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                },
                text = "This week",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = averiaSerifLibre,
                    fontWeight = FontWeight.Bold
                )
            )

            Row(
                modifier = Modifier.constrainAs(weekDates) {
                    top.linkTo(subTitle.top)
                    bottom.linkTo(subTitle.bottom)
                    end.linkTo(parent.end, margin = 16.dp)
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                val today = LocalDate.now()
                val start = today.with(DayOfWeek.MONDAY)
                val end = today.with(DayOfWeek.SUNDAY)

                Text(
                    text = "${start.month.value}/${start.dayOfMonth}/${start.year}"
                            + " - " +
                            "${end.month.value}/${end.dayOfMonth}/${end.year}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = averiaSerifLibre,
                    )
                )
            }

            val dayList =
                listOf(
                    "Mon",
                    "Tue",
                    "Wed",
                    "Thu",
                    "Fri",
                    "Sat",
                    "Sun"
                )

            val monday = LocalDate.now().with(DayOfWeek.MONDAY)
            val weeklyWaterLog by dailyStreakVM.weeklyWaterLog.collectAsStateWithLifecycle()

            Log.d("WeekStreak", "dayList size: ${dayList.size}")
            Log.d("WeekStreak", "weeklyWaterLog: ${weeklyWaterLog.waterInfoList}")

            LazyRow(
                modifier = Modifier
                    .constrainAs(weekStreak) {
                        top.linkTo(subTitle.bottom, margin = 16.dp)
                        start.linkTo(parent.start, margin = 8.dp)
                        end.linkTo(parent.end, margin = 8.dp)
                        bottom.linkTo(parent.bottom, margin = 16.dp)

                        width = Dimension.fillToConstraints
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                items(
                    count = dayList.size,
                ) { day ->
                    val date = monday.plusDays(day.toLong())
                    val hasLog : Boolean = weeklyWaterLog.waterInfoList.containsKey(date)

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (hasLog) {
                            Box(
                                modifier = Modifier
                                    .size(dimensionResource(R.dimen.pfpNavIconSize))
                                    .clip(CircleShape)
                                    .background(
                                        color = Color(0xFF1D9E75)
                                    )
                                    .border(
                                        border = BorderStroke(
                                            width = dimensionResource(R.dimen.BorderStroke),
                                            color = MaterialTheme.colorScheme.onBackground
                                        ),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    tint = MaterialTheme.colorScheme.background,
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(dimensionResource(R.dimen.pfpNavIconSize))
                                    .clip(CircleShape)
                                    .background(
                                        color = MaterialTheme.colorScheme.background
                                    )
                                    .border(
                                        border = BorderStroke(
                                            width = dimensionResource(R.dimen.BorderStroke),
                                            color = MaterialTheme.colorScheme.onBackground
                                        ),
                                        shape = CircleShape
                                    )
                                    .padding(bottom = dimensionResource(R.dimen.mini_text_padding)),
                                contentAlignment = Alignment.Center
                            ) {}
                        }
                        Spacer(
                            modifier = Modifier
                                .padding(bottom = 2.dp)
                        )
                        Text(
                            text = dayList[day],
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontSize = 12.sp,
                                fontFamily = averiaSerifLibre,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DailyGoalBarUi(
    modifier : Modifier,
    todayWLData : TodayWaterDataList,
    userData : UserPreferenceData
) {
    val totalIntake = todayWLData.waterInfoList.sumOf { it.amountOfWater }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.container_padding)),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.DailyGoal),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                Text(
                    text = if(userData.unitOfMeasurement == UnitMeasurementType.Metric.name) {
                        "${totalIntake.toFloat() / 1000f}L / ${userData.dailyGoal.toFloat() / 1000f}L"
                    }
                    else {
                        "${totalIntake}OZ / ${userData.dailyGoal}OZ"
                    },
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }

            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.text_padding))
                    .height(8.dp)
                    .clip(CircleShape),
                progress = {
                    (totalIntake.toFloat() / userData.dailyGoal.toFloat()).coerceIn(0f, 1f)
                },
                color = BrilliantAzure,
                trackColor = MaterialTheme.colorScheme.onBackground,
            )

            val measurementType =
                if(userData.unitOfMeasurement == UnitMeasurementType.Metric.name) { "ml" }
                else { "oz" }

            Text(
                text = if(totalIntake < userData.dailyGoal) {
                    "${userData.dailyGoal - totalIntake} $measurementType to go"
                }
                else {
                    "Goal completed!"
                },
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = poppins
                )
            )
        }
    }
}

@Composable
private fun TodayWaterLogsUi(
    modifier : Modifier,
    todayWLData : TodayWaterDataList,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.container_padding))
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = dimensionResource(R.dimen.mini_text_padding)),
                text = stringResource(R.string.todayLogs),
                style = MaterialTheme.typography.bodyLarge
            )
            if(!todayWLData.waterInfoList.isEmpty()) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    userScrollEnabled = false
                ) {
                    items(
                        count = todayWLData.waterInfoList.size
                    ) { data ->
                        val currentData = todayWLData.waterInfoList[data]
                        Card(
                            modifier = Modifier
                                .padding(dimensionResource(R.dimen.container_padding))
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f),
                                ) {
                                    Text(
                                        text = "${currentData.amountOfWater} ml"
                                    )
                                    Text(
                                        text = "${currentData.timeOfInput.hour} : ${currentData.timeOfInput.minute}"
                                    )
                                }

                                IconButton(
                                    onClick = {}
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Edit,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
            else {
                Text(
                    modifier = Modifier
                        .alpha(0.7f)
                        .padding(dimensionResource(R.dimen.container_padding))
                        .padding(dimensionResource(R.dimen.text_padding)),
                    text = stringResource(R.string.Empty_Log_Description),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}