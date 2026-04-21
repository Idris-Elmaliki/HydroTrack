package com.example.water_logging_app.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.example.water_logging_app.splashScreen.viewModel.LoadingViewModel
import com.example.water_logging_app.ui._navigation.backHandler.isABlockedScreen
import com.example.water_logging_app.ui._navigation.navActions.AppNavActions
import com.example.water_logging_app.ui._navigation.routes.AppNavRoutes
import com.example.water_logging_app.ui.homepage.HomePageUiLayout
import com.example.water_logging_app.ui.signUpPage.SignUpPageLayout
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.LoadingScreen
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.animations.DotLoadingAnimation
import kotlin.random.Random

/*
* This is the main NavHost,
* it controls the navigation between the Sign-Up and HomePage!
*/
@Composable
fun AppRoute(
    modifier: Modifier = Modifier,
    loadingViewModel: LoadingViewModel,
    navController : NavHostController = rememberNavController()
) {
    val mainNav = AppNavActions(navController)

    val readyVM by loadingViewModel.isReady.collectAsStateWithLifecycle()

    LaunchedEffect(readyVM.isReady) {
        if (readyVM.isReady) {
            if (readyVM.error == null) {
                navController.navigate(AppNavRoutes.HomePage.name)
            } else {
                navController.navigate(AppNavRoutes.SignUpScreen.name)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppNavRoutes.LoadingScreen.name,
        modifier = modifier
    ) {


        composable(
            route = AppNavRoutes.LoadingScreen.name
        ) {}


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
        ) {
            HomePageUiLayout(
                modifier = modifier
            )
        }
    }

    AnimatedContent(
        targetState = readyVM.isReady,
        transitionSpec = {
            fadeIn(tween(400)) togetherWith fadeOut(tween(400))
        },
        modifier = modifier,
        label = "LoadingAnimation"
    ) { ready ->
        if (!ready) {
            LoadingScreen(
                modifier = modifier,
                currentUi = {
                    Spacer(modifier = Modifier.padding(bottom = dimensionResource(R.dimen.extra_container_padding)))

                    DotLoadingAnimation()

                    Spacer(modifier = Modifier.padding(bottom = dimensionResource(R.dimen.extra_container_padding)))

                    RandomTextPrompt()
                }
            )
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