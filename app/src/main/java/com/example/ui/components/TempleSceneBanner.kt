package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.util.Calendar
import java.util.TimeZone
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

/**
 * Animated home banner: a Nagara-style mandir (in the spirit of Ayodhya's Ram Mandir) whose
 * sky follows the phone's local time. Sunrise glow, bright afternoon, sunset, then the moon
 * (in its real phase) and stars at night. Birds fly across, a monkey sits on the shikhar
 * and the saffron flag waves on top. Everything is drawn in code on a Canvas (no images).
 *
 * Usage:
 *   TempleSceneBanner(Modifier.fillMaxWidth().height(170.dp)) { Text("…") }
 *
 * [previewMinutes] forces a time of day (minutes after midnight) for previews/tests.
 */
@Composable
fun TempleSceneBanner(
    modifier: Modifier = Modifier,
    latitude: Double = 26.79, // Ayodhya
    previewMinutes: Float? = null,
    content: @Composable BoxScope.() -> Unit = {}
) {
    // Refresh the time of day every 30 s (sky changes slowly)
    var nowMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (true) { delay(30_000); nowMillis = System.currentTimeMillis() }
    }

    val t = rememberInfiniteTransition(label = "temple_scene")
    val flagPhase by t.animateFloat(0f, (2 * PI).toFloat(), infiniteRepeatable(tween(1400, easing = LinearEasing)), label = "flag")
    val birdProgress by t.animateFloat(0f, 1f, infiniteRepeatable(tween(26_000, easing = LinearEasing)), label = "birds")
    val wing by t.animateFloat(-1f, 1f, infiniteRepeatable(tween(420), RepeatMode.Reverse), label = "wing")
    val tail by t.animateFloat(-12f, 14f, infiniteRepeatable(tween(2200), RepeatMode.Reverse), label = "tail")
    val head by t.animateFloat(-8f, 6f, infiniteRepeatable(tween(5200), RepeatMode.Reverse), label = "head")
    val twinkle by t.animateFloat(0.35f, 1f, infiniteRepeatable(tween(1800), RepeatMode.Reverse), label = "twinkle")
    val glow by t.animateFloat(0.6f, 1f, infiniteRepeatable(tween(3000), RepeatMode.Reverse), label = "glow")

    val sky = remember(nowMillis, previewMinutes, latitude) { SkyState.at(nowMillis, latitude, previewMinutes) }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(22.dp))
            .semantics { contentDescription = "Temple at ${sky.label}" }
    ) {
        Canvas(Modifier.fillMaxSize()) {
            drawScene(sky, flagPhase, birdProgress, wing, tail, head, twinkle, glow)
        }
        content()
    }
}

/** Sky colours and sun/moon placement for a moment in time. */
internal data class SkyState(
    val dayWeight: Float, val twilightWeight: Float, val night: Float,
    val sunX: Float, val sunY: Float, val sunVisible: Boolean,
    val moonX: Float, val moonY: Float, val moonAgeDays: Double, val label: String
) {
    companion object {
        fun at(nowMillis: Long, lat: Double, previewMinutes: Float?): SkyState {
            val tz = TimeZone.getDefault()
            val cal = Calendar.getInstance(tz).apply { timeInMillis = nowMillis }
            val tzHours = tz.getOffset(nowMillis) / 3_600_000.0
            val lon = tzHours * 15.0 // longitude implied by the local time zone
            val doy = cal.get(Calendar.DAY_OF_YEAR)
            // NOAA sunrise/sunset approximation (same as the Panchang engine)
            val g = 2 * PI * (doy - 1) / 365.0
            val eqt = 229.18 * (0.000075 + 0.001868 * cos(g) - 0.032077 * sin(g) - 0.014615 * cos(2 * g) - 0.040849 * sin(2 * g))
            val decl = 0.006918 - 0.399912 * cos(g) + 0.070257 * sin(g) - 0.006758 * cos(2 * g) + 0.000907 * sin(2 * g) - 0.002697 * cos(3 * g) + 0.00148 * sin(3 * g)
            val la = Math.toRadians(lat)
            val cosH = (sin(Math.toRadians(-0.833)) - sin(la) * sin(decl)) / (cos(la) * cos(decl))
            val hDeg = Math.toDegrees(acos(cosH.coerceIn(-1.0, 1.0)))
            val noon = 720 - 4 * lon - eqt + 60 * tzHours
            val sunrise = (noon - 4 * hDeg).toFloat()
            val sunset = (noon + 4 * hDeg).toFloat()

            val mins = previewMinutes ?: (cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE) + cal.get(Calendar.SECOND) / 60f)
            val dayLen = sunset - sunrise
            val f = (mins - sunrise) / dayLen
            val alt = sin(PI * f.coerceIn(-0.2f, 1.2f)).toFloat()
            val wDay = ((alt - 0.05f) / 0.35f).coerceIn(0f, 1f)
            val inWindow = f > -0.15f && f < 1.15f
            val wTw = if (inWindow) max(0f, 1f - abs(alt) / 0.28f) else 0f
            val night = 1f - min(1f, wDay + wTw)

            var nf = (mins - sunset) / (1440f - dayLen)
            if (nf < 0) nf += 1440f / (1440f - dayLen)
            nf %= 1f

            // Moon age from a reference new moon (2000-01-06 18:14 UT), synodic month 29.530588 d
            val jd = nowMillis / 86_400_000.0 + 2440587.5
            val age = ((jd - 2451550.26) % 29.530588 + 29.530588) % 29.530588

            val label = when {
                wDay > 0.6f -> "Day"
                wTw > 0.3f -> if (f < 0.5f) "Sunrise" else "Sunset"
                else -> "Night"
            }
            return SkyState(
                dayWeight = wDay, twilightWeight = wTw, night = night,
                sunX = 60f + 880f * f, sunY = 560f - max(-0.3f, alt) * 470f, sunVisible = f > -0.08f && f < 1.08f,
                moonX = 80f + 840f * nf, moonY = 560f - sin(PI * nf).toFloat() * 450f, moonAgeDays = age, label = label
            )
        }
    }
}

