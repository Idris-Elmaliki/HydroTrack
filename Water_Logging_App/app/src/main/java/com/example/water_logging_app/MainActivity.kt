package com.example.water_logging_app

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
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
import com.example.water_logging_app.ui.homepage.viewModel.home.DailyStreakViewModel
import com.example.water_logging_app.ui.homepage.viewModel.home.WaterLogViewModel
import com.example.water_logging_app.ui.theme.Water_Logging_AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loadingModel by viewModels<SplashScreenViewModel>()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

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

    // this ensures that the uploading data functions get called on onStop!

    // This allows us to get the references of the VMs from hilt
    private val waterLogVM: WaterLogViewModel by viewModels()
    private val dailyStreakVM: DailyStreakViewModel by viewModels()

    override fun onStop() {
        super.onStop()

        waterLogVM.insertWaterLogData()
        dailyStreakVM.uploadDailyStreakData()
    }
}

// for hilt viewModels
// This ensures we get the same instance of a hiltViewModel across different screens!
@Composable
fun rememberActivity() : ComponentActivity {
    val context = LocalContext.current
    return remember(context) { context as ComponentActivity }
}