package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auraprofile.AuraProfileCard
import com.example.auraprofile.model.AuraBadge
import com.example.auraprofile.model.AuraCardColors
import com.example.auraprofile.model.AuraCardStyle
import com.example.auraprofile.model.AuraLink
import com.example.auraprofile.model.AuraLinkType
import com.example.auraprofile.model.AuraSkill
import com.example.auraprofile.model.AuraStat
import com.example.model.AppColorTheme
import com.example.model.CardStyle
import com.example.model.ProfileData
import com.example.model.SampleProfiles
import com.example.ui.components.CardFlipView
import com.example.ui.components.ControlsPanel
import com.example.ui.components.CurtainRevealView
import com.example.ui.components.EditProfileDialog
import com.example.ui.components.GlassmorphicCard
import com.example.ui.components.ParallaxCardView
import com.example.ui.components.TitaniumLuxeCard
import com.example.ui.particles.DynamicParticleBackground
import com.example.ui.theme.AuraTheme
import com.example.util.HapticHelper
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AuraProfileApp()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuraProfileApp() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // App state
    var selectedStyle by remember { mutableStateOf(CardStyle.GLASSMORPHISM) }
    var activeTheme by remember { mutableStateOf(AppColorTheme.CYBER_NEON) }
    var isDark by remember { mutableStateOf(true) }
    var speedMultiplier by remember { mutableFloatStateOf(1.0f) }
    var hapticsEnabled by remember { mutableStateOf(true) }
    var isFlipped by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var pendingStyle by remember { mutableStateOf<CardStyle?>(null) }
    var isCurtainExiting by remember { mutableStateOf(false) }

    // Profiles list
    val profiles = remember { mutableStateListOf(*SampleProfiles.all.toTypedArray()) }
    var currentProfile by remember { mutableStateOf(profiles[0]) }

    fun triggerHaptic(light: Boolean = true) {
        if (hapticsEnabled) {
            if (light) HapticHelper.triggerLight(null, context)
            else HapticHelper.triggerFlip(context)
        }
    }

    fun showFeedback(msg: String) {
        triggerHaptic(true)
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(msg)
        }
    }

    AuraTheme(activeTheme = activeTheme, darkTheme = isDark) {
        val rootBg = if (isDark) activeTheme.darkBackground else activeTheme.lightBackground
        val textColor = if (isDark) Color(0xFFF8FAFC) else Color(0xFF0F172A)
        val textSecondary = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(rootBg),
            containerColor = Color.Transparent,
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Layer 0: Dynamic Atmospheric Particle & Ambient Glow Canvas
                DynamicParticleBackground(
                    theme = activeTheme,
                    isDark = isDark,
                    speedMultiplier = speedMultiplier,
                    onTouchInteraction = {
                        triggerHaptic(true)
                    }
                )

                // Layer 1: Foreground Scrollable Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Top App Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.sweepGradient(
                                            listOf(
                                                activeTheme.primary,
                                                activeTheme.secondary,
                                                activeTheme.accent,
                                                activeTheme.primary
                                            )
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = if (isDark) Color(0xFF0A0D14) else Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "AURA PROFILE",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 17.sp,
                                    letterSpacing = 1.sp,
                                    color = textColor
                                )
                                Text(
                                    text = "${selectedStyle.badge} • ${activeTheme.title}",
                                    fontSize = 11.sp,
                                    color = activeTheme.primary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        // Quick Actions in Top Bar
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Quick 3D flip button
                            Surface(
                                shape = CircleShape,
                                color = activeTheme.primary.copy(alpha = 0.16f),
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        triggerHaptic(false)
                                        isFlipped = !isFlipped
                                    }
                                    .testTag("top_quick_flip_button")
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Sync,
                                        contentDescription = "Flip Card",
                                        tint = activeTheme.primary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // Quick theme cycle
                            Surface(
                                shape = CircleShape,
                                color = activeTheme.secondary.copy(alpha = 0.16f),
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        triggerHaptic(true)
                                        val themes = AppColorTheme.values()
                                        val nextIdx = (themes.indexOf(activeTheme) + 1) % themes.size
                                        activeTheme = themes[nextIdx]
                                    }
                                    .testTag("top_quick_theme_button")
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Palette,
                                        contentDescription = "Next Theme",
                                        tint = activeTheme.secondary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }

                    val libraryStyle = when (selectedStyle) {
                        CardStyle.GLASSMORPHISM -> AuraCardStyle.GLASSMORPHISM
                        CardStyle.CARD_FLIP_3D -> AuraCardStyle.CARD_FLIP_3D
                        CardStyle.CURTAIN_REVEAL -> AuraCardStyle.CURTAIN_REVEAL
                        CardStyle.PARALLAX_NEBULA -> AuraCardStyle.PARALLAX_NEBULA
                        CardStyle.TITANIUM_LUXE -> AuraCardStyle.TITANIUM_LUXE
                    }

                    val libraryColors = when (activeTheme) {
                        AppColorTheme.CYBER_NEON -> AuraCardColors.cyberNeon(isDark)
                        AppColorTheme.SOLAR_FLARE -> AuraCardColors.solarFlare(isDark)
                        AppColorTheme.EMERALD_PRISM -> AuraCardColors.emeraldJade(isDark)
                        AppColorTheme.AMETHYST_DREAM -> AuraCardColors.amethystBloom(isDark)
                        AppColorTheme.MONOCHROME_LUXE -> AuraCardColors.titaniumLuxe(isDark)
                    }

                    // Card Display Area (with Fluid Entry Transitions powered by Library API)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .widthIn(max = 520.dp)
                            .padding(vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AnimatedContent(
                            targetState = selectedStyle,
                            transitionSpec = {
                                (fadeIn(animationSpec = tween(380)) + scaleIn(
                                    initialScale = 0.94f,
                                    animationSpec = tween(380)
                                )).togetherWith(
                                    fadeOut(animationSpec = tween(260)) + scaleOut(
                                        targetScale = 1.04f,
                                        animationSpec = tween(260)
                                    )
                                )
                            },
                            label = "cardStyleTransition"
                        ) { _ ->
                            AuraProfileCard(
                                name = currentProfile.name,
                                handle = currentProfile.handle,
                                title = currentProfile.title,
                                company = currentProfile.company,
                                location = currentProfile.location,
                                bio = currentProfile.bio,
                                avatarRes = currentProfile.avatarRes,
                                statusText = currentProfile.status,
                                statusColor = currentProfile.statusColor,
                                memberId = currentProfile.memberId,
                                verificationHash = currentProfile.verificationHash,
                                style = libraryStyle,
                                colors = libraryColors,
                                isDark = isDark,
                                stats = currentProfile.stats.map { AuraStat(it.key, it.value) },
                                skills = currentProfile.skills.map { AuraSkill(it.name, it.level, it.category) },
                                badges = currentProfile.badges.map { AuraBadge(it.title, color = it.color) },
                                links = currentProfile.contactLinks.map {
                                    AuraLink(
                                        title = it.title,
                                        value = it.value,
                                        type = when (it.type) {
                                            "email" -> AuraLinkType.EMAIL
                                            "code" -> AuraLinkType.CODE
                                            else -> AuraLinkType.WEB
                                        }
                                    )
                                },
                                onConnectClick = { showFeedback("Connected with ${currentProfile.name}") },
                                onMessageClick = { showFeedback("Opening message to ${currentProfile.name}") },
                                onShareClick = { showFeedback("Shared profile link") },
                                onCardClick = { showFeedback("Selected ${currentProfile.name}") }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Studio Controls Station
                    ControlsPanel(
                        selectedStyle = selectedStyle,
                        onSelectStyle = { targetStyle ->
                            triggerHaptic(true)
                            if (selectedStyle == CardStyle.CURTAIN_REVEAL && targetStyle != CardStyle.CURTAIN_REVEAL) {
                                pendingStyle = targetStyle
                                isCurtainExiting = true
                            } else {
                                selectedStyle = targetStyle
                            }
                        },
                        activeTheme = activeTheme,
                        onSelectTheme = {
                            triggerHaptic(true)
                            activeTheme = it
                        },
                        isDark = isDark,
                        onToggleDark = {
                            triggerHaptic(true)
                            isDark = !isDark
                        },
                        currentProfile = currentProfile,
                        profilesList = profiles,
                        onSelectProfile = {
                            triggerHaptic(true)
                            currentProfile = it
                        },
                        onOpenEditProfile = {
                            triggerHaptic(true)
                            showEditDialog = true
                        },
                        speedMultiplier = speedMultiplier,
                        onToggleSpeed = {
                            triggerHaptic(true)
                            speedMultiplier = when (speedMultiplier) {
                                0.5f -> 1.0f
                                1.0f -> 1.5f
                                1.5f -> 2.0f
                                else -> 0.5f
                            }
                        },
                        hapticsEnabled = hapticsEnabled,
                        onToggleHaptics = {
                            hapticsEnabled = !hapticsEnabled
                            if (hapticsEnabled) HapticHelper.triggerLight(null, context)
                        },
                        modifier = Modifier.widthIn(max = 520.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Edit Profile Modal
                if (showEditDialog) {
                    EditProfileDialog(
                        profile = currentProfile,
                        theme = activeTheme,
                        isDark = isDark,
                        onDismiss = { showEditDialog = false },
                        onSave = { updated ->
                            val index = profiles.indexOfFirst { it.id == updated.id }
                            if (index != -1) {
                                profiles[index] = updated
                            } else {
                                profiles.add(updated)
                            }
                            currentProfile = updated
                            showEditDialog = false
                            showFeedback("Profile details updated successfully")
                        }
                    )
                }
            }
        }
    }
}
