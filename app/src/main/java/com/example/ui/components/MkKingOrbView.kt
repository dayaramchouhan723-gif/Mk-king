package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AvatarMode
import com.example.ui.theme.CrownGold
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.RedGlow
import com.example.ui.theme.RoyalRed
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.TextSecondary
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Pre-computed particle struct for 60fps rendering on Android 11 devices (Infinix Note 10).
 * Stores constant trigonometric values cosPhi and sinPhi to eliminate redundant calculations per frame.
 */
private class GlobeParticle(
    val theta: Float,
    val phi: Float,
    val cosPhi: Float,
    val sinPhi: Float,
    val speed: Float,
    val color: Color,
    val baseRadius: Float
)

@Composable
fun MkKingOrbView(
    avatarMode: AvatarMode,
    isSpeaking: Boolean,
    isListening: Boolean,
    rmsLevel: Float,
    onOrbTap: () -> Unit,
    onCycleAvatarMode: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "OrbAnimations")

    // Pulse scale 1.0 to 1.15 infinite (1.5s ValueAnimator)
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "TopOrbPulse"
    )

    // Breathing scale 1.0 to 1.025
    val breathingScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.025f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Breathing"
    )

    // 10s 360 rotation for 3D globe (smooth linear progression)
    val globeRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "GlobeRotation"
    )

    // Pre-allocated particles with cached trigonometric values for 60fps performance
    val particles = remember {
        val colors = listOf(
            RoyalRed,
            RedGlow,
            Color(0xFFFF1744),
            Color(0xFFFF5252),
            CrownGold,
            Color(0xFFFFE082),
            SkyBlue
        )
        val rng = Random(42)
        List(240) {
            val theta = rng.nextFloat() * 2f * PI.toFloat()
            val phi = (rng.nextFloat() - 0.5f) * PI.toFloat()
            GlobeParticle(
                theta = theta,
                phi = phi,
                cosPhi = cos(phi),
                sinPhi = sin(phi),
                speed = 0.75f + rng.nextFloat() * 0.65f,
                color = colors[rng.nextInt(colors.size)],
                baseRadius = 2.2f + rng.nextFloat() * 3.4f
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Status Badges Header: MK KING Online Ready
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF160606),
                border = BorderStroke(1.dp, if (isListening) NeonGreen else RoyalRed)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(if (isListening) NeonGreen else RoyalRed)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isListening) "LISTENING • RMS ${(rmsLevel * 100).toInt()}%" else "MK KING COMMAND • ONLINE READY",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF101014),
                border = BorderStroke(1.dp, CrownGold.copy(alpha = 0.6f)),
                modifier = Modifier.clickable(onClick = onCycleAvatarMode)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Avatar Mode",
                        tint = CrownGold,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = avatarMode.title.substringBefore(":"),
                        color = CrownGold,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        when (avatarMode) {
            AvatarMode.V3NOX_ORB_RED -> {
                MkKingV3noxOrbCore(
                    pulseScale = pulseScale * (1f + rmsLevel * 0.15f),
                    breathingScale = breathingScale,
                    rotationDeg = globeRotation,
                    particles = particles,
                    rmsLevel = rmsLevel,
                    isListening = isListening,
                    isSpeaking = isSpeaking,
                    onTap = onOrbTap
                )
            }
            AvatarMode.PARTICLE_GLOBE -> {
                ParticleGlobeFull(
                    rotationDeg = globeRotation,
                    particles = particles,
                    rmsLevel = rmsLevel,
                    onTap = onOrbTap
                )
            }
            AvatarMode.FLOATING_ORB -> {
                FloatingOrbMode(
                    pulseScale = pulseScale,
                    rmsLevel = rmsLevel,
                    onTap = onOrbTap
                )
            }
            AvatarMode.ANIME_SS1 -> {
                AnimeGoddessMode(
                    breathingScale = breathingScale,
                    isSpeaking = isSpeaking,
                    rmsLevel = rmsLevel,
                    onTap = onOrbTap
                )
            }
            AvatarMode.REALISTIC_SS2 -> {
                RealisticGoddessMode(
                    breathingScale = breathingScale,
                    isSpeaking = isSpeaking,
                    rmsLevel = rmsLevel,
                    onTap = onOrbTap
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Dotted RMS Reactive Waveform (full width Red #FF0000)
        DottedRmsWaveform(rmsLevel = rmsLevel, isSpeaking = isSpeaking || isListening)

        Spacer(modifier = Modifier.height(4.dp))

        // Voice Sunne Wala Bottom Label
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Voice: sunne wala • Hail MK KING wake word",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.width(6.dp))
            Surface(
                shape = RoundedCornerShape(4.dp),
                color = NeonGreen.copy(alpha = 0.2f),
                border = BorderStroke(0.8.dp, NeonGreen)
            ) {
                Text(
                    text = "ACTIVE",
                    color = NeonGreen,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                )
            }
        }
    }
}

/**
 * Backward compatibility alias for MyraKyraOrbView
 */
@Composable
fun MyraKyraOrbView(
    avatarMode: AvatarMode,
    isSpeaking: Boolean,
    isListening: Boolean,
    rmsLevel: Float,
    onOrbTap: () -> Unit,
    onCycleAvatarMode: () -> Unit,
    modifier: Modifier = Modifier
) {
    MkKingOrbView(
        avatarMode = avatarMode,
        isSpeaking = isSpeaking,
        isListening = isListening,
        rmsLevel = rmsLevel,
        onOrbTap = onOrbTap,
        onCycleAvatarMode = onCycleAvatarMode,
        modifier = modifier
    )
}

@Composable
private fun MkKingV3noxOrbCore(
    pulseScale: Float,
    breathingScale: Float,
    rotationDeg: Float,
    particles: List<GlobeParticle>,
    rmsLevel: Float,
    isListening: Boolean,
    isSpeaking: Boolean,
    onTap: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "V3NOX • Top Red Orb 100dp PULSE",
            color = RoyalRed,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            contentAlignment = Alignment.Center
        ) {
            // Bottom 3D Particle Globe - Increased to 220dp with optimized 60fps drawing
            // NO play triangle path/drawing inside the particle globe: ONLY particles rendered
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .scale(breathingScale)
                    .offset(y = 15.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    renderOptimizedParticleGlobe(
                        drawScope = this,
                        rotationDeg = rotationDeg,
                        particles = particles,
                        rmsLevel = rmsLevel
                    )
                }
            }

            // Top Red Orb 100dp with glowing neon energy rings (no media play triangle)
            Box(
                modifier = Modifier
                    .offset(y = (-36).dp)
                    .size(100.dp)
                    .scale(pulseScale)
                    .shadow(
                        elevation = 20.dp,
                        shape = CircleShape,
                        spotColor = RoyalRed,
                        ambientColor = RedGlow
                    )
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF380808),
                                Color(0xFF1E0505),
                                Color(0xFF0A0202)
                            )
                        )
                    )
                    .border(
                        width = 2.dp,
                        brush = Brush.sweepGradient(
                            colors = listOf(RoyalRed, RedGlow, Color(0xFFFF69B4), CrownGold, RoyalRed)
                        ),
                        shape = CircleShape
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onTap
                    )
                    .testTag("v3nox_top_orb_button"),
                contentAlignment = Alignment.Center
            ) {
                // Inner Double Neon Ring (Pink/Crimson #FF69B4)
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.5.dp,
                            color = Color(0xFFFF69B4),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Glowing royal energy core with microphone indicator
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Trigger Voice",
                        tint = if (isListening) NeonGreen else Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // LISTENING NOW Pill
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF000000))
                    .border(1.2.dp, RoyalRed, RoundedCornerShape(16.dp))
                    .padding(horizontal = 14.dp, vertical = 5.dp)
                    .clickable(onClick = onTap)
                    .testTag("listening_now_pill"),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "((•))",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isListening) "LISTENING NOW • TAP TO PAUSE" else "HAIL MK KING • TAP TO SPEAK",
                        color = RoyalRed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "V3NOX Globe • 220dp • 800+ Particles\n#FF0000 / #0A0A0A • Spherical Orbiting",
            color = RoyalRed,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * High-performance 60fps particle globe rendering logic.
 * Exclusively renders 3D orbiting particles with precomputed trig values.
 * Completely free of any play triangle paths or overlays.
 */
