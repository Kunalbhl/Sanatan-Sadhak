package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * Sacred Devotional Icon Set for Sanatan Sadhak (12 Canonical Custom Icons)
 */

/** 1. Sacred Japa Mala Icon */
@Composable
fun DivineJapaMalaIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    beadColor: Color = Color(0xFFE58A16),
    threadColor: Color = Color(0xFFD4A23A),
    guruBeadColor: Color = Color(0xFF7A1720)
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val cx = w / 2f
        val cy = h * 0.44f
        val rx = w * 0.38f
        val ry = h * 0.35f

        val beadCount = 12
        for (i in 0 until beadCount) {
            val angle = Math.toRadians((i * (360.0 / beadCount))).toFloat()
            val bx = cx + rx * cos(angle)
            val by = cy + ry * sin(angle)
            drawCircle(
                color = beadColor,
                center = Offset(bx, by),
                radius = w * 0.075f
            )
            drawCircle(
                color = Color(0xFFFFD54F),
                center = Offset(bx - w * 0.02f, by - w * 0.02f),
                radius = w * 0.025f
            )
        }

        val guruY = cy + ry
        drawCircle(
            color = guruBeadColor,
            center = Offset(cx, guruY),
            radius = w * 0.10f
        )
        drawCircle(
            color = Color(0xFFD4A23A),
            center = Offset(cx, guruY),
            radius = w * 0.10f,
            style = Stroke(width = 1.dp.toPx())
        )

        val tasselPath = Path().apply {
            moveTo(cx, guruY + w * 0.08f)
            lineTo(cx - w * 0.10f, h * 0.95f)
            lineTo(cx + w * 0.10f, h * 0.95f)
            close()
        }
        drawPath(path = tasselPath, color = threadColor)
    }
}

/** 2. Sacred Dhyan / Meditation Lotus Icon */
@Composable
fun DivineDhyanIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    tint: Color = Color(0xFFE58A16),
    auraTint: Color = Color(0xFFD4A23A)
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        drawCircle(
            color = auraTint.copy(alpha = 0.35f),
            center = Offset(w * 0.5f, h * 0.32f),
            radius = w * 0.22f
        )

        drawCircle(
            color = tint,
            center = Offset(w * 0.5f, h * 0.32f),
            radius = w * 0.14f
        )

        val bodyPath = Path().apply {
            moveTo(w * 0.44f, h * 0.44f)
            lineTo(w * 0.22f, h * 0.60f)
            quadraticTo(w * 0.10f, h * 0.78f, w * 0.24f, h * 0.88f)
            lineTo(w * 0.76f, h * 0.88f)
            quadraticTo(w * 0.90f, h * 0.78f, w * 0.78f, h * 0.60f)
            lineTo(w * 0.56f, h * 0.44f)
            close()
        }
        drawPath(path = bodyPath, color = tint)

        drawCircle(
            color = auraTint,
            center = Offset(w * 0.5f, h * 0.75f),
            radius = w * 0.08f
        )
    }
}

/** 3. Sacred My Mantra / Scroll Icon */
@Composable
fun DivineMyMantraIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    primaryColor: Color = Color(0xFF7A1720),
    accentColor: Color = Color(0xFFE58A16),
    goldColor: Color = Color(0xFFD4A23A)
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        val scrollPath = Path().apply {
            moveTo(w * 0.20f, h * 0.18f)
            quadraticTo(w * 0.50f, h * 0.12f, w * 0.80f, h * 0.18f)
            lineTo(w * 0.80f, h * 0.82f)
            quadraticTo(w * 0.50f, h * 0.88f, w * 0.20f, h * 0.82f)
            close()
        }
        drawPath(
            path = scrollPath,
            brush = Brush.verticalGradient(listOf(Color(0xFFFFF8EE), Color(0xFFFDECD2)))
        )
        drawPath(
            path = scrollPath,
            color = goldColor,
            style = Stroke(width = 1.2.dp.toPx())
        )

        drawCircle(color = accentColor, center = Offset(w * 0.18f, h * 0.18f), radius = w * 0.06f)
        drawCircle(color = accentColor, center = Offset(w * 0.82f, h * 0.18f), radius = w * 0.06f)
        drawCircle(color = accentColor, center = Offset(w * 0.18f, h * 0.82f), radius = w * 0.06f)
        drawCircle(color = accentColor, center = Offset(w * 0.82f, h * 0.82f), radius = w * 0.06f)

        drawCircle(
            color = primaryColor.copy(alpha = 0.15f),
            center = Offset(w * 0.5f, h * 0.5f),
            radius = w * 0.20f
        )
        drawCircle(
            color = primaryColor,
            center = Offset(w * 0.5f, h * 0.5f),
            radius = w * 0.06f
        )
    }
}