// ----- Palette -----
private val NightSky = listOf(Color(0xFF0B1638), Color(0xFF1F2A5C), Color(0xFF3A3A6E))
private val TwilightSky = listOf(Color(0xFF9A7FC9), Color(0xFFFFA36C), Color(0xFFFFD89A))
private val DaySky = listOf(Color(0xFF9FD6FF), Color(0xFFFDE9C4), Color(0xFFFFF1D6))
private val StoneLine = Color(0xFFB9724A)
private val Gold = Color(0xFFF5B301)
private val Saffron = Color(0xFFFF9800)
private val SaffronDeep = Color(0xFFE65100)

private fun blend(n: Color, tw: Color, d: Color, wDay: Float, wTw: Float): Color =
    lerp(lerp(n, tw, min(1f, wTw + wDay)), d, wDay)

private const val VW = 1000f
private const val VH = 690f
private const val GROUND = 600f

private fun DrawScope.drawScene(
    s: SkyState, flagPhase: Float, birds: Float, wing: Float, tail: Float, head: Float, twinkle: Float, glow: Float
) {
    // Fill the banner (like CSS "cover"), anchored to the bottom centre
    val k = max(size.width / VW, size.height / VH)
    val dx = (size.width - VW * k) / 2f
    val dy = size.height - VH * k
    withTransform({ translate(dx, dy); scale(k, k, Offset.Zero) }) {
        // Sky
        val top = blend(NightSky[0], TwilightSky[0], DaySky[0], s.dayWeight, s.twilightWeight)
        val mid = blend(NightSky[1], TwilightSky[1], DaySky[1], s.dayWeight, s.twilightWeight)
        val bot = blend(NightSky[2], TwilightSky[2], DaySky[2], s.dayWeight, s.twilightWeight)
        drawRect(Brush.verticalGradient(0f to top, 0.65f to mid, 1f to bot, startY = 0f, endY = VH), size = Size(VW, VH))

        // Stars
        if (s.night > 0.05f) {
            for (i in 0 until 60) {
                val x = (i * 157.3f) % VW
                val y = (i * 83.7f) % 330f
                val a = s.night * (if (i % 3 == 0) twinkle else 1.2f - twinkle).coerceIn(0.2f, 1f)
                drawCircle(Color.White.copy(alpha = a), radius = if (i % 4 == 0) 1.8f else 1.1f, center = Offset(x, y))
            }
        }

        // Moon with its phase
        if (s.night > 0.05f) {
            val c = Offset(s.moonX, s.moonY)
            drawCircle(Brush.radialGradient(listOf(Color(0x8CFFFBE8), Color.Transparent), center = c, radius = 70f), radius = 70f, center = c, alpha = s.night)
            drawCircle(Color(0xFFFDF6DC), radius = 26f, center = c, alpha = s.night)
            drawMoonShade(c, 26f, s.moonAgeDays, s.night)
        }

        // Sun with glow
        if (s.sunVisible) {
            val c = Offset(s.sunX, s.sunY)
            drawCircle(Brush.radialGradient(listOf(Color(0xFFFFF8D6), Color(0xFFFFD54F), Color(0x00FFB300)), center = c, radius = 90f), radius = 90f, center = c, alpha = glow)
            drawCircle(Color(0xFFFFF3C4), radius = 30f, center = c)
        }

        // Far hills and the temple glow at night
        val hills = Path().apply {
            moveTo(0f, 520f); cubicTo(120f, 470f, 220f, 500f, 330f, 480f); cubicTo(460f, 455f, 560f, 500f, 690f, 470f)
            cubicTo(820f, 445f, 900f, 490f, 1000f, 470f); lineTo(1000f, VH); lineTo(0f, VH); close()
        }
        drawPath(hills, Color(0xFFE9C79E).copy(alpha = 0.7f))
        if (s.night > 0.05f) {
            drawCircle(Brush.radialGradient(listOf(Color(0x8CFFCF6B), Color.Transparent), center = Offset(500f, 470f), radius = 420f), radius = 420f, center = Offset(500f, 470f), alpha = s.night)
        }

        drawTemple(s, flagPhase, tail, head)

        // Ground
        drawRect(Color(0xFFE8C9A0), topLeft = Offset(0f, GROUND), size = Size(VW, VH - GROUND))

        // Birds: five small flocks crossing at different heights and speeds
        val flocks = listOf(120f to 1f, 170f to 0.8f, 90f to 1.25f, 210f to 0.65f, 140f to 0.9f)
        flocks.forEachIndexed { i, (y, speed) ->
            val p = ((birds * speed + i * 0.23f) % 1f)
            val x = -80f + p * 1160f
            val yy = y - p * 40f
            val members = 1 + i % 3
            for (j in 0 until members) {
                drawBird(Offset(x + j * 22f, yy + j * 9f), (0.7f + (i % 3) * 0.2f), if (j % 2 == 0) wing else -wing)
            }
        }

        // Night tint
        if (s.night > 0f) drawRect(Color(0xFF0D1440).copy(alpha = s.night * 0.4f), size = Size(VW, VH))
    }
}

