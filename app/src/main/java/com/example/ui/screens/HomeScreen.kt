package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AgentItem
import com.example.model.MasterToolItem
import com.example.ui.components.MyraKyraOrbView
import com.example.ui.theme.CrownGold
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.RedGlow
import com.example.ui.theme.RoyalRed
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onNavigateToChat: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isSpeaking by viewModel.voiceManager.isSpeaking.collectAsState()
    val isListening by viewModel.voiceManager.isRecognizing.collectAsState()
    val rmsLevel by viewModel.voiceManager.rmsLevel.collectAsState()
    val avatarMode by viewModel.avatarMode.collectAsState()
    val energyCount by viewModel.energyCount.collectAsState()
    val isVipUnlocked by viewModel.isVipUnlocked.collectAsState()
    val agents by viewModel.agents.collectAsState()
    val selectedAgent by viewModel.selectedAgentForDetail.collectAsState()
    val toolNotice by viewModel.activeToolNotice.collectAsState()

    val systemUpdate by viewModel.systemUpdateEnabled.collectAsState()
    val wifiEngine by viewModel.wifiEngineEnabled.collectAsState()
    val betaFeatures by viewModel.betaFeaturesEnabled.collectAsState()

    val pcIp by viewModel.pcIp.collectAsState()
    val pcPin by viewModel.pcPin.collectAsState()
    val pcStatus by viewModel.pcStatus.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // BRAND HEADER: Exactly as in screenshot wa_image_1789147253629151277
            item {
                MkKingHeaderSection(
                    energyCount = energyCount,
                    isVip = isVipUnlocked,
                    onShow3DAvatar = { viewModel.cycleAvatarMode() }
                )
            }

            // TOOL NOTICE TOAST
            item {
                AnimatedVisibility(
                    visible = toolNotice != null,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .background(
                                Brush.horizontalGradient(listOf(RoyalRed, RedGlow)),
                                RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = toolNotice ?: "",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // GREETING CARD: Good morning Boss MK KING 👑
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurface),
                    border = BorderStroke(1.2.dp, RoyalRed.copy(alpha = 0.7f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Good morning Boss MK KING",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Black,
                                color = RoyalRed
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "👑",
                                fontSize = 17.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "11-IN-1 MULTI-AGENT HUB :: SONA CHOUHAN EMPIRE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = CrownGold
                        )
                    }
                }
            }

            // TOP CORE: JARVIS (Intelligence & Planning Agents)
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "TOP CORE : JARVIS",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = RoyalRed
                        )
                        Text(
                            text = "Intelligence & Planning Agents",
                            fontSize = 10.sp,
                            color = TextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Row 1: Tasks Agent & Calendar Agent (Red borders, LIVE badges)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        JarvisCoreCard(
                            title = "Tasks Agent",
                            subtitle = "Manage boss priorities, mission checklists and smart deadlines.",
                            badgeText = "LIVE",
                            icon = Icons.Default.Checklist,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                val a = agents.firstOrNull { it.id == "tasks_agent" }
                                a?.let { viewModel.selectAgent(it) }
                            }
                        )

                        JarvisCoreCard(
                            title = "Calendar Agent",
                            subtitle = "Sync calendar agendas, schedule boss meetings and time-blocks.",
                            badgeText = "LIVE",
                            icon = Icons.Default.CalendarMonth,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                val a = agents.firstOrNull { it.id == "calendar_agent" }
                                a?.let { viewModel.selectAgent(it) }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Row 2: Weather Live & Empire Mood
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        JarvisMiniCard(
                            title = "Weather Live",
                            subtitle = "29° Partly cloudy • 70%",
                            icon = Icons.Default.Cloud,
                            badgeText = "LIVE",
                            modifier = Modifier.weight(1f),
                            onClick = {
                                val a = agents.firstOrNull { it.id == "weather_agent" }
                                a?.let { viewModel.selectAgent(it) }
                            }
                        )

                        JarvisMiniCard(
                            title = "Empire Mood",
                            subtitle = "Happy Upbeat • Wed, 14 Oct",
                            icon = Icons.Default.Mood,
                            badgeText = "SYNC",
                            modifier = Modifier.weight(1f),
                            onClick = {
                                val a = agents.firstOrNull { it.id == "empire_mood" }
                                a?.let { viewModel.selectAgent(it) }
                            }
                        )
                    }
                }
            }

            // V3NOX ORB RED + LISTENING NOW PILL
            item {
                Spacer(modifier = Modifier.height(4.dp))
                MyraKyraOrbView(
                    avatarMode = avatarMode,
                    isSpeaking = isSpeaking,
                    isListening = isListening,
                    rmsLevel = rmsLevel,
                    onOrbTap = {
                        if (isSpeaking) {
                            viewModel.voiceManager.stopSpeaking()
                        } else if (isListening) {
                            viewModel.voiceManager.stopListening()
                        } else {
                            viewModel.voiceManager.startListening { query ->
                                if (query.isNotBlank()) {
                                    viewModel.sendPrompt(query)
                                }
                            }
                        }
                    },
                    onCycleAvatarMode = { viewModel.cycleAvatarMode() }
                )
            }

            // RESUME & WELCOME BACK CARD: Exactly as in screenshot
            item {
                Spacer(modifier = Modifier.height(4.dp))
                WelcomeBackCard(
                    onContinue = onNavigateToChat,
                    onNewChat = {
                        viewModel.addChatMessage("Starting fresh session with Boss MK KING.", "SYSTEM")
                        onNavigateToChat()
                    }
                )
            }

            // 24 MASTER TOOLS QUICK LAUNCH
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "24 MASTER TOOLS",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = RoyalRed
                            )
                            Text(
                                text = " • Quick Launch",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = CrownGold
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = RoyalRed.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, RoyalRed)
                        ) {
                            Text(
                                text = "Launch",
                                color = RoyalRed,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                val row1 = viewModel.masterTools.take(12)
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(row1) { tool ->
                        MasterToolChip(tool = tool, onClick = { viewModel.triggerMasterTool(tool) })
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                val row2 = viewModel.masterTools.drop(12)
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(row2) { tool ->
                        MasterToolChip(tool = tool, onClick = { viewModel.triggerMasterTool(tool) })
                    }
                }
            }

            // MY TRIGGERS & GUARDS
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Text(
                        text = "🛠️ My Triggers",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = RoyalRed
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    val triggers by viewModel.triggers.collectAsState()
                    triggers.forEach { trig ->
                        TriggerCardItem(
                            trigger = trig,
                            onToggle = { viewModel.toggleTrigger(trig.id) }
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            }

            // BATCH UPDATE v1.0.66
            item {
                Spacer(modifier = Modifier.height(12.dp))
                BatchUpdateCard(
                    systemUpdate = systemUpdate,
                    wifiEngine = wifiEngine,
                    betaFeatures = betaFeatures,
                    onToggleUpdate = { viewModel.toggleSystemUpdate() },
                    onToggleWifi = { viewModel.toggleWifiEngine() },
                    onToggleBeta = { viewModel.toggleBetaFeatures() }
                )
            }

            // EXTRA AGENTS (EDITH CORE & FRIDAY CORE)
            item {
                Spacer(modifier = Modifier.height(16.dp))
                ExtraAgentsSection(
                    agents = agents,
                    onSelectAgent = { viewModel.selectAgent(it) }
                )
            }

            // PC CONTROLLER
            item {
                Spacer(modifier = Modifier.height(14.dp))
                PcControllerQuickCard(
                    ip = pcIp,
                    pin = pcPin,
                    status = pcStatus,
                    onConnect = { viewModel.testPcConnection() }
                )
            }
        }

        // AGENT DETAIL MODAL DIALOG
        selectedAgent?.let { agent ->
            AgentDetailDialog(
                agent = agent,
                onDismiss = { viewModel.selectAgent(null) },
                onExecute = {
                    viewModel.triggerAgentAction(agent)
                    viewModel.selectAgent(null)
                }
            )
        }
    }
}