/** 4. Sacred Aarti Bell & Diya Icon */
@Composable
fun DivineAartiCounterIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    goldColor: Color = Color(0xFFD4A23A),
    flameColor: Color = Color(0xFFE58A16)
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        val platePath = Path().apply {
            moveTo(w * 0.15f, h * 0.65f)
            quadraticTo(w * 0.5f, h * 0.85f, w * 0.85f, h * 0.65f)
            lineTo(w * 0.75f, h * 0.82f)
            quadraticTo(w * 0.5f, h * 0.92f, w * 0.25f, h * 0.82f)
            close()
        }
        drawPath(path = platePath, color = goldColor)

        val flamePath = Path().apply {
            moveTo(w * 0.50f, h * 0.60f)
            quadraticTo(w * 0.38f, h * 0.40f, w * 0.50f, h * 0.18f)
            quadraticTo(w * 0.62f, h * 0.40f, w * 0.50f, h * 0.60f)
        }
        drawPath(
            path = flamePath,
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFFFEA7A), flameColor),
                center = Offset(w * 0.5f, h * 0.40f),
                radius = w * 0.25f
            )
        )

        drawLine(
            color = goldColor,
            start = Offset(w * 0.85f, h * 0.65f),
            end = Offset(w * 0.95f, h * 0.75f),
            strokeWidth = 1.8.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

/** 5. Sacred Chants Icon */
@Composable
fun DivineChantsIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    primaryColor: Color = Color(0xFF7A1720),
    accentColor: Color = Color(0xFFD4A23A)
) {
    DivineShankhaIcon(modifier = modifier, size = size, primaryColor = primaryColor, accentColor = accentColor)
}

/** 6. Sacred OM Emblem Symbol Icon */
@Composable
fun DivineOmSymbolIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = Color(0xFF7A1720)
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        drawCircle(
            color = Color(0xFFD4A23A).copy(alpha = 0.25f),
            center = Offset(w * 0.5f, h * 0.5f),
            radius = w * 0.42f
        )

        val omPath = Path().apply {
            moveTo(w * 0.35f, h * 0.35f)
            quadraticTo(w * 0.25f, h * 0.25f, w * 0.38f, h * 0.20f)
            quadraticTo(w * 0.52f, h * 0.25f, w * 0.40f, h * 0.45f)
            quadraticTo(w * 0.58f, h * 0.55f, w * 0.42f, h * 0.78f)
            quadraticTo(w * 0.22f, h * 0.70f, w * 0.30f, h * 0.55f)
        }
        drawPath(
            path = omPath,
            color = color,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )

        drawCircle(
            color = color,
            center = Offset(w * 0.62f, h * 0.22f),
            radius = w * 0.05f
        )
    }
}

/** 7. Sacred Diya Flame Icon */
@Composable
fun DivineDiyaIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    goldColor: Color = Color(0xFFD4A23A),
    flameColor: Color = Color(0xFFE58A16)
) {
    DivineAartiCounterIcon(modifier = modifier, size = size, goldColor = goldColor, flameColor = flameColor)
}

