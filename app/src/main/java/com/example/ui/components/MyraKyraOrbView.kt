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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ViewInAr
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AvatarMode
import com.example.ui.theme.CyberRed
import com.example.ui.theme.CyberRedGlow
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SkyBlueDeep
import com.example.ui.theme.SkyBlueGlow
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private data class GlobeParticle(
    val theta: Float,
    val phi: Float,
    val speed: Float,
    val color: Color,
    val baseRadius: Float
)

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
    val infiniteTransition = rememberInfiniteTransition(label = "OrbAnimations")

    // Pulse scale 1.0 to 1.15 infinite (1.5s)
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
        targetValue = 1.028f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Breathing"
    )

    // 10s 360 rotation for 3D globe
    val globeRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "GlobeRotation"
    )

    // Strictly Red and Sky Blue Particles
    val particles = remember {
        val colors = listOf(
            CyberRed, CyberRedGlow, Color(0xFFFF2255),
            SkyBlue, SkyBlueGlow, Color(0xFF66E5FF)
        )
        val rng = Random(42)
        List(180) {
            GlobeParticle(
                theta = rng.nextFloat() * 2f * PI.toFloat(),
                phi = (rng.nextFloat() - 0.5f) * PI.toFloat(),
                speed = 0.7f + rng.nextFloat() * 0.7f,
                color = colors[rng.nextInt(colors.size)],
                baseRadius = 2.2f + rng.nextFloat() * 3.8f
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Status Badges Header: Red & Sky Blue Theme
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF101420),
                border = BorderStroke(1.dp, if (isListening || isSpeaking) CyberRed else SkyBlue)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(if (isListening) CyberRed else if (isSpeaking) SkyBlueGlow else SkyBlue)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isListening) "V3NOX LISTENING..." else if (isSpeaking) "V3NOX SPEAKING..." else "VIRTUAL ASSISTANT ONLINE READY",
                        color = if (isListening) CyberRed else SkyBlue,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF18101C),
                border = BorderStroke(1.dp, Brush.horizontalGradient(listOf(CyberRed, SkyBlue))),
                modifier = Modifier
                    .clickable(onClick = onCycleAvatarMode)
                    .testTag("avatar_mode_toggle_button")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ViewInAr,
                        contentDescription = "Switch Avatar",
                        tint = SkyBlue,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = avatarMode.title.split(":").firstOrNull() ?: "Avatar",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        when (avatarMode) {
            AvatarMode.V3NOX_DUAL_ORB -> {
                V3noxDualOrbCore(
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
            AvatarMode.ANIME_MYRA -> {
                AnimeGoddessMode(
                    name = "SS-1 • Full Body Anime Myra (Red Halo)",
                    haloColor = CyberRed,
                    dressColor = Color(0xFFC2185B),
                    breathingScale = breathingScale,
                    isSpeaking = isSpeaking,
                    rmsLevel = rmsLevel,
                    onTap = onOrbTap
                )
            }
            AvatarMode.REALISTIC_MYRA -> {
                RealisticGoddessMode(
                    name = "SS-2 • Realistic Human Myra (Red Rim Light)",
                    primaryRim = CyberRed,
                    secondaryRim = SkyBlue,
                    breathingScale = breathingScale,
                    isSpeaking = isSpeaking,
                    rmsLevel = rmsLevel,
                    onTap = onOrbTap
                )
            }
            AvatarMode.ANIME_KYRA -> {
                AnimeGoddessMode(
                    name = "SS-3 • Full Body Anime Kyra (Sky Blue Halo)",
                    haloColor = SkyBlue,
                    dressColor = Color(0xFF0077B6),
                    breathingScale = breathingScale,
                    isSpeaking = isSpeaking,
                    rmsLevel = rmsLevel,
                    onTap = onOrbTap
                )
            }
            AvatarMode.REALISTIC_KYRA -> {
                RealisticGoddessMode(
                    name = "SS-4 • Realistic Human Kyra (Sky Blue Rim)",
                    primaryRim = SkyBlue,
                    secondaryRim = CyberRed,
                    breathingScale = breathingScale,
                    isSpeaking = isSpeaking,
                    rmsLevel = rmsLevel,
                    onTap = onOrbTap
                )
            }
            AvatarMode.DUAL_FUSION -> {
                DualGoddessFusionMode(
                    breathingScale = breathingScale,
                    isSpeaking = isSpeaking,
                    rmsLevel = rmsLevel,
                    onTap = onOrbTap
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Dotted RMS Reactive Waveform (full width Red to Sky Blue)
        DottedRmsWaveform(rmsLevel = rmsLevel, isSpeaking = isSpeaking || isListening)

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "V3NOX DUAL ORB ACTIVE • RMS Reactive Voice Wave 48kHz • Red ♥️ & Sky Blue 🔵",
            color = Color(0xFF718096),
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun V3noxDualOrbCore(
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
        // Top label from screenshot
        Text(
            text = "V3NOX • DUAL ORB ACT/Blue • PULSE",
            color = CyberRed,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp),
            contentAlignment = Alignment.Center
        ) {
            // Bottom 3D Particle Globe (180dp)
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .scale(breathingScale)
                    .offset(y = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val center = Offset(size.width / 2f, size.height / 2f)
                    val globeRadius = size.width * 0.44f
                    val rotRad = (rotationDeg * PI / 180f).toFloat()

                    // Faint radial dual boundary ring
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(CyberRed.copy(alpha = 0.12f), SkyBlue.copy(alpha = 0.15f), Color.Transparent),
                            center = center,
                            radius = globeRadius * 1.15f
                        ),
                        radius = globeRadius,
                        center = center
                    )

                    // Render Red and Sky Blue 3D orbiting particles
                    particles.forEach { p ->
                        val currentTheta = p.theta + rotRad * p.speed
                        val x3d = globeRadius * cos(p.phi) * sin(currentTheta)
                        val y3d = globeRadius * sin(p.phi)
                        val z3d = globeRadius * cos(p.phi) * cos(currentTheta)

                        val depthFactor = ((z3d / globeRadius) + 1f) / 2f
                        val scaleFactor = 0.55f + depthFactor * 0.7f + (rmsLevel * 0.35f)
                        val alpha = (0.28f + depthFactor * 0.72f).coerceIn(0.12f, 1f)

                        drawCircle(
                            color = p.color.copy(alpha = alpha),
                            radius = p.baseRadius * scaleFactor,
                            center = Offset(center.x + x3d, center.y + y3d)
                        )
                    }
                }
            }

            // Top Dual Orb (100dp) with Double Neon Ring: Red & Sky Blue
            Box(
                modifier = Modifier
                    .offset(y = (-30).dp)
                    .size(100.dp)
                    .scale(pulseScale)
                    .shadow(
                        elevation = 20.dp,
                        shape = CircleShape,
                        spotColor = if (isListening) CyberRed else SkyBlue,
                        ambientColor = if (isSpeaking) SkyBlueGlow else CyberRedGlow
                    )
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF24060E),
                                Color(0xFF091624),
                                Color(0xFF06080F)
                            )
                        )
                    )
                    .border(
                        width = 2.dp,
                        brush = Brush.sweepGradient(
                            colors = listOf(CyberRed, SkyBlue, CyberRedGlow, SkyBlueGlow, CyberRed)
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
                // Inner Neon Ring
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.5.dp,
                            brush = Brush.sweepGradient(
                                listOf(SkyBlue, CyberRed, SkyBlueGlow, CyberRedGlow, SkyBlue)
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Play triangle ▶️ center
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Trigger Voice",
                        tint = Color.White,
                        modifier = Modifier.size(34.dp)
                    )
                }
            }
        }

        // Bottom label from screenshot
        Text(
            text = "Bottom Particle Globe • 180dp • 800+ Particles\n#FF0000/#00D2FF • Spherical Orbiting",
            color = SkyBlue,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Composable
private fun ParticleGlobeFull(
    rotationDeg: Float,
    particles: List<GlobeParticle>,
    rmsLevel: Float,
    onTap: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(210.dp)
            .clickable(onClick = onTap),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val globeRadius = size.width * 0.44f
            val rotRad = (rotationDeg * PI / 180f).toFloat()

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(CyberRed.copy(alpha = 0.22f), SkyBlue.copy(alpha = 0.18f), Color.Transparent),
                    center = center,
                    radius = globeRadius * 1.2f
                ),
                radius = globeRadius,
                center = center
            )

            particles.forEach { p ->
                val currentTheta = p.theta + rotRad * p.speed
                val x3d = globeRadius * cos(p.phi) * sin(currentTheta)
                val y3d = globeRadius * sin(p.phi)
                val z3d = globeRadius * cos(p.phi) * cos(currentTheta)

                val depthFactor = ((z3d / globeRadius) + 1f) / 2f
                val scaleFactor = 0.6f + depthFactor * 0.8f + (rmsLevel * 0.5f)
                val alpha = (0.3f + depthFactor * 0.7f).coerceIn(0.1f, 1f)

                drawCircle(
                    color = p.color.copy(alpha = alpha),
                    radius = p.baseRadius * scaleFactor,
                    center = Offset(center.x + x3d, center.y + y3d)
                )
            }
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
            .height(200.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .scale(pulseScale)
                .shadow(18.dp, CircleShape, spotColor = SkyBlue)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color.White, Color(0xFFE0F7FA), Color(0xFF80DEEA))
                    )
                )
                .border(2.5.dp, Brush.sweepGradient(listOf(CyberRed, SkyBlue, CyberRed)), CircleShape)
                .clickable(onClick = onTap),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "VYRON",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0A101D)
                )
                Text(
                    text = "FLOATING ORB",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = CyberRed
                )
            }
        }
    }
}

