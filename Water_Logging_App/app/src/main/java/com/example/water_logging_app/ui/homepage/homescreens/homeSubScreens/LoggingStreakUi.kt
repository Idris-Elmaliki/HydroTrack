package com.example.water_logging_app.ui.homepage.homescreens.homeSubScreens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.homepage.viewModel.home.DailyStreakViewModel
import com.example.water_logging_app.ui.homepage.viewModel.home.WaterLogViewModel
import com.example.water_logging_app.ui.theme.averiaSerifLibre
import java.time.DayOfWeek
import java.time.LocalDate

private const val ALPHA_AMOUNT = 0.7f

@Composable
fun LoggingStreakUi(
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