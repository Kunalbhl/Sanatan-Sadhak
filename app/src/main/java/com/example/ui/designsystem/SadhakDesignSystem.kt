package com.example.ui.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.example.ui.theme.SadhakColors
import com.example.ui.theme.SadhakDimensions
import com.example.ui.theme.SadhakThemeColors
import com.example.ui.theme.SadhakThemeDimensions

/**
 * Sanatan Sadhak Design System
 * Central access object for theme tokens, dimensions, and semantic colors.
 */
object SadhakDesignSystem {
    val colors: SadhakColors
        @Composable
        @ReadOnlyComposable
        get() = SadhakThemeColors

    val dimensions: SadhakDimensions
        @Composable
        @ReadOnlyComposable
        get() = SadhakThemeDimensions
}
