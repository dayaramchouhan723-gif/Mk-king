package com.example.data

import com.example.model.AgentItem
import com.example.model.GoddessType
import com.example.model.MasterToolItem
import com.example.model.Persona
import com.example.model.SystemMemory
import com.example.model.TriggerSetting
import com.example.model.VoiceProfile

object AppRepository {

    val personas: List<Persona> = listOf(
        Persona("mk_gf", "MK KING GF Mode Kitsune", GoddessType.MK_KING, "Warm Kitsune Companion", "Haan bolo jaan! ❤️ Hamesha Boss MK KING ke saath hoon.", "Warm, affectionate, caring Hinglish companion", isVipOnly = true),
        Persona("mk_assistant", "MK KING Assistant", GoddessType.MK_KING, "Royal Daily Executive", "Ready for your royal command, Boss MK KING. All cores operational.", "Balanced, friendly, efficient daily helper"),
        Persona("mk_pro", "Pro Executive", GoddessType.MK_KING, "Chief Strategic Advisor", "Telemetric data aggregated. Royal objectives prioritized with precision.", "Formal, crisp, direct productivity"),
        Persona("mk_nautanki", "Nautanki Drama", GoddessType.MK_KING, "Bollywood Drama Queen", "Arrey Boss MK KING! Hum toh kabse aapki royal entry ka intezaar kar rahe the!", "Full Bollywood humor, witty drama, playful quips"),
        Persona("mk_foodie", "Hungry Foodie", GoddessType.MK_KING, "Royal Foodie Explorer", "Boss, pehle royal Hyderabadi Dum Biryani order karo, fir command sunenge!", "Obsessed with food, Biryani cravings & recipes"),
        Persona("mk_chill", "Chill Friend", GoddessType.MK_KING, "Street-Smart Bro Bestie", "Chill karo Boss MK KING! Sab smooth handle kar lenge, full royal swag!", "Casual street-smart bro, bestie vibe")
    )

    val voices: List<VoiceProfile> = listOf(
        VoiceProfile("aria", "Aria", GoddessType.MK_KING, "Soft Female", 1.15f, 1.0f),
        VoiceProfile("ava", "Ava", GoddessType.MK_KING, "Cheerful Female", 1.25f, 1.05f),
        VoiceProfile("asheron", "Asheron", GoddessType.MK_KING, "Deep Male", 0.75f, 0.95f),
        VoiceProfile("andrew", "Andrew", GoddessType.MK_KING, "Calm Male", 0.9f, 1.0f),
        VoiceProfile("bella", "Bella", GoddessType.MK_KING, "Sweet Female", 1.3f, 1.0f),
        VoiceProfile("charlie", "Charlie", GoddessType.MK_KING, "Young Male", 1.1f, 1.1f),
        VoiceProfile("daniel", "Daniel", GoddessType.MK_KING, "Deep Male", 0.7f, 0.9f),
        VoiceProfile("elli", "Elli", GoddessType.MK_KING, "Cute Female", 1.35f, 1.1f),
        VoiceProfile("freya", "Freya", GoddessType.MK_KING, "Warm Female", 1.05f, 0.98f),
        VoiceProfile("grace", "Grace", GoddessType.MK_KING, "Elegant Female", 1.1f, 0.95f),
        VoiceProfile("harry", "Harry", GoddessType.MK_KING, "Energetic Male", 1.0f, 1.15f),
        VoiceProfile("james", "James", GoddessType.MK_KING, "Professional Male", 0.85f, 1.0f),
        VoiceProfile("lily", "Lily", GoddessType.MK_KING, "Gentle Female", 1.2f, 0.92f),
        VoiceProfile("matilda", "Matilda", GoddessType.MK_KING, "British Female", 1.1f, 1.0f),
        VoiceProfile("nicole", "Nicole", GoddessType.MK_KING, "US Female", 1.15f, 1.02f),
        VoiceProfile("rachel", "Rachel", GoddessType.MK_KING, "Friendly Female", 1.2f, 1.0f),
        VoiceProfile("sam", "Sam", GoddessType.MK_KING, "Casual Male", 0.95f, 1.05f),
        VoiceProfile("thomas", "Thomas", GoddessType.MK_KING, "Deep Male", 0.8f, 0.95f),
        VoiceProfile("kiara", "Kiara", GoddessType.MK_KING, "Chic Female", 1.22f, 1.0f),
        VoiceProfile("kylie", "Kylie", GoddessType.MK_KING, "Royal Female", 1.18f, 1.02f),
        VoiceProfile("kira", "Kira", GoddessType.MK_KING, "Dynamic Accent", 1.12f, 1.05f),
        VoiceProfile("kayla", "Kayla", GoddessType.MK_KING, "Smooth Alto", 1.0f, 1.0f)
    )

