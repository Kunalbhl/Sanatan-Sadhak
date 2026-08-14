package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.designsystem.SadhakCard
import com.example.ui.designsystem.SadhakChip
import com.example.ui.designsystem.SadhakSectionHeader
import com.example.ui.theme.BrassTan
import com.example.ui.theme.Terracotta
import com.example.ui.theme.WarmBorder

@Composable
fun DiyaDecoration(modifier: Modifier = Modifier, size: Dp = 32.dp, color: Color = MaterialTheme.colorScheme.primary) {
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary

    Canvas(modifier = modifier.size(size)) {
        val width = size.toPx()
        val height = size.toPx()
        
        // Draw Flame first (higher up)
        val flamePath = Path().apply {
            moveTo(width * 0.5f, height * 0.45f)
            quadraticTo(width * 0.35f, height * 0.28f, width * 0.5f, height * 0.05f)
            quadraticTo(width * 0.65f, height * 0.28f, width * 0.5f, height * 0.45f)
        }
        drawPath(
            path = flamePath,
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFFFEA7A), tertiaryColor, Terracotta),
                center = androidx.compose.ui.geometry.Offset(width * 0.5f, height * 0.25f),
                radius = width * 0.25f
            )
        )
        // Draw Diya Bowl
        val bowlPath = Path().apply {
            moveTo(width * 0.15f, height * 0.55f)
            cubicTo(
                width * 0.15f, height * 0.9f,
                width * 0.85f, height * 0.9f,
                width * 0.85f, height * 0.55f
            )
            lineTo(width * 0.58f, height * 0.52f)
            quadraticTo(width * 0.5f, height * 0.45f, width * 0.42f, height * 0.52f)
            close()
        }
        drawPath(
            path = bowlPath,
            brush = Brush.linearGradient(
                colors = listOf(primaryColor, Terracotta, secondaryColor)
            )
        )
    }
}

/**
 * Migration Alias: SacredCard delegates to SadhakCard
 */
@Composable
fun SacredCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Unspecified,
    borderColor: Color = Color.Unspecified,
    content: @Composable () -> Unit
) {
    SadhakCard(
        modifier = modifier,
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        content = content
    )
}

/**
 * Migration Alias: SacredHeader delegates to SadhakSectionHeader
 */
@Composable
fun SacredHeader(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    showDiyas: Boolean = true
) {
    SadhakSectionHeader(
        modifier = modifier,
        title = title,
        subtitle = subtitle,
        showDecorations = showDiyas
    )
}

/**
 * Migration Alias: CategoryTab delegates to SadhakChip
 */
@Composable
fun CategoryTab(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTagStr: String? = null
) {
    SadhakChip(
        text = text,
        isSelected = isSelected,
        onClick = onClick,
        modifier = modifier,
        testTag = testTagStr
    )
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.FloatingActionButton(
        onClick = onClick,
        modifier = modifier.testTag("scroll_to_top_fab"),
        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
        contentColor = Color.White,
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Default.ArrowUpward,
            contentDescription = "Scroll to Top"
        )
    }
}

@Composable
fun SettingToggleTile(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = title, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
        androidx.compose.material3.Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = androidx.compose.material3.SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.Gray,
                checkedBorderColor = MaterialTheme.colorScheme.primary,
                uncheckedBorderColor = Color.Gray
            )
        )
    }
}

@Composable
fun SettingActionTile(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = title, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.outline)
    }
}

/**
 * Sacred Vedic Temple Header Artwork
 * Renders majestic temple shikharas, glowing golden sunrise aura, kalash finials, and distant sacred birds.
 */
