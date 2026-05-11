package com.example.water_logging_app.ui.homepage.homescreens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.water_logging_app.ui.homepage.viewModel.home.WaterLogViewModel
import com.example.water_logging_app.ui.theme.Aquamarine
import com.example.water_logging_app.ui.theme.BrilliantAzure
import com.example.water_logging_app.ui.theme.LightGray
import com.example.water_logging_app.ui.theme.averiaSerifLibre
import com.example.water_logging_app.ui.theme.poppins
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.util.Locale

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

private const val ALPHA_AMOUNT = 0.7f

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier : Modifier,
    todayWaterLogVM: WaterLogViewModel,
    userDataVM : ROUserDataViewModel,
    dailyStreakVM : DailyStreakViewModel
) {
    val todayWLData by todayWaterLogVM.todayWaterLogs.collectAsStateWithLifecycle()
    val userData by userDataVM.userData.collectAsStateWithLifecycle()
    val pfpData by userDataVM.profilePicture.collectAsStateWithLifecycle()

    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    var editWaterLogListIndex by rememberSaveable { mutableIntStateOf(0) }

    var isProfileDrawerOpen by rememberSaveable { mutableStateOf(false) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        dailyStreakVM.updateDailyStreakData(todayWLData)
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    LaunchedEffect(isProfileDrawerOpen) {
        if (isProfileDrawerOpen) drawerState.open()
        else drawerState.close()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ProfileScreenUi(
                userState = userData,
                photoState = pfpData,
                onDismiss = { isProfileDrawerOpen = false },
                onSave = { }
            )
        }
    ) {

        Scaffold(
            modifier = modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    scrollBehavior = scrollBehavior,
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
                                    isProfileDrawerOpen = !isProfileDrawerOpen
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
            bottomBar = {}
        ) { innerpadding ->
            Column(
                modifier = Modifier
                    .padding(
                        dimensionResource(R.dimen.container_padding)
                    )
                    .padding(innerpadding)
                    .navigationBarsPadding()
                    .padding(bottom = 65.dp)
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
                    dailyStreakVM = dailyStreakVM,
                    waterLogVM = todayWaterLogVM
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
                    onEditButtonClick = { index ->
                        editWaterLogListIndex = index
                        showBottomSheet = !showBottomSheet
                    }
                )
            }
        }
    }

    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false,)

    if(showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth(),
            onDismissRequest = { showBottomSheet = !showBottomSheet },
            sheetState = modalBottomSheetState,
        ) {
            var newWaterLogValue by rememberSaveable { mutableStateOf("${todayWLData.waterInfoList[editWaterLogListIndex].amountOfWater}") }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.container_padding)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = dimensionResource(R.dimen.text_padding)),
                    text = stringResource(R.string.Editing),
                    style = MaterialTheme.typography.bodyLarge
                )
                OutlinedTextField(
                    value = newWaterLogValue,
                    onValueChange = { newData ->
                        newWaterLogValue = newData
                    },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            val updatedAmount = newWaterLogValue.toIntOrNull() ?: 0
                            val logToUpdate = todayWLData.waterInfoList[editWaterLogListIndex]

                            todayWaterLogVM.updateWaterLog(
                                index = editWaterLogListIndex,
                                logToUpdate.copy(amountOfWater = updatedAmount)
                            )
                            showBottomSheet = !showBottomSheet
                        }
                    ),
                    singleLine = true,
                    trailingIcon = {
                        Text(
                            text = if (userData.unitOfMeasurement == UnitMeasurementType.Metric.name) {
                                "ml"
                            } else {
                                "oz"
                            },
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                )
            }
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

@Composable
private fun LoggingStreakUi(
    modifier : Modifier,
    dailyStreakVM : DailyStreakViewModel,
    waterLogVM : WaterLogViewModel,
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
                    modifier = Modifier
                        .padding(end = dimensionResource(R.dimen.mini_text_padding))
                        .alpha(
                            if(streakData.currentDailyStreak == 0) { ALPHA_AMOUNT }
                            else { 1.0f }
                        ),
                    imageVector = Icons.Filled.LocalFireDepartment,
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.alpha(
                        if(streakData.currentDailyStreak == 0) { ALPHA_AMOUNT }
                        else { 1.0f }
                    ),
                    text = "${streakData.currentDailyStreak}",
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

            val weeklyWaterLog by waterLogVM.weeklyWaterLog.collectAsStateWithLifecycle()

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
                horizontalArrangement = Arrangement.SpaceBetween,
                userScrollEnabled = false
            ) {
                items(
                    count = dayList.size,
                ) { day ->
                    val currentDate = LocalDate.now().with(DayOfWeek.MONDAY).plusDays(day.toLong())
                    val hasLog : Boolean = weeklyWaterLog.waterInfoList.containsKey(currentDate)

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
                                        .fillMaxSize()
                                        .padding(dimensionResource(R.dimen.text_padding)),
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
    userData : UserPreferenceData,
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

            val targetProgress =
                if (userData.dailyGoal > 0) {
                    (totalIntake.toFloat() / userData.dailyGoal.toFloat()).coerceIn(0f, 1f)
                }
                else { 0f }

            val animatedProgress by animateFloatAsState(
                targetValue = targetProgress,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                ),
                label = "progress"
            )

            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.text_padding))
                    .height(8.dp)
                    .clip(CircleShape),
                progress = { animatedProgress },
                color = BrilliantAzure,
                trackColor = MaterialTheme.colorScheme.secondaryContainer,
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
    onEditButtonClick : (Int) -> Unit
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    todayWLData.waterInfoList.forEachIndexed { index, currentData ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimensionResource(R.dimen.container_padding)),
                            colors = CardDefaults.cardColors(
                                containerColor = LightGray
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(R.dimen.text_padding)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f),
                                ) {
                                    Text(
                                        text = "${currentData.amountOfWater} ml",
                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = String.format(Locale.getDefault(), "%02d:%02d", currentData.timeOfInput.hour, currentData.timeOfInput.minute),
                                        style = MaterialTheme.typography.bodyMedium.copy(fontFamily = poppins)
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        onEditButtonClick(index)
                                    }
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
                        .alpha(ALPHA_AMOUNT)
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