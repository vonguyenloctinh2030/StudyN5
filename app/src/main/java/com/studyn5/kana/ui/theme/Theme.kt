package com.studyn5.kana.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BrandRed = Color(0xFFD64545)
private val BrandRedDark = Color(0xFFB91C1C)
private val PaperBg = Color(0xFFFBF7EF)
private val Ink = Color(0xFF1F2937)
private val Muted = Color(0xFF8B8577)

private val LightColors = lightColorScheme(
    primary = BrandRed,
    onPrimary = Color.White,
    secondary = BrandRedDark,
    background = PaperBg,
    onBackground = Ink,
    surface = Color.White,
    onSurface = Ink,
    outline = Color(0xFFECE6D8),
    onSurfaceVariant = Muted,
)

@Composable
fun KanaMasterTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content,
    )
}
