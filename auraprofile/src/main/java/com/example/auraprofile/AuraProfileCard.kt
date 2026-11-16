package com.example.auraprofile

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auraprofile.model.AuraBadge
import com.example.auraprofile.model.AuraCardActions
import com.example.auraprofile.model.AuraCardColors
import com.example.auraprofile.model.AuraCardStyle
import com.example.auraprofile.model.AuraLink
import com.example.auraprofile.model.AuraLinkType
import com.example.auraprofile.model.AuraProfileInfo
import com.example.auraprofile.model.AuraSkill
import com.example.auraprofile.model.AuraStat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * AuraProfileCard: Main Entry Point Composable
 * A production-ready, ultra-creative Profile & Info Card with five customizable
 * archetypes (Glassmorphism, 3D Holo Flip, Curtain Reveal, Parallax Nebula, Titanium Luxe).
 *
 * All parameters can be customized or passed dynamically.
 */
@Composable
fun AuraProfileCard(
    info: AuraProfileInfo,
    modifier: Modifier = Modifier,
    style: AuraCardStyle = AuraCardStyle.GLASSMORPHISM,
    colors: AuraCardColors = AuraCardColors.default(isSystemInDarkTheme()),
    isDark: Boolean = isSystemInDarkTheme(),
    stats: List<AuraStat> = emptyList(),
    skills: List<AuraSkill> = emptyList(),
    badges: List<AuraBadge> = emptyList(),
    links: List<AuraLink> = emptyList(),
    actions: AuraCardActions = AuraCardActions(),
    customHeaderSlot: (@Composable () -> Unit)? = null,
    customActionsSlot: (@Composable () -> Unit)? = null,
    customFooterSlot: (@Composable () -> Unit)? = null
) {
    when (style) {
        AuraCardStyle.GLASSMORPHISM -> {
            AuraGlassmorphicCard(
                info = info,
                colors = colors,
                isDark = isDark,
                stats = stats,
                skills = skills,
                badges = badges,
                links = links,
                actions = actions,
                customHeaderSlot = customHeaderSlot,
                customActionsSlot = customActionsSlot,
                customFooterSlot = customFooterSlot,
                modifier = modifier
            )
        }
        AuraCardStyle.CARD_FLIP_3D -> {
            AuraCardFlipView(
                info = info,
                colors = colors,
                isDark = isDark,
                stats = stats,
                skills = skills,
                badges = badges,
                links = links,
                actions = actions,
                customHeaderSlot = customHeaderSlot,
                customActionsSlot = customActionsSlot,
                customFooterSlot = customFooterSlot,
                modifier = modifier
            )
        }
        AuraCardStyle.CURTAIN_REVEAL -> {
            AuraCurtainCard(
                info = info,
                colors = colors,
                isDark = isDark,
                stats = stats,
                skills = skills,
                badges = badges,
                links = links,
                actions = actions,
                customHeaderSlot = customHeaderSlot,
                customActionsSlot = customActionsSlot,
                customFooterSlot = customFooterSlot,
                modifier = modifier
            )
        }
        AuraCardStyle.PARALLAX_NEBULA -> {
            AuraParallaxCard(
                info = info,
                colors = colors,
                isDark = isDark,
                stats = stats,
                skills = skills,
                badges = badges,
                links = links,
                actions = actions,
                customHeaderSlot = customHeaderSlot,
                customActionsSlot = customActionsSlot,
                customFooterSlot = customFooterSlot,
                modifier = modifier
            )
        }
        AuraCardStyle.TITANIUM_LUXE -> {
            AuraTitaniumCard(
                info = info,
                colors = colors,
                isDark = isDark,
                stats = stats,
                skills = skills,
                badges = badges,
                links = links,
                actions = actions,
                customHeaderSlot = customHeaderSlot,
                customActionsSlot = customActionsSlot,
                customFooterSlot = customFooterSlot,
                modifier = modifier
            )
        }
    }
}

/**
 * Convenient parameter overload for direct method invocation without building wrapper classes.
 */
