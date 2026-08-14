package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkSadhakColors.primary,
    secondary = DarkSadhakColors.secondary,
    tertiary = DarkSadhakColors.accent,
    background = DarkSadhakColors.background,
    surface = DarkSadhakColors.surface,
    surfaceVariant = DarkSadhakColors.surfaceVariant,
    primaryContainer = SoftGoldBgDark,
    outline = DarkSadhakColors.divider,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = DarkSadhakColors.textPrimary,
    onSurface = DarkSadhakColors.textPrimary,
    error = DarkSadhakColors.error
)

private val LightColorScheme = lightColorScheme(
    primary = LightSadhakColors.primary,
    secondary = LightSadhakColors.secondary,
    tertiary = LightSadhakColors.accent,
    background = LightSadhakColors.background,
    surface = LightSadhakColors.surface,
    surfaceVariant = LightSadhakColors.surfaceVariant,
    primaryContainer = LightThoughtBg,
    outline = LightSadhakColors.divider,
    onPrimary = SoftWhite,
    onSecondary = SoftWhite,
    onBackground = LightSadhakColors.textPrimary,
    onSurface = LightSadhakColors.textPrimary,
    error = LightSadhakColors.error
)

@Composable
fun SanatanSadhakTheme(
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

    val sadhakColors = if (darkTheme) DarkSadhakColors else LightSadhakColors
    val sadhakDimensions = SadhakDimensions()

    CompositionLocalProvider(
        LocalSadhakColors provides sadhakColors,
        LocalSadhakDimensions provides sadhakDimensions
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = SadhakTypography,
            content = content
        )
    }
}

// Backwards compatibility theme wrapper
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    SanatanSadhakTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor,
        content = content
    )
}
