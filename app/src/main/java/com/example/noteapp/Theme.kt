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
val md_theme_dark_primary = Color(0xFFFF9800)
val md_theme_dark_secondary = Color(0xFF03DAC6)
val md_theme_dark_background = Color(0xFF121212)
val md_theme_dark_surface = Color(0xFF1F1F1F)

// Define colors for light theme
// Màu chính (Primary)
val md_theme_light_primary = Color(0xFF4CAF50)  // Xanh lá cây dịu (Green)

// Màu phụ (Secondary)
val md_theme_light_secondary = Color(0xFFFF9800)  // Cam sáng (Orange)

// Màu nền (Background)
val md_theme_light_background = Color(0xFFF1F8E9)  // Xanh nhạt nền (Light green background)

// Màu bề mặt (Surface)
val md_theme_light_surface = Color(0xFFFFFFFF)  // Trắng (White)

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
