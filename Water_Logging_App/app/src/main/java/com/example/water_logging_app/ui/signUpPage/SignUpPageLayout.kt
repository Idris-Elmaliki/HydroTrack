package com.example.water_logging_app.ui.signUpPage

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.water_logging_app.ui._navigation.navActions.AppNavActions
import com.example.water_logging_app.ui._navigation.navGraphs.signUpGraph
import com.example.water_logging_app.ui._navigation.routes.SignUpPageRoutes
import com.example.water_logging_app.ui._navigation.backHandler.isABlockedScreen

@Composable
fun SignUpPageLayout(
    mainNavActions: AppNavActions,
    modifier: Modifier
) {
    val signUpNavController : NavHostController = rememberNavController()

    Surface(
        modifier = modifier
    ) {
        NavHost(
            navController = signUpNavController,
            startDestination = "sign_up",
        ) {
            signUpGraph(
                modifier = modifier,
                navController = signUpNavController,
                mainNavActions = mainNavActions,
            )
        }
    }

    val entry by signUpNavController.currentBackStackEntryAsState()

    BackHandler(
        enabled = isABlockedScreen(entry?.destination),
    ) { }
}