@Composable
fun AuraProfileCard(
    name: String,
    handle: String,
    title: String,
    modifier: Modifier = Modifier,
    company: String? = null,
    location: String? = null,
    bio: String? = null,
    @DrawableRes avatarRes: Int? = null,
    avatarPainter: Painter? = null,
    statusText: String = "ACTIVE",
    statusColor: Color? = null,
    memberId: String = "ID-2049",
    verificationHash: String = "0x7F9A2B4C",
    style: AuraCardStyle = AuraCardStyle.GLASSMORPHISM,
    colors: AuraCardColors = AuraCardColors.default(isSystemInDarkTheme()),
    isDark: Boolean = isSystemInDarkTheme(),
    stats: List<AuraStat> = emptyList(),
    skills: List<AuraSkill> = emptyList(),
    badges: List<AuraBadge> = emptyList(),
    links: List<AuraLink> = emptyList(),
    onConnectClick: (() -> Unit)? = null,
    onMessageClick: (() -> Unit)? = null,
    onShareClick: (() -> Unit)? = null,
    onCardClick: (() -> Unit)? = null
) {
    val info = AuraProfileInfo(
        name = name,
        handle = handle,
        title = title,
        company = company,
        location = location,
        bio = bio,
        avatarRes = avatarRes,
        avatarPainter = avatarPainter,
        statusText = statusText,
        statusColor = statusColor,
        memberId = memberId,
        verificationHash = verificationHash
    )
    val actions = AuraCardActions(
        onConnectClick = onConnectClick,
        onMessageClick = onMessageClick,
        onShareClick = onShareClick,
        onCardClick = onCardClick
    )

    AuraProfileCard(
        info = info,
        modifier = modifier,
        style = style,
        colors = colors,
        isDark = isDark,
        stats = stats,
        skills = skills,
        badges = badges,
        links = links,
        actions = actions
    )
}

// -------------------------------------------------------------
// INTERNAL IMPLEMENTATIONS OF THE 5 CARD ARCHETYPES
// -------------------------------------------------------------

@Composable
internal fun AuraGlassmorphicCard(
    info: AuraProfileInfo,
    colors: AuraCardColors,
    isDark: Boolean,
    stats: List<AuraStat>,
    skills: List<AuraSkill>,
    badges: List<AuraBadge>,
    links: List<AuraLink>,
    actions: AuraCardActions,
    customHeaderSlot: (@Composable () -> Unit)?,
    customActionsSlot: (@Composable () -> Unit)?,
    customFooterSlot: (@Composable () -> Unit)?,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(28.dp)
    val infiniteTransition = rememberInfiniteTransition(label = "glassShimmer")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -1.2f,
        targetValue = 2.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(4200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerOffset"
    )

    var isPressed by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .scale(if (isPressed) 0.985f else 1.0f)
            .shadow(24.dp, shape)
            .clip(shape)
            .background(if (isDark) Color(0x38121A2E) else Color(0x75FFFFFF))
            .border(1.6.dp, colors.borderBrush(isDark), shape)
            .drawBehind {
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(colors.primary.copy(alpha = if (isDark) 0.12f else 0.08f), Color.Transparent),
                        center = Offset(size.width * 0.25f, size.height * 0.2f),
                        radius = size.width * 0.75f
                    )
                )
                val shineWidth = size.width * 0.5f
                val startX = size.width * shimmerOffset
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White.copy(alpha = if (isDark) 0.18f else 0.35f),
                            colors.primary.copy(alpha = if (isDark) 0.25f else 0.4f),
                            Color.Transparent
                        ),
                        start = Offset(startX, 0f),
                        end = Offset(startX + shineWidth, size.height)
                    ),
                    start = Offset(startX, 0f),
                    end = Offset(startX + shineWidth, size.height),
                    strokeWidth = 60f
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = { actions.onCardClick?.invoke() }
                )
            }
    ) {
        AuraCardFrontContent(
            info = info,
            colors = colors,
            isDark = isDark,
            stats = stats,
            skills = skills,
            badges = badges,
            actions = actions,
            customHeaderSlot = customHeaderSlot,
            customActionsSlot = customActionsSlot,
            customFooterSlot = customFooterSlot,
            onFlipClick = actions.onFlipClick ?: {}
        )
    }
}

