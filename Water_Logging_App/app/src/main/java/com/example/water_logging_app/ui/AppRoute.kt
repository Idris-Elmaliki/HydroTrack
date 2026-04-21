package com.example.water_logging_app.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.water_logging_app.R
import com.example.water_logging_app.ui._navigation.backHandler.isABlockedScreen
import com.example.water_logging_app.ui._navigation.navActions.AppNavActions
import com.example.water_logging_app.ui._navigation.routes.AppNavRoutes
import com.example.water_logging_app.ui.homepage.HomePageUiLayout
import com.example.water_logging_app.ui.signUpPage.SignUpPageLayout
import com.example.water_logging_app.ui.subscreens.LoadingScreen
import com.example.water_logging_app.ui.subscreens.animations.DotLoadingAnimation
import com.example.water_logging_app.ui.viewModel.SplashScreenViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

/*
* This is the main NavHost,
* it controls the navigation between the Sign-Up and HomePage!
*/
@Composable
fun AppRoute(
    modifier: Modifier = Modifier,
    loadingViewModel: SplashScreenViewModel,
    navController : NavHostController = rememberNavController()
) {
    val mainNav = AppNavActions(navController)

    val readyVM by loadingViewModel.isReady.collectAsStateWithLifecycle()
    var loadDotAnimation by rememberSaveable { mutableStateOf(true) }

    NavHost(
        navController = navController,
        startDestination = AppNavRoutes.LoadingScreen.name,
        modifier = modifier
    ) {
        // this composable is here to ensure that the SignUpScreen doesn't load for a frame while going to homepage
        composable(
            route = AppNavRoutes.LoadingScreen.name
        ) {
            LoadingScreen(
                modifier = modifier,
                currentUi = {
                    Spacer(modifier = Modifier.padding(bottom = dimensionResource(R.dimen.extra_container_padding)))

                    if(loadDotAnimation) {
                        DotLoadingAnimation()
                    }
                    else {
                        Spacer(modifier = Modifier.padding(
                            bottom = dimensionResource(R.dimen.text_padding))
                        )
                    }

                    Spacer(modifier = Modifier.padding(bottom = dimensionResource(R.dimen.extra_container_padding)))

                    RandomTextPrompt()
                }
            )
        }

        composable(
            route = AppNavRoutes.SignUpScreen.name
        ) {
            SignUpPageLayout(
                mainNavActions = mainNav,
                modifier = modifier
            )
        }

        composable(
            route = AppNavRoutes.HomePage.name,
            enterTransition = { fadeIn(tween(300)) },
            exitTransition = { fadeOut(tween(300)) }
        ) {
            HomePageUiLayout(
                modifier = modifier
            )
        }
    }

    LaunchedEffect(readyVM.isReady) {
        delay(2000L)
        if (readyVM.isReady) {
            loadDotAnimation = false

            if (readyVM.error == null) {
                navController.navigate(AppNavRoutes.HomePage.name) {
                    popUpTo(0)
                }
            } else {
                navController.navigate(AppNavRoutes.SignUpScreen.name) {
                    popUpTo(0)
                }
            }
        }
    }

    val entry by navController.currentBackStackEntryAsState()

    BackHandler(
        enabled = isABlockedScreen(entry?.destination),
    ) { }
}

@Composable
private fun RandomTextPrompt() {
    val textList : List<Int> = listOf(
        R.string.loading_text1,
        R.string.loading_text2,
        R.string.loading_text3,
        R.string.loading_text4,
    )

    val rand : Int = Random.nextInt(textList.size)

    Text(
        text = stringResource(textList[rand]),
        style = MaterialTheme.typography.bodyLarge.copy(
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .padding(dimensionResource(R.dimen.container_padding))
    )
}