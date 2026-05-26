package com.example.water_logging_app.ui.homepage.homescreens.historySubScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.water_logging_app.R
import com.example.water_logging_app._waterLogs.domain.modelData.WaterLogDataList
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.preferenceData.domain.modelData.enums.UnitMeasurementType
import com.example.water_logging_app.ui.theme.Aquamarine
import java.time.LocalDate

@Composable
fun HistoryLogList(
    modifier: Modifier,
    waterLogDataList: WaterLogDataList,
    userData: UserPreferenceData
) {
    val isMetric = userData.unitOfMeasurement == UnitMeasurementType.Metric.name
    val measurementType = if (isMetric) "ml" else "oz"

    when {
        waterLogDataList.isLoading -> {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        waterLogDataList.error != null -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = waterLogDataList.error,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        waterLogDataList.waterInfoList.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.Empty_Log_Description),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.alpha(0.7f)
                )
            }
        }

        else -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.text_padding))
            ) {
                waterLogDataList.waterInfoList.forEach { (date, logs) ->
                    val totalIntake = logs.sumOf { it.amountOfWater }
                    val goalMet = totalIntake >= userData.dailyGoal
                    item(
                        key = date.toString()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = dimensionResource(R.dimen.text_padding)),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = when (date) {
                                    LocalDate.now() -> "Today — ${date.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${date.dayOfMonth}"
                                    LocalDate.now().minusDays(1) -> "Yesterday — ${date.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${date.dayOfMonth}"
                                    else -> "${date.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${date.dayOfMonth}, ${date.year}"
                                },
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Medium
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = if (isMetric) "${totalIntake.toFloat() / 1000f}L"
                                else "$totalIntake oz",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Medium
                                ),
                                color = if (goalMet) Color(0xFF1D9E75)
                                else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    items(
                        count = logs.size,
                        key = { index -> logs[index].id ?: logs[index].timeOfInput.toString() }
                    ) { index ->
                        val log = logs[index]
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = dimensionResource(R.dimen.text_padding)),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(R.dimen.container_padding)),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.text_padding))
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.WaterDrop,
                                        contentDescription = null,
                                        tint =
                                            if (goalMet) Aquamarine
                                            else MaterialTheme.colorScheme.onSurfaceVariant,

                                        modifier = Modifier
                                            .size(dimensionResource(R.dimen.NavIconSize))
                                    )
                                    Text(
                                        text = "${log.amountOfWater} $measurementType",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                }
                                Text(
                                    text = "${log.timeOfInput.hour}:${log.timeOfInput.minute.toString().padStart(2, '0')}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}