@Composable
internal fun AuraCardFlipView(
    info: AuraProfileInfo,
    colors: AuraCardColors,
    isDark: Boolean,
    stats: List<AuraStat>,
    skills: List<AuraSkill>,
    badges: List<AuraBadge>,
    links: List<AuraLink>,
    actions: AuraCardActions,
    customHeaderSlot: (@Composable () -> Unit)?,
    customActionsSlot: (@Composable () -> Unit)?,
    customFooterSlot: (@Composable () -> Unit)?,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val rotationAnim = remember { Animatable(0f) }
    val density = LocalDensity.current.density
    val shape = RoundedCornerShape(28.dp)

    val currentAngle = rotationAnim.value
    val normalizedAngle = ((currentAngle % 360f) + 360f) % 360f
    val isShowingBack = normalizedAngle in 90f..270f

    fun flipCard() {
        coroutineScope.launch {
            val target = if (isShowingBack) 0f else 180f
            actions.onFlipClick?.invoke()
            rotationAnim.animateTo(
                targetValue = target,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                var totalDrag = 0f
                detectHorizontalDragGestures(
                    onDragStart = { totalDrag = 0f },
                    onDragEnd = {
                        if (abs(totalDrag) > 60f) {
                            flipCard()
                        } else {
                            coroutineScope.launch {
                                val target = if (isShowingBack) 180f else 0f
                                rotationAnim.animateTo(target, spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium))
                            }
                        }
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        change.consume()
                        totalDrag += dragAmount
                        coroutineScope.launch {
                            rotationAnim.snapTo(rotationAnim.value + dragAmount * 0.45f)
                        }
                    }
                )
            }
            .graphicsLayer {
                rotationY = currentAngle
                cameraDistance = 16f * density
                shadowElevation = 26.dp.toPx()
                this.shape = shape
                clip = true
            }
            .clip(shape)
            .background(colors.cardBackground)
            .border(1.6.dp, colors.borderBrush(isDark), shape)
            .drawBehind {
                val glareRatio = (normalizedAngle / 180f).coerceIn(0f, 1f)
                val glareX = size.width * glareRatio
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = if (isDark) 0.16f else 0.35f),
                            colors.primary.copy(alpha = if (isDark) 0.12f else 0.20f),
                            Color.Transparent
                        ),
                        center = Offset(glareX, size.height * 0.35f),
                        radius = size.width * 0.75f
                    ),
                    radius = size.width * 0.75f,
                    center = Offset(glareX, size.height * 0.35f)
                )
            }
    ) {
        if (isShowingBack) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer { rotationY = 180f }
            ) {
                AuraCardBackContent(
                    info = info,
                    colors = colors,
                    isDark = isDark,
                    skills = skills,
                    badges = badges,
                    links = links,
                    actions = actions,
                    onFlipClick = { flipCard() }
                )
            }
        } else {
            AuraCardFrontContent(
                info = info,
                colors = colors,
                isDark = isDark,
                stats = stats,
                skills = skills,
                badges = badges,
                actions = actions,
                customHeaderSlot = customHeaderSlot,
                customActionsSlot = customActionsSlot,
                customFooterSlot = customFooterSlot,
                onFlipClick = { flipCard() }
            )
        }
    }
}

