package com.example.water_logging_app.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.water_logging_app.ui.screens.HistoryScreen
import com.example.water_logging_app.ui.screens.HomeScreen
import com.example.water_logging_app.ui.screens.SettingScreen

enum class UiNavigationRoutesEnum() {
    Home,
    Setting,
    History,
    AddWater
}

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
                slideIntoContainer(
                    towards = if(initialState.destination.route == UiNavigationRoutesEnum.Setting.name) {
                        AnimatedContentTransitionScope.SlideDirection.Left
                    }
                    else {
                        AnimatedContentTransitionScope.SlideDirection.Right
                    }
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = if(targetState.destination.route == UiNavigationRoutesEnum.Setting.name) {
                        AnimatedContentTransitionScope.SlideDirection.Right
                    } else {
                        AnimatedContentTransitionScope.SlideDirection.Left
                    }
                )
            }
        ) {
            HomeScreen(
                onButtonClick = {
                    // navController.navigate(UiNavigationRoutesEnum.AddWater.name)
                }
            )
        }
        composable(
            route = UiNavigationRoutesEnum.Setting.name,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
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
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }
        ) {
            HistoryScreen(
                modifier = modifier
            )
        }
    }
}

