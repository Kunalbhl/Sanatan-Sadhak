package com.example.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class SadhakDimensions(
    val screenPaddingSmall: Dp = 8.dp,
    val screenPaddingMedium: Dp = 16.dp,
    val screenPaddingLarge: Dp = 24.dp,
    val screenPaddingExtraLarge: Dp = 32.dp,

    val sectionSpacingSmall: Dp = 8.dp,
    val sectionSpacingMedium: Dp = 16.dp,
    val sectionSpacingLarge: Dp = 24.dp,
    val sectionSpacingExtraLarge: Dp = 32.dp,

    val cardPaddingSmall: Dp = 8.dp,
    val cardPaddingMedium: Dp = 12.dp,
    val cardPaddingLarge: Dp = 16.dp,

    val tileSpacing: Dp = 12.dp,

    val iconSizeSmall: Dp = 16.dp,
    val iconSizeMedium: Dp = 24.dp,
    val iconSizeLarge: Dp = 32.dp,
    val iconSizeExtraLarge: Dp = 48.dp,

    val cornerRadiusSmall: Dp = 8.dp,
    val cornerRadiusMedium: Dp = 12.dp,
    val cornerRadiusLarge: Dp = 16.dp,
    val cornerRadiusExtraLarge: Dp = 24.dp,
    val cornerRadiusPill: Dp = 50.dp,

    val buttonHeightStandard: Dp = 48.dp,
    val buttonHeightLarge: Dp = 56.dp,
    val minTouchTarget: Dp = 48.dp,

    val elevationLow: Dp = 2.dp,
    val elevationMedium: Dp = 4.dp,
    val elevationHigh: Dp = 8.dp
)

val LocalSadhakDimensions = staticCompositionLocalOf { SadhakDimensions() }

val SadhakThemeDimensions: SadhakDimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalSadhakDimensions.current