@Composable
internal fun AuraCurtainCard(
    info: AuraProfileInfo,
    colors: AuraCardColors,
    isDark: Boolean,
    stats: List<AuraStat>,
    skills: List<AuraSkill>,
    badges: List<AuraBadge>,
    links: List<AuraLink>,
    actions: AuraCardActions,
    customHeaderSlot: (@Composable () -> Unit)?,
    customActionsSlot: (@Composable () -> Unit)?,
    customFooterSlot: (@Composable () -> Unit)?,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val curtainAnim = remember { Animatable(0.0f) }
    var isBioPage by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(28.dp)

    // Auto open on initial display
    LaunchedEffect(Unit) {
        delay(120)
        curtainAnim.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(durationMillis = 850, easing = FastOutSlowInEasing)
        )
    }

    fun flipWithCurtain() {
        coroutineScope.launch {
            curtainAnim.animateTo(0.0f, tween(340, easing = FastOutSlowInEasing))
            isBioPage = !isBioPage
            actions.onFlipClick?.invoke()
            delay(50)
            curtainAnim.animateTo(1.0f, spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium))
        }
    }

    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(26.dp, shape)
                .clip(shape)
                .background(colors.cardBackground)
                .border(1.6.dp, colors.borderBrush(isDark), shape)
        ) {
            if (isBioPage) {
                AuraCardBackContent(
                    info = info,
                    colors = colors,
                    isDark = isDark,
                    skills = skills,
                    badges = badges,
                    links = links,
                    actions = actions,
                    onFlipClick = { flipWithCurtain() }
                )
            } else {
                AuraCardFrontContent(
                    info = info,
                    colors = colors,
                    isDark = isDark,
                    stats = stats,
                    skills = skills,
                    badges = badges,
                    actions = actions,
                    customHeaderSlot = customHeaderSlot,
                    customActionsSlot = customActionsSlot,
                    customFooterSlot = customFooterSlot,
                    onFlipClick = { flipWithCurtain() }
                )
            }

            // Curtain Canvas
            val openRatio = curtainAnim.value.coerceIn(0f, 1f)
            if (openRatio < 0.99f) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    val w = size.width
                    val h = size.height
                    if (w <= 0f || h <= 0f) return@Canvas
                    val halfW = w / 2f
                    val curtainW = halfW * (1f - openRatio)

                    if (curtainW > 0.5f) {
                        val numPleats = 9
                        val pleatW = curtainW / numPleats

                        // Left Pleats
                        for (i in 0 until numPleats) {
                            val px = i * pleatW
                            val isRidge = i % 2 == 0
                            val pleatBrush = Brush.horizontalGradient(
                                colors = listOf(
                                    if (isRidge) colors.secondary.copy(alpha = 0.99f) else colors.primary.copy(alpha = 0.93f),
                                    if (isDark) Color(0xFF0C0617) else Color(0xFF281331),
                                    if (isRidge) colors.secondary.copy(alpha = 0.96f) else colors.accent.copy(alpha = 0.90f)
                                ),
                                startX = px,
                                endX = px + pleatW
                            )
                            drawRect(brush = pleatBrush, topLeft = Offset(px, 0f), size = Size(pleatW + 0.5f, h))
                        }

                        // Right Pleats
                        val rightStartX = w - curtainW
                        for (i in 0 until numPleats) {
                            val px = rightStartX + (i * pleatW)
                            val isRidge = i % 2 == 1
                            val pleatBrush = Brush.horizontalGradient(
                                colors = listOf(
                                    if (isRidge) colors.accent.copy(alpha = 0.93f) else colors.primary.copy(alpha = 0.93f),
                                    if (isDark) Color(0xFF0C0617) else Color(0xFF281331),
                                    if (isRidge) colors.secondary.copy(alpha = 0.99f) else colors.secondary.copy(alpha = 0.90f)
                                ),
                                startX = px,
                                endX = px + pleatW
                            )
                            drawRect(brush = pleatBrush, topLeft = Offset(px, 0f), size = Size(pleatW + 0.5f, h))
                        }

                        // Gold ropes
                        drawLine(Color(0xFFFFD166), Offset(curtainW, 0f), Offset(curtainW, h), 4f)
                        drawLine(Color(0xFFFFD166), Offset(rightStartX, 0f), Offset(rightStartX, h), 4f)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Clean Curtain Flip Button
        Button(
            onClick = { flipWithCurtain() },
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.primary,
                contentColor = if (isDark) Color(0xFF070B12) else Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Icon(
                imageVector = if (isBioPage) Icons.Default.Person else Icons.Default.Badge,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isBioPage) "CURTAIN FLIP TO PROFILE" else "CURTAIN FLIP TO BIO DOSSIER",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 12.sp,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.Sync, contentDescription = null, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
internal fun AuraParallaxCard(
    info: AuraProfileInfo,
    colors: AuraCardColors,
    isDark: Boolean,
    stats: List<AuraStat>,
    skills: List<AuraSkill>,
    badges: List<AuraBadge>,
    links: List<AuraLink>,
    actions: AuraCardActions,
    customHeaderSlot: (@Composable () -> Unit)?,
    customActionsSlot: (@Composable () -> Unit)?,
    customFooterSlot: (@Composable () -> Unit)?,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current.density
    val shape = RoundedCornerShape(28.dp)

    val tiltX = remember { Animatable(0f) }
    val tiltY = remember { Animatable(0f) }
    var isDragging by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "ambientParallax")
    val ambientOscillation by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ambientWave"
    )

    fun resetTilt() {
        coroutineScope.launch { tiltX.animateTo(0f, spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow)) }
        coroutineScope.launch { tiltY.animateTo(0f, spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow)) }
    }

    val effectiveTiltX = if (isDragging) tiltX.value else (tiltX.value + ambientOscillation * 4.5f)
    val effectiveTiltY = if (isDragging) tiltY.value else (tiltY.value + sin(ambientOscillation * 3.14f) * 6.0f)

    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = { isDragging = true },
                        onDragEnd = { isDragging = false; resetTilt() },
                        onDragCancel = { isDragging = false; resetTilt() },
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            coroutineScope.launch {
                                val nextY = (tiltY.value + dragAmount * 0.22f).coerceIn(-18f, 18f)
                                tiltY.snapTo(nextY)
                            }
                        }
                    )
                }
                .graphicsLayer {
                    rotationX = effectiveTiltX
                    rotationY = effectiveTiltY
                    cameraDistance = 16f * density
                    shadowElevation = 28.dp.toPx()
                    this.shape = shape
                    clip = true
                }
                .clip(shape)
                .background(colors.cardBackground)
                .border(2.dp, colors.borderBrush(isDark), shape)
                .drawBehind {
                    val parallaxShiftX = effectiveTiltY * 2.2f
                    val parallaxShiftY = -effectiveTiltX * 2.2f
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(colors.primary.copy(alpha = if (isDark) 0.28f else 0.16f), Color.Transparent),
                            center = Offset((size.width * 0.5f) + parallaxShiftX * 3f, (size.height * 0.35f) + parallaxShiftY * 3f),
                            radius = size.width * 0.8f
                        ),
                        radius = size.width * 0.8f,
                        center = Offset((size.width * 0.5f) + parallaxShiftX * 3f, (size.height * 0.35f) + parallaxShiftY * 3f)
                    )
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset { IntOffset((effectiveTiltY * 0.8f).roundToInt(), (-effectiveTiltX * 0.8f).roundToInt()) }
            ) {
                AuraCardFrontContent(
                    info = info,
                    colors = colors,
                    isDark = isDark,
                    stats = stats,
                    skills = skills,
                    badges = badges,
                    actions = actions,
                    customHeaderSlot = customHeaderSlot,
                    customActionsSlot = customActionsSlot,
                    customFooterSlot = customFooterSlot,
                    onFlipClick = actions.onFlipClick ?: {}
                )
            }

            // High-depth floating holographic badge
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = colors.primary.copy(alpha = 0.92f),
                shadowElevation = 10.dp,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 18.dp, end = 20.dp)
                    .offset { IntOffset((effectiveTiltY * 1.8f).roundToInt(), (-effectiveTiltX * 1.8f).roundToInt()) }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (isDark) Color(0xFF070B12) else Color.White,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "3D PARALLAX",
                        color = if (isDark) Color(0xFF070B12) else Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

