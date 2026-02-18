package com.example.water_logging_app.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.water_logging_app.R.font

val averiaSerifLibre = FontFamily(
    Font(font.averia_serif_libre_regular, weight = FontWeight.Normal),
    Font(font.averia_serif_libre_bold, weight = FontWeight.Bold),
    Font(font.averia_serif_libre_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(font.averia_serif_libre_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(font.averia_serif_libre_light, weight = FontWeight.Light),
    Font(font.averia_serif_libre_light_italic, weight = FontWeight.Light, style = FontStyle.Italic)
)

val poppins = FontFamily(
    Font(font.poppins_regular, weight = FontWeight.Normal),
    Font(font.poppins_bold, weight = FontWeight.Bold),
)