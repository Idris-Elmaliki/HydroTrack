package com.example.water_logging_app.ui._navigation.navGraphs

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.water_logging_app.ui._navigation.navActions.AppNavActions
import com.example.water_logging_app.ui._navigation.navActions.SignUpPageActions
import com.example.water_logging_app.ui._navigation.routes.SignUpPageRoutes
import com.example.water_logging_app.ui.signUpPage.screens.MainLoadingScreenUi

fun NavGraphBuilder.signUpGraph(
    modifier: Modifier,
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
            MainLoadingScreenUi(
                modifier = modifier
            )
        }

        // insert the ui (will complete soon!)
    }
}