@Composable
internal fun AuraTitaniumCard(
    info: AuraProfileInfo,
    colors: AuraCardColors,
    isDark: Boolean,
    stats: List<AuraStat>,
    skills: List<AuraSkill>,
    badges: List<AuraBadge>,
    links: List<AuraLink>,
    actions: AuraCardActions,
    customHeaderSlot: (@Composable () -> Unit)?,
    customActionsSlot: (@Composable () -> Unit)?,
    customFooterSlot: (@Composable () -> Unit)?,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(26.dp)
    val infiniteTransition = rememberInfiniteTransition(label = "equalizer")
    val wavePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f,
        animationSpec = infiniteRepeatable(animation = tween(2400, easing = LinearEasing), repeatMode = RepeatMode.Restart),
        label = "wavePhase"
    )

    val titaniumBg = if (isDark) {
        Brush.linearGradient(listOf(Color(0xFF14171F), Color(0xFF1A1F2C), Color(0xFF0F1219)))
    } else {
        Brush.linearGradient(listOf(Color(0xFFF1F5F9), Color(0xFFE2E8F0), Color(0xFFCBD5E1)))
    }

    val goldLaserBorder = Brush.linearGradient(listOf(Color(0xFFFFD166), Color(0xFFB388FF), Color(0xFFFFD166).copy(alpha = 0.5f), colors.primary))

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(28.dp, shape)
            .clip(shape)
            .background(titaniumBg)
            .border(2.dp, goldLaserBorder, shape)
            .drawBehind {
                val step = 14f
                var curY = -size.width
                while (curY < size.height + size.width) {
                    drawLine(
                        color = (if (isDark) Color.White else Color.Black).copy(alpha = 0.035f),
                        start = Offset(0f, curY),
                        end = Offset(size.width, curY + size.width),
                        strokeWidth = 1f
                    )
                    curY += step
                }
                drawRect(
                    brush = Brush.horizontalGradient(listOf(Color(0xFFFFD166), colors.primary, Color(0xFFFFD166))),
                    topLeft = Offset(0f, 0f),
                    size = Size(size.width, 4.dp.toPx())
                )
            }
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp, start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = Color(0xFFFFD166), modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "TITANIUM APEX GRADE",
                        color = Color(0xFFFFD166),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    )
                }

                // Waveform Canvas
                Canvas(modifier = Modifier.size(width = 54.dp, height = 16.dp)) {
                    val numBars = 8
                    val barW = size.width / (numBars * 1.6f)
                    for (i in 0 until numBars) {
                        val phase = wavePhase + i * 0.7f
                        val heightRatio = (sin(phase.toDouble()).toFloat() * 0.45f + 0.55f).coerceIn(0.2f, 1f)
                        val barH = size.height * heightRatio
                        val bx = i * (barW * 1.6f)
                        drawRect(color = colors.primary, topLeft = Offset(bx, size.height - barH), size = Size(barW, barH))
                    }
                }
            }

            AuraCardFrontContent(
                info = info,
                colors = colors,
                isDark = isDark,
                stats = stats,
                skills = skills,
                badges = badges,
                actions = actions,
                customHeaderSlot = customHeaderSlot,
                customActionsSlot = customActionsSlot,
                customFooterSlot = customFooterSlot,
                onFlipClick = actions.onFlipClick ?: {}
            )
        }
    }
}

