package com.example.water_logging_app.ui._navigation.navGraphs

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.water_logging_app.rememberActivity
import com.example.water_logging_app.ui._navigation.routes.HomePageRoutes
import com.example.water_logging_app.ui.homepage.homescreens.HistoryScreenUi
import com.example.water_logging_app.ui.homepage.homescreens.HomeScreenUi

const val TWEEN_AMOUNT = 550
fun NavGraphBuilder.homeGraph(
    modifier : Modifier,
    isProfileClick : () -> Unit
) {
    navigation(
        route = "home_graph",
        startDestination = HomePageRoutes.Home.name
    ) {
        composable(
            route = HomePageRoutes.Home.name,
            arguments = listOf(/*this will be very useful for loading the user's data!*/), // f that, dagger hilt >>>
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
            HomeScreenUi(
                modifier = modifier,
                todayWaterLogVM = hiltViewModel(rememberActivity()),
                userDataVM = hiltViewModel(rememberActivity()),
                dailyStreakVM = hiltViewModel(rememberActivity()),
                isProfileClick = isProfileClick
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
            HistoryScreenUi(
                modifier = modifier,
                allWaterLogsVM = hiltViewModel(rememberActivity()),
                userDataVM = hiltViewModel(rememberActivity())
            )
        }
    }
}