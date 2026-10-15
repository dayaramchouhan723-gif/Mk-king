package com.example.ui.screens

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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ChatMessage
import com.example.model.GoddessType
import com.example.ui.theme.CrownGold
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.KyraBlue
import com.example.ui.theme.KyraCyan
import com.example.ui.theme.KyraPink
import com.example.ui.theme.KyraPurple
import com.example.ui.theme.MyraGreen
import com.example.ui.theme.MyraRed
import com.example.ui.theme.RoyalRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.MainViewModel

@Composable
fun ChatScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val messages by viewModel.chatMessages.collectAsState()
    val activeGoddess by viewModel.activeGoddess.collectAsState()
    val persona by viewModel.currentPersona.collectAsState()
    val isSpeaking by viewModel.voiceManager.isSpeaking.collectAsState()
    val isListening by viewModel.voiceManager.isRecognizing.collectAsState()

    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    val quickPrompts = listOf(
        "Analyze System Cores",
        "Fashion & UI Advice",
        "Scan WiFi Ping",
        "Play Synth Tone",
        "Boss Mission Schedule",
        "Weather Forecast"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .imePadding()
    ) {
        // Chat Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = DarkSurface,
            border = BorderStroke(1.dp, DarkCardBorder)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = when (activeGoddess) {
                                GoddessType.MK_KING -> "MK KING 👑 ROYAL COMMAND"
                                GoddessType.MYRA -> "MYRA COMMAND"
                                GoddessType.KYRA -> "KYRA HARMONY"
                                GoddessType.DUAL -> "DUAL GODDESS COMMAND"
                            },
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            color = when (activeGoddess) {
                                GoddessType.MK_KING -> CrownGold
                                GoddessType.MYRA -> MyraRed
                                GoddessType.KYRA -> KyraBlue
                                GoddessType.DUAL -> KyraCyan
                            }
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(if (isSpeaking) MyraGreen else if (isListening) MyraRed else Color(0xFF6B7280))
                        )
                    }
                    Text(
                        text = "Persona: ${persona.name}",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }

                // Stop speaking button if TTS active
                if (isSpeaking) {
                    IconButton(
                        onClick = { viewModel.voiceManager.stopSpeaking() },
                        modifier = Modifier
                            .size(36.dp)
                            .background(MyraRed.copy(alpha = 0.2f), CircleShape)
                            .border(1.dp, MyraRed, CircleShape)
                    ) {
                        Icon(Icons.Default.Stop, "Stop TTS", tint = MyraRed, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }

        // Messages List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = listState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages) { msg ->
                ChatMessageItem(
                    message = msg,
                    onPlayAudio = {
                        viewModel.voiceManager.speak(msg.text, viewModel.voicePitch.value, viewModel.voiceSpeed.value)
                    }
                )
            }
        }

        // Quick Prompts Row
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(quickPrompts) { prompt ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF161824),
                    border = BorderStroke(1.dp, DarkCardBorder),
                    modifier = Modifier.clickable {
                        viewModel.sendPrompt(prompt)
                    }
                ) {
                    Text(
                        text = prompt,
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Input Bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = DarkSurface,
            border = BorderStroke(1.dp, DarkCardBorder)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = {
                        Text(
                            text = "Ask Myra & Kyra anything…",
                            color = TextSecondary,
                            fontSize = 13.sp
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("chat_input_field"),
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = KyraCyan,
                        unfocusedBorderColor = DarkCardBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        cursorColor = KyraCyan
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Mic Button
                IconButton(
                    onClick = {
                        if (isListening) {
                            viewModel.voiceManager.stopListening()
                        } else {
                            viewModel.voiceManager.startListening { query ->
                                if (query.isNotBlank()) {
                                    viewModel.sendPrompt(query)
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(if (isListening) MyraRed else Color(0xFF222230))
                        .border(1.dp, if (isListening) MyraRed else KyraBlue, CircleShape)
                        .testTag("mic_input_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Mic Voice Input",
                        tint = if (isListening) Color.White else KyraCyan,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                // Send Button
                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            viewModel.sendPrompt(inputText)
                            inputText = ""
                        }
                    },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(listOf(MyraRed, KyraBlue))
                        )
                        .testTag("send_message_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatMessageItem(
    message: ChatMessage,
    onPlayAudio: () -> Unit
) {
    val isUser = message.sender == "USER"

    val alignment = if (isUser) Alignment.End else Alignment.Start
    val containerColor = when (message.sender) {
        "USER" -> Color(0xFF1E2438)
        "MK_KING" -> Color(0xFF2E0A0A)
        "MYRA" -> Color(0xFF200A10)
        "KYRA" -> Color(0xFF0C1628)
        "DUAL_GODDESS" -> Color(0xFF160F26)
        else -> Color(0xFF181820)
    }

    val borderColor = when (message.sender) {
        "USER" -> KyraBlue.copy(alpha = 0.5f)
        "MK_KING" -> RoyalRed.copy(alpha = 0.8f)
        "MYRA" -> MyraRed.copy(alpha = 0.6f)
        "KYRA" -> KyraBlue.copy(alpha = 0.6f)
        "DUAL_GODDESS" -> KyraCyan.copy(alpha = 0.7f)
        else -> DarkCardBorder
    }

    val senderName = when (message.sender) {
        "USER" -> "Boss"
        "MK_KING" -> "MK KING 👑 (Royal Red Core)"
        "MYRA" -> "MYRA (Red Core)"
        "KYRA" -> "KYRA (Blue Core)"
        "DUAL_GODDESS" -> "MYRA KYRA (Dual Goddess)"
        else -> "SYSTEM"
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Text(
            text = senderName,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = when (message.sender) {
                "USER" -> KyraCyan
                "MK_KING" -> CrownGold
                "MYRA" -> MyraRed
                "KYRA" -> KyraBlue
                "DUAL_GODDESS" -> KyraPink
                else -> TextSecondary
            },
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = containerColor),
            border = BorderStroke(1.dp, borderColor),
            modifier = Modifier.widthIn(max = 320.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = message.text,
                    color = TextPrimary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )

                if (!isUser) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onPlayAudio,
                            modifier = Modifier.size(26.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Play Aloud",
                                tint = KyraCyan,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
