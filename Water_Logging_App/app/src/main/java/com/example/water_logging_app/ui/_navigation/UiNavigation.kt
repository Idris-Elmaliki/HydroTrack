package com.example.water_logging_app.ui._navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.water_logging_app.ui._navigation.navActions.AppNavActions
import com.example.water_logging_app.ui._navigation.routes.AppNavRoutes
import com.example.water_logging_app.ui.homepage.HomePageUiLayout
import com.example.water_logging_app.ui.signUpPage.SignUpPageLayout


/*
* This is the main NavHost,
* it controls the navigation between the Sign-Up and HomePage!
*/
@Composable
fun UiNavigationRoutes(
    modifier: Modifier = Modifier,
    navController : NavHostController = rememberNavController()
) {
    val mainNav = remember(navController) {
        AppNavActions(navController)
    }

    NavHost(
        navController = navController,
        startDestination = AppNavRoutes.SignUpScreen.name,
        modifier = modifier
    ) {
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
}