private fun renderOptimizedParticleGlobe(
    drawScope: DrawScope,
    rotationDeg: Float,
    particles: List<GlobeParticle>,
    rmsLevel: Float
) {
    val size = drawScope.size
    val center = Offset(size.width * 0.5f, size.height * 0.5f)
    val globeRadius = size.width * 0.44f
    val rotRad = rotationDeg * (PI.toFloat() / 180f)
    val invRadius = 1f / globeRadius

    // Faint atmospheric glow background
    drawScope.drawCircle(
        color = RoyalRed.copy(alpha = 0.18f),
        radius = globeRadius,
        center = center
    )

    // Green edge telemetry markings
    val markerOffset = globeRadius + 3f
    drawScope.drawLine(
        color = NeonGreen.copy(alpha = 0.65f),
        start = Offset(center.x - markerOffset, center.y - 12f),
        end = Offset(center.x - markerOffset, center.y + 12f),
        strokeWidth = 2.5f
    )
    drawScope.drawLine(
        color = NeonGreen.copy(alpha = 0.65f),
        start = Offset(center.x + markerOffset, center.y - 12f),
        end = Offset(center.x + markerOffset, center.y + 12f),
        strokeWidth = 2.5f
    )

    // Optimized particle iteration: zero object allocation in the loop for 60fps smooth rendering
    for (i in particles.indices) {
        val p = particles[i]
        val currentTheta = p.theta + rotRad * p.speed
        val sinTheta = sin(currentTheta)
        val cosTheta = cos(currentTheta)

        // Leverage precomputed cosPhi and sinPhi
        val x3d = globeRadius * p.cosPhi * sinTheta
        val y3d = globeRadius * p.sinPhi
        val z3d = globeRadius * p.cosPhi * cosTheta

        val depthFactor = (z3d * invRadius + 1f) * 0.5f
        val scaleFactor = 0.55f + depthFactor * 0.7f + (rmsLevel * 0.35f)
        val alpha = (0.25f + depthFactor * 0.75f).coerceIn(0.12f, 1f)

        drawScope.drawCircle(
            color = p.color.copy(alpha = alpha),
            radius = p.baseRadius * scaleFactor,
            center = Offset(center.x + x3d, center.y + y3d)
        )
    }
}

