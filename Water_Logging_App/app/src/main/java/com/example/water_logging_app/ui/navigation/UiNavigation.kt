package com.example.water_logging_app.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.water_logging_app.ui.mainpage.main_screens.HistoryScreen
import com.example.water_logging_app.ui.mainpage.main_screens.HomeScreen
import com.example.water_logging_app.ui.mainpage.main_screens.SettingScreen


const val TWEEN_AMOUNT = 550

enum class UiNavigationRoutesEnum() {

    LoadingScreen,

    MainPage,
        Home,
            AddWater,
        Setting,
        History,

}

/*
* I want the Water Logging Screen to not be apart of the main navigation screen
* It will be a pop-up screen once they user includes that overlays the entire screen!
 */

@Composable
fun UiNavigationRoutes(
    navController : NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = UiNavigationRoutesEnum.Home.name,
        modifier = modifier
    ) {
        /*
        * I will need to create a composable for the add water tab screen (will be making soon)
        * Must implement a stack nav with home screen being the parent.
        * It must include a popEnter && popExit transition!
        */
        composable(
            route = UiNavigationRoutesEnum.Home.name,
            arguments = listOf(/*this will be very useful for loading the users data!*/),
            deepLinks = listOf(/*I will need to implement this soon, will be useful for push notifications!*/),
            enterTransition = {
                when(initialState.destination.route) {
                    UiNavigationRoutesEnum.Setting.name -> {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(TWEEN_AMOUNT)
                        )
                    }
                    UiNavigationRoutesEnum.History.name -> {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(TWEEN_AMOUNT)
                        )
                    }
                    UiNavigationRoutesEnum.AddWater.name -> {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Down,
                            animationSpec = tween(TWEEN_AMOUNT)
                        )
                    }
                    else -> {
                        EnterTransition.None
                    }
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    UiNavigationRoutesEnum.Setting.name -> {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(TWEEN_AMOUNT)
                        )
                    }
                    UiNavigationRoutesEnum.History.name -> {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(TWEEN_AMOUNT)
                        )
                    }
                    UiNavigationRoutesEnum.AddWater.name -> {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Down,
                            animationSpec = tween(TWEEN_AMOUNT)
                        )
                    }
                    else -> {
                        ExitTransition.None
                    }
                }
            }
        ) {
            HomeScreen(
                onButtonClick = {
                    navController.navigate(UiNavigationRoutesEnum.AddWater.name)
                }
            )
        }

        waterGraph(navController)

        composable(
            route = UiNavigationRoutesEnum.Setting.name,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(TWEEN_AMOUNT)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(TWEEN_AMOUNT)
                )
            }
        ) {
            SettingScreen(
                modifier = modifier
            )
        }
        composable(
            route = UiNavigationRoutesEnum.History.name,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(TWEEN_AMOUNT)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(TWEEN_AMOUNT)
                )
            }
        ) {
            HistoryScreen(
                modifier = modifier
            )
        }
    }
}

// Nested Nav Graph function
fun NavGraphBuilder.waterGraph(navController : NavController) {
    navigation(
        route = "add_water",
        startDestination = UiNavigationRoutesEnum.AddWater.name,
    ) {
        composable(
            route = UiNavigationRoutesEnum.AddWater.name,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(TWEEN_AMOUNT)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(TWEEN_AMOUNT)
                )
            }
        ) {
        }
    }
}
