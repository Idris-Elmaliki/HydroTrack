package com.example.water_logging_app.ui.signUpPage.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.LoadingScreen
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.PaginationSystemUi
import com.example.water_logging_app.ui.signUpPage.viewModels.parent.SignUpViewModel
import kotlinx.coroutines.delay
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

private suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(100)
    }
}

@Composable
fun UserDailyGoalScreen(
    modifier : Modifier,
    signUpVM : SignUpViewModel,
    currentNavAction : () -> Unit,
) {
    var showLoadingScreen by rememberSaveable { mutableStateOf(true) }

    var currentProgress by rememberSaveable { mutableFloatStateOf(0f) }
    var isLoaded by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isLoaded) {
        signUpVM.calculateDailyGoal()

        if(isLoaded)
            showLoadingScreen = false
    }

    if(showLoadingScreen) {
        LoadingScreen(
            modifier = modifier,
        ) {
            Spacer(
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.extra_container_padding))
            )

            LaunchedEffect(Unit) {
                loadProgress { progress ->
                    currentProgress = progress
                }
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
                    text = stringResource(R.string.CalculatingRecommendedGoal),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
    else {
        val pagerList = listOf(
            "Your daily recommended water intake has been calculated!",
            "Your daily recommended water intake is...!",
            "${signUpVM.signUpData.collectAsStateWithLifecycle().value.dailyGoal}"
        )

        val pagerState = rememberPagerState { pagerList.size }

        Scaffold(
            modifier = modifier,
            bottomBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PaginationSystemUi(
                        modifier = Modifier
                            .padding(
                                bottom = dimensionResource(R.dimen.container_padding)
                            )
                            .navigationBarsPadding()
                            .fillMaxWidth(),
                        pagerState = pagerState,
                        pagerList = pagerList
                    )
                }
            }
        ) { innerpadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerpadding)
                    .padding(
                        top = dimensionResource(R.dimen.container_padding),
                        bottom = dimensionResource(R.dimen.container_padding)
                    )
            ) {
                HorizontalPager(
                    state = pagerState,
                ) {
                    LoadPagerDataUi(
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.container_padding)),
                        signUpVM = signUpVM,
                        pagerState = pagerState,
                        pagerList = pagerList
                    )
                }
            }
        }

        if(pagerState.currentPage == 2) {
            val party = Party(emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100))
            KonfettiView(
                modifier = Modifier.fillMaxSize(),
                parties = listOf(party)
            )
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
        trackColor = MaterialTheme.colorScheme.background,
        gapSize = 4.dp
    )
}

@Composable
private fun LoadPagerDataUi(
    modifier : Modifier,
    signUpVM : SignUpViewModel,
    pagerState : PagerState,
    pagerList : List<String>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = pagerList[pagerState.currentPage],
            style = MaterialTheme.typography.labelMedium
        )
    }
}