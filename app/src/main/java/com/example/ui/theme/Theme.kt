package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.model.AppColorTheme

@Composable
fun AuraTheme(
    activeTheme: AppColorTheme = AppColorTheme.CYBER_NEON,
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = activeTheme.primary,
            secondary = activeTheme.secondary,
            tertiary = activeTheme.accent,
            background = activeTheme.darkBackground,
            surface = activeTheme.cardSurfaceDark,
            onPrimary = Color(0xFF090D16),
            onSecondary = Color.White,
            onBackground = Color(0xFFF8FAFC),
            onSurface = Color(0xFFF8FAFC)
        )
    } else {
        lightColorScheme(
            primary = activeTheme.primary,
            secondary = activeTheme.secondary,
            tertiary = activeTheme.accent,
            background = activeTheme.lightBackground,
            surface = activeTheme.cardSurfaceLight,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFF0F172A),
            onSurface = Color(0xFF0F172A)
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
