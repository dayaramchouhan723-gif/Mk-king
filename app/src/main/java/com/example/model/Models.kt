package com.example.model

enum class GoddessType {
    MYRA,
    KYRA,
    DUAL
}

enum class AvatarMode(val title: String, val subtitle: String) {
    V3NOX_DUAL_ORB("Mode 0: V3NOX Dual Orb", "Red & Sky Blue Dual Glowing Fusion Orb"),
    PARTICLE_GLOBE("Mode 1: Particle Globe 3D", "220+ Orbiting Particles Red & Sky Blue"),
    FLOATING_ORB("Mode 2: Floating Orb 120dp", "Overlay Orb Floating Window VYRON"),
    ANIME_MYRA("Mode 3: Full Body Anime Myra SS-1", "White & Blue Dress Galaxy Halo"),
    REALISTIC_MYRA("Mode 4: Realistic Human Myra SS-2", "Red Top Cybernetic Rim Light"),
    ANIME_KYRA("Mode 5: Full Body Anime Kyra SS-3", "Blue Purple Dress Celestial Stars"),
    REALISTIC_KYRA("Mode 6: Realistic Human Kyra SS-4", "Sky Blue Top Ribbed Rim Light"),
    DUAL_FUSION("Mode 7: Dual Goddess Fusion", "Myra + Kyra Together Side by Side")
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
    val sender: String, // "USER", "MYRA", "KYRA", "DUAL_GODDESS", "SYSTEM"
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
