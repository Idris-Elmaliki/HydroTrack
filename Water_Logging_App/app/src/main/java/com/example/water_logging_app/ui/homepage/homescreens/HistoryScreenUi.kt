package com.example.water_logging_app.ui.homepage.homescreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.getSelectedDate
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.homepage.viewModel.history.AllWaterLogsViewModel
import com.example.water_logging_app.ui.homepage.viewModel.home.ROUserDataViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreenUi(
    modifier: Modifier,
    allWaterLogsVM: AllWaterLogsViewModel,
    userDataVM: ROUserDataViewModel
) {
    val allWaterLogs by allWaterLogsVM.allWaterLogs.collectAsStateWithLifecycle()
    val userData by userDataVM.userData.collectAsStateWithLifecycle()

    var isTimeRange by rememberSaveable { mutableStateOf(false) }
    var isNewestWaterLogs by rememberSaveable { mutableStateOf(true) }

    var startDate by rememberSaveable { mutableStateOf<LocalDate?>(null) }
    var endDate by rememberSaveable { mutableStateOf<LocalDate?>(null) }

    var showStartDatePicker by rememberSaveable { mutableStateOf(false) }
    var showEndDatePicker by rememberSaveable { mutableStateOf(false) }

    val startDatePickerState = rememberDatePickerState()
    val endDatePickerState = rememberDatePickerState()

    if (showStartDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showStartDatePicker = !showStartDatePicker
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        startDate = startDatePickerState.getSelectedDate()
                        showStartDatePicker = !showStartDatePicker

                        if (isTimeRange) {
                            showEndDatePicker = !showEndDatePicker
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.OK).trim()
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showStartDatePicker = !showStartDatePicker
                    }
                ) {
                    Text(
                        text = stringResource(R.string.Cancel).trim()
                    )
                }
            }
        ) {
            DatePicker(state = startDatePickerState)
        }
    }

    if (showEndDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showEndDatePicker = !showEndDatePicker
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        endDate = if(endDatePickerState.getSelectedDate()?.isBefore(startDate) == true) {
                            startDate?.plusDays(1L)
                        }
                        else {
                            endDatePickerState.getSelectedDate()
                        }

                        showEndDatePicker = !showEndDatePicker
                    }
                ) {
                    Text(
                        text = stringResource(R.string.OK).trim()
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showEndDatePicker = !showEndDatePicker
                    }
                ) {
                    Text(
                        text = stringResource(R.string.Cancel).trim()
                    )
                }
            }
        ) {
            DatePicker(state = endDatePickerState)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.History),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.container_padding))
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.text_padding))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.text_padding))
            ) {
                Switch(
                    checked = isTimeRange,
                    onCheckedChange = {
                        isTimeRange = it
                        startDate = null
                        endDate = null
                    }
                )
                Text(
                    text = if (isTimeRange) stringResource(R.string.Range) else stringResource(R.string.SingleDay),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                )
            }

            if (isTimeRange) {
                DateRangeSearchBarUi(
                    startDate = startDate,
                    endDate = endDate,
                    onSearchClick = { showStartDatePicker = !showStartDatePicker },
                    onClear = {
                        startDate = null
                        endDate = null
                    }
                )
            } else {
                SingleDaySearchBarUi(
                    selectedDate = startDate,
                    onSearchClick = { showStartDatePicker = !showStartDatePicker },
                    onClear = { startDate = null }
                )
            }

            SortToggleUi(
                isNewest = isNewestWaterLogs,
                onSortChange = { isNewestWaterLogs = it }
            )
        }
    }
}

@Composable
fun SingleDaySearchBarUi(
    selectedDate: LocalDate?,
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.text_padding),
                    vertical = dimensionResource(R.dimen.text_padding)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.text_padding))
        ) {
            Icon(
                imageVector = Icons.Outlined.CalendarMonth,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Selected Date",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = selectedDate?.toString() ?: "Select date",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                )
            }
            if (selectedDate != null) {
                IconButton(onClick = onClear) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DateRangeSearchBarUi(
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.text_padding),
                    vertical = dimensionResource(R.dimen.text_padding)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.text_padding))
        ) {
            Icon(
                imageVector = Icons.Outlined.CalendarMonth,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "From",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = startDate?.toString() ?: "Select date",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                )
            }
            VerticalDivider(modifier = Modifier.height(28.dp))
            Column(modifier = Modifier.weight(1f).padding(start = 10.dp)) {
                Text(
                    text = "To",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = endDate?.toString() ?: "Select date",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                )
            }
            if (startDate != null || endDate != null) {
                IconButton(onClick = onClear) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SortToggleUi(
    isNewest: Boolean,
    onSortChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Sort by",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.text_padding))) {
            FilterChip(
                selected = isNewest,
                onClick = { onSortChange(true) },
                label = { Text("Newest") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                }
            )
            FilterChip(
                selected = !isNewest,
                onClick = { onSortChange(false) },
                label = { Text("Oldest") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                }
            )
        }
    }
}
