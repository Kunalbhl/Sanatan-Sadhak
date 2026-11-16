package com.example.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Basic Sanatan Palette Tokens (Master Design Reference)
val TempleCream = Color(0xFFFDF8EE)      // Warm Ivory / Temple Cream (#FDF8EE)
val SoftCreamSurface = Color(0xFFFFFDF8) // Soft Cream Surface
val ElevatedCream = Color(0xFFFFF8E9)    // Elevated Cream
val DeepSacredMaroon = Color(0xFF7A1720) // Deep Sacred Maroon (#7A1720)
val DarkMaroon = Color(0xFF5C1018)       // Dark Maroon
val DevotionalSaffron = Color(0xFFE58A16)// Devotional Saffron (#E58A16)
val BrightSacredOrange = Color(0xFFE58A16) // Bright Sacred Orange (#E58A16)
val SacredGold = Color(0xFFD4A23A)       // Sacred Gold (#D4A23A)
val SoftGold = Color(0xFFEBC66A)         // Soft Gold
val WarmBronze = Color(0xFF9A5A18)       // Warm Bronze
val PrimaryTextMaroon = Color(0xFF5C1B1B)// Primary Text
val SecondaryTextBrown = Color(0xFF76544A)// Secondary Text
val MutedTextWarm = Color(0xFF9A8175)    // Muted Text
val WarmDivider = Color(0xFFE8D4B0)      // Warm Divider / Border
val MutedSand = Color(0xFFEFE6D5)        // Muted Sand Separator
val TempleBrown = Color(0xFF4A2318)      // Supporting Text Brown
val IconBgCream = Color(0xFFFFF1D6)      // Icon Background
val NavigationBgCream = Color(0xFFFFFCF5)// Navigation Background

// Aliases for backwards compatibility
val Saffron = BrightSacredOrange
val DeepMaroon = DeepSacredMaroon
val SoftWhite = Color(0xFFFFFFFF)
val Charcoal = PrimaryTextMaroon
val SageGreen = Color(0xFF2E7D32)
val Terracotta = DevotionalSaffron
val WarmBorder = WarmDivider
val BrassTan = MutedTextWarm
val LightThoughtBg = IconBgCream
val SoftGoldBg = ElevatedCream
val WarmSand = Color(0xFFF5ECD8)
val SoftBronze = SecondaryTextBrown
val RoyalBlue = Color(0xFF1565C0)
val WarmAmber = BrightSacredOrange
val CrimsonRed = DeepSacredMaroon

// Dark Mode Adaptations (Deep Temple Theme)
val TempleDarkBackground = Color(0xFF160D08) // Deep Temple Dark (#160D08)
val TempleDarkSurface = Color(0xFF261710)    // Warm Temple Surface
val TempleDarkElevated = Color(0xFF352017)   // Elevated Surface
val SaffronDark = Color(0xFFE58A16)          // Saffron
val SacredGoldDark = Color(0xFFD4A23A)       // Gold
val DeepMaroonDark = Color(0xFF7A1720)       // Maroon
val WarmIvoryTextDark = Color(0xFFF8EEDC)    // Warm Ivory Text
val CharcoalDark = WarmIvoryTextDark
val SoftBronzeDark = Color(0xFFC7B19C)
val DarkBorder = Color(0xFF4A332A)
val LightThoughtBgDark = Color(0xFF3D271F)
val SoftGoldBgDark = Color(0xFF38231C)

// Semantic Color Scheme Data Class
data class SadhakColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val accent: Color,
    val background: Color,
    val surface: Color,
    val surfaceElevated: Color,
    val surfaceMuted: Color,
    val surfaceVariant: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val textInverse: Color,
    val divider: Color,
    val outline: Color,
    val disabled: Color,
    val success: Color,
    val warning: Color,
    val error: Color,
    val info: Color,
    // Sacred Design System Specific Tokens
    val sacredBackground: Color = background,
    val sacredSurface: Color = surface,
    val sacredSurfaceElevated: Color = surfaceElevated,
    val sacredPrimary: Color = primary,
    val sacredSecondary: Color = secondary,
    val sacredGold: Color = accent,
    val sacredSaffron: Color = primary,
    val sacredTextPrimary: Color = textPrimary,
    val sacredTextSecondary: Color = textSecondary,
    val sacredTextMuted: Color = textMuted,
    val sacredDivider: Color = divider,
    val sacredIconBackground: Color = primaryContainer,
    val sacredNavigationBackground: Color = surface,
    val sacredNavigationSelected: Color = primary,
    val sacredNavigationUnselected: Color = textSecondary
)

