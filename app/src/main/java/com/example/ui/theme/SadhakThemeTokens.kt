package com.example.ui.theme

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * SANATAN SADHAK PERMANENT CANONICAL DIVINE DESIGN SYSTEM
 *
 * Theme Tokens & Visual Language Lock:
 * Warm Ivory / Temple Cream + Deep Sacred Maroon + Sacred Gold + Divine Saffron + Temple Brown
 */
object SadhakCanonicalTokens {
    // Primary Tokens
    val TempleCream = Color(0xFFFDF8EE)        // Primary Background (#FDF8EE)
    val DeepSacredMaroon = Color(0xFF7A1720)   // Primary Typography & Headings (#7A1720)
    val SacredGold = Color(0xFFD4A23A)         // Primary Accent (#D4A23A)
    val DivineSaffron = Color(0xFFE65100)      // Secondary Accent (#E65100)
    val TempleBrown = Color(0xFF4A2318)        // Supporting Text/Icon Color (#4A2318)

    // Supporting Tokens
    val SoftCream = Color(0xFFFFFDF7)          // Card Background
    val WarmWhite = Color(0xFFFFFFFF)          // Elevated Surfaces
    val MutedSand = Color(0xFFEFE6D5)          // Separators & Subtle Borders (#EFE6D5)
    val SacredGreen = Color(0xFF2E7D32)        // Verified / Success States (#2E7D32)
    val SoftRed = Color(0xFFC62828)            // Destructive / Error States (#C62828)

    // Deep Temple Dark Theme Adaptations
    val TempleDarkBackground = Color(0xFF160D08) // Deep Temple Dark (#160D08)
    val TempleDarkSurface = Color(0xFF261710)    // Deep Dark Surface (#261710)
    val TempleDarkElevated = Color(0xFF352017)   // Elevated Surface (#352017)
    val SacredAmberGlow = Color(0xFFE5B85C)      // Dark Mode Amber Glow
}

object SadhakThemeTypography {
    val brandTitle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.5.sp,
        color = DeepSacredMaroon
    )

    val sectionHeading = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        color = DeepSacredMaroon
    )

    val bodyText = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        color = TempleBrown
    )

    val sanskritVerse = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.5.sp,
        color = DeepSacredMaroon
    )
}

object SadhakShapes {
    val small = RoundedCornerShape(8.dp)
    val medium = RoundedCornerShape(12.dp)
    val large = RoundedCornerShape(16.dp)
    val extraLarge = RoundedCornerShape(24.dp)
}

object SadhakAnimations {
    val gentleGlowSpec: AnimationSpec<Float> = tween(durationMillis = 2400, easing = FastOutSlowInEasing)
    val dhwajaWaveSpec: AnimationSpec<Float> = tween(durationMillis = 3000, easing = FastOutSlowInEasing)
    val birdsFlightSpec: AnimationSpec<Float> = tween(durationMillis = 18000, easing = FastOutSlowInEasing)
}
