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
import com.example.water_logging_app.ui.signUpPage.screens.MainLoadingScreenUi
import com.example.water_logging_app.ui.signUpPage.viewModel.SignUpViewModel

fun NavGraphBuilder.signUpGraph(
    modifier: Modifier, // we just passed in .fillMaxSize()
    navController: NavHostController,
    mainNavActions : AppNavActions
) {
    val actions = SignUpPageActions(navController)

    navigation(
        route = "sign_up",
        startDestination = SignUpPageRoutes.MainLoadingScreen.name,
    ) {
        composable(
            route = SignUpPageRoutes.MainLoadingScreen.name
        ) {
            val signUpViewModel : SignUpViewModel = hiltViewModel(rememberActivity())

            MainLoadingScreenUi(
                modifier = modifier,
                signUpViewModel = signUpViewModel,
                mainNavAction = { mainNavActions.navigateToHomePage() },
                currentNavAction = { actions.navigateToBeginSignUpPage() }
            )
        }

        composable(
            route = SignUpPageRoutes.BeginSignUpPage.name
        ) {
            BeginSignUpPageUi(
                modifier = modifier,
            ) {
                actions.navigateToInfoPage1()
            }
        }

        composable(
            route = SignUpPageRoutes.InfoScreens.name
        ) {
            InfoScreenUi(
                modifier = modifier
            ) {
                actions.navigateToUsersNamePage()
            }
        }

        composable(
            route = SignUpPageRoutes.GetUsersNamePage.name
        ) {

        }

        composable(
            route = SignUpPageRoutes.GetUserDataPage.name
        ) {

        }

        composable(
            route = SignUpPageRoutes.LoadingScreen.name
        ) {

        }

        // insert the ui (will complete soon!)
    }
}