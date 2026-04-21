package com.example.water_logging_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.water_logging_app.ui.viewModel.SplashScreenViewModel
import com.example.water_logging_app.ui.AppRoute
import com.example.water_logging_app.ui.theme.Water_Logging_AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loadingModel by viewModels<SplashScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            !loadingModel.isReady.value.isReady
        }

        enableEdgeToEdge()
        setContent {
            Water_Logging_AppTheme {
                AppRoute(
                    loadingViewModel = loadingModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

// for hilt viewModels
// This ensures we get the same instance of a hiltViewModel across different screens!
@Composable
fun rememberActivity() : ComponentActivity {
    val context = LocalContext.current
    return remember(context) { context as ComponentActivity }
}