package com.example.water_logging_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.water_logging_app.ui.MainAppFunc
import com.example.water_logging_app.ui.theme.Water_Logging_AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Water_Logging_AppTheme {
                MainAppFunc(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}
