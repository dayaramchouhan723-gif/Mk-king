package com.example.model

enum class GoddessType {
    MK_KING,
    MYRA,
    KYRA,
    DUAL
}

enum class AvatarMode(val title: String, val subtitle: String) {
    V3NOX_ORB_RED("Mode 0: V3NOX Orb Red", "Red Glowing Orb 100dp with Dual Neon Pulse"),
    PARTICLE_GLOBE("Mode 1: Particle Globe 3D", "220dp Particle Globe • 220+ Particles Red #FF0000"),
    FLOATING_ORB("Mode 2: White Floating Orb 120dp", "Overlay Orb Floating Window VYRON"),
    ANIME_SS1("Mode 3: Full Body Anime SS-1", "White Blue Dress Galaxy Halo Lip-sync"),
    REALISTIC_SS2("Mode 4: Realistic Human SS-2", "Red Top Ribbed Blue Rim Light Lip-sync")
}

data class Persona(
    val id: String,
    val name: String,
    val goddess: GoddessType,
    val title: String,
    val quote: String,
    val style: String,
    val isVipOnly: Boolean = false
)

data class VoiceProfile(
    val id: String,
    val name: String,
    val goddess: GoddessType,
    val style: String,
    val pitch: Float = 1.0f,
    val speed: Float = 1.0f
)

data class AgentItem(
    val id: String,
    val name: String,
    val goddess: GoddessType,
    val role: String,
    val status: String,
    val isLive: Boolean = true,
    val iconTag: String,
    val description: String
)

data class MasterToolItem(
    val id: String,
    val name: String,
    val iconTag: String,
    val colorHex: Long,
    val actionCategory: String
)

data class ChatMessage(
    val id: String,
    val text: String,
    val sender: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isAudioAvailable: Boolean = true
)

data class SystemMemory(
    val id: String,
    val title: String,
    val category: String,
    val snippet: String,
    val timestamp: String,
    val isPinned: Boolean = false
)

data class TriggerSetting(
    val id: String,
    val title: String,
    val description: String,
    var isGranted: Boolean
)
