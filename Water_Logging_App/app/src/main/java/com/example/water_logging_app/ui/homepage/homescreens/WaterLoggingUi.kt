package com.example.water_logging_app.ui.homepage.homescreens

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.water_logging_app.R
import com.example.water_logging_app.ui._navigation.navActions.AppNavActions
import com.example.water_logging_app.ui.homepage.viewModel.home.WaterLogViewModel
import com.example.water_logging_app.ui.theme.Aquamarine
import com.example.water_logging_app.ui.theme.MetallicGray
import com.example.water_logging_app.ui.theme.VibrantBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterLoggingUi(
    modifier : Modifier,
    todayWaterLogVM: WaterLogViewModel,
    mainNavActions : AppNavActions
) {
    var currentWaterInput by rememberSaveable { mutableStateOf("0") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.LogWater),
                        style = MaterialTheme.typography.titleSmall,
                        color = Aquamarine
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            mainNavActions.navigateBackToHomePage()
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.NavIconSize)),
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = null,
                            tint = Aquamarine
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = VibrantBlue
                )
            )
        },
        bottomBar = {
            Card(
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(
                        start = dimensionResource(R.dimen.extra_container_padding),
                        end = dimensionResource(R.dimen.extra_container_padding),
                    ),
                onClick = {
                    todayWaterLogVM.updateWaterLogData(currentWaterInput.toInt())
                    mainNavActions.navigateBackToHomePage()
                },
                colors = CardDefaults.cardColors(
                    containerColor = Aquamarine
                ),
                shape = MaterialTheme.shapes.medium,
                enabled = currentWaterInput.isBlank() || !currentWaterInput.isBlank() && currentWaterInput.toInt() != 0
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.padding(
                            end = dimensionResource(R.dimen.text_padding)
                        ),
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(
                        text = "Log $currentWaterInput ml",
                        color = Color.White
                    )
                }
            }
        }
    ) { innerpadding ->
        Column(
            modifier = modifier
                .padding(dimensionResource(R.dimen.container_padding))
                .navigationBarsPadding()
                .padding(innerpadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CurrentWaterLogInputUi(
                modifier = Modifier
                    .fillMaxWidth(),
                currentWaterLogValue =
                   if(!currentWaterInput.isBlank()) { currentWaterInput.toInt() }
                   else { 0 }
            )
            Spacer(modifier = Modifier.padding(
                bottom = dimensionResource(R.dimen.container_padding))
            )
            OutlinedTextField(
                value =
                    if(!currentWaterInput.isBlank() && currentWaterInput.toInt() == 0) { "" }
                    else { currentWaterInput },
                onValueChange = { newData ->
                    if(!newData.isBlank() && newData.toInt() < 10000) {
                        currentWaterInput = newData
                    }
                    else if(!newData.isBlank() && newData.toInt() >= 10000) {
                        // I left this empty, since we don't want to do anything
                        // We want to limit the amount of dta the user can input
                    }
                    else {
                        currentWaterInput = "0"
                    }
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                },
                supportingText = {
                    Text(
                        text = stringResource(R.string.EnterCustomAmount)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                shape = MaterialTheme.shapes.medium
            )
            RecentWaterLogsUi(
                modifier = Modifier
                    .fillMaxWidth(),
                todayWaterLogVM = todayWaterLogVM,
                currentWaterLogValue =
                    if(!currentWaterInput.isBlank()) {
                        currentWaterInput.toInt()
                    }
                    else { 0 },
                onPresetSelected = { newInput ->
                    currentWaterInput = newInput.toString()
                }
            )
        }
    }
}

@Composable
private fun CurrentWaterLogInputUi(
    modifier: Modifier,
    currentWaterLogValue : Int
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .padding(
                    bottom = dimensionResource(R.dimen.mini_text_padding)
                ),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "$currentWaterLogValue",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 100.sp
                )
            )
            Text(
                modifier = Modifier
                    .padding(
                        bottom = dimensionResource(R.dimen.text_padding)
                    )
                    .padding(
                        bottom = dimensionResource(R.dimen.mini_text_padding)
                    ),
                text = "ml",
                style = MaterialTheme.typography.titleSmall
            )
        }

        Text(
            text = stringResource(R.string.WaterLoggingDescription)
        )
    }
}

@Composable
private fun RecentWaterLogsUi(
    modifier: Modifier,
    todayWaterLogVM: WaterLogViewModel,
    currentWaterLogValue : Int,
    onPresetSelected : (Int) -> Unit
) {
    val recentWaterLogs by todayWaterLogVM.recentWaterLogs.collectAsStateWithLifecycle()
    var presetSelected : Int? by rememberSaveable { mutableStateOf(null) }

    if(!recentWaterLogs.recentWaterLogs.contains(currentWaterLogValue)) {
        presetSelected = null
    }
    Column(
        modifier = modifier
            .padding(top = dimensionResource(R.dimen.container_padding))
    ) {
        if(!recentWaterLogs.recentWaterLogs.isEmpty())
            Text(
                text = "Recent"
            )
        LazyRow(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            userScrollEnabled = false
        ) {
            items(
                count = recentWaterLogs.recentWaterLogs.size
            ) { index ->
                Card(
                    onClick = {
                        onPresetSelected(recentWaterLogs.recentWaterLogs[index])
                        presetSelected = index
                    },
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = if (presetSelected == index) Aquamarine
                        else MetallicGray
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(end = dimensionResource(R.dimen.mini_text_padding))
                                .size(14.dp),
                            imageVector = Icons.Filled.WaterDrop,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Text(
                            text = "${recentWaterLogs.recentWaterLogs[index]} ml",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}