@Composable
fun SacredTempleArtwork(
    modifier: Modifier = Modifier,
    sunTint: Color = Color(0xFFE58A16),
    templeTint: Color = Color(0xFFD4A23A),
    glowTint: Color = Color(0xFFFFF1D6)
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // 1. Soft Sunrise Radial Glow
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(sunTint.copy(alpha = 0.35f), glowTint.copy(alpha = 0.15f), Color.Transparent),
                center = androidx.compose.ui.geometry.Offset(w * 0.72f, h * 0.40f),
                radius = w * 0.45f
            ),
            center = androidx.compose.ui.geometry.Offset(w * 0.72f, h * 0.40f),
            radius = w * 0.45f
        )

        // 2. Rising Sun Disk
        drawCircle(
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFFFFB300), Color(0xFFE65100))
            ),
            center = androidx.compose.ui.geometry.Offset(w * 0.72f, h * 0.40f),
            radius = w * 0.14f
        )

        // 3. Distant Flying Sacred Birds
        fun drawBird(bx: Float, by: Float, bSize: Float) {
            val birdPath = Path().apply {
                moveTo(bx - bSize, by + bSize * 0.3f)
                quadraticTo(bx - bSize * 0.4f, by - bSize * 0.4f, bx, by)
                quadraticTo(bx + bSize * 0.4f, by - bSize * 0.4f, bx + bSize, by + bSize * 0.3f)
            }
            drawPath(
                path = birdPath,
                color = templeTint.copy(alpha = 0.5f),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.5.dp.toPx())
            )
        }
        drawBird(w * 0.35f, h * 0.22f, 7.dp.toPx())
        drawBird(w * 0.48f, h * 0.15f, 9.dp.toPx())
        drawBird(w * 0.58f, h * 0.24f, 6.dp.toPx())

        // 4. Main Temple Shikhara (Grand Central Spire)
        val mainSpirePath = Path().apply {
            moveTo(w * 0.75f, h * 0.08f) // Top Kalash point
            // Curving down to right
            cubicTo(w * 0.78f, h * 0.25f, w * 0.88f, h * 0.65f, w * 0.95f, h)
            lineTo(w * 0.55f, h)
            // Curving up to left
            cubicTo(w * 0.62f, h * 0.65f, w * 0.72f, h * 0.25f, w * 0.75f, h * 0.08f)
            close()
        }
        drawPath(
            path = mainSpirePath,
            brush = Brush.verticalGradient(
                colors = listOf(templeTint.copy(alpha = 0.40f), templeTint.copy(alpha = 0.18f), Color.Transparent)
            )
        )

        // 5. Left Secondary Shikhara
        val leftSpirePath = Path().apply {
            moveTo(w * 0.60f, h * 0.25f)
            cubicTo(w * 0.63f, h * 0.45f, w * 0.68f, h * 0.75f, w * 0.72f, h)
            lineTo(w * 0.45f, h)
            cubicTo(w * 0.48f, h * 0.75f, w * 0.57f, h * 0.45f, w * 0.60f, h * 0.25f)
            close()
        }
        drawPath(
            path = leftSpirePath,
            brush = Brush.verticalGradient(
                colors = listOf(templeTint.copy(alpha = 0.30f), templeTint.copy(alpha = 0.12f), Color.Transparent)
            )
        )

        // 6. Right Mandapa Dome
        val rightMandapaPath = Path().apply {
            moveTo(w * 0.88f, h * 0.45f)
            quadraticTo(w * 0.96f, h * 0.55f, w, h * 0.75f)
            lineTo(w, h)
            lineTo(w * 0.82f, h)
            close()
        }
        drawPath(
            path = rightMandapaPath,
            brush = Brush.verticalGradient(
                colors = listOf(templeTint.copy(alpha = 0.25f), Color.Transparent)
            )
        )

        // 7. Kalash Finial on Central Shikhara
        drawCircle(
            color = Color(0xFFD4A23A),
            center = androidx.compose.ui.geometry.Offset(w * 0.75f, h * 0.08f),
            radius = 3.dp.toPx()
        )
        // Sacred Temple Dhwaja (Flag)
        val flagPath = Path().apply {
            moveTo(w * 0.75f, h * 0.06f)
            lineTo(w * 0.82f, h * 0.085f)
            lineTo(w * 0.75f, h * 0.11f)
            close()
        }
        drawPath(path = flagPath, color = sunTint.copy(alpha = 0.85f))
    }
}

