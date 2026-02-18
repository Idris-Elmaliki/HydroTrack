package com.example.water_logging_app.ui._navigation.navGraphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.water_logging_app.ui._navigation.routes.InfoPageRoutes

fun NavGraphBuilder.infoScreensGraph(
    navController: NavHostController,
) {
    navigation(
        route = "InfoPages",
        startDestination = InfoPageRoutes.InfoScreen1.name
    ) {
        composable(
            route = InfoPageRoutes.InfoScreen1.name
        ) {

        }

        composable(
            route = InfoPageRoutes.InfoScreen2.name
        ) {

        }

        composable(
            route = InfoPageRoutes.InfoScreen3.name
        ) {

        }
    }
}
