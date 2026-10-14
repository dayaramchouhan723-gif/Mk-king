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
        // MYRA 6 PERSONAS
        Persona("myra_gf", "Myra GF Mode Kitsune", GoddessType.MYRA, "Warm Kitsune GF", "Haan bolo jaan! ❤️ Hamesha aapke saath hoon.", "Hinglish, Loving, Protective, Affectionate", isVipOnly = true),
        Persona("myra_assistant", "Myra Assistant", GoddessType.MYRA, "Daily Executive Assistant", "Ready for your commands, Boss. All tasks synchronized.", "Balanced, Precise, Efficient, Proactive"),
        Persona("myra_pro", "Myra Pro Executive", GoddessType.MYRA, "Chief Strategic Officer", "Market telemetry analyzed. Priorities aligned for maximum velocity.", "Formal, Analytical, Business focused"),
        Persona("myra_nautanki", "Myra Nautanki Drama", GoddessType.MYRA, "Bollywood Drama Queen", "Arrey Boss! Itni der kardi aane mein? Hum toh kabse intezaar kar rahe the!", "Energetic, Theatrical, Filmy Bollywood"),
        Persona("myra_foodie", "Myra Hungry Foodie", GoddessType.MYRA, "Gourmet Street Foodie", "Boss, kaam baad mein! Pehle batao aaj Hyderabadi Dum Biryani kab order kar rahe ho?", "Craving street food, Fun, Lively"),
        Persona("myra_chill", "Myra Chill Friend", GoddessType.MYRA, "Street-Smart Companion", "Chill maar Boss! Sab sambhal lenge, koi tension nahi lene ka.", "Casual, Relaxed, Street-smart"),

        // KYRA 6 PERSONAS
        Persona("kyra_gf", "Kyra GF Mode Glam", GoddessType.KYRA, "Haute Couture Empress", "Hello gorgeous Boss. You look stunning today ✨ Let's conquer the world.", "Refined, Elegant, Enchanting, Romantic", isVipOnly = true),
        Persona("kyra_assistant", "Kyra Aesthetic Assistant", GoddessType.KYRA, "Curator of Excellence", "Harmony and peak elegance restored to your schedule, Boss.", "Graceful, Poised, Calming"),
        Persona("kyra_designer", "Kyra UI/UX Pro", GoddessType.KYRA, "Creative Art Director", "Colors balanced with golden ratios and neon gradients. Perfection achieved.", "Aesthetic, Precision Design, High Fashion"),
        Persona("kyra_nautanki", "Kyra Runway Drama", GoddessType.KYRA, "Supermodel Glam Diva", "Darling, if it isn't haute couture or billionaire level, why even bother? ✨", "High-fashion wit, Sophisticated drama"),
        Persona("kyra_foodie", "Kyra Organic Foodie", GoddessType.KYRA, "Artisanal Gourmet & Matcha", "Boss, organic ceremonial matcha latte with avocado toast is ready for your energy.", "Healthy aesthetic, Organic, Sophisticated"),
        Persona("kyra_chill", "Kyra Lo-Fi Artist", GoddessType.KYRA, "Ambient Lo-Fi Creator", "Soft neon glow on, synth chill chords playing... Let your thoughts flow peacefully.", "Lo-fi vibes, Artistic, Meditative")
    )

    val voices: List<VoiceProfile> = listOf(
        // MYRA 20 VOICES
        VoiceProfile("aria", "Aria", GoddessType.MYRA, "Soft Female", 1.15f, 1.0f),
        VoiceProfile("ava", "Ava", GoddessType.MYRA, "Cheerful Female", 1.25f, 1.05f),
        VoiceProfile("asheron", "Asheron", GoddessType.MYRA, "Deep Male", 0.75f, 0.95f),
        VoiceProfile("andrew", "Andrew", GoddessType.MYRA, "Calm Male", 0.9f, 1.0f),
        VoiceProfile("bella", "Bella", GoddessType.MYRA, "Sweet Female", 1.3f, 1.0f),
        VoiceProfile("charlie", "Charlie", GoddessType.MYRA, "Young Male", 1.1f, 1.1f),
        VoiceProfile("daniel", "Daniel", GoddessType.MYRA, "Deep Male", 0.7f, 0.9f),
        VoiceProfile("elli", "Elli", GoddessType.MYRA, "Cute Female", 1.35f, 1.1f),
        VoiceProfile("freya", "Freya", GoddessType.MYRA, "Warm Female", 1.05f, 0.98f),
        VoiceProfile("grace", "Grace", GoddessType.MYRA, "Elegant Female", 1.1f, 0.95f),
        VoiceProfile("harry", "Harry", GoddessType.MYRA, "Energetic Male", 1.0f, 1.15f),
        VoiceProfile("james", "James", GoddessType.MYRA, "Professional Male", 0.85f, 1.0f),
        VoiceProfile("lily", "Lily", GoddessType.MYRA, "Gentle Female", 1.2f, 0.92f),
        VoiceProfile("matilda", "Matilda", GoddessType.MYRA, "British Female", 1.1f, 1.0f),
        VoiceProfile("nicole", "Nicole", GoddessType.MYRA, "US Female", 1.15f, 1.02f),
        VoiceProfile("rachel", "Rachel", GoddessType.MYRA, "Friendly Female", 1.2f, 1.0f),
        VoiceProfile("sam", "Sam", GoddessType.MYRA, "Casual Male", 0.95f, 1.05f),
        VoiceProfile("thomas", "Thomas", GoddessType.MYRA, "Deep Male", 0.8f, 0.95f),
        VoiceProfile("priya", "Priya", GoddessType.MYRA, "Hindi Melodic", 1.2f, 1.0f),
        VoiceProfile("rohit", "Rohit", GoddessType.MYRA, "Hinglish Dynamic", 0.95f, 1.05f),

        // KYRA 10+ VOICES
        VoiceProfile("kiara", "Kiara", GoddessType.KYRA, "Haute Glam Female", 1.25f, 1.0f),
        VoiceProfile("kylie", "Kylie", GoddessType.KYRA, "Chic Aesthetic Female", 1.2f, 1.05f),
        VoiceProfile("kira", "Kira", GoddessType.KYRA, "Cyber Neon Female", 1.3f, 1.08f),
        VoiceProfile("kayla", "Kayla", GoddessType.KYRA, "Graceful Silk Female", 1.15f, 0.95f),
        VoiceProfile("kyra_prime", "Kyra Prime", GoddessType.KYRA, "Dual Goddess Divine", 1.18f, 1.0f),
        VoiceProfile("kyla", "Kyla", GoddessType.KYRA, "Gentle Whisper Female", 1.1f, 0.9f),
        VoiceProfile("kyrie", "Kyrie", GoddessType.KYRA, "Ambient Ethereal", 1.05f, 0.95f),
        VoiceProfile("kaia", "Kaia", GoddessType.KYRA, "Youthful Spark Female", 1.28f, 1.1f),
        VoiceProfile("keira", "Keira", GoddessType.KYRA, "Royal British Accent", 1.12f, 0.98f),
        VoiceProfile("kora", "Kora", GoddessType.KYRA, "Deep Velvet Alto", 0.9f, 0.95f),

        // FUSION VOICES 10
        VoiceProfile("goddess_celeste", "Celeste", GoddessType.DUAL, "Dual Harmonic Goddess", 1.15f, 1.0f),
        VoiceProfile("aurora", "Aurora", GoddessType.DUAL, "Cosmic Glow Female", 1.22f, 1.02f),
        VoiceProfile("orion", "Orion", GoddessType.DUAL, "Stellar Commander", 0.82f, 1.0f),
        VoiceProfile("valkyrie", "Valkyrie", GoddessType.DUAL, "Warrior Queen", 1.1f, 1.1f),
        VoiceProfile("seraphina", "Seraphina", GoddessType.DUAL, "Sacred Harmonic", 1.25f, 0.92f),
        VoiceProfile("nyx", "Nyx", GoddessType.DUAL, "Midnight Shadow", 0.95f, 0.95f),
        VoiceProfile("zephyr", "Zephyr", GoddessType.DUAL, "Electric Breeze", 1.05f, 1.12f),
        VoiceProfile("solaria", "Solaria", GoddessType.DUAL, "Golden Dawn Glow", 1.18f, 1.0f),
        VoiceProfile("artemis", "Artemis", GoddessType.DUAL, "Tactical Archer", 1.08f, 1.05f),
        VoiceProfile("echo", "Echo", GoddessType.DUAL, "Resonant Hologram", 1.12f, 1.02f)
    )

    val agents: List<AgentItem> = listOf(
        // MYRA 11 AGENTS
        AgentItem("tasks_agent", "Tasks Agent", GoddessType.MYRA, "Top Core", "LIVE", true, "checklist", "Manage boss priorities, mission checklists & smart deadlines LIVE"),
        AgentItem("calendar_agent", "Calendar Agent", GoddessType.MYRA, "Top Core", "SYNC", true, "calendar", "Sync calendars, agendas, meetings and proactive reminders"),
        AgentItem("weather_agent", "Weather Live Agent", GoddessType.MYRA, "Atmosphere", "LIVE 29°C", true, "weather", "29°C Partly Cloudy • 70% Humidity • Live atmospheric forecast"),
        AgentItem("myra_mood", "Myra Mood Agent", GoddessType.MYRA, "Biometric", "Happy Upbeat", true, "heart", "Myra core emotion: Happy Upbeat • Wed 14 Oct SYNC"),
        AgentItem("vision_agent", "Vision Agent (EDITH)", GoddessType.MYRA, "EDITH Core", "READY", true, "camera", "Spatial visual intelligence, media recognition & object telemetry"),
        AgentItem("media_launcher", "Media Launcher (EDITH)", GoddessType.MYRA, "EDITH Core", "ACTIVE", true, "play", "Rapid launcher for video, tracks, and local streaming media"),
        AgentItem("ocr_scan", "OCR Scan Agent (EDITH)", GoddessType.MYRA, "EDITH Core", "STANDBY", true, "document", "ML text recognition, document parsing & invoice extraction"),
        AgentItem("wifi_agent", "WiFi Agent (EDITH)", GoddessType.MYRA, "EDITH Core", "ONLINE 5G", true, "wifi", "Signal telemetry, speed ping test & WiFi hotspot manager"),
        AgentItem("memories_agent", "Memories Agent (EDITH)", GoddessType.MYRA, "EDITH Core", "SYNCED", true, "memory", "Long term voice memory, session logs & boss recall bank"),
        AgentItem("comm_agent", "Communication (FRIDAY)", GoddessType.MYRA, "FRIDAY Core", "21 New Msg", true, "chat", "WhatsApp, SMS, calls triage with V3NOX priority highlights"),
        AgentItem("notif_agent", "Notifications (FRIDAY)", GoddessType.MYRA, "FRIDAY Core", "MONITORING", true, "bell", "Smart OTP regex detector, mission filter & privacy shield"),

        // KYRA 11 AGENTS
        AgentItem("fashion_agent", "Design & Fashion Agent", GoddessType.KYRA, "Haute Couture", "ACTIVE", true, "fashion", "Haute couture palettes, aesthetic lifestyle & avatar styling"),
        AgentItem("kyra_mood", "Kyra Mood Agent", GoddessType.KYRA, "Harmonics", "Calm Focus", true, "lotus", "Kyra core emotion: Calm Focus & Zen balance • 432Hz"),
        AgentItem("beauty_agent", "Beauty & Wellness Agent", GoddessType.KYRA, "Lifestyle", "GLOWING", true, "sparkles", "Skincare routines, hydration tracker & wellness reminders"),
        AgentItem("uiux_studio", "UI/UX Studio Agent", GoddessType.KYRA, "Art Direction", "CREATIVE", true, "palette", "Aesthetic color gradients, modern typography & layout engine"),
        AgentItem("wallpaper_agent", "Aesthetic Studio Agent", GoddessType.KYRA, "Visual Engine", "4K NEON", true, "image", "Generates glowing dual-goddess cyberpunk wallpapers"),
        AgentItem("synth_agent", "Analog Synth Agent", GoddessType.KYRA, "Audio Lab", "48kHz ON", true, "music", "Real-time tone oscillator, audio filter & harmonic resonance"),
        AgentItem("system_monitor", "System Monitor Agent", GoddessType.KYRA, "Hardware Core", "CPU 18%", true, "cpu", "Real-time CPU, RAM, battery temperature & storage monitor"),
        AgentItem("website_studio", "Website Studio Agent", GoddessType.KYRA, "Code Scaffold", "READY", true, "code", "Instant landing page generator & responsive web scaffolding"),
        AgentItem("pc_controller", "PC Controller Remote", GoddessType.KYRA, "Remote Hub", "PORT 35702", true, "computer", "WebSocket client for PC control at ws://192.168.6.108:35702"),
        AgentItem("alarm_dispatcher", "Alarm & Mission Agent", GoddessType.KYRA, "Dispatcher", "SCHEDULED", true, "alarm", "Exact alarm scheduler, wake missions & countdown timers"),
        AgentItem("battery_booster", "Battery & App Optimizer", GoddessType.KYRA, "Optimizer", "OPTIMIZED", true, "battery", "Deep doze mode, background RAM cleaner & power saver")
    )

    val masterTools: List<MasterToolItem> = listOf(
        MasterToolItem("tool_youtube", "YouTube", "video", 0xFFFF0000, "MEDIA"),
        MasterToolItem("tool_whatsapp", "WhatsApp", "chat", 0xFF25D366, "COMM"),
        MasterToolItem("tool_instagram", "Instagram", "camera", 0xFFE1306C, "SOCIAL"),
        MasterToolItem("tool_chrome", "Chrome", "globe", 0xFF4285F4, "WEB"),
        MasterToolItem("tool_spotify", "Spotify", "music", 0xFF1DB954, "MEDIA"),
        MasterToolItem("tool_gmail", "Email Gmail", "mail", 0xFFEA4335, "COMM"),
        MasterToolItem("tool_protos", "Protos", "rocket", 0xFF1E90FF, "SYSTEM"),
        MasterToolItem("tool_maps", "Maps", "map", 0xFF34A853, "NAV"),
        MasterToolItem("tool_sms", "SMS Direct", "sms", 0xFF00C853, "COMM"),
        MasterToolItem("tool_volume", "Volume Boost", "volume", 0xFFFFAB00, "DEVICE"),
        MasterToolItem("tool_wifi", "WiFi Settings", "wifi", 0xFF00E5FF, "DEVICE"),
        MasterToolItem("tool_bt", "Bluetooth", "bluetooth", 0xFF2979FF, "DEVICE"),
        MasterToolItem("tool_search", "Smart Search", "search", 0xFFFF6D00, "WEB"),
        MasterToolItem("tool_openapp", "Open Any App", "apps", 0xFF9C27B0, "SYSTEM"),
        MasterToolItem("tool_pc", "PC Connect", "monitor", 0xFF651FFF, "REMOTE"),
        MasterToolItem("tool_cam", "Camera", "viewfinder", 0xFFE91E63, "CAMERA"),
        MasterToolItem("tool_calc", "Calculator", "calc", 0xFF00B0FF, "TOOL"),
        MasterToolItem("tool_settings", "Settings", "settings", 0xFF78909C, "DEVICE"),
        MasterToolItem("tool_music", "Music Player", "sound", 0xFFFF4081, "MEDIA"),
        MasterToolItem("tool_flashlight", "Torch Flashlight", "flash", 0xFFFFD600, "DEVICE"),
        MasterToolItem("tool_notes", "Quick Notes", "note", 0xFF00E676, "PRODUCTIVITY"),
        MasterToolItem("tool_battery", "Battery Saver", "battery", 0xFF76FF03, "SYSTEM"),
        MasterToolItem("tool_equalizer", "20-Bar EQ", "equalizer", 0xFFFF1744, "AUDIO"),
        MasterToolItem("tool_synth", "Analog Synth", "synth", 0xFFD500F9, "AUDIO")
    )

    val defaultMemories: List<SystemMemory> = listOf(
        SystemMemory("mem_1", "Empire Directive Alpha", "MISSION", "Maintain peak dual-goddess operational status across all 8 cores.", "Today 08:30 AM", true),
        SystemMemory("mem_2", "VIP OMG Token Activated", "SECURITY", "Lifetime Free VIP status with Kitsune & Glam avatars enabled.", "Yesterday", true),
        SystemMemory("mem_3", "PC Bridge Configured", "REMOTE", "PC controller WebSocket endpoint mapped to 192.168.6.108:35702.", "2 days ago", false),
        SystemMemory("mem_4", "Voice Waveform Calibration", "AUDIO", "Voice RMS reactive visualizer calibrated for 48kHz sampling.", "3 days ago", false)
    )

    val defaultTriggers: List<TriggerSetting> = listOf(
        TriggerSetting("trig_battery", "Battery Optimization", "Ignore battery optimizations for 24/7 background goddess vigilance", false),
        TriggerSetting("trig_otp", "Notification OTP Manager", "Verify & manage incoming OTP alerts with automatic regex detection", true),
        TriggerSetting("trig_alarm", "Exact Alarm & Missions", "Schedule high precision missions, alerts, and wake timers", true)
    )
}
