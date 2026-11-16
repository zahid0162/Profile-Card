package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppColorTheme
import com.example.model.ProfileData
import com.example.util.HapticHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CurtainRevealView(
    profile: ProfileData,
    theme: AppColorTheme,
    isDark: Boolean,
    modifier: Modifier = Modifier,
    isExiting: Boolean = false,
    onExitComplete: () -> Unit = {},
    onActionClick: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current.density
    val shape = RoundedCornerShape(28.dp)

    // Curtains open ratio: 0.0f = completely closed, 1.0f = completely open
    val curtainAnim = remember { Animatable(0.0f) }
    var isBioPage by remember { mutableStateOf(false) }

    // 1. AUTO OPEN when curtain style is chosen
    LaunchedEffect(Unit) {
        delay(100)
        HapticHelper.triggerCurtainSnap(context)
        curtainAnim.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(durationMillis = 850, easing = FastOutSlowInEasing)
        )
    }

    // 2. AUTO CLOSE when user chooses another style
    LaunchedEffect(isExiting) {
        if (isExiting) {
            HapticHelper.triggerCurtainSnap(context)
            curtainAnim.animateTo(
                targetValue = 0.0f,
                animationSpec = tween(durationMillis = 380, easing = FastOutSlowInEasing)
            )
            onExitComplete()
        }
    }

    // 3. FLIP LIKE A CURTAIN TO OPEN BIO / PROFILE PAGE
    fun performCurtainFlip() {
        coroutineScope.launch {
            // Step 1: Draw curtains closed
            HapticHelper.triggerCurtainSnap(context)
            curtainAnim.animateTo(
                targetValue = 0.0f,
                animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing)
            )

            // Step 2: Switch between front identity and back bio telemetry
            isBioPage = !isBioPage
            HapticHelper.triggerFlip(context)

            delay(60)

            // Step 3: Draw curtains back open to reveal the bio/profile
            HapticHelper.triggerCurtainSnap(context)
            curtainAnim.animateTo(
                targetValue = 1.0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        }
    }

    val cardBg = if (isDark) Color(0xF2101626) else Color(0xF5FFFFFF)
    val cardBorder = Brush.linearGradient(
        colors = listOf(
            theme.primary.copy(alpha = 0.85f),
            theme.secondary.copy(alpha = 0.55f),
            theme.accent.copy(alpha = 0.85f)
        )
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Main Card Container with layered content and matchParentSize curtains
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(26.dp, shape)
                .clip(shape)
                .background(cardBg)
                .border(1.6.dp, cardBorder, shape)
                .graphicsLayer {
                    cameraDistance = 16f * density
                }
        ) {
            // Layer 1: Front Profile or Back Bio Telemetry
            if (isBioPage) {
                ProfileBackContent(
                    profile = profile,
                    theme = theme,
                    isDark = isDark,
                    onFlipClick = { performCurtainFlip() },
                    onActionClick = onActionClick
                )
            } else {
                ProfileFrontContent(
                    profile = profile,
                    theme = theme,
                    isDark = isDark,
                    onFlipClick = { performCurtainFlip() },
                    onActionClick = onActionClick
                )
            }

            // Layer 2: Silk Pleated Theater Curtains
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

                        // Left Pleated Silk Drape
                        for (i in 0 until numPleats) {
                            val px = i * pleatW
                            val isRidge = i % 2 == 0
                            val pleatBrush = Brush.horizontalGradient(
                                colors = listOf(
                                    if (isRidge) theme.secondary.copy(alpha = 0.99f) else theme.primary.copy(alpha = 0.93f),
                                    if (isDark) Color(0xFF0C0617) else Color(0xFF281331),
                                    if (isRidge) theme.secondary.copy(alpha = 0.96f) else theme.accent.copy(alpha = 0.90f)
                                ),
                                startX = px,
                                endX = px + pleatW
                            )
                            drawRect(
                                brush = pleatBrush,
                                topLeft = Offset(px, 0f),
                                size = Size(pleatW + 0.5f, h)
                            )
                        }

                        // Right Pleated Silk Drape (mirrored)
                        val rightStartX = w - curtainW
                        for (i in 0 until numPleats) {
                            val px = rightStartX + (i * pleatW)
                            val isRidge = i % 2 == 1
                            val pleatBrush = Brush.horizontalGradient(
                                colors = listOf(
                                    if (isRidge) theme.accent.copy(alpha = 0.93f) else theme.primary.copy(alpha = 0.93f),
                                    if (isDark) Color(0xFF0C0617) else Color(0xFF281331),
                                    if (isRidge) theme.secondary.copy(alpha = 0.99f) else theme.secondary.copy(alpha = 0.90f)
                                ),
                                startX = px,
                                endX = px + pleatW
                            )
                            drawRect(
                                brush = pleatBrush,
                                topLeft = Offset(px, 0f),
                                size = Size(pleatW + 0.5f, h)
                            )
                        }

                        // Gold braided cords along the meeting edges
                        val cordColor = Color(0xFFFFD166)
                        drawLine(
                            color = cordColor,
                            start = Offset(curtainW, 0f),
                            end = Offset(curtainW, h),
                            strokeWidth = 4f
                        )
                        drawLine(
                            color = cordColor,
                            start = Offset(rightStartX, 0f),
                            end = Offset(rightStartX, h),
                            strokeWidth = 4f
                        )

                        // Top curtain valance shadow
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.5f),
                                    Color.Transparent
                                ),
                                startY = 0f,
                                endY = 64f
                            ),
                            topLeft = Offset(0f, 0f),
                            size = Size(w, 64f)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Clean, elegant Curtain Flip Action Button (all extra sliders & tooltips removed!)
        Button(
            onClick = { performCurtainFlip() },
            colors = ButtonDefaults.buttonColors(
                containerColor = theme.primary,
                contentColor = if (isDark) Color(0xFF070B12) else Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("curtain_flip_bio_button")
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
            Icon(
                imageVector = Icons.Default.Sync,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
