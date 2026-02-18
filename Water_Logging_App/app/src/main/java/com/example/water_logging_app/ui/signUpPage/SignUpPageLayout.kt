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
import com.example.water_logging_app.ui.signUpPage.viewModel.BackHandlerViewModel

@Composable
fun SignUpPageLayout(
    mainNavActions: AppNavActions,
    viewModel : BackHandlerViewModel,
    modifier: Modifier
) {
    val signUpNavController : NavHostController = rememberNavController()

    // what these lines of code create is a global Back-handler for all the screens I desire for the sign-up pages!
    // This ensures that I don't need to include Back-handler in each screen's main func I want it for

    BackHandler(enabled = true) { viewModel.onBackPressed(signUpNavController)}

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