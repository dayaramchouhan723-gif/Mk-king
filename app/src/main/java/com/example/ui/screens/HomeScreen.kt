package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AgentItem
import com.example.model.GoddessType
import com.example.model.MasterToolItem
import com.example.ui.components.MyraKyraOrbView
import com.example.ui.theme.CyberRed
import com.example.ui.theme.CyberRedGlow
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.MyraGreen
import com.example.ui.theme.OrangeBadge
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SkyBlueGlow
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
    val activeGoddess by viewModel.activeGoddess.collectAsState()
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

    var agentFilter by remember { mutableStateOf<GoddessType?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // BRAND HEADER (Screenshot 4 exact layout: Red & Sky Blue)
            item {
                BrandHeaderSection(
                    energyCount = energyCount,
                    isVip = isVipUnlocked,
                    activeGoddess = activeGoddess,
                    onSelectGoddess = { viewModel.setGoddess(it) },
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
                                Brush.horizontalGradient(listOf(CyberRed, SkyBlue)),
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

            // GREETING & MULTI-AGENT HUB TITLE
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Good morning Boss Myra Kyra",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFF121422),
                        border = BorderStroke(1.dp, Brush.horizontalGradient(listOf(CyberRed, SkyBlue)))
                    ) {
                        Text(
                            text = "✨ 22-IN-1 DUAL MULTI-AGENT HUB • MYRA KYRA EMPIRE ✨",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = SkyBlue,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // TOP CORE: PLANNING & INTELLIGENCE AGENTS (Row 1 & 2 cards)
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "TOP CORE • MYRA KYRA Intelligence Planning Agents",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    // Row 1: Tasks Agent (Red) & Calendar Agent (Sky Blue)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TopCoreCard(
                            title = "Tasks Agent",
                            subtitle = "Manage boss priorities • mission checklists • smart deadlines",
                            badgeText = "LIVE",
                            badgeColor = MyraGreen,
                            borderColor = CyberRed,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                val a = agents.firstOrNull { it.id == "tasks_agent" }
                                a?.let { viewModel.selectAgent(it) }
                            }
                        )

                        TopCoreCard(
                            title = "Calendar Agent",
                            subtitle = "Sync calendars • agendas • reminders • meetings",
                            badgeText = "SYNC",
                            badgeColor = SkyBlue,
                            borderColor = SkyBlue,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                val a = agents.firstOrNull { it.id == "calendar_agent" }
                                a?.let { viewModel.selectAgent(it) }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Row 2: Weather Live & Mood Agents
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TopCoreCard(
                            title = "Weather Live",
                            subtitle = "29° Partly cloudy • 70% humidity",
                            badgeText = "LIVE",
                            badgeColor = MyraGreen,
                            borderColor = CyberRed,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                val a = agents.firstOrNull { it.id == "weather_agent" }
                                a?.let { viewModel.selectAgent(it) }
                            }
                        )

                        TopCoreCard(
                            title = "Myra Mood Agent",
                            subtitle = "Happy • Upbeat • Wed 14 Oct",
                            badgeText = "SYNC",
                            badgeColor = CyberRed,
                            borderColor = CyberRed,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                val a = agents.firstOrNull { it.id == "myra_mood" }
                                a?.let { viewModel.selectAgent(it) }
                            }
                        )

                        TopCoreCard(
                            title = "Kyra Mood Agent",
                            subtitle = "Calm • Focus • Balanced",
                            badgeText = "SYNC",
                            badgeColor = SkyBlue,
                            borderColor = SkyBlue,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                val a = agents.firstOrNull { it.id == "kyra_mood" }
                                a?.let { viewModel.selectAgent(it) }
                            }
                        )
                    }
                }
            }

            // V3NOX DUAL ORB & AVATAR VIEW (Red & Sky Blue)
            item {
                Spacer(modifier = Modifier.height(6.dp))
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

            // RESUME & WELCOME BACK CARD
            item {
                WelcomeBackCard(
                    onContinue = onNavigateToChat,
                    onNewChat = {
                        viewModel.addChatMessage("Starting fresh session with Boss Myra Kyra.", "SYSTEM")
                        onNavigateToChat()
                    }
                )
            }

            // 24 MASTER TOOLS QUICK LAUNCH
            item {
                Spacer(modifier = Modifier.height(18.dp))
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "✨ 24 MASTER TOOLS",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            Text(
                                text = " • Quick Launch",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = SkyBlue
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SkyBlue.copy(alpha = 0.15f),
                            border = BorderStroke(1.dp, SkyBlue.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = "Launch",
                                color = SkyBlue,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = "One-tap automation & system device triggers",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                // Row 1 of tools (12 tools)
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

                // Row 2 of tools (12 tools)
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

            // MY TRIGGERS & HARDWARE GUARDS
            item {
                Spacer(modifier = Modifier.height(18.dp))
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "⚙️ My Triggers",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = CyberRed
                        )
                        Text(
                            text = "Battery Optimization • Notification OTP • Exact Alarm",
                            fontSize = 10.sp,
                            color = TextSecondary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

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

            // BATCH UPDATE v1.0.66 (Red to Sky Blue Gradient)
            item {
                Spacer(modifier = Modifier.height(16.dp))
                BatchUpdateCard(
                    systemUpdate = systemUpdate,
                    wifiEngine = wifiEngine,
                    betaFeatures = betaFeatures,
                    onToggleUpdate = { viewModel.toggleSystemUpdate() },
                    onToggleWifi = { viewModel.toggleWifiEngine() },
                    onToggleBeta = { viewModel.toggleBetaFeatures() }
                )
            }

            // 22 MULTI-AGENTS EXPANDED SECTION (EDITH, KYRA Special Agent, FRIDAY)
            item {
                Spacer(modifier = Modifier.height(20.dp))
                MultiAgentHubSection(
                    agents = agents,
                    onSelectAgent = { viewModel.selectAgent(it) }
                )
            }

            // PC CONTROLLER QUICK CARD
            item {
                Spacer(modifier = Modifier.height(16.dp))
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
private fun BrandHeaderSection(
    energyCount: Int,
    isVip: Boolean,
    activeGoddess: GoddessType,
    onSelectGoddess: (GoddessType) -> Unit,
    onShow3DAvatar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 4.dp)
    ) {
        // Badges Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = OrangeBadge.copy(alpha = 0.2f),
                border = BorderStroke(1.dp, OrangeBadge)
            ) {
                Text(
                    text = "0₹ FREE LIFETIME",
                    color = OrangeBadge,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = SkyBlue.copy(alpha = 0.2f),
                border = BorderStroke(1.dp, SkyBlue)
            ) {
                Text(
                    text = "DUAL GODDESS COMMAND",
                    color = SkyBlue,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = "Energy",
                    tint = SkyBlue,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "$energyCount/3 Energy",
                    color = SkyBlue,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Title with Pure Red & Sky Blue Dual Gradient
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "MYRA ",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = CyberRed
                    )
                    Text(
                        text = "KYRA ",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = SkyBlue
                    )
                    Text(
                        text = "v2.0",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
                Text(
                    text = "Designed by Myra & Kyra • ACTIVE AVATAR PARTICLE GLOBE DUAL",
                    fontSize = 10.sp,
                    color = TextSecondary
                )
            }

            if (isVip) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = GoldAccent.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, GoldAccent)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Stars, "VIP", tint = GoldAccent, modifier = Modifier.size(13.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text("VIP OMG", color = GoldAccent, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Show 3D Avatar Center Pill Button
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFF141A28),
            border = BorderStroke(1.dp, Brush.horizontalGradient(listOf(CyberRed, SkyBlue))),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable(onClick = onShow3DAvatar)
                .testTag("show_3d_avatar_center_button")
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.ViewInAr, "3D", tint = SkyBlue, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Show 3D Avatar", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Goddess Mode Toggle: DUAL vs MYRA (Red) vs KYRA (Sky Blue)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F131D), RoundedCornerShape(12.dp))
                .border(1.dp, DarkCardBorder, RoundedCornerShape(12.dp))
                .padding(3.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GoddessTabItem(
                title = "⚡ DUAL GODDESS",
                isSelected = activeGoddess == GoddessType.DUAL,
                activeColor = SkyBlue,
                onClick = { onSelectGoddess(GoddessType.DUAL) }
            )
            GoddessTabItem(
                title = "♥️ MYRA CORE",
                isSelected = activeGoddess == GoddessType.MYRA,
                activeColor = CyberRed,
                onClick = { onSelectGoddess(GoddessType.MYRA) }
            )
            GoddessTabItem(
                title = "🔵 KYRA CORE",
                isSelected = activeGoddess == GoddessType.KYRA,
                activeColor = SkyBlue,
                onClick = { onSelectGoddess(GoddessType.KYRA) }
            )
        }
    }
}

@Composable
private fun GoddessTabItem(
    title: String,
    isSelected: Boolean,
    activeColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(9.dp))
            .background(if (isSelected) activeColor.copy(alpha = 0.35f) else Color.Transparent)
            .border(
                width = if (isSelected) 1.dp else 0.dp,
                color = if (isSelected) activeColor else Color.Transparent,
                shape = RoundedCornerShape(9.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = if (isSelected) Color.White else TextSecondary,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
private fun TopCoreCard(
    title: String,
    subtitle: String,
    badgeText: String,
    badgeColor: Color,
    borderColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag("top_core_${title.replace(" ", "_")}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF10131E)),
        border = BorderStroke(1.dp, borderColor.copy(alpha = 0.6f))
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = badgeColor.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = badgeText,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        color = badgeColor,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

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
private fun WelcomeBackCard(
    onContinue: () -> Unit,
    onNewChat: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = BorderStroke(1.2.dp, Brush.horizontalGradient(listOf(CyberRed.copy(alpha = 0.7f), SkyBlue.copy(alpha = 0.7f))))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Welcome back Boss Myra Kyra",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = SkyBlue.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, SkyBlue.copy(alpha = 0.6f))
                ) {
                    Text(
                        text = "LAST SESSION SUMMARY",
                        color = SkyBlue,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Boss Myra • Hail Myra • Hail Kyra • At your command Boss • all 8 cores operational • Energy 3/3 • 29°C Partly cloudy • Mood Happy Upbeat • Focus ✓",
                color = TextSecondary,
                fontSize = 11.sp,
                lineHeight = 15.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = onNewChat,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberRed),
                    border = BorderStroke(1.dp, CyberRed),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("new_chat_button")
                ) {
                    Text("New Chat", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onContinue,
                    colors = ButtonDefaults.buttonColors(containerColor = SkyBlue, contentColor = Color(0xFF070B14)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("continue_session_button")
                ) {
                    Text("Continue", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
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
                .size(52.dp)
                .clip(CircleShape)
                .background(Color(tool.colorHex).copy(alpha = 0.22f))
                .border(1.5.dp, Color(tool.colorHex), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = tool.name.take(2).uppercase(),
                color = Color(tool.colorHex),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 13.sp
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = tool.name,
            color = Color.White,
            fontSize = 10.sp,
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
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF101420)),
        border = BorderStroke(1.dp, if (trigger.isGranted) SkyBlue.copy(alpha = 0.4f) else CyberRed.copy(alpha = 0.4f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = trigger.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = trigger.description,
                    fontSize = 10.sp,
                    color = TextSecondary,
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onToggle,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (trigger.isGranted) SkyBlue else CyberRed,
                    contentColor = if (trigger.isGranted) Color(0xFF070B14) else Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(if (trigger.isGranted) "Granted" else "Grant", fontSize = 11.sp, fontWeight = FontWeight.Bold)
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
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C0D24)),
        border = BorderStroke(1.2.dp, Brush.horizontalGradient(listOf(CyberRed, SkyBlue)))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Batch Update v1.0.66",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text(
                        text = "System updates & dual goddess core modules",
                        fontSize = 10.sp,
                        color = TextSecondary
                    )
                }
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = SkyBlue.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, SkyBlue)
                ) {
                    Text(
                        text = "READY",
                        color = SkyBlue,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("System Update Auto-Patch", color = Color.White, fontSize = 11.sp)
                Switch(
                    checked = systemUpdate,
                    onCheckedChange = { onToggleUpdate() },
                    colors = SwitchDefaults.colors(checkedThumbColor = CyberRed, checkedTrackColor = CyberRed.copy(alpha = 0.4f))
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
                    colors = SwitchDefaults.colors(checkedThumbColor = SkyBlue, checkedTrackColor = SkyBlue.copy(alpha = 0.4f))
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Beta Experimental Cores", color = Color.White, fontSize = 11.sp)
                Switch(
                    checked = betaFeatures,
                    onCheckedChange = { onToggleBeta() },
                    colors = SwitchDefaults.colors(checkedThumbColor = SkyBlueGlow, checkedTrackColor = SkyBlueGlow.copy(alpha = 0.4f))
                )
            }
        }
    }
}

@Composable
private fun MultiAgentHubSection(
    agents: List<AgentItem>,
    onSelectAgent: (AgentItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "⚙️ MYRA KYRA 4-CORE EXTRA AGENTS",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Text(
                    text = "22 AGENTS ACTIVE • Voice Library 40+ Voices",
                    fontSize = 10.sp,
                    color = SkyBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // EDITH CARD (Vision, Media, Memory, OCR, WiFi)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF10131E)),
            border = BorderStroke(1.dp, CyberRed.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("EDITH CORE", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = CyberRed)
                Text("Vision • Media • Memory • Files • Media Launcher Agent • OCR Scan Agent • WiFi Agent • Memories Voice Free", fontSize = 11.sp, color = TextSecondary, lineHeight = 15.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // KYRA SPECIAL AGENT CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF101524)),
            border = BorderStroke(1.dp, SkyBlue.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("KYRA SPECIAL AGENT", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SkyBlue)
                Text("Design • Beauty • Fashion • Aesthetic • UI/UX • Avatar Studio • Haute Couture Curation", fontSize = 11.sp, color = TextSecondary, lineHeight = 15.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // FRIDAY CARD (Communication & Notifications)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF12121E)),
            border = BorderStroke(1.dp, Brush.horizontalGradient(listOf(CyberRed, SkyBlue)))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("FRIDAY CORE • Communication & Notifications", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = CyberRed.copy(alpha = 0.2f)
                    ) {
                        Text("V3NOX 21 new msgs", color = CyberRed, fontSize = 8.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("WhatsApp • SMS • Call • Email Notifications • OTP Agent Missions • OTP regex • File Manager • Open App", fontSize = 11.sp, color = TextSecondary, lineHeight = 15.sp)
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
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF101524)),
        border = BorderStroke(1.dp, SkyBlue.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Computer, "PC", tint = SkyBlue, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("PC Connect & Controller", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = SkyBlue.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, SkyBlue)
                ) {
                    Text(
                        text = "ws://$ip:$pin",
                        color = SkyBlue,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Status: $status",
                color = if (status.contains("CONNECTED")) SkyBlue else TextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onConnect,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = SkyBlue, contentColor = Color(0xFF070B14)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Test Connection to PC Bridge", fontSize = 12.sp, fontWeight = FontWeight.Bold)
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = agent.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        },
        text = {
            Column {
                Text(
                    text = "Goddess Core: ${if (agent.goddess == GoddessType.MYRA) "MYRA (Red Core)" else "KYRA (Sky Blue Core)"}",
                    color = if (agent.goddess == GoddessType.MYRA) CyberRed else SkyBlue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Role: ${agent.role}",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = agent.description,
                    color = Color.White,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onExecute,
                colors = ButtonDefaults.buttonColors(containerColor = SkyBlue, contentColor = Color(0xFF070B14))
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
        shape = RoundedCornerShape(16.dp)
    )
}