/** 8. Sacred Lotus Blossom Icon */
@Composable
fun DivineLotusIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    petalColor: Color = Color(0xFFE58A16),
    accentColor: Color = Color(0xFFD4A23A)
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        val centerPetal = Path().apply {
            moveTo(w * 0.5f, h * 0.75f)
            quadraticTo(w * 0.35f, h * 0.45f, w * 0.5f, h * 0.18f)
            quadraticTo(w * 0.65f, h * 0.45f, w * 0.5f, h * 0.75f)
        }
        drawPath(path = centerPetal, color = petalColor)

        val leftPetal = Path().apply {
            moveTo(w * 0.5f, h * 0.75f)
            quadraticTo(w * 0.15f, h * 0.55f, w * 0.22f, h * 0.35f)
            quadraticTo(w * 0.40f, h * 0.50f, w * 0.5f, h * 0.75f)
        }
        drawPath(path = leftPetal, color = accentColor)

        val rightPetal = Path().apply {
            moveTo(w * 0.5f, h * 0.75f)
            quadraticTo(w * 0.85f, h * 0.55f, w * 0.78f, h * 0.35f)
            quadraticTo(w * 0.60f, h * 0.50f, w * 0.5f, h * 0.75f)
        }
        drawPath(path = rightPetal, color = accentColor)
    }
}

/** 9. Sacred Temple Mandir Icon */
@Composable
fun DivineTempleIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    maroonColor: Color = Color(0xFF7A1720),
    goldColor: Color = Color(0xFFD4A23A)
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        val shikhara = Path().apply {
            moveTo(w * 0.50f, h * 0.15f)
            lineTo(w * 0.22f, h * 0.55f)
            lineTo(w * 0.78f, h * 0.55f)
            close()
        }
        drawPath(path = shikhara, color = maroonColor)

        drawRect(
            color = goldColor,
            topLeft = Offset(w * 0.25f, h * 0.55f),
            size = Size(w * 0.50f, h * 0.32f)
        )

        drawCircle(
            color = goldColor,
            center = Offset(w * 0.50f, h * 0.12f),
            radius = w * 0.05f
        )
    }
}

/** 10. Sacred Shankha Conch Icon */
@Composable
fun DivineShankhaIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    primaryColor: Color = Color(0xFF7A1720),
    accentColor: Color = Color(0xFFD4A23A)
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        val shankhPath = Path().apply {
            moveTo(w * 0.20f, h * 0.35f)
            cubicTo(w * 0.30f, h * 0.15f, w * 0.75f, h * 0.18f, w * 0.82f, h * 0.42f)
            cubicTo(w * 0.88f, h * 0.65f, w * 0.60f, h * 0.85f, w * 0.45f, h * 0.90f)
            lineTo(w * 0.25f, h * 0.92f)
            cubicTo(w * 0.12f, h * 0.75f, w * 0.10f, h * 0.55f, w * 0.20f, h * 0.35f)
            close()
        }
        drawPath(
            path = shankhPath,
            brush = Brush.linearGradient(listOf(Color(0xFFFFF8EE), accentColor, primaryColor))
        )
        drawPath(
            path = shankhPath,
            color = primaryColor,
            style = Stroke(width = 1.2.dp.toPx())
        )

        val spiral = Path().apply {
            moveTo(w * 0.45f, h * 0.35f)
            cubicTo(w * 0.65f, h * 0.40f, w * 0.60f, h * 0.65f, w * 0.42f, h * 0.68f)
        }
        drawPath(
            path = spiral,
            color = primaryColor,
            style = Stroke(width = 1.2.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

/** 11. Sacred Temple Bell Icon */
@Composable
fun DivineBellIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    goldColor: Color = Color(0xFFD4A23A),
    maroonColor: Color = Color(0xFF7A1720)
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        val bellPath = Path().apply {
            moveTo(w * 0.5f, h * 0.18f)
            quadraticTo(w * 0.28f, h * 0.40f, w * 0.22f, h * 0.72f)
            lineTo(w * 0.78f, h * 0.72f)
            quadraticTo(w * 0.72f, h * 0.40f, w * 0.5f, h * 0.18f)
            close()
        }
        drawPath(path = bellPath, color = goldColor)

        drawCircle(
            color = maroonColor,
            center = Offset(w * 0.5f, h * 0.82f),
            radius = w * 0.08f
        )
    }
}

/** 12. Sacred Scripture Granth Icon */
@Composable
fun DivineScriptureIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    primaryColor: Color = Color(0xFF7A1720),
    accentColor: Color = Color(0xFFE58A16),
    goldColor: Color = Color(0xFFD4A23A)
) {
    DivineMyMantraIcon(modifier = modifier, size = size, primaryColor = primaryColor, accentColor = accentColor, goldColor = goldColor)
}
