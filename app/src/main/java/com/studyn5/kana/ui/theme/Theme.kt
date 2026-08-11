package com.studyn5.kana.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val KanaNavy = Color(0xFF102D55)
val KanaNavyDark = Color(0xFF0B1F3A)
val KanaRed = Color(0xFFE43D32)
val KanaJade = Color(0xFF3C9365)
val KanaIvory = Color(0xFFFAF7F0)
val KanaSurface = Color(0xFFFFFDF8)
val KanaSurfaceSoft = Color(0xFFF1ECE3)
val KanaInk = Color(0xFF172033)
val KanaMuted = Color(0xFF6F7480)
val KanaOutline = Color(0xFFE5DED2)

private val LightColors = lightColorScheme(
    primary = KanaNavy,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDCE8F6),
    onPrimaryContainer = KanaNavyDark,
    secondary = KanaRed,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFE2DE),
    onSecondaryContainer = Color(0xFF772018),
    tertiary = KanaJade,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFDDEFE4),
    onTertiaryContainer = Color(0xFF17492E),
    background = KanaIvory,
    onBackground = KanaInk,
    surface = KanaSurface,
    onSurface = KanaInk,
    surfaceVariant = KanaSurfaceSoft,
    onSurfaceVariant = KanaMuted,
    outline = KanaOutline,
    outlineVariant = Color(0xFFEDE7DE),
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
