package com.example.model

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

enum class CardStyle(
    val title: String,
    val subtitle: String,
    val badge: String
) {
    GLASSMORPHISM(
        title = "Glass Prism",
        subtitle = "Frosted glass refraction with iridescent specular border",
        badge = "Frosted Glass"
    ),
    CARD_FLIP_3D(
        title = "3D Hologram Flip",
        subtitle = "Interactive 3D gyroscopic flip with deep telemetry on reverse",
        badge = "3D Flip"
    ),
    CURTAIN_REVEAL(
        title = "Curtain Reveal",
        subtitle = "Silky pleated theatrical drapery split with physical slide action",
        badge = "Curtain Pull"
    ),
    PARALLAX_NEBULA(
        title = "Parallax Nebula",
        subtitle = "Multi-depth spatial layering responsive to finger touch & drag",
        badge = "Parallax 3D"
    ),
    TITANIUM_LUXE(
        title = "Titanium Luxe",
        subtitle = "Brushed cyber-alloy finish with gold laser foil and audio visualizer",
        badge = "Titanium"
    )
}

enum class AppColorTheme(
    val title: String,
    val primary: Color,
    val secondary: Color,
    val accent: Color,
    val glowColor: Color,
    val darkBackground: Color,
    val lightBackground: Color,
    val cardSurfaceDark: Color,
    val cardSurfaceLight: Color
) {
    CYBER_NEON(
        title = "Cyber Neon",
        primary = Color(0xFF00F5D4),
        secondary = Color(0xFF7928CA),
        accent = Color(0xFFFF0080),
        glowColor = Color(0x6600F5D4),
        darkBackground = Color(0xFF0A0D14),
        lightBackground = Color(0xFFF1F5F9),
        cardSurfaceDark = Color(0x33101524),
        cardSurfaceLight = Color(0xCCFFFFFF)
    ),
    SOLAR_FLARE(
        title = "Solar Flare",
        primary = Color(0xFFFF5E62),
        secondary = Color(0xFFFF9966),
        accent = Color(0xFFFFD166),
        glowColor = Color(0x66FF5E62),
        darkBackground = Color(0xFF140A0D),
        lightBackground = Color(0xFFFFF7ED),
        cardSurfaceDark = Color(0x33261217),
        cardSurfaceLight = Color(0xCCFFFFFF)
    ),
    EMERALD_PRISM(
        title = "Emerald Jade",
        primary = Color(0xFF06D6A0),
        secondary = Color(0xFF118AB2),
        accent = Color(0xFF073B4C),
        glowColor = Color(0x6606D6A0),
        darkBackground = Color(0xFF071411),
        lightBackground = Color(0xFFF0FDF4),
        cardSurfaceDark = Color(0x330D2922),
        cardSurfaceLight = Color(0xCCFFFFFF)
    ),
    AMETHYST_DREAM(
        title = "Amethyst Bloom",
        primary = Color(0xFFB388FF),
        secondary = Color(0xFFFF4081),
        accent = Color(0xFF7C4DFF),
        glowColor = Color(0x66B388FF),
        darkBackground = Color(0xFF12081C),
        lightBackground = Color(0xFFFAF5FF),
        cardSurfaceDark = Color(0x33220F36),
        cardSurfaceLight = Color(0xCCFFFFFF)
    ),
    MONOCHROME_LUXE(
        title = "Monochrome",
        primary = Color(0xFFE2E8F0),
        secondary = Color(0xFF94A3B8),
        accent = Color(0xFF38BDF8),
        glowColor = Color(0x44CBD5E1),
        darkBackground = Color(0xFF0B0C0E),
        lightBackground = Color(0xFFF8FAFC),
        cardSurfaceDark = Color(0x331E2024),
        cardSurfaceLight = Color(0xCCFFFFFF)
    );

    fun gradient(isDark: Boolean): Brush {
        return Brush.linearGradient(
            colors = listOf(primary, secondary, accent)
        )
    }

    fun subtleGradient(isDark: Boolean): Brush {
        return Brush.linearGradient(
            colors = listOf(
                primary.copy(alpha = if (isDark) 0.35f else 0.2f),
                secondary.copy(alpha = if (isDark) 0.25f else 0.15f),
                accent.copy(alpha = if (isDark) 0.35f else 0.2f)
            )
        )
    }
}

data class ThemeSettings(
    val isDark: Boolean = true,
    val activeTheme: AppColorTheme = AppColorTheme.CYBER_NEON,
    val particlesSpeed: Float = 1.0f,
    val particlesDensity: Int = 45,
    val tiltSensitivity: Float = 1.2f,
    val hapticsEnabled: Boolean = true,
    val autoAnimateShine: Boolean = true
)