@Composable
private fun AnimeGoddessMode(
    name: String,
    haloColor: Color,
    dressColor: Color,
    breathingScale: Float,
    isSpeaking: Boolean,
    rmsLevel: Float,
    onTap: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(210.dp)
            .fillMaxWidth()
            .clickable(onClick = onTap),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().scale(breathingScale)) {
            val cx = size.width / 2f
            val cy = size.height / 2f

            // Cosmic Halo
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(haloColor.copy(alpha = 0.35f), Color.Transparent),
                    center = Offset(cx, cy - 30f),
                    radius = 90.dp.toPx()
                ),
                radius = 85.dp.toPx(),
                center = Offset(cx, cy - 30f)
            )

            // Halo Ring
            drawCircle(
                brush = Brush.sweepGradient(listOf(CyberRed, SkyBlue, CyberRed)),
                radius = 65.dp.toPx(),
                center = Offset(cx, cy - 40f),
                style = Stroke(width = 3.dp.toPx())
            )

            // Head Silhouette
            drawCircle(
                color = Color(0xFFFDE8E8),
                radius = 32.dp.toPx(),
                center = Offset(cx, cy - 20f)
            )

            // Hair Strands
            val hairPath = Path().apply {
                moveTo(cx - 36.dp.toPx(), cy - 10f)
                quadraticTo(cx, cy - 65.dp.toPx(), cx + 36.dp.toPx(), cy - 10f)
                lineTo(cx + 42.dp.toPx(), cy + 40f)
                lineTo(cx - 42.dp.toPx(), cy + 40f)
                close()
            }
            drawPath(hairPath, color = dressColor)

            // Eyes
            drawCircle(color = CyberRed, radius = 3.5.dp.toPx(), center = Offset(cx - 12.dp.toPx(), cy - 22f))
            drawCircle(color = SkyBlue, radius = 3.5.dp.toPx(), center = Offset(cx + 12.dp.toPx(), cy - 22f))

            // Lip-sync Mouth
            val mouthHeight = if (isSpeaking) (3f + rmsLevel * 12f).dp.toPx() else 2.dp.toPx()
            drawOval(
                color = CyberRed,
                topLeft = Offset(cx - 6.dp.toPx(), cy - 4f),
                size = androidx.compose.ui.geometry.Size(12.dp.toPx(), mouthHeight)
            )

            // Dress
            val dressPath = Path().apply {
                moveTo(cx - 45.dp.toPx(), cy + 60.dp.toPx())
                lineTo(cx - 16.dp.toPx(), cy + 18.dp.toPx())
                lineTo(cx + 16.dp.toPx(), cy + 18.dp.toPx())
                lineTo(cx + 45.dp.toPx(), cy + 60.dp.toPx())
                close()
            }
            drawPath(dressPath, color = dressColor)
            drawPath(dressPath, color = SkyBlue, style = Stroke(width = 2.dp.toPx()))
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color(0xCC070912), RoundedCornerShape(8.dp))
                .border(1.dp, Brush.horizontalGradient(listOf(CyberRed, SkyBlue)), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Text(name, color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun RealisticGoddessMode(
    name: String,
    primaryRim: Color,
    secondaryRim: Color,
    breathingScale: Float,
    isSpeaking: Boolean,
    rmsLevel: Float,
    onTap: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(210.dp)
            .fillMaxWidth()
            .clickable(onClick = onTap),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().scale(breathingScale)) {
            val cx = size.width / 2f
            val cy = size.height / 2f

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(primaryRim.copy(alpha = 0.35f), secondaryRim.copy(alpha = 0.2f), Color.Transparent),
                    center = Offset(cx, cy),
                    radius = 95.dp.toPx()
                ),
                radius = 90.dp.toPx(),
                center = Offset(cx, cy)
            )

            val chestPath = Path().apply {
                moveTo(cx - 50.dp.toPx(), cy + 55.dp.toPx())
                lineTo(cx, cy + 22.dp.toPx())
                lineTo(cx + 50.dp.toPx(), cy + 55.dp.toPx())
            }
            drawPath(chestPath, color = primaryRim, style = Stroke(width = 4.dp.toPx()))

            drawCircle(color = Color(0xFFE2E8F0), radius = 34.dp.toPx(), center = Offset(cx, cy - 15f))

            drawArc(
                color = secondaryRim,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(cx - 36.dp.toPx(), cy - 50.dp.toPx()),
                size = androidx.compose.ui.geometry.Size(72.dp.toPx(), 70.dp.toPx()),
                style = Stroke(width = 3.dp.toPx())
            )

            drawCircle(color = CyberRed, radius = 3.dp.toPx(), center = Offset(cx - 13.dp.toPx(), cy - 18f))
            drawCircle(color = SkyBlue, radius = 3.dp.toPx(), center = Offset(cx + 13.dp.toPx(), cy - 18f))

            val lipHeight = if (isSpeaking) (2.5f + rmsLevel * 10f).dp.toPx() else 2.dp.toPx()
            drawOval(
                color = CyberRed,
                topLeft = Offset(cx - 7.dp.toPx(), cy - 1f),
                size = androidx.compose.ui.geometry.Size(14.dp.toPx(), lipHeight)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color(0xCC070912), RoundedCornerShape(8.dp))
                .border(1.dp, Brush.horizontalGradient(listOf(primaryRim, secondaryRim)), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Text(name, color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun DualGoddessFusionMode(
    breathingScale: Float,
    isSpeaking: Boolean,
    rmsLevel: Float,
    onTap: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(210.dp)
            .fillMaxWidth()
            .clickable(onClick = onTap),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().scale(breathingScale)) {
            val cx = size.width / 2f
            val cy = size.height / 2f

            // Left Myra Halo (Red)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(CyberRed.copy(alpha = 0.35f), Color.Transparent),
                    center = Offset(cx - 38.dp.toPx(), cy),
                    radius = 65.dp.toPx()
                ),
                radius = 60.dp.toPx(),
                center = Offset(cx - 38.dp.toPx(), cy)
            )

            // Right Kyra Halo (Sky Blue)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(SkyBlue.copy(alpha = 0.35f), Color.Transparent),
                    center = Offset(cx + 38.dp.toPx(), cy),
                    radius = 65.dp.toPx()
                ),
                radius = 60.dp.toPx(),
                center = Offset(cx + 38.dp.toPx(), cy)
            )

            // Intersecting Crown Core
            drawCircle(
                brush = Brush.sweepGradient(listOf(CyberRed, SkyBlue, CyberRed)),
                radius = 24.dp.toPx(),
                center = Offset(cx, cy),
                style = Stroke(width = 2.5.dp.toPx())
            )

            // Myra Left Silhouette
            drawCircle(color = CyberRed, radius = 18.dp.toPx(), center = Offset(cx - 38.dp.toPx(), cy - 10f))
            // Kyra Right Silhouette
            drawCircle(color = SkyBlue, radius = 18.dp.toPx(), center = Offset(cx + 38.dp.toPx(), cy - 10f))
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color(0xCC070912), RoundedCornerShape(8.dp))
                .border(1.dp, Brush.horizontalGradient(listOf(CyberRed, SkyBlue)), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Text("Mode 7 • Dual Goddess Fusion (Myra ♥️ + Kyra 🔵 Together)", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun DottedRmsWaveform(
    rmsLevel: Float,
    isSpeaking: Boolean
) {
    val dotCount = 52

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .padding(horizontal = 8.dp)
    ) {
        val spacing = size.width / (dotCount + 1)
        val cy = size.height / 2f
        val activeRms = if (isSpeaking) rmsLevel.coerceAtLeast(0.18f) else 0.06f

        for (i in 0 until dotCount) {
            val progress = i.toFloat() / dotCount
            val x = spacing * (i + 1)

            val wavePhase = sin((i * 0.45f) + (activeRms * 12f))
            val dotHeight = (3.dp.toPx() + (size.height * 0.75f * activeRms * kotlin.math.abs(wavePhase)))
                .coerceIn(2.5.dp.toPx(), size.height)

            // Gradient strictly from Red #FF0033 to Sky Blue #00D2FF
            val dotColor = when {
                progress < 0.50f -> CyberRed
                else -> SkyBlue
            }

            drawCircle(
                color = dotColor.copy(alpha = (0.5f + activeRms * 0.5f).coerceIn(0.25f, 1f)),
                radius = (dotHeight / 4.5f).coerceIn(1.8.dp.toPx(), 4.dp.toPx()),
                center = Offset(x, cy)
            )
        }
    }
}
