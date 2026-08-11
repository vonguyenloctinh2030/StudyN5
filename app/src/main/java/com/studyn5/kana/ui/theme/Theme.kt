package com.studyn5.kana.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.studyn5.kana.R

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

/**
 * Font được đóng gói trong APK để giao diện không thay đổi theo font hệ thống
 * mà người dùng cài trên Samsung/Xiaomi/OPPO.
 */
val AppFontFamily = FontFamily(
    Font(R.font.noto_sans, FontWeight.Normal),
    Font(R.font.noto_sans, FontWeight.Medium),
    Font(R.font.noto_sans, FontWeight.SemiBold),
    Font(R.font.noto_sans, FontWeight.Bold),
    Font(R.font.noto_sans, FontWeight.ExtraBold),
    Font(R.font.noto_sans, FontWeight.Black),
)

private fun TextStyle.withAppFont() = copy(fontFamily = AppFontFamily)

private val defaults = Typography()
private val AppTypography = Typography(
    displayLarge = defaults.displayLarge.withAppFont(),
    displayMedium = defaults.displayMedium.withAppFont(),
    displaySmall = defaults.displaySmall.withAppFont(),
    headlineLarge = defaults.headlineLarge.withAppFont(),
    headlineMedium = defaults.headlineMedium.withAppFont(),
    headlineSmall = defaults.headlineSmall.withAppFont(),
    titleLarge = defaults.titleLarge.withAppFont(),
    titleMedium = defaults.titleMedium.withAppFont(),
    titleSmall = defaults.titleSmall.withAppFont(),
    bodyLarge = defaults.bodyLarge.withAppFont(),
    bodyMedium = defaults.bodyMedium.withAppFont(),
    bodySmall = defaults.bodySmall.withAppFont(),
    labelLarge = defaults.labelLarge.withAppFont(),
    labelMedium = defaults.labelMedium.withAppFont(),
    labelSmall = defaults.labelSmall.withAppFont(),
)

@Composable
fun KanaMasterTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(colorScheme = LightColors, typography = AppTypography) {
        ProvideTextStyle(value = AppTypography.bodyLarge, content = content)
    }
}
