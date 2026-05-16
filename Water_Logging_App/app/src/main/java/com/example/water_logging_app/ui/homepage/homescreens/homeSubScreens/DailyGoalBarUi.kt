package com.example.water_logging_app.ui.homepage.homescreens.homeSubScreens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.water_logging_app.R
import com.example.water_logging_app._waterLogs.domain.modelData.TodayWaterDataList
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.preferenceData.domain.modelData.enums.UnitMeasurementType
import com.example.water_logging_app.ui.theme.BrilliantAzure
import com.example.water_logging_app.ui.theme.poppins

@Composable
fun DailyGoalBarUi(
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