// -------------------------------------------------------------
// REUSABLE CARD CONTENT LAYOUTS
// -------------------------------------------------------------

@Composable
internal fun AuraCardFrontContent(
    info: AuraProfileInfo,
    colors: AuraCardColors,
    isDark: Boolean,
    stats: List<AuraStat>,
    skills: List<AuraSkill>,
    badges: List<AuraBadge>,
    actions: AuraCardActions,
    customHeaderSlot: (@Composable () -> Unit)?,
    customActionsSlot: (@Composable () -> Unit)?,
    customFooterSlot: (@Composable () -> Unit)?,
    onFlipClick: () -> Unit
) {
    val cardBackground = if (isDark) Color(0x351E293B) else Color(0x35F8FAFC)
    val statusColor = info.statusColor ?: colors.statusColor

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(tween(1200), repeatMode = RepeatMode.Reverse),
        label = "pulseScale"
    )

    Column(
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (customHeaderSlot != null) {
            customHeaderSlot()
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = statusColor.copy(alpha = 0.16f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, statusColor.copy(alpha = 0.45f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .scale(pulseScale)
                                .background(statusColor, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = info.statusText,
                            color = statusColor,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                }

                Surface(
                    shape = CircleShape,
                    color = colors.primary.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, colors.primary.copy(alpha = 0.35f)),
                    modifier = Modifier.clip(CircleShape).clickable { onFlipClick() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Sync, contentDescription = "Flip", tint = colors.primary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("3D FLIP", color = colors.primary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Avatar
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(116.dp)) {
            Box(
                modifier = Modifier
                    .size(114.dp)
                    .clip(CircleShape)
                    .background(Brush.sweepGradient(listOf(colors.primary, colors.secondary, colors.accent, colors.primary)))
            )

            if (info.avatarPainter != null) {
                Image(
                    painter = info.avatarPainter,
                    contentDescription = info.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(108.dp)
                        .clip(CircleShape)
                        .border(2.5.dp, if (isDark) Color(0xFF0F172A) else Color.White, CircleShape)
                )
            } else if (info.avatarRes != null) {
                Image(
                    painter = painterResource(id = info.avatarRes),
                    contentDescription = info.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(108.dp)
                        .clip(CircleShape)
                        .border(2.5.dp, if (isDark) Color(0xFF0F172A) else Color.White, CircleShape)
                )
            } else {
                Surface(
                    shape = CircleShape,
                    color = colors.primary.copy(alpha = 0.25f),
                    modifier = Modifier.size(108.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = colors.primary, modifier = Modifier.size(48.dp))
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 4.dp, bottom = 4.dp)
                    .size(26.dp)
                    .background(colors.primary, CircleShape)
                    .border(2.dp, if (isDark) Color(0xFF0F172A) else Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = "Verified", tint = if (isDark) Color(0xFF0A0D14) else Color.White, modifier = Modifier.size(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Name & Handle
        Text(
            text = info.name,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 22.sp,
            color = colors.textColor,
            textAlign = TextAlign.Center
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 2.dp)
        ) {
            Text(text = info.handle, color = colors.primary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            if (info.location != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.size(3.dp).background(colors.textSecondaryColor, CircleShape))
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = colors.textSecondaryColor, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = info.location, color = colors.textSecondaryColor, fontSize = 11.sp)
            }
        }

        val subtitle = if (info.company != null) "${info.title} • ${info.company}" else info.title
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = cardBackground,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = subtitle,
                color = colors.textColor.copy(alpha = 0.88f),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }

        if (info.bio != null) {
            Text(
                text = info.bio,
                color = colors.textSecondaryColor,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 10.dp, start = 8.dp, end = 8.dp)
            )
        }

        if (stats.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                stats.take(4).forEach { stat ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = cardBackground,
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isDark) Color(0x22FFFFFF) else Color(0x15000000)),
                        modifier = Modifier.weight(1f).clickable(enabled = stat.onClick != null) { stat.onClick?.invoke() }
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = stat.value, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = colors.primary)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = stat.label, fontSize = 10.sp, color = colors.textSecondaryColor, maxLines = 1)
                        }
                    }
                }
            }
        }

        if (skills.isNotEmpty()) {
            Spacer(modifier = Modifier.height(14.dp))
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                skills.take(2).forEach { skill ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = skill.name, fontSize = 11.sp, color = colors.textColor, fontWeight = FontWeight.Medium)
                            Text(text = "${(skill.level * 100).toInt()}%", fontSize = 11.sp, color = colors.accent, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth().height(5.dp).clip(RoundedCornerShape(3.dp))
                                .background(if (isDark) Color(0x33FFFFFF) else Color(0x22000000))
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth(skill.level.coerceIn(0f, 1f)).height(5.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(Brush.horizontalGradient(listOf(colors.primary, colors.accent)))
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (customActionsSlot != null) {
            customActionsSlot()
        } else {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = { actions.onConnectClick?.invoke() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary,
                        contentColor = if (isDark) Color(0xFF090D16) else Color.White
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1f).height(46.dp)
                ) {
                    Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = actions.connectLabel, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                OutlinedButton(
                    onClick = { actions.onMessageClick?.invoke() },
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, colors.primary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = colors.primary),
                    modifier = Modifier.weight(1f).height(46.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = actions.messageLabel, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = cardBackground,
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isDark) Color(0x33FFFFFF) else Color(0x22000000)),
                    modifier = Modifier.size(46.dp).clip(RoundedCornerShape(14.dp)).clickable { actions.onShareClick?.invoke() }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = colors.textColor, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }

        if (customFooterSlot != null) {
            Spacer(modifier = Modifier.height(12.dp))
            customFooterSlot()
        }
    }
}

