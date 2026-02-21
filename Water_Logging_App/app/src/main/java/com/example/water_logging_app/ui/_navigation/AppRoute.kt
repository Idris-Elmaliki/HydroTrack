package com.example.water_logging_app.ui._navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.water_logging_app.ui._navigation.navActions.AppNavActions
import com.example.water_logging_app.ui._navigation.routes.AppNavRoutes
import com.example.water_logging_app.ui.homepage.HomePageUiLayout
import com.example.water_logging_app.ui.signUpPage.SignUpPageLayout
import com.example.water_logging_app.ui._navigation.backHandler.isABlockedScreen


/*
* This is the main NavHost,
* it controls the navigation between the Sign-Up and HomePage!
*/
@Composable
fun AppRoute(
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

    val entry by navController.currentBackStackEntryAsState()

    BackHandler(
        enabled = isABlockedScreen(entry?.destination),
    ) { }
}