package com.example.ui.designsystem

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.SadhakThemeColors
import com.example.ui.theme.SadhakThemeDimensions

@Composable
fun SadhakCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    backgroundColor: Color = Color.Unspecified,
    borderColor: Color = Color.Unspecified,
    shape: Shape = RoundedCornerShape(SadhakThemeDimensions.cornerRadiusLarge),
    elevation: Dp = SadhakThemeDimensions.elevationLow,
    testTag: String? = null,
    content: @Composable () -> Unit
) {
    val effectiveBg = if (backgroundColor == Color.Unspecified) MaterialTheme.colorScheme.surface else backgroundColor
    val effectiveBorder = if (borderColor == Color.Unspecified) SadhakThemeColors.divider else borderColor

    val cardShape = shape

    val clickModifier = if (onClick != null && enabled) {
        Modifier.clickable(onClick = onClick)
    } else {
        Modifier
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier)
            .border(1.dp, effectiveBorder, cardShape)
            .clip(cardShape)
            .then(clickModifier)
            .defaultMinSize(minHeight = SadhakThemeDimensions.minTouchTarget),
        shape = cardShape,
        colors = CardDefaults.cardColors(containerColor = effectiveBg),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        Box(modifier = Modifier.padding(SadhakThemeDimensions.cardPaddingLarge)) {
            content()
        }
    }
}