@Composable
internal fun AuraCardBackContent(
    info: AuraProfileInfo,
    colors: AuraCardColors,
    isDark: Boolean,
    skills: List<AuraSkill>,
    badges: List<AuraBadge>,
    links: List<AuraLink>,
    actions: AuraCardActions,
    onFlipClick: () -> Unit
) {
    val cardBackground = if (isDark) Color(0x351E293B) else Color(0x35F8FAFC)
    val statusColor = info.statusColor ?: colors.statusColor

    Column(
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Fingerprint, contentDescription = null, tint = colors.primary, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("NEURAL ID SPEC", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = colors.primary, letterSpacing = 1.sp)
            }

            Surface(
                shape = CircleShape,
                color = colors.secondary.copy(alpha = 0.15f),
                border = androidx.compose.foundation.BorderStroke(1.dp, colors.secondary.copy(alpha = 0.35f)),
                modifier = Modifier.clip(CircleShape).clickable { onFlipClick() }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Sync, contentDescription = "Return Front", tint = colors.secondary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("FRONT", color = colors.secondary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Center Identity QR Code Matrix & Security Stamp
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(cardBackground)
                .border(1.dp, if (isDark) Color(0x25FFFFFF) else Color(0x15000000), RoundedCornerShape(14.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(86.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isDark) Color(0xFF080C14) else Color(0xFFE2E8F0))
                    .padding(6.dp)
            ) {
                Canvas(modifier = Modifier.size(74.dp)) {
                    val primary = colors.primary
                    val dotSize = size.width / 9f
                    val matrix = listOf(
                        listOf(1,1,1,0,1,0,1,1,1),
                        listOf(1,0,1,0,0,1,1,0,1),
                        listOf(1,1,1,0,1,0,1,1,1),
                        listOf(0,0,0,1,0,1,0,0,0),
                        listOf(1,0,1,0,1,0,1,0,1),
                        listOf(0,1,0,1,1,1,0,1,0),
                        listOf(1,1,1,0,0,1,1,1,1),
                        listOf(1,0,1,1,0,0,1,0,1),
                        listOf(1,1,1,0,1,1,1,1,1)
                    )
                    for (r in matrix.indices) {
                        for (c in matrix[r].indices) {
                            if (matrix[r][c] == 1) {
                                drawRect(primary, Offset(c * dotSize, r * dotSize), Size(dotSize * 0.88f, dotSize * 0.88f))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(info.memberId, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = colors.textColor, fontFamily = FontFamily.Monospace)
                Text("AUTH: ${info.verificationHash}", fontSize = 10.sp, color = colors.accent, fontFamily = FontFamily.Monospace, modifier = Modifier.padding(top = 2.dp))
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Security, contentDescription = null, tint = statusColor, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Encrypted Ledger Validated", fontSize = 10.sp, color = statusColor, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        if (skills.isNotEmpty()) {
            Spacer(modifier = Modifier.height(14.dp))
            Text("VERIFIED CAPABILITIES", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = colors.textSecondaryColor, letterSpacing = 1.sp, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                skills.forEach { skill ->
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(skill.name, fontSize = 11.sp, color = colors.textColor, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier.width(110.dp).height(6.dp).clip(RoundedCornerShape(3.dp))
                                .background(if (isDark) Color(0x33FFFFFF) else Color(0x22000000))
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth(skill.level.coerceIn(0f, 1f)).height(6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(Brush.horizontalGradient(listOf(colors.primary, colors.secondary)))
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("${(skill.level * 100).toInt()}%", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = colors.primary, fontFamily = FontFamily.Monospace, modifier = Modifier.width(28.dp))
                    }
                }
            }
        }

        if (badges.isNotEmpty()) {
            Spacer(modifier = Modifier.height(14.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                badges.forEach { badge ->
                    val badgeColor = badge.color ?: colors.primary
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = badgeColor.copy(alpha = 0.14f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, badgeColor.copy(alpha = 0.35f)),
                        modifier = Modifier.weight(1f).clickable(enabled = badge.onClick != null) { badge.onClick?.invoke() }
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(badge.icon ?: Icons.Default.Star, contentDescription = null, tint = badgeColor, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(badge.title, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = badgeColor, maxLines = 1)
                        }
                    }
                }
            }
        }

        if (links.isNotEmpty()) {
            Spacer(modifier = Modifier.height(14.dp))
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                links.forEach { link ->
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = cardBackground,
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isDark) Color(0x22FFFFFF) else Color(0x15000000)),
                        modifier = Modifier.fillMaxWidth().clickable(enabled = link.onClick != null) { link.onClick?.invoke() }
                    ) {
                        Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                            val icon = when (link.type) {
                                AuraLinkType.EMAIL -> Icons.Default.AlternateEmail
                                AuraLinkType.CODE -> Icons.Default.Code
                                else -> Icons.Default.Language
                            }
                            Icon(icon, contentDescription = null, tint = colors.primary, modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(link.title, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = colors.textColor)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(link.value, fontSize = 10.sp, color = colors.textSecondaryColor, fontFamily = FontFamily.Monospace)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = { actions.onFlipClick?.invoke() },
            colors = ButtonDefaults.buttonColors(containerColor = colors.secondary, contentColor = Color.White),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(44.dp)
        ) {
            Icon(Icons.Default.QrCode2, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Verified Identity Pass", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}
