package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
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
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppColorTheme
import com.example.model.ProfileData
import kotlin.math.sin

@Composable
fun TitaniumLuxeCard(
    profile: ProfileData,
    theme: AppColorTheme,
    isDark: Boolean,
    modifier: Modifier = Modifier,
    onFlipClick: () -> Unit = {},
    onActionClick: (String) -> Unit = {}
) {
    val shape = RoundedCornerShape(26.dp)

    // Animated Equalizer / Frequency Wave for telemetry
    val infiniteTransition = rememberInfiniteTransition(label = "equalizer")
    val wavePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wavePhase"
    )

    // Titanium Dark Brushed Alloy Brush
    val titaniumBg = if (isDark) {
        Brush.linearGradient(
            colors = listOf(
                Color(0xFF14171F),
                Color(0xFF1A1F2C),
                Color(0xFF0F1219)
            ),
            start = Offset(0f, 0f),
            end = Offset(900f, 1400f)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                Color(0xFFF1F5F9),
                Color(0xFFE2E8F0),
                Color(0xFFCBD5E1)
            ),
            start = Offset(0f, 0f),
            end = Offset(900f, 1400f)
        )
    }

    val goldLaserBorder = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFD166),
            Color(0xFFB388FF),
            Color(0xFFFFD166).copy(alpha = 0.5f),
            theme.primary
        )
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(28.dp, shape)
                .clip(shape)
                .background(titaniumBg)
                .border(2.dp, goldLaserBorder, shape)
                .drawBehind {
                    // Brushed metal fine diagonal texture
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

                    // Top golden accent bar
                    drawRect(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFFFD166),
                                theme.primary,
                                Color(0xFFFFD166)
                            )
                        ),
                        topLeft = Offset(0f, 0f),
                        size = androidx.compose.ui.geometry.Size(size.width, 4.dp.toPx())
                    )
                }
                .testTag("titanium_card_container")
        ) {
            Column {
                // Top Telemetry Equalizer Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = Color(0xFFFFD166),
                            modifier = Modifier.size(14.dp)
                        )
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

                    // Mini Animated Waveform Canvas
                    Canvas(modifier = Modifier.size(width = 54.dp, height = 16.dp)) {
                        val numBars = 8
                        val barW = size.width / (numBars * 1.6f)
                        for (i in 0 until numBars) {
                            val phase = wavePhase + i * 0.7f
                            val heightRatio = (sin(phase.toDouble()).toFloat() * 0.45f + 0.55f).coerceIn(0.2f, 1f)
                            val barH = size.height * heightRatio
                            val bx = i * (barW * 1.6f)
                            drawRect(
                                color = theme.primary,
                                topLeft = Offset(bx, size.height - barH),
                                size = androidx.compose.ui.geometry.Size(barW, barH)
                            )
                        }
                    }
                }

                ProfileFrontContent(
                    profile = profile,
                    theme = theme,
                    isDark = isDark,
                    onFlipClick = onFlipClick,
                    onActionClick = onActionClick
                )
            }
        }
    }
}