@Composable
private fun MkKingHeaderSection(
    energyCount: Int,
    isVip: Boolean,
    onShow3DAvatar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 4.dp)
    ) {
        // Top line: ACTIVE AVATAR & Show 3D Avatar pill (from screenshot)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ACTIVE AVATAR: PARTICLE GLOBE",
                color = RoyalRed,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFF220808),
                border = BorderStroke(1.dp, RoyalRed),
                modifier = Modifier.clickable(onClick = onShow3DAvatar)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.ViewInAr, "3D", tint = RoyalRed, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Show 3D Avatar", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Main Title Row: MK KING 👑 + 0₹ FREE LIFETIME + 3 Energy
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "MK KING ",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = RoyalRed
                    )
                    Text(
                        text = "👑",
                        fontSize = 22.sp
                    )
                }
                Text(
                    text = "ROYAL COMMAND • Sub Designed by MK",
                    fontSize = 10.sp,
                    color = TextSecondary,
                    fontWeight = FontWeight.Medium
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color.Black,
                    border = BorderStroke(1.dp, RoyalRed)
                ) {
                    Text(
                        text = "0₹ FREE LIFETIME",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFF220808),
                    border = BorderStroke(1.dp, RoyalRed)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Bolt, "Energy", tint = RoyalRed, modifier = Modifier.size(13.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text("$energyCount Energy", color = RoyalRed, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun JarvisCoreCard(
    title: String,
    subtitle: String,
    badgeText: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag("core_${title.replace(" ", "_")}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = BorderStroke(1.2.dp, RoyalRed)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, title, tint = RoyalRed, modifier = Modifier.size(18.dp))
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = RoyalRed
                ) {
                    Text(
                        text = badgeText,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = subtitle,
                fontSize = 9.sp,
                color = TextSecondary,
                lineHeight = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun JarvisMiniCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    badgeText: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag("mini_${title.replace(" ", "_")}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = BorderStroke(1.dp, RoyalRed.copy(alpha = 0.8f))
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, title, tint = RoyalRed, modifier = Modifier.size(16.dp))
                Text(badgeText, color = RoyalRed, fontSize = 8.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(title, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(subtitle, color = TextSecondary, fontSize = 8.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
private fun WelcomeBackCard(
    onContinue: () -> Unit,
    onNewChat: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = BorderStroke(1.2.dp, RoyalRed)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.History, "History", tint = RoyalRed, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Welcome back Boss MK KING",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Text("Last you were in Home", color = TextSecondary, fontSize = 9.sp)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFF0F0404),
                border = BorderStroke(0.8.dp, DarkCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text("LAST SESSION SUMMARY", color = RoyalRed, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Boss MK KING: Hail MK KING. Dilber: At your command Boss, all 4 cores are operational.",
                        color = TextPrimary,
                        fontSize = 10.sp,
                        lineHeight = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "⚡ Energy: 3/3  ☁️ 29°C Partly cloudy  😊 Mood: Happy Upbeat",
                        color = CrownGold,
                        fontSize = 9.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = onNewChat,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = RoyalRed),
                    border = BorderStroke(1.dp, RoyalRed),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("new_chat_button")
                ) {
                    Text("New Chat", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onContinue,
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalRed, contentColor = Color.White),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("continue_session_button")
                ) {
                    Text("Continue", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold)
                }
            }
        }
    }
}

@Composable
private fun MasterToolChip(
    tool: MasterToolItem,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .testTag("tool_${tool.id}")
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color(tool.colorHex).copy(alpha = 0.22f))
                .border(1.5.dp, Color(tool.colorHex), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = tool.name.take(2).uppercase(),
                color = Color(tool.colorHex),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = tool.name,
            color = Color.White,
            fontSize = 9.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1
        )
    }
}

@Composable
private fun TriggerCardItem(
    trigger: com.example.model.TriggerSetting,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = BorderStroke(1.dp, if (trigger.isGranted) NeonGreen.copy(alpha = 0.4f) else RoyalRed.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = trigger.title,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = trigger.description,
                    fontSize = 9.sp,
                    color = TextSecondary,
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Button(
                onClick = onToggle,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (trigger.isGranted) NeonGreen else RoyalRed,
                    contentColor = if (trigger.isGranted) Color.Black else Color.White
                ),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(if (trigger.isGranted) "Granted" else "Grant", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun BatchUpdateCard(
    systemUpdate: Boolean,
    wifiEngine: Boolean,
    betaFeatures: Boolean,
    onToggleUpdate: () -> Unit,
    onToggleWifi: () -> Unit,
    onToggleBeta: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF220808)),
        border = BorderStroke(1.dp, RoyalRed)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Batch Update v1.0.66",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Text("READY", color = RoyalRed, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("System Update Auto-Patch", color = Color.White, fontSize = 11.sp)
                Switch(
                    checked = systemUpdate,
                    onCheckedChange = { onToggleUpdate() },
                    colors = SwitchDefaults.colors(checkedThumbColor = RoyalRed, checkedTrackColor = RoyalRed.copy(alpha = 0.4f))
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("WiFi Telemetry Engine", color = Color.White, fontSize = 11.sp)
                Switch(
                    checked = wifiEngine,
                    onCheckedChange = { onToggleWifi() },
                    colors = SwitchDefaults.colors(checkedThumbColor = RoyalRed, checkedTrackColor = RoyalRed.copy(alpha = 0.4f))
                )
            }
        }
    }
}

@Composable
private fun ExtraAgentsSection(
    agents: List<AgentItem>,
    onSelectAgent: (AgentItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "MYRA 4-CORE EXTRA AGENTS • 11 AGENTS ACTIVE",
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            color = RoyalRed
        )

        Spacer(modifier = Modifier.height(8.dp))

        // EDITH CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            border = BorderStroke(1.dp, RoyalRed.copy(alpha = 0.6f))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("EDITH CORE", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = RoyalRed)
                Text("Vision • Media • Memory • Files • Media Launcher Agent • OCR Scan Agent • WiFi Agent • Memories Voice Free", fontSize = 10.sp, color = TextSecondary, lineHeight = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // FRIDAY CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            border = BorderStroke(1.dp, RoyalRed.copy(alpha = 0.6f))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Communication • Notifications", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("V3N0X 21 new messages", color = RoyalRed, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text("WhatsApp • SMS • Call • Email • Notifications • OTP Missions • OTP regex • File Manager • Open App", fontSize = 10.sp, color = TextSecondary, lineHeight = 14.sp)
            }
        }
    }
}

@Composable
private fun PcControllerQuickCard(
    ip: String,
    pin: String,
    status: String,
    onConnect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = BorderStroke(1.dp, RoyalRed.copy(alpha = 0.6f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Computer, "PC", tint = RoyalRed, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("PC Connect & Controller", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
                Text("ws://$ip:$pin", color = CrownGold, fontSize = 9.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text("Status: $status", color = if (status.contains("CONNECTED")) NeonGreen else TextSecondary, fontSize = 10.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onConnect,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = RoyalRed, contentColor = Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Test Connection to PC Bridge", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun AgentDetailDialog(
    agent: AgentItem,
    onDismiss: () -> Unit,
    onExecute: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(agent.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        },
        text = {
            Column {
                Text(agent.role, color = RoyalRed, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(agent.description, color = Color.White, fontSize = 12.sp, lineHeight = 16.sp)
            }
        },
        confirmButton = {
            Button(
                onClick = onExecute,
                colors = ButtonDefaults.buttonColors(containerColor = RoyalRed, contentColor = Color.White)
            ) {
                Text("Execute Directive", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = TextSecondary)
            }
        },
        containerColor = DarkSurface,
        shape = RoundedCornerShape(14.dp)
    )
}
