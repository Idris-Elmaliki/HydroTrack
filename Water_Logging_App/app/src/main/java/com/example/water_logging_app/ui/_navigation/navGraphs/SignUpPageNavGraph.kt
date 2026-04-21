package com.example.water_logging_app.ui._navigation.navGraphs

import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.water_logging_app.rememberActivity
import com.example.water_logging_app.ui._navigation.navActions.AppNavActions
import com.example.water_logging_app.ui._navigation.navActions.SignUpPageActions
import com.example.water_logging_app.ui._navigation.routes.SignUpPageRoutes
import com.example.water_logging_app.ui.signUpPage.screens.BeginSignUpPageUi
import com.example.water_logging_app.ui.signUpPage.screens.InfoScreenUi
import com.example.water_logging_app.ui.signUpPage.screens.SaveUserDataLoadingScreen
import com.example.water_logging_app.ui.signUpPage.screens.UserDailyGoalScreen
import com.example.water_logging_app.ui.signUpPage.screens.UserDataPageUi
import com.example.water_logging_app.ui.signUpPage.screens.UserActivityLevelPageUi
import com.example.water_logging_app.ui.signUpPage.screens.UsersProfilePageUi
import com.example.water_logging_app.ui.signUpPage.viewModels.parent.SignUpViewModel

fun NavGraphBuilder.signUpGraph(
    modifier: Modifier, // we just passed in .fillMaxSize()
    navController: NavHostController,
    mainNavActions : AppNavActions
) {
    val actions = SignUpPageActions(navController)

    navigation(
        route = "sign_up",
        startDestination = SignUpPageRoutes.BeginSignUpPage.name
    ) {
        composable(
            route = SignUpPageRoutes.BeginSignUpPage.name
        ) {
            BeginSignUpPageUi(
                modifier = modifier,
            ) {
                actions.navigateToInfoPage()
            }
        }

        composable(
            route = SignUpPageRoutes.InfoScreens.name
        ) {
            InfoScreenUi(
                modifier = modifier
            ) {
                actions.navigateToUserDetailsPage()
            }
        }

        // Updated the navigation routes for the sign-up page!
        composable(
            route = SignUpPageRoutes.GetUserDataPage.name
        ) {
            UserDataPageUi(
                modifier = modifier,
                signUpVM = hiltViewModel<SignUpViewModel>(rememberActivity()),
                currentNavAction = { actions.navigateToUsersActivityLevelPage() }
            )
        }

        composable(
            route = SignUpPageRoutes.GetUsersActivityLevelPage.name
        ) {
            UserActivityLevelPageUi(
                modifier = modifier,
                signUpVM = hiltViewModel(rememberActivity()),
                previousNavAction = { actions.navigateToUserDetailsPage() },
                currentNavAction = { actions.navigateToUsersGoalsPage() }
            )
        }

        // will need to fix the nav path from here:
        composable(
            route = SignUpPageRoutes.GetUsersGoalPage.name
        ) {
            UserDailyGoalScreen(
                modifier = modifier,
                signUpVM = hiltViewModel(rememberActivity()),
                currentNavAction = { actions.navigateToUsersProfilePage() }
            )
        }

        composable(
            route = SignUpPageRoutes.GetUsersProfilePage.name
        ) {
            UsersProfilePageUi(
                modifier = modifier,
                signUpVM = hiltViewModel(rememberActivity()),
                profilePicVM = hiltViewModel(rememberActivity()),
                currentNavAction = { actions.navigateToLoadingPage() },
            )
        }

        composable(
            route = SignUpPageRoutes.LoadingScreen.name
        ) {
            SaveUserDataLoadingScreen(
                modifier = modifier,
                signUpVM = hiltViewModel(rememberActivity()),
                profilePicVM = hiltViewModel(rememberActivity()),
                toHomePage = { mainNavActions.navigateToHomePage() }
            )
        }

        // insert the ui (will complete soon!)
    }
}