private fun DrawScope.drawMoonShade(c: Offset, r: Float, ageDays: Double, alpha: Float) {
    // Fraction lit: 0 new → 1 full → 0. Waxing: lit on the right.
    val phase = ageDays / 29.530588
    val lit = (1 - cos(2 * PI * phase)) / 2
    val waxing = phase < 0.5
    val moonPath = Path().apply { addOval(androidx.compose.ui.geometry.Rect(c.x - r, c.y - r, c.x + r, c.y + r)) }
    clipPath(moonPath) {
        // shadow = half disc on the dark side, plus/minus an ellipse for the terminator
        val shadow = Color(0xFF1B2350).copy(alpha = 0.92f * alpha)
        val ex = (abs(1 - 2 * lit) * r).toFloat()
        val darkLeft = waxing
        val halfLeft = androidx.compose.ui.geometry.Rect(c.x - r, c.y - r, c.x, c.y + r)
        val halfRight = androidx.compose.ui.geometry.Rect(c.x, c.y - r, c.x + r, c.y + r)
        drawRect(shadow, topLeft = (if (darkLeft) halfLeft else halfRight).topLeft, size = halfLeft.size)
        val ellipse = androidx.compose.ui.geometry.Rect(c.x - ex, c.y - r, c.x + ex, c.y + r)
        if (lit < 0.5) drawOval(shadow, topLeft = ellipse.topLeft, size = ellipse.size) // crescent: extend shadow
        else drawOval(Color(0xFFFDF6DC).copy(alpha = alpha), topLeft = ellipse.topLeft, size = ellipse.size) // gibbous: extend light
    }
}

private fun DrawScope.drawBird(at: Offset, scale: Float, wing: Float) {
    val lift = 8f * wing * scale
    val p = Path().apply {
        moveTo(at.x, at.y)
        quadraticBezierTo(at.x + 8f * scale, at.y - lift, at.x + 16f * scale, at.y)
        quadraticBezierTo(at.x + 24f * scale, at.y - lift, at.x + 32f * scale, at.y)
    }
    drawPath(p, Color(0xFF3A2A2A), style = Stroke(width = 2.4f, cap = StrokeCap.Round))
}