val LightSadhakColors = SadhakColors(
    primary = DevotionalSaffron,
    onPrimary = SoftWhite,
    primaryContainer = IconBgCream,
    onPrimaryContainer = PrimaryTextMaroon,
    secondary = DeepSacredMaroon,
    onSecondary = SoftWhite,
    secondaryContainer = ElevatedCream,
    onSecondaryContainer = PrimaryTextMaroon,
    accent = SacredGold,
    background = TempleCream,
    surface = SoftCreamSurface,
    surfaceElevated = ElevatedCream,
    surfaceMuted = WarmSand,
    surfaceVariant = ElevatedCream,
    textPrimary = PrimaryTextMaroon,
    textSecondary = SecondaryTextBrown,
    textMuted = MutedTextWarm,
    textInverse = SoftWhite,
    divider = WarmDivider,
    outline = WarmDivider,
    disabled = WarmSand,
    success = SageGreen,
    warning = BrightSacredOrange,
    error = DarkMaroon,
    info = RoyalBlue,
    sacredBackground = TempleCream,
    sacredSurface = SoftCreamSurface,
    sacredSurfaceElevated = ElevatedCream,
    sacredPrimary = DevotionalSaffron,
    sacredSecondary = DeepSacredMaroon,
    sacredGold = SacredGold,
    sacredSaffron = DevotionalSaffron,
    sacredTextPrimary = PrimaryTextMaroon,
    sacredTextSecondary = SecondaryTextBrown,
    sacredTextMuted = MutedTextWarm,
    sacredDivider = WarmDivider,
    sacredIconBackground = IconBgCream,
    sacredNavigationBackground = NavigationBgCream,
    sacredNavigationSelected = DeepSacredMaroon,
    sacredNavigationUnselected = SecondaryTextBrown
)

val DarkSadhakColors = SadhakColors(
    primary = SaffronDark,
    onPrimary = TempleDarkBackground,
    primaryContainer = SoftGoldBgDark,
    onPrimaryContainer = WarmIvoryTextDark,
    secondary = DeepMaroonDark,
    onSecondary = WarmIvoryTextDark,
    secondaryContainer = LightThoughtBgDark,
    onSecondaryContainer = WarmIvoryTextDark,
    accent = SacredGoldDark,
    background = TempleDarkBackground,
    surface = TempleDarkSurface,
    surfaceElevated = TempleDarkElevated,
    surfaceMuted = Color(0xFF1C120E),
    surfaceVariant = SoftGoldBgDark,
    textPrimary = WarmIvoryTextDark,
    textSecondary = SoftBronzeDark,
    textMuted = DarkBorder,
    textInverse = TempleDarkBackground,
    divider = DarkBorder,
    outline = DarkBorder,
    disabled = DarkBorder,
    success = Color(0xFF81C784),
    warning = Color(0xFFFFB74D),
    error = Color(0xFFE57373),
    info = Color(0xFF64B5F6),
    sacredBackground = TempleDarkBackground,
    sacredSurface = TempleDarkSurface,
    sacredSurfaceElevated = TempleDarkElevated,
    sacredPrimary = SaffronDark,
    sacredSecondary = DeepMaroonDark,
    sacredGold = SacredGoldDark,
    sacredSaffron = SaffronDark,
    sacredTextPrimary = WarmIvoryTextDark,
    sacredTextSecondary = SoftBronzeDark,
    sacredTextMuted = DarkBorder,
    sacredDivider = DarkBorder,
    sacredIconBackground = Color(0xFF382921),
    sacredNavigationBackground = Color(0xFF1E130F),
    sacredNavigationSelected = SaffronDark,
    sacredNavigationUnselected = SoftBronzeDark
)

val LocalSadhakColors = staticCompositionLocalOf { LightSadhakColors }

val SadhakThemeColors: SadhakColors
    @Composable
    @ReadOnlyComposable
    get() = LocalSadhakColors.current
