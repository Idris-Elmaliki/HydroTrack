package com.example.water_logging_app.ui.signUpPage.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.water_logging_app.R
import com.example.water_logging_app.preferenceData.domain.modelData.enums.UnitMeasurementType
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.LoadingScreen
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.PaginationSystemUi
import com.example.water_logging_app.ui.signUpPage.viewModels.parent.SignUpViewModel
import com.example.water_logging_app.ui.theme.Aquamarine
import com.example.water_logging_app.ui.theme.VibrantBlue
import com.example.water_logging_app.ui.theme.VividCobalt
import kotlinx.coroutines.delay
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

private suspend fun loadProgress(
    progress : Int,
    updateProgress: (Float) -> Unit
) {
    for (i in progress..100) {
        updateProgress(i.toFloat() / 100)
        delay(100)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDailyGoalScreen(
    modifier : Modifier,
    signUpVM : SignUpViewModel,
    currentNavAction : () -> Unit,
) {
    val signUpData by signUpVM.signUpData.collectAsStateWithLifecycle()

    // for the loading bar progress
    var showLoadingScreen by rememberSaveable { mutableStateOf(true) }
    var currentProgress by rememberSaveable { mutableFloatStateOf(0f) }

    var isLoaded by rememberSaveable { mutableStateOf(false) } // checked for the progress bar
    var isClicked by rememberSaveable { mutableStateOf(false) } // for the button click

    LaunchedEffect(isLoaded, isClicked) {
        signUpVM.calculateDailyGoal()

        if(isClicked)
            showLoadingScreen = false
    }

    val measurement =   if(signUpData.unitOfMeasurement == UnitMeasurementType.Metric.name) {
                            "ml"
                        }
                        else {
                            "oz"
                        }

    val pagerList = listOf(
        "Your daily recommended water intake is...!",
        "${signUpData.dailyGoal} $measurement"
    )

    if(showLoadingScreen) {
        LoadingScreen(
            modifier = modifier,
        ) {
            Spacer(
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.extra_container_padding))
            )

            LaunchedEffect(Unit) {
                loadProgress(
                    progress = (currentProgress * 100).toInt(), // this fixes the issue of the progress bar resetting
                    updateProgress = { progress ->
                        currentProgress = progress
                    }
                )

                isLoaded = true
            }

            LoadingBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = dimensionResource(R.dimen.extra_container_padding),
                        end = dimensionResource(R.dimen.extra_container_padding)
                    ),
                currentProgress = currentProgress
            )

            Spacer(
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.container_padding))
            )

            var dotCount by rememberSaveable { mutableIntStateOf(0) }

            LaunchedEffect(Unit) {
                while(!isClicked) {
                    delay(500L)
                    dotCount = if(dotCount == 3) 1 else dotCount + 1
                }
            }

            when(isLoaded) {
                false -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(
                                start = dimensionResource(R.dimen.container_padding),
                                end = dimensionResource(R.dimen.container_padding)
                            )
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.CalculatingRecommendedGoal) + ".".repeat(dotCount),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
                true -> {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = dimensionResource(R.dimen.container_padding),
                                end = dimensionResource(R.dimen.container_padding)
                            )
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(bottom = dimensionResource(R.dimen.container_padding)),
                            text = stringResource(R.string.GoalHasBeenCalculated),
                            style = MaterialTheme.typography.labelMedium
                        )
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = dimensionResource(R.dimen.container_padding),
                                    end = dimensionResource(R.dimen.container_padding)
                                )
                                .height(dimensionResource(R.dimen.ClickableCardHeight))
                                .shadow(
                                    elevation = dimensionResource(R.dimen.card_shadow_elevation),
                                    clip = true,
                                    spotColor = VividCobalt,
                                    ambientColor = VividCobalt,
                                    shape = MaterialTheme.shapes.medium
                                ),
                            colors = CardDefaults.cardColors(
                                containerColor = VividCobalt
                            ),
                            shape = MaterialTheme.shapes.medium,
                            onClick = {
                                isClicked = !isClicked
                            }
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Text(
                                    text = stringResource(R.string.Continue),
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        lineHeight = 0.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    else {
        val pagerState = rememberPagerState { pagerList.size }

        var showBottomSheet by rememberSaveable { mutableStateOf(false) }
        val modalBottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false
        )

        Scaffold(
            modifier = modifier,
            bottomBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PaginationSystemUi(
                        modifier = Modifier
                            .fillMaxWidth(),
                        pagerState = pagerState,
                        pagerList = pagerList
                    )

                    if(pagerState.currentPage == (pagerList.size - 1)) {
                        Row(
                            modifier = Modifier
                                .padding(top = dimensionResource(R.dimen.text_padding))
                                .background(MaterialTheme.colorScheme.background)
                                .fillMaxWidth()
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .weight(1f)
                            )
                            Row(
                                modifier = Modifier
                                    .padding(dimensionResource(R.dimen.text_padding))
                                    .clip(CircleShape)
                                    .clickable(
                                        onClick = {
                                            currentNavAction()
                                        }
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(R.string.Next),
                                    color = Aquamarine,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    tint = Aquamarine,
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                    else {
                        Spacer(
                            modifier = Modifier
                                .padding(top = dimensionResource(R.dimen.text_padding))
                        )
                    }
                }
            }
        ) { innerpadding ->
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerpadding)
                    .padding(
                        top = dimensionResource(R.dimen.container_padding),
                        bottom = dimensionResource(R.dimen.container_padding)
                    ),
                state = pagerState,
            ) {
                PagerDataUi(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.container_padding)),
                    pagerState = pagerState,
                    pagerList = pagerList,
                    onCardClick = {
                        showBottomSheet = !showBottomSheet
                    }
                )
            }
        }

        if(pagerState.currentPage == (pagerList.size - 1)) {
            val deviceWidth = LocalWindowInfo.current.containerSize.width
            val deviceLength = LocalWindowInfo.current.containerSize.height

            val party = Party(
                emitter = Emitter(duration = 500, TimeUnit.MILLISECONDS).max(500),
                position = Position.Absolute(
                    x = (deviceWidth.toFloat() / 2f),
                    y = deviceLength.toFloat() * 0.0625f
                ),
            )
            KonfettiView(
                modifier = Modifier
                    .fillMaxSize(),
                parties = listOf(party)
            )
        }


        if(showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier
                    .fillMaxWidth(),
                onDismissRequest = { showBottomSheet = !showBottomSheet },
                sheetState = modalBottomSheetState,
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = dimensionResource(R.dimen.container_padding)),
                    value = if(signUpData.dailyGoal != 0L) {
                        signUpData.dailyGoal.toString()
                    }
                    else {
                        ""
                    },
                    onValueChange = { data ->
                        if(data.isNotBlank())
                            signUpVM.updateDailyGoal(data)
                        else
                            signUpVM.updateDailyGoal("0")
                    },
                    trailingIcon = {
                        Text(
                            text = if(signUpData.unitOfMeasurement == UnitMeasurementType.Metric.name) {
                                "ml"
                            }
                            else {
                                "oz"
                            },
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    )
                )
            }
        }
    }
}

