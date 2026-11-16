package com.example.auraprofile.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

enum class AuraCardStyle(val displayName: String) {
    GLASSMORPHISM("Glass Prism"),
    CARD_FLIP_3D("3D Hologram Flip"),
    CURTAIN_REVEAL("Curtain Reveal"),
    PARALLAX_NEBULA("Parallax Nebula"),
    TITANIUM_LUXE("Titanium Luxe")
}

data class AuraCardColors(
    val primary: Color,
    val secondary: Color,
    val accent: Color,
    val glowColor: Color,
    val cardBackground: Color,
    val textColor: Color,
    val textSecondaryColor: Color,
    val statusColor: Color
) {
    fun gradientBrush(): Brush = Brush.linearGradient(listOf(primary, secondary, accent))

    fun borderBrush(isDark: Boolean): Brush = Brush.linearGradient(
        listOf(
            primary.copy(alpha = if (isDark) 0.85f else 0.9f),
            secondary.copy(alpha = 0.5f),
            accent.copy(alpha = if (isDark) 0.85f else 0.9f)
        )
    )

    companion object {
        fun cyberNeon(isDark: Boolean = true): AuraCardColors = AuraCardColors(
            primary = Color(0xFF00F5D4),
            secondary = Color(0xFF7928CA),
            accent = Color(0xFFFF0080),
            glowColor = Color(0x6600F5D4),
            cardBackground = if (isDark) Color(0xF2101626) else Color(0xF5FFFFFF),
            textColor = if (isDark) Color(0xFFF8FAFC) else Color(0xFF0F172A),
            textSecondaryColor = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B),
            statusColor = Color(0xFF00F5D4)
        )

        fun solarFlare(isDark: Boolean = true): AuraCardColors = AuraCardColors(
            primary = Color(0xFFFF5E62),
            secondary = Color(0xFFFF9966),
            accent = Color(0xFFFFD166),
            glowColor = Color(0x66FF5E62),
            cardBackground = if (isDark) Color(0xF21A0F14) else Color(0xF5FFFFFF),
            textColor = if (isDark) Color(0xFFF8FAFC) else Color(0xFF0F172A),
            textSecondaryColor = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B),
            statusColor = Color(0xFFFF5E62)
        )

        fun emeraldJade(isDark: Boolean = true): AuraCardColors = AuraCardColors(
            primary = Color(0xFF06D6A0),
            secondary = Color(0xFF118AB2),
            accent = Color(0xFF073B4C),
            glowColor = Color(0x6606D6A0),
            cardBackground = if (isDark) Color(0xF20B1A16) else Color(0xF5FFFFFF),
            textColor = if (isDark) Color(0xFFF8FAFC) else Color(0xFF0F172A),
            textSecondaryColor = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B),
            statusColor = Color(0xFF06D6A0)
        )

        fun amethystBloom(isDark: Boolean = true): AuraCardColors = AuraCardColors(
            primary = Color(0xFFB388FF),
            secondary = Color(0xFFFF4081),
            accent = Color(0xFF7C4DFF),
            glowColor = Color(0x66B388FF),
            cardBackground = if (isDark) Color(0xF2160C26) else Color(0xF5FFFFFF),
            textColor = if (isDark) Color(0xFFF8FAFC) else Color(0xFF0F172A),
            textSecondaryColor = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B),
            statusColor = Color(0xFFB388FF)
        )

        fun titaniumLuxe(isDark: Boolean = true): AuraCardColors = AuraCardColors(
            primary = Color(0xFFFFD166),
            secondary = Color(0xFFE2E8F0),
            accent = Color(0xFF94A3B8),
            glowColor = Color(0x44FFD166),
            cardBackground = if (isDark) Color(0xF212151D) else Color(0xF5FFFFFF),
            textColor = if (isDark) Color(0xFFF8FAFC) else Color(0xFF0F172A),
            textSecondaryColor = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B),
            statusColor = Color(0xFFFFD166)
        )

        fun default(isDark: Boolean = true): AuraCardColors = cyberNeon(isDark)
    }
}

data class AuraProfileInfo(
    val name: String,
    val handle: String,
    val title: String,
    val company: String? = null,
    val location: String? = null,
    val bio: String? = null,
    @DrawableRes val avatarRes: Int? = null,
    val avatarPainter: Painter? = null,
    val statusText: String = "SYNCHRONIZED",
    val statusColor: Color? = null,
    val memberId: String = "ID-2049-VIP",
    val verificationHash: String = "0x7F9A2B4C91E"
)

data class AuraStat(
    val label: String,
    val value: String,
    val onClick: (() -> Unit)? = null
)

data class AuraSkill(
    val name: String,
    val level: Float, // 0.0f to 1.0f
    val category: String? = null
)

data class AuraBadge(
    val title: String,
    val icon: ImageVector? = null,
    val color: Color? = null,
    val onClick: (() -> Unit)? = null
)

enum class AuraLinkType {
    WEB, EMAIL, CODE, SOCIAL, OTHER
}

data class AuraLink(
    val title: String,
    val value: String,
    val type: AuraLinkType = AuraLinkType.WEB,
    val onClick: (() -> Unit)? = null
)

data class AuraCardActions(
    val onConnectClick: (() -> Unit)? = null,
    val connectLabel: String = "Connect",
    val onMessageClick: (() -> Unit)? = null,
    val messageLabel: String = "Message",
    val onShareClick: (() -> Unit)? = null,
    val onFlipClick: (() -> Unit)? = null,
    val onCardClick: (() -> Unit)? = null
)