/**
 * Sacred Granth / Holy Scriptures Open Book Artwork
 */
@Composable
fun SacredGranthArtwork(
    modifier: Modifier = Modifier,
    primaryTint: Color = Color(0xFF7A1720),
    goldTint: Color = Color(0xFFD4A23A)
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Outer cover back
        val coverPath = Path().apply {
            moveTo(w * 0.05f, h * 0.78f)
            quadraticTo(w * 0.50f, h * 0.88f, w * 0.95f, h * 0.78f)
            lineTo(w * 0.93f, h * 0.84f)
            quadraticTo(w * 0.50f, h * 0.94f, w * 0.07f, h * 0.84f)
            close()
        }
        drawPath(path = coverPath, color = primaryTint)

        // Left Page
        val leftPage = Path().apply {
            moveTo(w * 0.50f, h * 0.25f)
            cubicTo(w * 0.35f, h * 0.20f, w * 0.20f, h * 0.22f, w * 0.08f, h * 0.30f)
            lineTo(w * 0.08f, h * 0.75f)
            cubicTo(w * 0.20f, h * 0.67f, w * 0.35f, h * 0.65f, w * 0.50f, h * 0.70f)
            close()
        }
        drawPath(
            path = leftPage,
            brush = Brush.horizontalGradient(
                colors = listOf(Color(0xFFFFF8E9), Color(0xFFFFFDF8))
            )
        )
        drawPath(
            path = leftPage,
            color = goldTint.copy(alpha = 0.5f),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx())
        )

        // Right Page
        val rightPage = Path().apply {
            moveTo(w * 0.50f, h * 0.25f)
            cubicTo(w * 0.65f, h * 0.20f, w * 0.80f, h * 0.22f, w * 0.92f, h * 0.30f)
            lineTo(w * 0.92f, h * 0.75f)
            cubicTo(w * 0.80f, h * 0.67f, w * 0.65f, h * 0.65f, w * 0.50f, h * 0.70f)
            close()
        }
        drawPath(
            path = rightPage,
            brush = Brush.horizontalGradient(
                colors = listOf(Color(0xFFFFFDF8), Color(0xFFFFF8E9))
            )
        )
        drawPath(
            path = rightPage,
            color = goldTint.copy(alpha = 0.5f),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx())
        )

        // Center spine bookmark ribbon
        val ribbon = Path().apply {
            moveTo(w * 0.48f, h * 0.25f)
            lineTo(w * 0.52f, h * 0.25f)
            lineTo(w * 0.54f, h * 0.85f)
            lineTo(w * 0.50f, h * 0.80f)
            lineTo(w * 0.46f, h * 0.85f)
            close()
        }
        drawPath(path = ribbon, color = primaryTint)

        // Subtle Sanskrit scripture line etchings
        for (i in 0..2) {
            val yOffset = h * (0.38f + i * 0.10f)
            drawLine(
                color = goldTint.copy(alpha = 0.35f),
                start = androidx.compose.ui.geometry.Offset(w * 0.15f, yOffset),
                end = androidx.compose.ui.geometry.Offset(w * 0.42f, yOffset),
                strokeWidth = 1.5.dp.toPx()
            )
            drawLine(
                color = goldTint.copy(alpha = 0.35f),
                start = androidx.compose.ui.geometry.Offset(w * 0.58f, yOffset),
                end = androidx.compose.ui.geometry.Offset(w * 0.85f, yOffset),
                strokeWidth = 1.5.dp.toPx()
            )
        }
    }
}

/**
 * Sacred Brass Temple Bell Artwork (Ghanti)
 */
