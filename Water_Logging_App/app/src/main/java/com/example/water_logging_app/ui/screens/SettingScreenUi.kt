package com.example.water_logging_app.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.water_logging_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    modifier : Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.Settings),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                },
                modifier = modifier
            )
        }
    ) { innerpadding ->

    }
}