@Composable
private fun LoadingBar(
    modifier : Modifier,
    currentProgress : Float,
) {
    LinearProgressIndicator(
        modifier = modifier,
        progress = { currentProgress },
        color = VibrantBlue,
        trackColor = MaterialTheme.colorScheme.background,
        gapSize = 4.dp
    )
}

@Composable
private fun PagerDataUi(
    modifier : Modifier,
    pagerState : PagerState,
    pagerList : List<String>,
    onCardClick : () -> Unit
) {
    var showDailyGoalEditor by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage == (pagerList.size - 1)) {
            delay(3000L)
            showDailyGoalEditor = true
        } else {
            showDailyGoalEditor = false
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = pagerList[pagerState.currentPage],
            style = MaterialTheme.typography.titleMedium
        )
        if(showDailyGoalEditor) {
            Column(
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.container_padding)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .alpha(0.75f)
                        .padding(bottom = dimensionResource(R.dimen.mini_text_padding)),
                    text = stringResource(R.string.NotSatifisedWithYourGoal),
                    style = MaterialTheme.typography.labelMedium.copy(
                        lineHeight = 0.sp
                    ),
                )
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(R.dimen.text_padding),
                            end = dimensionResource(R.dimen.text_padding)
                        )
                        .height(dimensionResource(R.dimen.ClickableCardHeight))
                        .shadow(
                            elevation = dimensionResource(R.dimen.card_shadow_elevation),
                            clip = true,
                            spotColor = Aquamarine,
                            ambientColor = Aquamarine,
                            shape = MaterialTheme.shapes.medium
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = Aquamarine
                    ),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        onCardClick()
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(R.dimen.text_padding)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.EditDailyGoal),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                lineHeight = 0.sp
                            )
                        )
                    }
                }
            }
        }
    }
}