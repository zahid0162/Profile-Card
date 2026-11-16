package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.model.AppColorTheme
import com.example.model.ProfileData
import com.example.util.HapticHelper
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun CardFlipView(
    profile: ProfileData,
    theme: AppColorTheme,
    isDark: Boolean,
    modifier: Modifier = Modifier,
    isFlipped: Boolean = false,
    onFlipToggle: () -> Unit = {},
    onActionClick: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val rotationAnim = remember { Animatable(if (isFlipped) 180f else 0f) }
    val density = LocalDensity.current.density
    val shape = RoundedCornerShape(28.dp)

    // Sync external isFlipped state
    LaunchedEffect(isFlipped) {
        val target = if (isFlipped) 180f else 0f
        if (abs(rotationAnim.targetValue - target) > 1f) {
            HapticHelper.triggerFlip(context)
            rotationAnim.animateTo(
                targetValue = target,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
    }

    val currentAngle = rotationAnim.value
    val normalizedAngle = ((currentAngle % 360f) + 360f) % 360f
    val isShowingBack = normalizedAngle in 90f..270f

    val cardBg = if (isDark) Color(0xF0101626) else Color(0xF5FFFFFF)
    val cardBorder = Brush.linearGradient(
        colors = listOf(
            theme.primary.copy(alpha = 0.85f),
            theme.secondary.copy(alpha = 0.6f),
            theme.accent.copy(alpha = 0.85f)
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .testTag("card_flip_container")
            .pointerInput(Unit) {
                var totalDrag = 0f
                detectHorizontalDragGestures(
                    onDragStart = { totalDrag = 0f },
                    onDragEnd = {
                        coroutineScope.launch {
                            val target = if (abs(totalDrag) > 60f) {
                                if (isShowingBack) 0f else 180f
                            } else {
                                if (isShowingBack) 180f else 0f
                            }
                            HapticHelper.triggerFlip(context)
                            onFlipToggle()
                            rotationAnim.animateTo(
                                targetValue = target,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessMedium
                                )
                            )
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
            .background(cardBg)
            .border(1.6.dp, cardBorder, shape)
            .drawBehind {
                // Dynamic specular light reflection that rotates opposite to card angle
                val glareRatio = (normalizedAngle / 180f).coerceIn(0f, 1f)
                val glareX = size.width * glareRatio
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = if (isDark) 0.16f else 0.35f),
                            theme.primary.copy(alpha = if (isDark) 0.12f else 0.20f),
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
            // Reverse rotation so content is rendered right-side up on reverse
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer { rotationY = 180f }
            ) {
                ProfileBackContent(
                    profile = profile,
                    theme = theme,
                    isDark = isDark,
                    onFlipClick = {
                        coroutineScope.launch {
                            HapticHelper.triggerFlip(context)
                            onFlipToggle()
                            rotationAnim.animateTo(
                                targetValue = 0f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                        }
                    },
                    onActionClick = onActionClick
                )
            }
        } else {
            ProfileFrontContent(
                profile = profile,
                theme = theme,
                isDark = isDark,
                onFlipClick = {
                    coroutineScope.launch {
                        HapticHelper.triggerFlip(context)
                        onFlipToggle()
                        rotationAnim.animateTo(
                            targetValue = 180f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                    }
                },
                onActionClick = onActionClick
            )
        }
    }
}
