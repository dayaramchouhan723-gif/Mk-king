package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppRepository
import com.example.model.AvatarMode
import com.example.model.GoddessType
import com.example.model.Persona
import com.example.model.VoiceProfile
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.KyraBlue
import com.example.ui.theme.KyraCyan
import com.example.ui.theme.KyraPink
import com.example.ui.theme.KyraPurple
import com.example.ui.theme.MyraGreen
import com.example.ui.theme.MyraRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isVipUnlocked by viewModel.isVipUnlocked.collectAsState()
    val vipMessage by viewModel.vipMessage.collectAsState()
    val currentPersona by viewModel.currentPersona.collectAsState()
    val currentVoice by viewModel.currentVoice.collectAsState()
    val voicePitch by viewModel.voicePitch.collectAsState()
    val voiceSpeed by viewModel.voiceSpeed.collectAsState()
    val avatarMode by viewModel.avatarMode.collectAsState()

    val pcIp by viewModel.pcIp.collectAsState()
    val pcPin by viewModel.pcPin.collectAsState()
    val pcStatus by viewModel.pcStatus.collectAsState()

    var vipInput by remember { mutableStateOf("") }
    var voiceDropdownExpanded by remember { mutableStateOf(false) }
    var wakeSensitivity by remember { mutableFloatStateOf(0.7f) }

    var inputIp by remember { mutableStateOf(pcIp) }
    var inputPin by remember { mutableStateOf(pcPin) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Settings Header
        item {
            Column {
                Text(
                    text = "MYRA KYRA PREFERENCES",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Text(
                    text = "VIP Tokens, 12 Personas, 40+ Voices & Hardware Matrix",
                    fontSize = 11.sp,
                    color = KyraCyan
                )
            }
        }

        // SECRET VIP CODE OMG CARD
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = if (isVipUnlocked) Color(0xFF261808) else DarkSurface),
                border = BorderStroke(1.5.dp, if (isVipUnlocked) GoldAccent else DarkCardBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Stars, "VIP", tint = GoldAccent, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Secret VIP Code OMG",
                                color = GoldAccent,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isVipUnlocked) MyraGreen.copy(alpha = 0.2f) else Color(0xFF2A2A38),
                            border = BorderStroke(1.dp, if (isVipUnlocked) MyraGreen else Color.Gray)
                        ) {
                            Text(
                                text = if (isVipUnlocked) "VIP ACTIVE" else "LOCKED",
                                color = if (isVipUnlocked) MyraGreen else Color.LightGray,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Enter secret code 'OMG' to permanently unlock Lifetime Free VIP + Kitsune GF & Glam Goddess visual effects.",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    if (!isVipUnlocked) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(
                                value = vipInput,
                                onValueChange = { vipInput = it },
                                placeholder = { Text("Enter OMG code", fontSize = 12.sp) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("vip_code_input"),
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = GoldAccent,
                                    unfocusedBorderColor = DarkCardBorder,
                                    focusedTextColor = TextPrimary,
                                    unfocusedTextColor = TextPrimary
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    val success = viewModel.applyVipCode(vipInput)
                                    if (success) {
                                        Toast.makeText(context, "VIP OMG Unlocked!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Invalid Code! Use: OMG", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = GoldAccent, contentColor = Color.Black),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.testTag("unlock_vip_button")
                            ) {
                                Text("Unlock", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    } else {
                        Text(
                            text = vipMessage ?: "VIP OMG Code Verified • Lifetime Free Active",
                            color = MyraGreen,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // 12 PERSONAS SELECTION
        item {
            Text(
                text = "12 DUAL PERSONAS (6 MYRA + 6 KYRA)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(6.dp))
        }

        items(AppRepository.personas) { persona ->
            PersonaCardItem(
                persona = persona,
                isSelected = persona.id == currentPersona.id,
                isVipUnlocked = isVipUnlocked,
                onSelect = {
                    if (persona.isVipOnly && !isVipUnlocked) {
                        Toast.makeText(context, "Enter secret code OMG to unlock this persona!", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.setPersona(persona)
                    }
                }
            )
        }

        // AVATAR MODE SELECTOR
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = BorderStroke(1.dp, KyraCyan.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ViewInAr, "Avatar", tint = KyraCyan, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Avatar Mode Selector", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    AvatarMode.values().forEach { mode ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (mode == avatarMode) KyraCyan.copy(alpha = 0.15f) else Color.Transparent)
                                .clickable { viewModel.setAvatarMode(mode) }
                                .padding(horizontal = 10.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(mode.title, color = if (mode == avatarMode) KyraCyan else Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                Text(mode.subtitle, color = TextSecondary, fontSize = 10.sp)
                            }
                            if (mode == avatarMode) {
                                Icon(Icons.Default.Check, "Selected", tint = KyraCyan, modifier = Modifier.size(16.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }

        // VOICE LIBRARY (40+ VOICES)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = BorderStroke(1.dp, MyraRed.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.RecordVoiceOver, "Voices", tint = MyraRed, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Voice Library (40+ Voices)", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    ExposedDropdownMenuBox(
                        expanded = voiceDropdownExpanded,
                        onExpandedChange = { voiceDropdownExpanded = !voiceDropdownExpanded }
                    ) {
                        OutlinedTextField(
                            value = "${currentVoice.name} (${currentVoice.style}) - ${currentVoice.goddess}",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = voiceDropdownExpanded) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary,
                                focusedBorderColor = MyraRed,
                                unfocusedBorderColor = DarkCardBorder
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = voiceDropdownExpanded,
                            onDismissRequest = { voiceDropdownExpanded = false },
                            modifier = Modifier.background(DarkSurface)
                        ) {
                            AppRepository.voices.forEach { v ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(v.name, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                            Text("${v.style} • ${v.goddess}", color = TextSecondary, fontSize = 10.sp)
                                        }
                                    },
                                    onClick = {
                                        viewModel.setVoiceProfile(v)
                                        voiceDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Pitch Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Pitch: ${String.format("%.2f", voicePitch)}", color = Color.White, fontSize = 11.sp)
                    }
                    Slider(
                        value = voicePitch,
                        onValueChange = { viewModel.setVoicePitch(it) },
                        valueRange = 0.5f..1.6f,
                        colors = SliderDefaults.colors(thumbColor = MyraRed, activeTrackColor = MyraRed)
                    )

                    // Speed Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Speed: ${String.format("%.2f", voiceSpeed)}", color = Color.White, fontSize = 11.sp)
                    }
                    Slider(
                        value = voiceSpeed,
                        onValueChange = { viewModel.setVoiceSpeed(it) },
                        valueRange = 0.5f..1.5f,
                        colors = SliderDefaults.colors(thumbColor = KyraBlue, activeTrackColor = KyraBlue)
                    )

                    Button(
                        onClick = {
                            viewModel.voiceManager.speak("Testing Voice ${currentVoice.name}. All 8 cores operational.", voicePitch, voiceSpeed)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MyraGreen, contentColor = Color.Black),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Preview Current Voice", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }

        // WAKE WORD SETTINGS
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = BorderStroke(1.dp, DarkCardBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Mic, "Wake Words", tint = KyraCyan, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Wake Words: Hail Myra • Hail Kyra • Hail Sona", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Continuous low-power sensitivity: ${String.format("%.1f", wakeSensitivity)}",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                    Slider(
                        value = wakeSensitivity,
                        onValueChange = { wakeSensitivity = it },
                        valueRange = 0.3f..1.0f,
                        colors = SliderDefaults.colors(thumbColor = KyraCyan, activeTrackColor = KyraCyan)
                    )
                }
            }
        }

        // PC CONTROLLER BRIDGE SETTINGS
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = BorderStroke(1.dp, KyraBlue.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Computer, "PC", tint = KyraBlue, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("PC Controller WebSocket Settings", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = inputIp,
                            onValueChange = { inputIp = it },
                            label = { Text("IP Address") },
                            modifier = Modifier.weight(2f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary, focusedBorderColor = KyraBlue)
                        )
                        OutlinedTextField(
                            value = inputPin,
                            onValueChange = { inputPin = it },
                            label = { Text("PIN") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary, focusedBorderColor = KyraBlue)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            viewModel.updatePcConfig(inputIp, inputPin)
                            viewModel.testPcConnection()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = KyraBlue),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save & Ping Bridge", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // ANALOG SYNTH CONTROLLER
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = BorderStroke(1.dp, KyraPink.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.MusicNote, "Synth", tint = KyraPink, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Analog Synth & Sound Lab", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Trigger warm harmonic frequencies via Android native AudioTrack.", color = TextSecondary, fontSize = 11.sp)
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.voiceManager.playSynthTone(220f, 600) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("220Hz", fontSize = 11.sp)
                        }
                        OutlinedButton(
                            onClick = { viewModel.voiceManager.playSynthTone(440f, 600) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("440Hz", fontSize = 11.sp)
                        }
                        OutlinedButton(
                            onClick = { viewModel.voiceManager.playSynthTone(528f, 600) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("528Hz", fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PersonaCardItem(
    persona: Persona,
    isSelected: Boolean,
    isVipUnlocked: Boolean,
    onSelect: () -> Unit
) {
    val borderColor = if (isSelected) {
        if (persona.goddess == GoddessType.MYRA) MyraRed else KyraBlue
    } else DarkCardBorder

    val isLocked = persona.isVipOnly && !isVipUnlocked

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect)
            .testTag("persona_${persona.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) Color(0xFF1C1826) else DarkSurface),
        border = BorderStroke(if (isSelected) 1.5.dp else 1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) (if (persona.goddess == GoddessType.MYRA) MyraRed else KyraBlue) else Color.Transparent)
                    .border(1.dp, if (persona.goddess == GoddessType.MYRA) MyraRed else KyraBlue, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(Icons.Default.Check, "Selected", tint = Color.White, modifier = Modifier.size(12.dp))
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = persona.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    if (persona.isVipOnly) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = GoldAccent.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, GoldAccent)
                        ) {
                            Text("VIP", color = GoldAccent, fontSize = 8.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                        }
                    }
                }
                Text(
                    text = persona.quote,
                    fontSize = 11.sp,
                    color = TextSecondary,
                    lineHeight = 14.sp
                )
            }
        }
    }
}
