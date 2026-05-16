package com.example.water_logging_app.ui.homepage.homescreens.homeSubScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.example.water_logging_app.R
import com.example.water_logging_app._waterLogs.domain.modelData.WaterLogData
import com.example.water_logging_app.ui.theme.LightGray
import com.example.water_logging_app.ui.theme.poppins
import java.util.Locale

@Composable
fun TodayWaterLogsUi(
    modifier : Modifier,
    todayWLData : WaterLogData,
    onEditButtonClick : () -> Unit
) {
    Card(
        modifier = modifier,
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
                    text = "${todayWLData.amountOfWater} ml",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        todayWLData.timeOfInput.hour,
                        todayWLData.timeOfInput.minute
                    ),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = poppins
                    )
                )
            }

            IconButton(
                onClick = {
                    onEditButtonClick()
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
