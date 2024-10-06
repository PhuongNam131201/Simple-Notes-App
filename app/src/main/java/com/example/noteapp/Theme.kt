package com.example.noteapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle

// Define colors for dark theme
val md_theme_dark_primary = Color(0xFFBB86FC)
val md_theme_dark_secondary = Color(0xFF03DAC6)
val md_theme_dark_background = Color(0xFF121212)
val md_theme_dark_surface = Color(0xFF1F1F1F)

// Define colors for light theme
val md_theme_light_primary = Color(0xFF6200EE)
val md_theme_light_secondary = Color(0xFF03DAC6)
val md_theme_light_background = Color(0xFFFFFFFF)
val md_theme_light_surface = Color(0xFFFAFAFA)

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = Color.White,
    secondary = md_theme_dark_secondary,
    onSecondary = Color.White,
    background = md_theme_dark_background,
    onBackground = Color.White,
    surface = md_theme_dark_surface,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = Color.White,
    secondary = md_theme_light_secondary,
    onSecondary = Color.Black,
    background = md_theme_light_background,
    onBackground = Color.Black,
    surface = md_theme_light_surface,
    onSurface = Color.Black
)

@Composable
fun NoteAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Typography definitions
private val Typography = Typography(
    titleLarge = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start
    ),
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start
    )
)