@Composable
fun SacredTempleBellArtwork(
    modifier: Modifier = Modifier,
    goldTint: Color = Color(0xFFD4A23A),
    bronzeTint: Color = Color(0xFF9A5A18)
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Brass Chain Links
        for (i in 0..2) {
            val cy = h * (0.08f + i * 0.09f)
            drawOval(
                brush = Brush.linearGradient(colors = listOf(goldTint, bronzeTint)),
                topLeft = androidx.compose.ui.geometry.Offset(w * 0.45f, cy - h * 0.04f),
                size = androidx.compose.ui.geometry.Size(w * 0.10f, h * 0.08f),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.5.dp.toPx())
            )
        }

        // Bell Body (Cupola)
        val bellPath = Path().apply {
            moveTo(w * 0.40f, h * 0.35f)
            lineTo(w * 0.60f, h * 0.35f)
            cubicTo(w * 0.62f, h * 0.45f, w * 0.78f, h * 0.68f, w * 0.85f, h * 0.75f)
            quadraticTo(w * 0.50f, h * 0.82f, w * 0.15f, h * 0.75f)
            cubicTo(w * 0.22f, h * 0.68f, w * 0.38f, h * 0.45f, w * 0.40f, h * 0.35f)
            close()
        }
        drawPath(
            path = bellPath,
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFFFE082), goldTint, bronzeTint),
                center = androidx.compose.ui.geometry.Offset(w * 0.45f, h * 0.52f),
                radius = w * 0.35f
            )
        )

        // Bell Bottom Rim
        drawOval(
            brush = Brush.linearGradient(colors = listOf(goldTint, bronzeTint)),
            topLeft = androidx.compose.ui.geometry.Offset(w * 0.15f, h * 0.71f),
            size = androidx.compose.ui.geometry.Size(w * 0.70f, h * 0.09f)
        )

        // Clapper Ball
        drawCircle(
            brush = Brush.radialGradient(colors = listOf(Color(0xFFFFD54F), bronzeTint)),
            center = androidx.compose.ui.geometry.Offset(w * 0.50f, h * 0.83f),
            radius = w * 0.08f
        )

        // Acoustic Aura Waves
        val wavePaint = goldTint.copy(alpha = 0.30f)
        drawArc(
            color = wavePaint,
            startAngle = 20f,
            sweepAngle = 140f,
            useCenter = false,
            topLeft = androidx.compose.ui.geometry.Offset(w * 0.05f, h * 0.65f),
            size = androidx.compose.ui.geometry.Size(w * 0.90f, h * 0.30f),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.2.dp.toPx())
        )
    }
}

/**
 * Golden Lotus Medallion with concentric sacred geometry
 */
@Composable
fun GoldenLotusMedallion(
    modifier: Modifier = Modifier,
    goldTint: Color = Color(0xFFD4A23A),
    maroonTint: Color = Color(0xFF7A1720)
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f
        val r = (w.coerceAtMost(h) / 2f) * 0.9f

        // Outer Gold Ring
        drawCircle(
            brush = Brush.sweepGradient(
                colors = listOf(goldTint, Color(0xFFFFE082), goldTint, Color(0xFF9A5A18), goldTint)
            ),
            center = androidx.compose.ui.geometry.Offset(cx, cy),
            radius = r,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.8.dp.toPx())
        )

        // Inner Maroon Disc
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(maroonTint.copy(alpha = 0.85f), maroonTint)
            ),
            center = androidx.compose.ui.geometry.Offset(cx, cy),
            radius = r * 0.85f
        )

        // 8 Lotus Petals
        for (i in 0 until 8) {
            val angle = Math.toRadians((i * 45.0)).toFloat()
            val px = cx + (r * 0.52f) * kotlin.math.cos(angle)
            val py = cy + (r * 0.52f) * kotlin.math.sin(angle)
            drawCircle(
                color = goldTint.copy(alpha = 0.7f),
                center = androidx.compose.ui.geometry.Offset(px, py),
                radius = r * 0.22f,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx())
            )
        }

        // Center Divine Dot / Bindi
        drawCircle(
            color = Color(0xFFFFD54F),
            center = androidx.compose.ui.geometry.Offset(cx, cy),
            radius = r * 0.18f
        )
    }
}
