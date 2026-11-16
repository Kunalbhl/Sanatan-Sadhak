package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.DeepSacredMaroon
import com.example.ui.theme.MutedSand
import com.example.ui.theme.SacredGold

/**
 * Reusable Sacred Border Components for Sanatan Sadhak.
 * Provides fine gold/maroon ornamental borders, subtle glow, and warm ivory/cream surfaces.
 */

@Composable
fun SacredCardBorder(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = SacredGold.copy(alpha = 0.45f),
    borderWidth: Dp = 1.dp,
    elevation: Dp = 2.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .shadow(elevation = elevation, shape = shape, spotColor = SacredGold.copy(alpha = 0.25f))
            .clip(shape)
            .background(backgroundColor)
            .border(width = borderWidth, color = borderColor, shape = shape),
        content = content
    )
}

@Composable
fun PanchangBorder(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    SacredCardBorder(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        borderColor = SacredGold.copy(alpha = 0.65f),
        borderWidth = 1.2.dp,
        elevation = 3.dp,
        content = content
    )
}

@Composable
fun DivineSectionBorder(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    SacredCardBorder(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        borderColor = MutedSand,
        borderWidth = 1.dp,
        elevation = 1.dp,
        content = content
    )
}

@Composable
fun SacredDialogBorder(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    SacredCardBorder(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        borderColor = SacredGold,
        borderWidth = 1.5.dp,
        elevation = 6.dp,
        content = content
    )
}

@Composable
fun ScriptureBorder(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    SacredCardBorder(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        borderColor = DeepSacredMaroon.copy(alpha = 0.35f),
        borderWidth = 1.dp,
        elevation = 2.dp,
        content = content
    )
}

@Composable
fun AuthenticationBorder(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    SacredCardBorder(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        borderColor = SacredGold.copy(alpha = 0.5f),
        borderWidth = 1.2.dp,
        elevation = 4.dp,
        content = content
    )
}

@Composable
fun ProfileBorder(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    SacredCardBorder(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        borderColor = SacredGold.copy(alpha = 0.55f),
        borderWidth = 1.2.dp,
        elevation = 3.dp,
        content = content
    )
}
