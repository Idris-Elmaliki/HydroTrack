package com.example.water_logging_app.ui._navigation.navGraphs

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.water_logging_app.ui._navigation.routes.HomePageRoutes
import com.example.water_logging_app.ui.homepage.homescreens.HistoryScreen
import com.example.water_logging_app.ui.homepage.homescreens.HomeScreen
import com.example.water_logging_app.ui.mainpage.mainScreens.SettingScreen


const val TWEEN_AMOUNT = 550
fun NavGraphBuilder.homeGraph(
    navController : NavHostController,
    modifier : Modifier = Modifier
) {
    navigation(
        route = "home_graph",
        startDestination = HomePageRoutes.Home.name
    ) {
        composable(
            route = HomePageRoutes.Home.name,
            arguments = listOf(/*this will be very useful for loading the users data!*/), // f that, dagger hilt >>>
            deepLinks = listOf(/*I will need to implement this soon, will be useful for push notifications!*/),
            enterTransition = {
                when (initialState.destination.route) {
                    HomePageRoutes.Setting.name -> {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(TWEEN_AMOUNT)
                        )
                    }
                    HomePageRoutes.History.name -> {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(TWEEN_AMOUNT)
                        )
                    }
                    else -> {
                        EnterTransition.None
                    }
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    HomePageRoutes.Setting.name -> {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(TWEEN_AMOUNT)
                        )
                    }

                    HomePageRoutes.History.name -> {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(TWEEN_AMOUNT)
                        )
                    }

                    else -> {
                        ExitTransition.None
                    }
                }
            }
        ) {
            HomeScreen() {} // I will either have a separate page, OR have a modal (either or) [this is for the insert Water screen]
        }
        composable(
            route = HomePageRoutes.Setting.name,
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
            route = HomePageRoutes.History.name,
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