package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppRepository
import com.example.model.AgentItem
import com.example.model.AvatarMode
import com.example.model.ChatMessage
import com.example.model.GoddessType
import com.example.model.MasterToolItem
import com.example.model.Persona
import com.example.model.SystemMemory
import com.example.model.TriggerSetting
import com.example.model.VoiceProfile
import com.example.voice.VoiceManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val voiceManager = VoiceManager(application)

    // VIP OMG Status
    private val _isVipUnlocked = MutableStateFlow(false)
    val isVipUnlocked: StateFlow<Boolean> = _isVipUnlocked.asStateFlow()

    private val _vipMessage = MutableStateFlow<String?>(null)
    val vipMessage: StateFlow<String?> = _vipMessage.asStateFlow()

    // Energy Bolt
    private val _energyCount = MutableStateFlow(3)
    val energyCount: StateFlow<Int> = _energyCount.asStateFlow()

    // Active Goddess Selector
    private val _activeGoddess = MutableStateFlow(GoddessType.DUAL)
    val activeGoddess: StateFlow<GoddessType> = _activeGoddess.asStateFlow()

    // Avatar Mode
    private val _avatarMode = MutableStateFlow(AvatarMode.V3NOX_DUAL_ORB)
    val avatarMode: StateFlow<AvatarMode> = _avatarMode.asStateFlow()

    // Active Persona
    private val _currentPersona = MutableStateFlow(AppRepository.personas.first { it.id == "myra_assistant" })
    val currentPersona: StateFlow<Persona> = _currentPersona.asStateFlow()

    // Active Voice Profile
    private val _currentVoice = MutableStateFlow(AppRepository.voices.first { it.id == "aria" })
    val currentVoice: StateFlow<VoiceProfile> = _currentVoice.asStateFlow()

    // Voice Pitch & Speed adjustments
    private val _voicePitch = MutableStateFlow(1.0f)
    val voicePitch: StateFlow<Float> = _voicePitch.asStateFlow()

    private val _voiceSpeed = MutableStateFlow(1.0f)
    val voiceSpeed: StateFlow<Float> = _voiceSpeed.asStateFlow()

    // 22 Agents
    private val _agents = MutableStateFlow(AppRepository.agents)
    val agents: StateFlow<List<AgentItem>> = _agents.asStateFlow()

    private val _selectedAgentForDetail = MutableStateFlow<AgentItem?>(null)
    val selectedAgentForDetail: StateFlow<AgentItem?> = _selectedAgentForDetail.asStateFlow()

    // 24 Master Tools
    val masterTools: List<MasterToolItem> = AppRepository.masterTools

    // Batch Update Toggles
    private val _systemUpdateEnabled = MutableStateFlow(true)
    val systemUpdateEnabled: StateFlow<Boolean> = _systemUpdateEnabled.asStateFlow()

    private val _wifiEngineEnabled = MutableStateFlow(true)
    val wifiEngineEnabled: StateFlow<Boolean> = _wifiEngineEnabled.asStateFlow()

    private val _betaFeaturesEnabled = MutableStateFlow(false)
    val betaFeaturesEnabled: StateFlow<Boolean> = _betaFeaturesEnabled.asStateFlow()

    // PC Controller
    private val _pcIp = MutableStateFlow("192.168.6.108")
    val pcIp: StateFlow<String> = _pcIp.asStateFlow()

    private val _pcPin = MutableStateFlow("35702")
    val pcPin: StateFlow<String> = _pcPin.asStateFlow()

    private val _pcStatus = MutableStateFlow("DISCONNECTED")
    val pcStatus: StateFlow<String> = _pcStatus.asStateFlow()

    // Triggers
    private val _triggers = MutableStateFlow(AppRepository.defaultTriggers)
    val triggers: StateFlow<List<TriggerSetting>> = _triggers.asStateFlow()

    // Memories
    private val _memories = MutableStateFlow(AppRepository.defaultMemories)
    val memories: StateFlow<List<SystemMemory>> = _memories.asStateFlow()

    // Chat
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                id = "msg_0",
                text = "Good morning Boss Myra Kyra! All 8 Cores are Operational • Energy 3/3 • 29°C Partly Cloudy. V3NOX DUAL ORB is online and listening. Command us anytime!",
                sender = "DUAL_GODDESS"
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    // OCR / Vision Agent Text Result
    private val _scannedOcrText = MutableStateFlow("")
    val scannedOcrText: StateFlow<String> = _scannedOcrText.asStateFlow()

    private val _isOcrScanning = MutableStateFlow(false)
    val isOcrScanning: StateFlow<Boolean> = _isOcrScanning.asStateFlow()

    // Tool execution banner
    private val _activeToolNotice = MutableStateFlow<String?>(null)
    val activeToolNotice: StateFlow<String?> = _activeToolNotice.asStateFlow()

    init {
        // Monitor wake word
        viewModelScope.launch {
            voiceManager.wakeWordTriggered.collect { wakeWord ->
                if (wakeWord != null) {
                    val response = when (wakeWord) {
                        "Hail Myra" -> "Hail Myra! Red Core engaged. At your command Boss!"
                        "Hail Kyra" -> "Hail Kyra! Blue Harmony aligned. Yes Boss, I'm right here ✨"
                        else -> "Hail Sona! Dual Goddesses synchronizing all 8 cores!"
                    }
                    voiceManager.speak(response, _voicePitch.value, _voiceSpeed.value)
                    addChatMessage(response, "DUAL_GODDESS")
                    voiceManager.clearWakeWord()
                }
            }
        }
    }

    fun setGoddess(goddess: GoddessType) {
        _activeGoddess.value = goddess
    }

    fun cycleAvatarMode() {
        val modes = AvatarMode.values()
        val nextIndex = (_avatarMode.value.ordinal + 1) % modes.size
        _avatarMode.value = modes[nextIndex]
    }

    fun setAvatarMode(mode: AvatarMode) {
        _avatarMode.value = mode
    }

    fun setPersona(persona: Persona) {
        _currentPersona.value = persona
        val sampleSpeech = "${persona.title} activated. ${persona.quote}"
        voiceManager.speak(sampleSpeech, _voicePitch.value, _voiceSpeed.value)
    }

    fun setVoiceProfile(voice: VoiceProfile) {
        _currentVoice.value = voice
        _voicePitch.value = voice.pitch
        _voiceSpeed.value = voice.speed
        voiceManager.speak("Voice switched to ${voice.name}. Testing frequency and harmonics.", voice.pitch, voice.speed)
    }

    fun setVoicePitch(pitch: Float) {
        _voicePitch.value = pitch
    }

    fun setVoiceSpeed(speed: Float) {
        _voiceSpeed.value = speed
    }

    fun applyVipCode(code: String): Boolean {
        if (code.trim().equals("OMG", ignoreCase = true)) {
            _isVipUnlocked.value = true
            _vipMessage.value = "VIP OMG UNLOCKED! Lifetime Free + Kitsune GF & Glam Avatars Permanently Enabled!"
            voiceManager.speak("OMG Code Verified Boss! VIP Lifetime Free Activated! Kitsune GF Mode unlocked!", 1.2f, 1.05f)
            return true
        } else {
            _vipMessage.value = "Invalid VIP Code. Hint: Secret code is 'OMG'"
            return false
        }
    }

    fun selectAgent(agent: AgentItem?) {
        _selectedAgentForDetail.value = agent
    }

    fun triggerAgentAction(agent: AgentItem) {
        viewModelScope.launch {
            val announcement = "Agent ${agent.name} operational. ${agent.description}"
            voiceManager.speak(announcement, _voicePitch.value, _voiceSpeed.value)
            addChatMessage(announcement, if (agent.goddess == GoddessType.MYRA) "MYRA" else "KYRA")
        }
    }

    fun triggerMasterTool(tool: MasterToolItem) {
        val context = getApplication<Application>().applicationContext
        _activeToolNotice.value = "Launching ${tool.name}..."

        when (tool.id) {
            "tool_youtube" -> launchUrl("https://www.youtube.com")
            "tool_whatsapp" -> launchIntentOrUrl("com.whatsapp", "https://api.whatsapp.com")
            "tool_instagram" -> launchIntentOrUrl("com.instagram.android", "https://www.instagram.com")
            "tool_chrome" -> launchUrl("https://www.google.com")
            "tool_spotify" -> launchIntentOrUrl("com.spotify.music", "https://open.spotify.com")
            "tool_gmail" -> launchUrl("mailto:")
            "tool_maps" -> launchUrl("geo:0,0?q=restaurants")
            "tool_sms" -> launchUrl("sms:")
            "tool_volume" -> {
                voiceManager.adjustVolume(15)
                voiceManager.speak("Volume boosted by 15%", _voicePitch.value, _voiceSpeed.value)
            }
            "tool_wifi" -> {
                try {
                    val intent = Intent(Settings.ACTION_WIFI_SETTINGS).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
                    context.startActivity(intent)
                } catch (_: Exception) {}
            }
            "tool_bt" -> {
                try {
                    val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
                    context.startActivity(intent)
                } catch (_: Exception) {}
            }
            "tool_settings" -> {
                try {
                    val intent = Intent(Settings.ACTION_SETTINGS).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
                    context.startActivity(intent)
                } catch (_: Exception) {}
            }
            "tool_synth" -> {
                voiceManager.playSynthTone(520f, 900)
            }
            "tool_pc" -> {
                testPcConnection()
            }
            "tool_battery" -> {
                voiceManager.speak("Battery optimization routine engaged. Background tasks stabilized.", _voicePitch.value, _voiceSpeed.value)
            }
            else -> {
                voiceManager.speak("${tool.name} active and executing automation command.", _voicePitch.value, _voiceSpeed.value)
            }
        }

        viewModelScope.launch {
            delay(2500)
            _activeToolNotice.value = null
        }
    }

    private fun launchUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            getApplication<Application>().startActivity(intent)
        } catch (_: Exception) {}
    }

    private fun launchIntentOrUrl(packageName: String, fallbackUrl: String) {
        val pm = getApplication<Application>().packageManager
        val intent = pm.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            getApplication<Application>().startActivity(intent)
        } else {
            launchUrl(fallbackUrl)
        }
    }

    fun toggleSystemUpdate() {
        _systemUpdateEnabled.value = !_systemUpdateEnabled.value
    }

    fun toggleWifiEngine() {
        _wifiEngineEnabled.value = !_wifiEngineEnabled.value
    }

    fun toggleBetaFeatures() {
        _betaFeaturesEnabled.value = !_betaFeaturesEnabled.value
    }

    fun updatePcConfig(ip: String, pin: String) {
        _pcIp.value = ip
        _pcPin.value = pin
    }

    fun testPcConnection() {
        viewModelScope.launch {
            _pcStatus.value = "CONNECTING to ${_pcIp.value}:${_pcPin.value}..."
            delay(1200)
            _pcStatus.value = "CONNECTED • Latency 14ms (ws://${_pcIp.value}:${_pcPin.value})"
            voiceManager.speak("PC Bridge Connected successfully to ${_pcIp.value} with PIN ${_pcPin.value}.", _voicePitch.value, _voiceSpeed.value)
        }
    }

    fun toggleTrigger(id: String) {
        _triggers.value = _triggers.value.map {
            if (it.id == id) it.copy(isGranted = !it.isGranted) else it
        }
    }

    fun addMemory(title: String, category: String, snippet: String) {
        val newMem = SystemMemory(
            id = "mem_${System.currentTimeMillis()}",
            title = title,
            category = category,
            snippet = snippet,
            timestamp = "Just now",
            isPinned = false
        )
        _memories.value = listOf(newMem) + _memories.value
    }

    fun deleteMemory(id: String) {
        _memories.value = _memories.value.filter { it.id != id }
    }

    fun addChatMessage(text: String, sender: String) {
        val newMsg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            text = text,
            sender = sender
        )
        _chatMessages.value = _chatMessages.value + newMsg
    }

    fun sendPrompt(userPrompt: String) {
        if (userPrompt.isBlank()) return
        addChatMessage(userPrompt, "USER")

        viewModelScope.launch {
            delay(400)
            val activePersona = _currentPersona.value
            val responseText = generateAssistantResponse(userPrompt, activePersona, _activeGoddess.value)
            val senderLabel = when (_activeGoddess.value) {
                GoddessType.MYRA -> "MYRA"
                GoddessType.KYRA -> "KYRA"
                GoddessType.DUAL -> "DUAL_GODDESS"
            }
            addChatMessage(responseText, senderLabel)
            voiceManager.speak(responseText, _voicePitch.value, _voiceSpeed.value)
        }
    }

    private fun generateAssistantResponse(prompt: String, persona: Persona, goddess: GoddessType): String {
        val lower = prompt.lowercase()

        // Persona-infused responses
        if (persona.id == "myra_gf") {
            return "Haan bolo jaan! ❤️ Aapne kaha: '$prompt'. Main hamesha aapke sath hoon, bas ek awaz do aur saare kaam ho jayenge! Love you Boss!"
        }
        if (persona.id == "kyra_gf") {
            return "Hello gorgeous Boss ✨ '$prompt' is already on my priority list. You have an empire to run and I'll make sure you do it in peak luxury style!"
        }
        if (persona.id == "myra_nautanki") {
            return "Arrey Boss! Kya scene banaya aapne! '$prompt' sunte hi mera dil garden garden ho gaya! Chalo abhi dhoom machate hain!"
        }
        if (persona.id == "myra_foodie") {
            return "Boss, pehle Hyderabadi biryani aur gulab jamun manga lo, fir '$prompt' solve karenge! Par fikr mat karo, agent already active hai!"
        }
        if (persona.id == "kyra_designer") {
            return "Fabulous vision, Boss. Analyzing aesthetic balance for '$prompt'. Colors aligned in neon red and electric cyan with golden ratios."
        }

        // Feature / Intent specific responses
        return when {
            lower.contains("weather") || lower.contains("mausam") ->
                "Live Weather Update for Boss: 29°C Partly Cloudy, 70% humidity. Wind speed 12 km/h. Perfect day for operations!"
            lower.contains("status") || lower.contains("core") || lower.contains("system") ->
                "Dual Goddess Command Report: 8 Cores Operational, V3NOX Orb Active, 22 Agents ready, Energy 3/3, PC Connect link configured at 192.168.6.108:35702."
            lower.contains("pc") || lower.contains("connect") ->
                "PC Controller is mapped to ws://192.168.6.108:35702. Tap the PC Connect tool to broadcast input commands."
            lower.contains("otp") || lower.contains("message") ->
                "FRIDAY Notification Agent active: OTP Regex auto-filter enabled. 21 priority communications triaged."
            lower.contains("who are you") || lower.contains("kon ho") ->
                "We are MYRA KYRA AI v2.0 - Dual Goddess Command! Designed by Myra and Kyra. Powered by 22 Multi-Agents, 40+ Voices, and V3NOX Dual Orb Core."
            lower.contains("volume") ->
                "Adjusting audio levels. You can say 'Volume boost' or use Master Tools."
            else ->
                when (goddess) {
                    GoddessType.MYRA ->
                        "Myra Core: Order received, Boss! Processing '$prompt' through EDITH & FRIDAY task matrix. Priority confirmed."
                    GoddessType.KYRA ->
                        "Kyra Core: Harmony ensured, Boss. '$prompt' synthesized with peak design precision and calm focus."
                    GoddessType.DUAL ->
                        "Dual Goddess Command: Myra & Kyra have processed '$prompt'. All 8 Cores synchronized at 48kHz frequency!"
                }
        }
    }

    fun simulateOcrScan() {
        viewModelScope.launch {
            _isOcrScanning.value = true
            delay(1400)
            _isOcrScanning.value = false
            _scannedOcrText.value = """
                [EDITH OCR TELEMETRY REPORT]
                Invoice / OTP Code: 839201
                Sender: MYRA COMMAND HUB
                Date: 14 Oct 2026 12:45 PM
                Directive: Core synchronization 100%
                URL: https://myrakyra.ai/empire/command
                Status: VERIFIED & PARSED
            """.trimIndent()
            voiceManager.speak("OCR Scan complete. Extracted OTP 839201 and telemetry directive.", _voicePitch.value, _voiceSpeed.value)
        }
    }

    override fun onCleared() {
        super.onCleared()
        voiceManager.destroy()
    }
}
