package org.ramees.kmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val beautyPrimary = Color(0xFFFFB4AB)
val beautyOnPrimary = Color(0xFF690005)
val beautyPrimaryContainer = Color(0xFF93000A)
val beautyOnPrimaryContainer = Color(0xFFFFDAD6)

// Secondary: Muted Lavender (Calming)
val beautySecondary = Color(0xFFE6BDFF)
val beautyOnSecondary = Color(0xFF4A007F)
val beautySecondaryContainer = Color(0xFF6700AF)
val beautyOnSecondaryContainer = Color(0xFFF3DAFF)

// Tertiary: Soft Sage Green (Natural/Organic feel)
val beautyTertiary = Color(0xFFBCCF9F)
val beautyOnTertiary = Color(0xFF283515)
val beautyTertiaryContainer = Color(0xFF3E4C29)
val beautyOnTertiaryContainer = Color(0xFFD8EBBA)

// Backgrounds: Deep Charcoal/Obsidian (Not pure black, easier on eyes)
val beautyBackground = Color(0xFF1A1110) // Very dark warm grey
val beautyOnBackground = Color(0xFFF1DEE3)
val beautySurface = Color(0xFF1A1110)
val beautyOnSurface = Color(0xFFF1DEE3)
val beautySurfaceVariant = Color(0xFF534341)
val beautyOnSurfaceVariant = Color(0xFFD8C2BF)
val beautyOutline = Color(0xFFA08C8A)

// Error Colors
val beautyError = Color(0xFFFFB4AB)
val beautyOnError = Color(0xFF690005)

private val DarkColors = darkColorScheme(
    primary = beautyPrimary,
    onPrimary = beautyOnPrimary,
    primaryContainer = beautyPrimaryContainer,
    onPrimaryContainer = beautyOnPrimaryContainer,

    secondary = beautySecondary,
    onSecondary = beautyOnSecondary,
    secondaryContainer = beautySecondaryContainer,
    onSecondaryContainer = beautyOnSecondaryContainer,

    tertiary = beautyTertiary,
    onTertiary = beautyOnTertiary,
    tertiaryContainer = beautyTertiaryContainer,
    onTertiaryContainer = beautyOnTertiaryContainer,

    background = beautyBackground,
    onBackground = beautyOnBackground,

    surface = beautySurface,
    onSurface = beautyOnSurface,
    surfaceVariant = beautySurfaceVariant,
    onSurfaceVariant = beautyOnSurfaceVariant,

    outline = beautyOutline,

    error = beautyError,
    onError = beautyOnError
)

@Composable
fun AppTheme(
    content: @Composable() () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColors,
        content = content
    )
}