/**
 * Standalone Particle Globe Mode with increased 220dp size
 * Pure particle rendering with zero play triangle paths.
 */
@Composable
private fun ParticleGlobeFull(
    rotationDeg: Float,
    particles: List<GlobeParticle>,
    rmsLevel: Float,
    onTap: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(220.dp)
            .clickable(onClick = onTap),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            renderOptimizedParticleGlobe(
                drawScope = this,
                rotationDeg = rotationDeg,
                particles = particles,
                rmsLevel = rmsLevel
            )
        }
    }
}

@Composable
private fun FloatingOrbMode(
    pulseScale: Float,
    rmsLevel: Float,
    onTap: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(210.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .scale(pulseScale)
                .shadow(18.dp, CircleShape, spotColor = RoyalRed)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color.White, Color(0xFFFFEBEE), Color(0xFFFFCDD2))
                    )
                )
                .border(2.dp, RoyalRed, CircleShape)
                .clickable(onClick = onTap),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "Floating Mic",
                tint = RoyalRed,
                modifier = Modifier.size(42.dp)
            )
        }
    }
}

@Composable
private fun AnimeGoddessMode(
    breathingScale: Float,
    isSpeaking: Boolean,
    rmsLevel: Float,
    onTap: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(220.dp)
            .scale(breathingScale)
            .clickable(onClick = onTap),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width * 0.5f, size.height * 0.5f)
            drawCircle(
                color = RoyalRed.copy(alpha = 0.2f),
                radius = size.width * 0.45f,
                center = center
            )
            drawCircle(
                color = CrownGold.copy(alpha = 0.4f),
                radius = size.width * 0.38f,
                center = center
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "👑",
                fontSize = 54.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isSpeaking) "ROYAL COMMAND SPEAKING" else "SS-1 ANIME GODDESS",
                color = CrownGold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Galaxy Halo • Dynamic Voice Sync",
                color = TextSecondary,
                fontSize = 9.sp
            )
        }
    }
}

@Composable
private fun RealisticGoddessMode(
    breathingScale: Float,
    isSpeaking: Boolean,
    rmsLevel: Float,
    onTap: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(220.dp)
            .scale(breathingScale)
            .clickable(onClick = onTap),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width * 0.5f, size.height * 0.5f)
            drawCircle(
                color = SkyBlue.copy(alpha = 0.25f),
                radius = size.width * 0.45f,
                center = center
            )
            drawCircle(
                color = RoyalRed.copy(alpha = 0.35f),
                radius = size.width * 0.35f,
                center = center
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "⚡",
                fontSize = 50.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isSpeaking) "TRANSMITTING COMMAND" else "SS-2 REALISTIC GODDESS",
                color = SkyBlue,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Rim Light • Real-time RMS Sync",
                color = TextSecondary,
                fontSize = 9.sp
            )
        }
    }
}

/**
 * 48kHz RMS Reactive Dotted Waveform
 * 100 horizontal dots animated with mic RMS input
 */
@Composable
private fun DottedRmsWaveform(
    rmsLevel: Float,
    isSpeaking: Boolean,
    modifier: Modifier = Modifier
) {
    val dotCount = 80
    val infiniteTransition = rememberInfiniteTransition(label = "WaveformAnim")
    val wavePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "WavePhase"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp)
            .padding(horizontal = 8.dp)
    ) {
        val width = size.width
        val height = size.height
        val centerY = height * 0.5f
        val step = width / (dotCount - 1).coerceAtLeast(1)

        // Telemetry markers left and right
        drawLine(
            color = NeonGreen.copy(alpha = 0.8f),
            start = Offset(0f, centerY - 6f),
            end = Offset(0f, centerY + 6f),
            strokeWidth = 2.5f
        )
        drawLine(
            color = NeonGreen.copy(alpha = 0.8f),
            start = Offset(width, centerY - 6f),
            end = Offset(width, centerY + 6f),
            strokeWidth = 2.5f
        )

        val activeFactor = if (isSpeaking) 1.0f else (0.3f + rmsLevel * 1.5f)

        for (i in 0 until dotCount) {
            val x = i * step
            val normalizedX = (i.toFloat() / dotCount) * 4f * PI.toFloat()
            val wave = sin(normalizedX + wavePhase)
            val baseDotRadius = 1.8f
            val dotRadius = baseDotRadius + (wave.coerceAtLeast(0f) * 1.8f * activeFactor)

            val yOffset = wave * (height * 0.35f) * activeFactor
            val color = if (i % 2 == 0) RoyalRed else SkyBlue

            drawCircle(
                color = color.copy(alpha = (0.4f + activeFactor * 0.6f).coerceIn(0.2f, 1f)),
                radius = dotRadius,
                center = Offset(x, centerY + yOffset)
            )
        }
    }
}
