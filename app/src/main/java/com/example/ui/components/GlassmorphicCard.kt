package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.model.AppColorTheme
import com.example.model.ProfileData

@Composable
fun GlassmorphicCard(
    profile: ProfileData,
    theme: AppColorTheme,
    isDark: Boolean,
    modifier: Modifier = Modifier,
    onFlipClick: () -> Unit = {},
    onActionClick: (String) -> Unit = {}
) {
    val cornerRadius = 28.dp
    val shape = RoundedCornerShape(cornerRadius)

    // Animated shimmering specular highlight across the glass
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

    // Interactive press/hover state
    var isPressed by remember { mutableStateOf(false) }
    val cardScale = if (isPressed) 0.985f else 1.0f

    // Frosted glass translucent background colors
    val glassBg = if (isDark) {
        Color(0x38121A2E)
    } else {
        Color(0x75FFFFFF)
    }

    val glassBorderBrush = Brush.linearGradient(
        colors = listOf(
            theme.primary.copy(alpha = if (isDark) 0.75f else 0.85f),
            theme.secondary.copy(alpha = 0.35f),
            Color.White.copy(alpha = if (isDark) 0.15f else 0.45f),
            theme.accent.copy(alpha = if (isDark) 0.65f else 0.75f)
        ),
        start = Offset(0f, 0f),
        end = Offset(800f, 1200f)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .scale(cardScale)
            .graphicsLayer {
                shadowElevation = 24.dp.toPx()
                this.shape = shape
                clip = true
            }
            .clip(shape)
            .background(glassBg)
            .border(1.6.dp, glassBorderBrush, shape)
            .drawBehind {
                // Subtle frosted refractive inner glow
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            theme.primary.copy(alpha = if (isDark) 0.12f else 0.08f),
                            Color.Transparent
                        ),
                        center = Offset(size.width * 0.25f, size.height * 0.2f),
                        radius = size.width * 0.75f
                    )
                )

                // Iridescent specular shine stripe passing across the card
                val shineWidth = size.width * 0.5f
                val startX = size.width * shimmerOffset
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White.copy(alpha = if (isDark) 0.18f else 0.35f),
                            theme.primary.copy(alpha = if (isDark) 0.25f else 0.4f),
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
                    }
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
}
