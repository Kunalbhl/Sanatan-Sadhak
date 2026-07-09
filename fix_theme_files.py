import re

with open("app/src/main/java/com/example/ui/theme/Color.kt", "w") as f:
    f.write("""package com.example.ui.theme
import androidx.compose.ui.graphics.Color

val Saffron = Color(0xFFFF9933)
val SacredGold = Color(0xFFD4AF37)
val DeepMaroon = Color(0xFF800000)
val TempleCream = Color(0xFFFEF9F3)
val SoftWhite = Color(0xFFFFFFFF)
val Charcoal = Color(0xFF4A2C2A)
val SageGreen = Color(0xFF008000)
val Terracotta = Color(0xFFC24A21)
val WarmBorder = Color(0xFFF0E6D8)
val BrassTan = Color(0xFFB5895B)
val LightThoughtBg = Color(0xFFFFF4E6)
val SoftGoldBg = Color(0xFFFFFBF0)
val WarmSand = Color(0xFFE8DCC4)

// Dark Mode adaptations
val SaffronDark = Color(0xFFFF9933) 
val SacredGoldDark = Color(0xFFD4AF37)
val DeepMaroonDark = Color(0xFFFFB3B3) // Lighter for contrast
val TempleDarkBackground = Color(0xFF1E1511)
val TempleDarkSurface = Color(0xFF2D201A)
val CharcoalDark = Color(0xFFF0E6D8)
val LightThoughtBgDark = Color(0xFF3E2D23)
val SoftGoldBgDark = Color(0xFF36281D)
""")

with open("app/src/main/java/com/example/ui/theme/Theme.kt", "w") as f:
    f.write("""package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = SaffronDark,
    secondary = DeepMaroonDark,
    tertiary = SacredGoldDark,
    background = TempleDarkBackground,
    surface = TempleDarkSurface,
    surfaceVariant = LightThoughtBgDark,
    primaryContainer = SoftGoldBgDark,
    outline = Color(0xFF5A4435),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = CharcoalDark,
    onSurface = CharcoalDark
)

private val LightColorScheme = lightColorScheme(
    primary = Saffron,
    secondary = DeepMaroon,
    tertiary = SacredGold,
    background = TempleCream,
    surface = SoftWhite,
    surfaceVariant = SoftGoldBg,
    primaryContainer = LightThoughtBg,
    outline = WarmBorder,
    onPrimary = SoftWhite,
    onSecondary = SoftWhite,
    onBackground = Charcoal,
    onSurface = Charcoal
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
""")
