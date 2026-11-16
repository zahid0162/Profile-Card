package com.example.ui.particles

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import com.example.model.AppColorTheme
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

data class Particle(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    val radius: Float,
    val baseAlpha: Float,
    var currentAlpha: Float,
    val color: Color,
    val pulseSpeed: Float,
    var pulsePhase: Float
)

data class TouchRipple(
    val origin: Offset,
    var radius: Float,
    var maxRadius: Float,
    var alpha: Float,
    val color: Color
)

@Composable
fun DynamicParticleBackground(
    theme: AppColorTheme,
    isDark: Boolean,
    modifier: Modifier = Modifier,
    particleCount: Int = 42,
    speedMultiplier: Float = 1.0f,
    onTouchInteraction: () -> Unit = {}
) {
    val particles = remember { mutableStateListOf<Particle>() }
    val ripples = remember { mutableStateListOf<TouchRipple>() }
    var lastNanoTime by remember { mutableLongStateOf(0L) }

    // Color palette based on theme
    val primaryColor = theme.primary
    val secondaryColor = theme.secondary
    val accentColor = theme.accent
    val bgColor = if (isDark) theme.darkBackground else theme.lightBackground

    // Initialize or resize particles when screen dimensions or count changes
    LaunchedEffect(particleCount, primaryColor, secondaryColor) {
        particles.clear()
        val random = Random(42)
        for (i in 0 until particleCount) {
            val color = when (i % 3) {
                0 -> primaryColor
                1 -> secondaryColor
                else -> accentColor
            }
            particles.add(
                Particle(
                    x = random.nextFloat() * 1080f,
                    y = random.nextFloat() * 2400f,
                    vx = (random.nextFloat() - 0.5f) * 45f * speedMultiplier,
                    vy = (random.nextFloat() - 0.5f) * 45f * speedMultiplier,
                    radius = random.nextFloat() * 2.8f + 1.2f,
                    baseAlpha = if (isDark) random.nextFloat() * 0.5f + 0.3f else random.nextFloat() * 0.35f + 0.15f,
                    currentAlpha = 0.5f,
                    color = color,
                    pulseSpeed = random.nextFloat() * 2.5f + 1.0f,
                    pulsePhase = random.nextFloat() * 6.28f
                )
            )
        }
    }

    // Animation frame loop
    LaunchedEffect(speedMultiplier) {
        while (true) {
            withFrameNanos { frameTimeNanos ->
                if (lastNanoTime == 0L) {
                    lastNanoTime = frameTimeNanos
                    return@withFrameNanos
                }
                val dt = ((frameTimeNanos - lastNanoTime) / 1_000_000_000f).coerceIn(0.001f, 0.05f)
                lastNanoTime = frameTimeNanos

                // Update particles
                for (particle in particles) {
                    particle.x += particle.vx * dt * speedMultiplier
                    particle.y += particle.vy * dt * speedMultiplier

                    // Screen wrap (virtual bounds)
                    if (particle.x < 0f) particle.x += 1080f
                    if (particle.x > 1080f) particle.x -= 1080f
                    if (particle.y < 0f) particle.y += 2400f
                    if (particle.y > 2400f) particle.y -= 2400f

                    particle.pulsePhase += particle.pulseSpeed * dt
                    val pulse = (sin(particle.pulsePhase.toDouble()).toFloat() + 1f) * 0.5f
                    particle.currentAlpha = particle.baseAlpha * (0.6f + 0.4f * pulse)
                }

                // Update ripples
                val rippleIterator = ripples.iterator()
                while (rippleIterator.hasNext()) {
                    val ripple = rippleIterator.next()
                    ripple.radius += 360f * dt
                    ripple.alpha = (1f - (ripple.radius / ripple.maxRadius)).coerceAtLeast(0f)
                    if (ripple.radius >= ripple.maxRadius || ripple.alpha <= 0.01f) {
                        rippleIterator.remove()
                    }
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    onTouchInteraction()
                    ripples.add(
                        TouchRipple(
                            origin = offset,
                            radius = 10f,
                            maxRadius = 320f,
                            alpha = 0.7f,
                            color = primaryColor
                        )
                    )
                    // Push nearby particles away with gentle burst
                    for (p in particles) {
                        val dx = p.x - offset.x
                        val dy = p.y - offset.y
                        val distSq = dx * dx + dy * dy
                        if (distSq < 40000f && distSq > 1f) {
                            val dist = sqrt(distSq)
                            val force = (200f - dist) * 1.5f
                            p.vx += (dx / dist) * force
                            p.vy += (dy / dist) * force
                        }
                    }
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    change.consume()
                    val offset = change.position
                    if (ripples.size < 4) {
                        ripples.add(
                            TouchRipple(
                                origin = offset,
                                radius = 8f,
                                maxRadius = 180f,
                                alpha = 0.45f,
                                color = secondaryColor
                            )
                        )
                    }
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // 1. Atmospheric Ambient Radial Glowing Gradients
            // Top-left ambient glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        primaryColor.copy(alpha = if (isDark) 0.18f else 0.12f),
                        primaryColor.copy(alpha = 0f)
                    ),
                    center = Offset(width * 0.2f, height * 0.18f),
                    radius = width * 0.85f
                ),
                radius = width * 0.85f,
                center = Offset(width * 0.2f, height * 0.18f)
            )

            // Center-bottom ambient secondary glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        secondaryColor.copy(alpha = if (isDark) 0.16f else 0.10f),
                        secondaryColor.copy(alpha = 0f)
                    ),
                    center = Offset(width * 0.82f, height * 0.78f),
                    radius = width * 0.95f
                ),
                radius = width * 0.95f,
                center = Offset(width * 0.82f, height * 0.78f)
            )

            // Accent deep focal glow right behind card center
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        accentColor.copy(alpha = if (isDark) 0.14f else 0.08f),
                        accentColor.copy(alpha = 0f)
                    ),
                    center = Offset(width * 0.5f, height * 0.46f),
                    radius = width * 0.65f
                ),
                radius = width * 0.65f,
                center = Offset(width * 0.5f, height * 0.46f)
            )

            // 2. Interactive Touch Ripples
            for (ripple in ripples) {
                drawCircle(
                    color = ripple.color.copy(alpha = ripple.alpha * 0.4f),
                    radius = ripple.radius,
                    center = ripple.origin,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3.5f)
                )
            }

            // 3. Constellation Proximity Lines
            val maxLineDist = 130f
            val maxLineDistSq = maxLineDist * maxLineDist
            val count = particles.size
            for (i in 0 until count) {
                val p1 = particles[i]
                val scaledX1 = (p1.x / 1080f) * width
                val scaledY1 = (p1.y / 2400f) * height

                for (j in i + 1 until count) {
                    val p2 = particles[j]
                    val scaledX2 = (p2.x / 1080f) * width
                    val scaledY2 = (p2.y / 2400f) * height

                    val dx = scaledX1 - scaledX2
                    val dy = scaledY1 - scaledY2
                    val distSq = dx * dx + dy * dy

                    if (distSq < maxLineDistSq) {
                        val dist = sqrt(distSq)
                        val lineAlpha = (1f - (dist / maxLineDist)) * 0.22f * (if (isDark) 1f else 0.6f)
                        drawLine(
                            color = p1.color.copy(alpha = lineAlpha),
                            start = Offset(scaledX1, scaledY1),
                            end = Offset(scaledX2, scaledY2),
                            strokeWidth = 1.2f
                        )
                    }
                }
            }

            // 4. Render Dynamic Particles with Glowing Halos
            for (p in particles) {
                val px = (p.x / 1080f) * width
                val py = (p.y / 2400f) * height

                // Subtle particle outer glow halo
                drawCircle(
                    color = p.color.copy(alpha = p.currentAlpha * 0.35f),
                    radius = p.radius * 2.8f,
                    center = Offset(px, py)
                )

                // Crisp inner particle core
                drawCircle(
                    color = p.color.copy(alpha = p.currentAlpha),
                    radius = p.radius,
                    center = Offset(px, py)
                )
            }
        }
    }
}
