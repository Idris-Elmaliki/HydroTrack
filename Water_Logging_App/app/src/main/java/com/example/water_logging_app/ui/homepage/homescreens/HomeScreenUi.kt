package com.example.water_logging_app.ui.homepage.homescreens

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxDefaults
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.water_logging_app.R
import com.example.water_logging_app.preferenceData.domain.modelData.enums.UnitMeasurementType
import com.example.water_logging_app.ui.homepage.homescreens.homeSubScreens.DailyGoalBarUi
import com.example.water_logging_app.ui.homepage.homescreens.homeSubScreens.GreetUserTextUi
import com.example.water_logging_app.ui.homepage.homescreens.homeSubScreens.LoggingStreakUi
import com.example.water_logging_app.ui.homepage.homescreens.homeSubScreens.TodayWaterLogsUi
import com.example.water_logging_app.ui.homepage.viewModel.home.DailyStreakViewModel
import com.example.water_logging_app.ui.homepage.viewModel.home.ROUserDataViewModel
import com.example.water_logging_app.ui.homepage.viewModel.home.WaterLogViewModel
import com.example.water_logging_app.ui.theme.Aquamarine
import com.example.water_logging_app.ui.theme.BrilliantAzure

private const val ALPHA_AMOUNT = 0.7f

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier : Modifier,
    todayWaterLogVM: WaterLogViewModel,
    userDataVM : ROUserDataViewModel,
    dailyStreakVM : DailyStreakViewModel,
    isProfileClick : () -> Unit
) {
    val todayWLData by todayWaterLogVM.todayWaterLogs.collectAsStateWithLifecycle()
    val userData by userDataVM.userData.collectAsStateWithLifecycle()
    val pfpData by userDataVM.profilePicture.collectAsStateWithLifecycle()

    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    var editWaterLogListIndex by rememberSaveable { mutableIntStateOf(0) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        dailyStreakVM.updateDailyStreakData(todayWLData)
    }

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    GreetUserTextUi(
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
                            .clickable(
                                onClick = {
                                    isProfileClick()
                                }
                            ),
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
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.container_padding))
                .padding(innerpadding)
                .navigationBarsPadding()
        ) {
            item {
                LoggingStreakUi(
                    modifier = Modifier
                        .padding(top = dimensionResource(R.dimen.container_padding))
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
            }
            item {
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
            }

            item {
                Text(
                    modifier = Modifier
                        .padding(vertical = dimensionResource(R.dimen.text_padding)),
                    text = stringResource(R.string.todayLogs),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            if(todayWLData.waterInfoList.isEmpty()) {
                item {
                    Text(
                        modifier = Modifier
                            .alpha(ALPHA_AMOUNT)
                            .padding(dimensionResource(R.dimen.container_padding))
                            .padding(dimensionResource(R.dimen.container_padding)),
                        text = stringResource(R.string.Empty_Log_Description),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
            else {
                items(
                    count = todayWLData.waterInfoList.size,
                    key = { todayWLData.waterInfoList[it].id ?: todayWLData.waterInfoList[it].timeOfInput  }
                ) { index ->
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        val dismissState = rememberSwipeToDismissBoxState(
                            SwipeToDismissBoxValue.Settled,
                            SwipeToDismissBoxDefaults.positionalThreshold
                        )

                        SwipeToDismissBox(
                            state = dismissState,
                            enableDismissFromStartToEnd = false,
                            enableDismissFromEndToStart = true,
                            gesturesEnabled = true,
                            onDismiss = {
                                todayWaterLogVM.deleteWaterLog(todayWLData.waterInfoList[index])
                            },
                            backgroundContent = {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(dimensionResource(R.dimen.container_padding)),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Red
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.DeleteForever,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        ) {
                            TodayWaterLogsUi(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(R.dimen.container_padding)),
                                todayWLData = todayWLData.waterInfoList[index],
                                onEditButtonClick = {
                                    editWaterLogListIndex = index
                                    showBottomSheet = !showBottomSheet
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

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