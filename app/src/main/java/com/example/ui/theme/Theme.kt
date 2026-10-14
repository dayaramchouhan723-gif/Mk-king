package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DualGoddessColorScheme = darkColorScheme(
    primary = MyraRed,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF3B0D14),
    onPrimaryContainer = Color(0xFFFFDADE),
    secondary = KyraBlue,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF0F2B48),
    onSecondaryContainer = Color(0xFFD1E4FF),
    tertiary = MyraGreen,
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF00391E),
    onTertiaryContainer = Color(0xFF67FFA7),
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
        colorScheme = DualGoddessColorScheme,
        typography = Typography,
        content = content
    )
}