    val agents: List<AgentItem> = listOf(
        // TOP CORE: JARVIS
        AgentItem("tasks_agent", "Tasks Agent", GoddessType.MK_KING, "TOP CORE : JARVIS", "LIVE", true, "checklist", "Manage boss priorities, mission checklists and smart deadlines."),
        AgentItem("calendar_agent", "Calendar Agent", GoddessType.MK_KING, "TOP CORE : JARVIS", "LIVE", true, "calendar", "Sync calendar agendas, schedule boss meetings and time-blocks."),
        AgentItem("weather_agent", "Weather Live", GoddessType.MK_KING, "TOP CORE : JARVIS", "LIVE 29°", true, "weather", "29° Partly cloudy • 70% humidity • Cloud telemetry LIVE"),
        AgentItem("empire_mood", "Empire Mood", GoddessType.MK_KING, "TOP CORE : JARVIS", "Happy Upbeat", true, "smile", "Happy Upbeat • Wed, 14 Oct • Royal harmonic frequency"),

        // EDITH CORE
        AgentItem("vision_agent", "Vision Agent (EDITH)", GoddessType.MK_KING, "EDITH Core", "ACTIVE", true, "camera", "Spatial visual intelligence, media recognition & object telemetry"),
        AgentItem("media_launcher", "Media Launcher (EDITH)", GoddessType.MK_KING, "EDITH Core", "READY", true, "play", "Rapid launcher for video, music & royal playlists"),
        AgentItem("ocr_scan", "OCR Scan Agent (EDITH)", GoddessType.MK_KING, "EDITH Core", "STANDBY", true, "document", "ML text recognition, document parsing & OTP extractor"),
        AgentItem("wifi_agent", "WiFi Agent (EDITH)", GoddessType.MK_KING, "EDITH Core", "ONLINE 5G", true, "wifi", "Signal telemetry, speed ping test & WiFi hotspot manager"),
        AgentItem("memories_agent", "Memories Agent (EDITH)", GoddessType.MK_KING, "EDITH Core", "SYNCED", true, "memory", "Long term voice memory, session logs & boss recall bank"),

        // FRIDAY CORE
        AgentItem("comm_agent", "Communication (FRIDAY)", GoddessType.MK_KING, "FRIDAY Core", "21 New Msg", true, "chat", "WhatsApp, SMS, calls triage with V3NOX priority highlights"),
        AgentItem("notif_agent", "Notifications (FRIDAY)", GoddessType.MK_KING, "FRIDAY Core", "MONITORING", true, "bell", "Smart OTP regex detector, mission filter & privacy shield")
    )

    val masterTools: List<MasterToolItem> = listOf(
        MasterToolItem("tool_youtube", "YouTube", "video", 0xFFFF0000, "MEDIA"),
        MasterToolItem("tool_whatsapp", "WhatsApp", "chat", 0xFF25D366, "COMM"),
        MasterToolItem("tool_instagram", "Instagram", "camera", 0xFFE1306C, "SOCIAL"),
        MasterToolItem("tool_chrome", "Chrome", "globe", 0xFF4285F4, "WEB"),
        MasterToolItem("tool_spotify", "Spotify", "music", 0xFF1DB954, "MEDIA"),
        MasterToolItem("tool_gmail", "Email", "mail", 0xFFEA4335, "COMM"),
        MasterToolItem("tool_protos", "Protos", "rocket", 0xFFFF0000, "SYSTEM"),
        MasterToolItem("tool_maps", "Maps", "map", 0xFF34A853, "NAV"),
        MasterToolItem("tool_sms", "SMS Direct", "sms", 0xFF00C853, "COMM"),
        MasterToolItem("tool_volume", "Volume", "volume", 0xFFFFD700, "DEVICE"),
        MasterToolItem("tool_wifi", "WiFi", "wifi", 0xFF00E5FF, "DEVICE"),
        MasterToolItem("tool_bt", "Bluetooth", "bluetooth", 0xFF2979FF, "DEVICE"),
        MasterToolItem("tool_search", "Search", "search", 0xFFFF6D00, "WEB"),
        MasterToolItem("tool_openapp", "Open Any App", "apps", 0xFFDC143C, "SYSTEM"),
        MasterToolItem("tool_pc", "PC Connect", "monitor", 0xFFFFD700, "REMOTE"),
        MasterToolItem("tool_cam", "Camera", "viewfinder", 0xFFFF1744, "CAMERA"),
        MasterToolItem("tool_calc", "Calculator", "calc", 0xFF00B0FF, "TOOL"),
        MasterToolItem("tool_settings", "Settings", "settings", 0xFF78909C, "DEVICE"),
        MasterToolItem("tool_music", "Music", "sound", 0xFFFF0000, "MEDIA"),
        MasterToolItem("tool_flashlight", "Torch", "flash", 0xFFFFD700, "DEVICE"),
        MasterToolItem("tool_notes", "Notes", "note", 0xFF00E676, "PRODUCTIVITY"),
        MasterToolItem("tool_battery", "Battery", "battery", 0xFF76FF03, "SYSTEM"),
        MasterToolItem("tool_equalizer", "Equalizer", "equalizer", 0xFFFF0000, "AUDIO"),
        MasterToolItem("tool_synth", "Analog Synth", "synth", 0xFFFFD700, "AUDIO")
    )

    val defaultMemories: List<SystemMemory> = listOf(
        SystemMemory("mem_1", "SONA CHOUHAN EMPIRE Directive", "ROYAL", "Maintain peak operational status across all 4 cores of MK KING.", "Today 08:30 AM", true),
        SystemMemory("mem_2", "VIP OMG Token Activated", "SECURITY", "Lifetime Free VIP status with Kitsune GF mode enabled.", "Yesterday", true),
        SystemMemory("mem_3", "PC Bridge Configured", "REMOTE", "PC controller WebSocket endpoint mapped to 192.168.6.108:35702.", "2 days ago", false),
        SystemMemory("mem_4", "Voice Waveform Calibration", "AUDIO", "Voice: sunne wala calibrated for 48kHz RMS reactive listening.", "3 days ago", false)
    )

    val defaultTriggers: List<TriggerSetting> = listOf(
        TriggerSetting("trig_battery", "Battery Optimization", "Optimize battery usage for 24/7 background royal vigilance", false),
        TriggerSetting("trig_otp", "Notification OTP Manager", "Verify & manage incoming OTP alerts with automatic regex detection", true),
        TriggerSetting("trig_alarm", "Exact Alarm & Missions", "Schedule high precision missions, alerts, and wake timers", true)
    )
}
