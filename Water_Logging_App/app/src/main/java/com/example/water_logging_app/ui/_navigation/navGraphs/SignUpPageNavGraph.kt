package com.example.water_logging_app.ui._navigation.navGraphs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.water_logging_app.ui._navigation.actions.AppNavActions
import com.example.water_logging_app.ui._navigation.actions.SignUpPageActions

@Composable
fun NavGraphBuilder.SignUpGraph(
    modifier: Modifier,
    navController: NavHostController,
    mainNavActions : AppNavActions
) {
    val actions = rememberSaveable(navController) {
        SignUpPageActions(navController)
    }

    NavHost(
        navController = navController,
        startDestination = "sign_up",
        modifier = modifier
    ) {
        // insert the ui (will complete soon!)
    }
}