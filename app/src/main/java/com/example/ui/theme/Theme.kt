package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MkKingColorScheme = darkColorScheme(
    primary = RoyalRed,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF380808),
    onPrimaryContainer = Color(0xFFFFD5D5),
    secondary = CrownGold,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF382A00),
    onSecondaryContainer = Color(0xFFFFECB3),
    tertiary = NeonGreen,
    onTertiary = Color.Black,
    background = DarkBackground,
    onBackground = TextPrimary,
    surface = DarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = DarkCardBorder
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MkKingColorScheme,
        typography = Typography,
        content = content
    )
}
