package com.example.water_logging_app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.water_logging_app.ui.theme.BrilliantAzure

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,

    surfaceVariant = BrilliantAzure
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

    surfaceVariant = BrilliantAzure,
    onSurfaceVariant = Color.White,

    /*
    onPrimary = TODO(),
    primaryContainer = TODO(),
    onPrimaryContainer = TODO(),
    inversePrimary = TODO(),
    onSecondary = TODO(),
    secondaryContainer = TODO(),
    onSecondaryContainer = TODO(),
    onTertiary = TODO(),
    tertiaryContainer = TODO(),
    onTertiaryContainer = TODO(),
    background = TODO(),
    onBackground = TODO(),
    surface = TODO(),
    onSurface = TODO(),
    surfaceVariant = TODO(),
    onSurfaceVariant = TODO(),
    surfaceTint = TODO(),
    inverseSurface = TODO(),
    inverseOnSurface = TODO(),
    error = TODO(),
    onError = TODO(),
    errorContainer = TODO(),
    onErrorContainer = TODO(),
    outline = TODO(),
    outlineVariant = TODO(),
    scrim = TODO(),
    surfaceBright = TODO(),
    surfaceContainer = TODO(),
    surfaceContainerHigh = TODO(),
    surfaceContainerHighest = TODO(),
    surfaceContainerLow = TODO(),
    surfaceContainerLowest = TODO(),
    surfaceDim = TODO()
    */

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun Water_Logging_AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}