// ----- Temple -----
private fun stone(s: SkyState) = Brush.verticalGradient(
    listOf(lerp(Color(0xFFE9A77A), Color(0xFFF6CFAE), s.dayWeight), lerp(Color(0xFFC47A52), Color(0xFFE2A982), s.dayWeight))
)

private fun DrawScope.body(path: Path, s: SkyState) {
    drawPath(path, stone(s))
    drawPath(path, StoneLine, style = Stroke(1.4f))
}

private fun DrawScope.line(x1: Float, y1: Float, x2: Float, y2: Float) =
    drawLine(StoneLine.copy(alpha = 0.75f), Offset(x1, y1), Offset(x2, y2), strokeWidth = 1f)

private fun shikharaPath(cx: Float, baseY: Float, topY: Float, halfW: Float) = Path().apply {
    val h = baseY - topY
    moveTo(cx - halfW, baseY)
    cubicTo(cx - halfW, baseY - h * .45f, cx - halfW * .78f, topY + h * .22f, cx - halfW * .32f, topY)
    lineTo(cx + halfW * .32f, topY)
    cubicTo(cx + halfW * .78f, topY + h * .22f, cx + halfW, baseY - h * .45f, cx + halfW, baseY)
    close()
}

private fun DrawScope.shikhara(cx: Float, baseY: Float, topY: Float, halfW: Float, bands: Int, s: SkyState) {
    body(shikharaPath(cx, baseY, topY, halfW), s)
    val h = baseY - topY
    for (i in 1 until bands) {
        val t = i.toFloat() / bands
        val y = baseY - h * t
        val hw = halfW * (.32f + .68f * Math.pow((1 - t).toDouble(), .55).toFloat())
        line(cx - hw, y, cx + hw, y)
    }
    for (f in listOf(-.55f, 0f, .55f)) {
        val rib = Path().apply {
            moveTo(cx + f * halfW, baseY)
            cubicTo(cx + f * halfW, baseY - h * .5f, cx + f * halfW * .6f, topY + h * .2f, cx + f * halfW * .25f, topY + 6f)
        }
        drawPath(rib, StoneLine.copy(alpha = 0.75f), style = Stroke(1f))
    }
}

private fun DrawScope.amalaka(cx: Float, y: Float, r: Float, s: SkyState) {
    val rect = androidx.compose.ui.geometry.Rect(cx - r, y - r * .42f, cx + r, y + r * .42f)
    drawOval(stone(s), rect.topLeft, rect.size)
    drawOval(StoneLine, rect.topLeft, rect.size, style = Stroke(1.4f))
}

private fun DrawScope.kalash(cx: Float, y: Float, sz: Float) {
    val pot = Path().apply { moveTo(cx - sz * .6f, y); quadraticBezierTo(cx, y - sz * 1.5f * 2, cx + sz * .6f, y); close() }
    drawPath(pot, Gold)
    drawCircle(Gold, radius = sz * .35f, center = Offset(cx, y - sz * 1.2f))
}

private fun DrawScope.dome(cx: Float, baseY: Float, r: Float, s: SkyState) {
    val p = Path().apply { moveTo(cx - r, baseY); cubicTo(cx - r, baseY - r * 1.25f, cx + r, baseY - r * 1.25f, cx + r, baseY); close() }
    body(p, s)
    kalash(cx, baseY - r * .93f, r * .16f)
}

private fun DrawScope.hall(x1: Float, x2: Float, y1: Float, y2: Float, n: Int, s: SkyState) {
    body(Path().apply { addRect(androidx.compose.ui.geometry.Rect(x1, y1, x2, y2)) }, s)
    val step = (x2 - x1) / n
    for (i in 0..n) line(x1 + i * step, y1 + 6f, x1 + i * step, y2 - 4f)
    drawLine(Color(0xFFA35D36), Offset(x1 - 6f, y1), Offset(x2 + 6f, y1), strokeWidth = 3f)
}

