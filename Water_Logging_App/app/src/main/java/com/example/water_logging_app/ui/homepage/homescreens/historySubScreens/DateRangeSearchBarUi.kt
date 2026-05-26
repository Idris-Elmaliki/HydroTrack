package com.example.water_logging_app.ui.homepage.homescreens.historySubScreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.water_logging_app.R
import java.time.LocalDate

@Composable
fun DateRangeSearchBarUi(
    modifier: Modifier,
    startDate: LocalDate?,
    endDate: LocalDate?,
    onSearchClick: () -> Unit,
    onClear: () -> Unit
) {
    Card(
        onClick = onSearchClick,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = dimensionResource(R.dimen.BorderStroke),
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.text_padding))
        ) {
            Icon(
                imageVector = Icons.Outlined.CalendarMonth,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "From",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = startDate?.toString() ?: stringResource(R.string.SelectDate),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                )
            }
            VerticalDivider(
                modifier = Modifier
                    .height(28.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = dimensionResource(R.dimen.text_padding))
            ) {
                Text(
                    text = "To",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = endDate?.toString() ?: stringResource(R.string.SelectDate),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                )
            }
            if (startDate != null || endDate != null) {
                IconButton(onClick = onClear) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.container_padding))
                    )
                }
            }
        }
    }
}
