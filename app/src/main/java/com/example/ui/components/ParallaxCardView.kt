package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppColorTheme
import com.example.model.ProfileData
import com.example.util.HapticHelper
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun ParallaxCardView(
    profile: ProfileData,
    theme: AppColorTheme,
    isDark: Boolean,
    modifier: Modifier = Modifier,
    onFlipClick: () -> Unit = {},
    onActionClick: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current.density
    val shape = RoundedCornerShape(28.dp)

    // Interactive 3D tilt angles
    val tiltX = remember { Animatable(0f) }
    val tiltY = remember { Animatable(0f) }

    // Ambient floating oscillation when idle
    var isDragging by remember { mutableStateOf(false) }
    var isHovered by remember { mutableStateOf(false) }

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

    // Reset tilt spring on release
    fun resetTilt() {
        coroutineScope.launch {
            tiltX.animateTo(
                0f,
                spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
            )
        }
        coroutineScope.launch {
            tiltY.animateTo(
                0f,
                spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
            )
        }
    }

    val effectiveTiltX = if (isDragging) tiltX.value else (tiltX.value + ambientOscillation * 4.5f)
    val effectiveTiltY = if (isDragging) tiltY.value else (tiltY.value + sin(ambientOscillation * 3.14f) * 6.0f)

    val cardBg = if (isDark) Color(0xF20F1626) else Color(0xF5FFFFFF)
    val cardBorder = Brush.linearGradient(
        colors = listOf(
            theme.primary.copy(alpha = 0.9f),
            theme.accent.copy(alpha = 0.6f),
            theme.secondary.copy(alpha = 0.9f)
        )
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .scale(if (isHovered || isDragging) 1.02f else 1.0f)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isHovered = true
                            HapticHelper.triggerLight(null, context)
                            tryAwaitRelease()
                            isHovered = false
                        }
                    )
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            isDragging = true
                            HapticHelper.triggerLight(null, context)
                        },
                        onDragEnd = {
                            isDragging = false
                            resetTilt()
                        },
                        onDragCancel = {
                            isDragging = false
                            resetTilt()
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            coroutineScope.launch {
                                // dragAmount.y tilts around X axis, dragAmount.x tilts around Y axis
                                val nextX = (tiltX.value - dragAmount.y * 0.22f).coerceIn(-18f, 18f)
                                val nextY = (tiltY.value + dragAmount.x * 0.22f).coerceIn(-18f, 18f)
                                tiltX.snapTo(nextX)
                                tiltY.snapTo(nextY)
                            }
                        }
                    )
                }
                .graphicsLayer {
                    rotationX = effectiveTiltX
                    rotationY = effectiveTiltY
                    cameraDistance = 16f * density
                    shadowElevation = (if (isHovered || isDragging) 34.dp else 22.dp).toPx()
                    this.shape = shape
                    clip = true
                }
                .clip(shape)
                .background(cardBg)
                .border(2.dp, cardBorder, shape)
                .drawBehind {
                    // Deep Layer: Cyber geometric grid lines shifted by parallax
                    val parallaxShiftX = effectiveTiltY * 2.2f
                    val parallaxShiftY = -effectiveTiltX * 2.2f

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                theme.primary.copy(alpha = if (isDark) 0.28f else 0.16f),
                                Color.Transparent
                            ),
                            center = Offset(
                                (size.width * 0.5f) + parallaxShiftX * 3f,
                                (size.height * 0.35f) + parallaxShiftY * 3f
                            ),
                            radius = size.width * 0.8f
                        ),
                        radius = size.width * 0.8f,
                        center = Offset(
                            (size.width * 0.5f) + parallaxShiftX * 3f,
                            (size.height * 0.35f) + parallaxShiftY * 3f
                        )
                    )
                }
                .testTag("parallax_card_container")
        ) {
            // Mid Layer: Profile Content with slight counter-shift for deep stereoscopic effect
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset {
                        IntOffset(
                            x = (effectiveTiltY * 0.8f).roundToInt(),
                            y = (-effectiveTiltX * 0.8f).roundToInt()
                        )
                    }
            ) {
                ProfileFrontContent(
                    profile = profile,
                    theme = theme,
                    isDark = isDark,
                    onFlipClick = onFlipClick,
                    onActionClick = onActionClick
                )
            }

            // Top Layer: Floating Parallax Holographic Tag (High depth offset)
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = theme.primary.copy(alpha = 0.92f),
                shadowElevation = 10.dp,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 18.dp, end = 20.dp)
                    .offset {
                        IntOffset(
                            x = (effectiveTiltY * 1.8f).roundToInt(),
                            y = (-effectiveTiltX * 1.8f).roundToInt()
                        )
                    }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
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

        Spacer(modifier = Modifier.height(14.dp))

        // Interactive instruction & reset button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.TouchApp,
                    contentDescription = null,
                    tint = theme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Drag or tilt card in 3D space",
                    fontSize = 12.sp,
                    color = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B),
                    fontWeight = FontWeight.Medium
                )
            }

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = theme.primary.copy(alpha = 0.15f),
                border = androidx.compose.foundation.BorderStroke(1.dp, theme.primary.copy(alpha = 0.35f)),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        HapticHelper.triggerLight(null, context)
                        resetTilt()
                    }
                    .testTag("reset_tilt_button")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.RestartAlt,
                        contentDescription = "Reset Angle",
                        tint = theme.primary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Reset Angle",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.primary
                    )
                }
            }
        }
    }
}
