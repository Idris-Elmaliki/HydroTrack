package com.example.water_logging_app.ui.signUpPage

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.water_logging_app.ui._navigation.navActions.AppNavActions
import com.example.water_logging_app.ui._navigation.navGraphs.signUpGraph

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
}