private fun DrawScope.drawTemple(s: SkyState, flagPhase: Float, tail: Float, head: Float) {
    val cx = 500f
    // Plinth steps
    body(Path().apply { moveTo(150f, GROUND); lineTo(850f, GROUND); lineTo(830f, GROUND - 14); lineTo(170f, GROUND - 14); close() }, s)
    body(Path().apply { moveTo(180f, GROUND - 14); lineTo(820f, GROUND - 14); lineTo(805f, GROUND - 30); lineTo(195f, GROUND - 30); close() }, s)
    body(Path().apply { moveTo(205f, GROUND - 30); lineTo(795f, GROUND - 30); lineTo(785f, GROUND - 44); lineTo(215f, GROUND - 44); close() }, s)
    // Pillared halls
    hall(220f, 360f, GROUND - 120, GROUND - 44, 7, s)
    hall(640f, 780f, GROUND - 120, GROUND - 44, 7, s)
    hall(360f, 640f, GROUND - 150, GROUND - 44, 10, s)
    hall(240f, 350f, GROUND - 168, GROUND - 120, 5, s)
    hall(650f, 760f, GROUND - 168, GROUND - 120, 5, s)
    dome(295f, GROUND - 168, 46f, s); dome(705f, GROUND - 168, 46f, s)
    dome(400f, GROUND - 150, 40f, s); dome(600f, GROUND - 150, 40f, s)
    shikhara(232f, GROUND - 120, GROUND - 200, 16f, 5, s); amalaka(232f, GROUND - 202, 9f, s)
    shikhara(768f, GROUND - 120, GROUND - 200, 16f, 5, s); amalaka(768f, GROUND - 202, 9f, s)
    hall(430f, 570f, GROUND - 230, GROUND - 150, 6, s)
    shikhara(cx - 64, GROUND - 230, GROUND - 330, 26f, 6, s)
    shikhara(cx + 64, GROUND - 230, GROUND - 330, 26f, 6, s)
    shikhara(cx, GROUND - 230, 150f, 78f, 12, s)
    amalaka(cx, 146f, 30f, s)
    kalash(cx, 132f, 13f)

    // Flag pole and waving flag
    drawLine(Color(0xFF8A5A1A), Offset(cx, 112f), Offset(cx, 40f), strokeWidth = 3f, cap = StrokeCap.Round)
    val flag = Path().apply {
        val x0 = cx + 1f; val y0 = 44f
        moveTo(x0, y0)
        for (i in 1..10) {
            val x = i * 7.4f
            lineTo(x0 + x, y0 + x / 74f * 10f + sin(flagPhase + x / 14f) * (x / 74f) * 6f)
        }
        lineTo(x0 + 50f + sin(flagPhase + 3.5f) * 3f, y0 + 20f)
        for (i in 10 downTo 0) {
            val x = i * 7.4f
            lineTo(x0 + x, y0 + 38f - x / 74f * 8f + sin(flagPhase + x / 14f) * (x / 74f) * 6f)
        }
        close()
    }
    drawPath(flag, Brush.horizontalGradient(listOf(Saffron, SaffronDeep), startX = cx, endX = cx + 74f))

    // Monkey on the shikhar
    withTransform({ translate(cx - 56f, 176f); scale(1.55f, 1.55f, Offset.Zero) }) {
        val brown = Color(0xFF7A4E2C)
        rotate(tail, pivot = Offset(6f, 10f)) {
            val tp = Path().apply { moveTo(6f, 10f); cubicTo(-10f, 22f, -26f, 10f, -22f, -6f); cubicTo(-20f, -14f, -12f, -14f, -10f, -8f) }
            drawPath(tp, Color(0xFF6B4426), style = Stroke(3.2f, cap = StrokeCap.Round))
        }
        drawOval(brown, Offset(-1f, -9f), Size(22f, 26f))
        drawOval(Color(0xFFC99467), Offset(4f, -1f), Size(12f, 16f))
        rotate(head, pivot = Offset(11f, -6f)) {
            drawCircle(brown, 9f, Offset(11f, -14f))
            drawOval(Color(0xFFD9A77A), Offset(7f, -17f), Size(12f, 10f))
            drawCircle(Color(0xFF2A160A), 1.3f, Offset(11f, -14f))
            drawCircle(Color(0xFF2A160A), 1.3f, Offset(16f, -14f))
            drawCircle(brown, 3f, Offset(2.5f, -15f))
        }
    }

    // Diyas along the plinth (brighter at night)
    var x = 230f
    while (x <= 770f) {
        drawPath(Path().apply { moveTo(x - 7, GROUND - 46); quadraticBezierTo(x, GROUND - 39, x + 7, GROUND - 46); close() }, Color(0xFFB35A1F))
        drawCircle(Color(0xFFFFB300).copy(alpha = 0.4f + s.night * 0.6f), 3.5f, Offset(x, GROUND - 52))
        x += 54f
    }
}
