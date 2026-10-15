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

    // Active Assistant Selector
    private val _activeGoddess = MutableStateFlow(GoddessType.MK_KING)
    val activeGoddess: StateFlow<GoddessType> = _activeGoddess.asStateFlow()

    // Avatar Mode
    private val _avatarMode = MutableStateFlow(AvatarMode.V3NOX_ORB_RED)
    val avatarMode: StateFlow<AvatarMode> = _avatarMode.asStateFlow()

    // Active Persona
    private val _currentPersona = MutableStateFlow(AppRepository.personas.first { it.id == "mk_assistant" })
    val currentPersona: StateFlow<Persona> = _currentPersona.asStateFlow()

    // Active Voice Profile
    private val _currentVoice = MutableStateFlow(AppRepository.voices.first { it.id == "aria" })
    val currentVoice: StateFlow<VoiceProfile> = _currentVoice.asStateFlow()

    // Voice Pitch & Speed adjustments
    private val _voicePitch = MutableStateFlow(1.0f)
    val voicePitch: StateFlow<Float> = _voicePitch.asStateFlow()

    private val _voiceSpeed = MutableStateFlow(1.0f)
    val voiceSpeed: StateFlow<Float> = _voiceSpeed.asStateFlow()

    // Agents
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
                text = "Good morning Boss MK KING 👑! All 4 Cores are Operational • Energy 3/3 • 29°C Partly Cloudy. V3NOX Orb Red is online and listening. Command me anytime!",
                sender = "MK_KING"
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
                        "Hail MK KING" -> "Hail MK KING! Royal Command engaged. At your royal service, Boss!"
                        "Hail Sona" -> "Hail Sona! SONA CHOUHAN EMPIRE operational at your royal command!"
                        "Hail Myra" -> "Hail Myra! At your command Boss!"
                        else -> "Hail MK KING! All 4 cores synchronized at your service!"
                    }
                    voiceManager.speak(response, _voicePitch.value, _voiceSpeed.value)
                    addChatMessage(response, "MK_KING")
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
                GoddessType.MK_KING -> "MK_KING"
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
        if (persona.id == "mk_gf") {
            return "Haan bolo jaan! ❤️ Aapne kaha: '$prompt'. Main hamesha Boss MK KING ke saath hoon, bas ek awaz do aur saare royal kaam ho jayenge!"
        }
        if (persona.id == "mk_assistant") {
            return "Royal Command received, Boss MK KING! Executing '$prompt' across all JARVIS planning cores."
        }
        if (persona.id == "mk_nautanki") {
            return "Arrey Boss MK KING! Kya dialog maara aapne! '$prompt' sunkar royal dhoom mach gayi!"
        }
        if (persona.id == "mk_foodie") {
            return "Boss MK KING, pehle garma-garam Hyderabadi Dum Biryani mangwa lo, fir '$prompt' poora karenge!"
        }
        if (persona.id == "mk_chill") {
            return "Full chill Boss MK KING! '$prompt' ho jayega araam se, no tension!"
        }

        // Feature / Intent specific responses
        return when {
            lower.contains("weather") || lower.contains("mausam") ->
                "Live Weather for Boss MK KING: 29°C Partly Cloudy, 70% humidity. Royal skies clear!"
            lower.contains("status") || lower.contains("core") || lower.contains("system") ->
                "Royal Command Report: All 4 Cores Operational (JARVIS, EDITH, FRIDAY), V3NOX Orb Red active, Energy 3/3, SONA CHOUHAN EMPIRE."
            lower.contains("pc") || lower.contains("connect") ->
                "PC Controller is mapped to ws://192.168.6.108:35702. Tap the PC Connect tool to broadcast input commands."
            lower.contains("otp") || lower.contains("message") ->
                "FRIDAY Notification Agent active: OTP Regex auto-filter enabled. 21 priority communications triaged."
            lower.contains("who are you") || lower.contains("kon ho") ->
                "I am MK KING 👑 VOICE SUNNE WALA v3.0! ROYAL COMMAND • Sub Designed by MK. Powered by 11-in-1 Multi-Agent Hub :: SONA CHOUHAN EMPIRE!"
            lower.contains("volume") ->
                "Adjusting royal audio volume. You can say 'Volume boost' or use Master Tools."
            else ->
                "Boss MK KING! Royal Command received: '$prompt'. Processed via JARVIS intelligence and EDITH matrix. Operational and listening."
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
