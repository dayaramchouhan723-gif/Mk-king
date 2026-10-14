package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.KyraBlue
import com.example.ui.theme.KyraCyan
import com.example.ui.theme.MyraGreen
import com.example.ui.theme.MyraRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.MainViewModel

@Composable
fun VisionScanScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scannedText by viewModel.scannedOcrText.collectAsState()
    val isScanning by viewModel.isOcrScanning.collectAsState()

    val infiniteTransition = rememberInfiniteTransition(label = "LaserScan")
    val laserY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "LaserY"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .padding(bottom = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Screen Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "EDITH VISION & OCR AGENT",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Text(
                    text = "Text Recognition, Media Parsing & OTP Regex",
                    fontSize = 11.sp,
                    color = KyraCyan
                )
            }
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MyraRed.copy(alpha = 0.2f),
                border = BorderStroke(1.dp, MyraRed)
            ) {
                Text(
                    text = "ML CORE",
                    color = MyraRed,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Scanner Viewfinder Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            border = BorderStroke(1.5.dp, Brush.horizontalGradient(listOf(KyraBlue, MyraRed)))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // Viewfinder grid & laser Canvas
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // Corner brackets
                    val stroke = 3.dp.toPx()
                    val cornerLen = 28.dp.toPx()

                    // Top-Left
                    drawLine(KyraCyan, Offset(16f, 16f), Offset(16f + cornerLen, 16f), stroke)
                    drawLine(KyraCyan, Offset(16f, 16f), Offset(16f, 16f + cornerLen), stroke)

                    // Top-Right
                    drawLine(KyraCyan, Offset(w - 16f, 16f), Offset(w - 16f - cornerLen, 16f), stroke)
                    drawLine(KyraCyan, Offset(w - 16f, 16f), Offset(w - 16f, 16f + cornerLen), stroke)

                    // Bottom-Left
                    drawLine(MyraRed, Offset(16f, h - 16f), Offset(16f + cornerLen, h - 16f), stroke)
                    drawLine(MyraRed, Offset(16f, h - 16f), Offset(16f, h - 16f - cornerLen), stroke)

                    // Bottom-Right
                    drawLine(MyraRed, Offset(w - 16f, h - 16f), Offset(w - 16f - cornerLen, h - 16f), stroke)
                    drawLine(MyraRed, Offset(w - 16f, h - 16f), Offset(w - 16f, h - 16f - cornerLen), stroke)

                    // Laser scan line
                    val currentLaserY = 24f + (h - 48f) * laserY
                    drawLine(
                        brush = Brush.horizontalGradient(
                            listOf(Color.Transparent, MyraRed, KyraCyan, Color.Transparent)
                        ),
                        start = Offset(24f, currentLaserY),
                        end = Offset(w - 24f, currentLaserY),
                        strokeWidth = 3.5.dp.toPx()
                    )
                }

                if (isScanning) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = KyraCyan, modifier = Modifier.size(36.dp))
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("EDITH Telemetry Scanning...", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.DocumentScanner, "Viewfinder", tint = KyraCyan.copy(alpha = 0.6f), modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Position document or text within frame", color = TextSecondary, fontSize = 11.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Trigger Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = { viewModel.simulateOcrScan() },
                modifier = Modifier
                    .weight(1f)
                    .testTag("scan_document_button"),
                colors = ButtonDefaults.buttonColors(containerColor = MyraRed, contentColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.CameraAlt, "Scan", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Scan Document", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = { viewModel.simulateOcrScan() },
                modifier = Modifier
                    .weight(1f)
                    .testTag("extract_otp_button"),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = KyraCyan),
                border = BorderStroke(1.dp, KyraCyan),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Lock, "OTP", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Extract OTP", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // OCR Result Output Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            border = BorderStroke(1.dp, DarkCardBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "PARSED OCR TELEMETRY",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MyraGreen
                    )

                    Row {
                        if (scannedText.isNotBlank()) {
                            IconButton(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("EDITH OCR", scannedText)
                                    clipboard.setPrimaryClip(clip)
                                    Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.ContentCopy, "Copy", tint = KyraCyan, modifier = Modifier.size(16.dp))
                            }

                            IconButton(
                                onClick = {
                                    viewModel.voiceManager.speak(scannedText, viewModel.voicePitch.value, viewModel.voiceSpeed.value)
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.VolumeUp, "Speak", tint = MyraRed, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0F0F14), RoundedCornerShape(10.dp))
                        .border(1.dp, DarkCardBorder, RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = if (scannedText.isNotBlank()) scannedText else "No text parsed yet. Tap 'Scan Document' or 'Extract OTP' above to run EDITH Vision OCR analysis.",
                        color = if (scannedText.isNotBlank()) Color.White else TextSecondary,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}
