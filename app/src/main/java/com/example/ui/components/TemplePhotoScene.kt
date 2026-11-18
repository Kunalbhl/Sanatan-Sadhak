package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.example.R
import kotlinx.coroutines.delay
import java.util.Calendar
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

/**
 * Photo temple banner (same artwork as the website hero).
 * Layers: temple photo → sun / moon / stars → temple cut-out (so the sun sets behind
 * the shikharas) → waving dhwaja and flying birds. The photo is colour-graded by the
 * phone's local time: Brahma Muhurta, morning, afternoon, sandhya, night.
 */
@Composable
fun TemplePhotoScene(modifier: Modifier = Modifier) {
    val hero = ImageBitmap.imageResource(R.drawable.temple_hero)
    val cutout = ImageBitmap.imageResource(R.drawable.temple_fg)

    var now by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) { while (true) { delay(30_000); now = System.currentTimeMillis() } }

    val t = rememberInfiniteTransition(label = "temple_photo")
    val clock by t.animateFloat(0f, 600f, infiniteRepeatable(tween(600_000, easing = LinearEasing)), label = "clock")

    val sky = remember(now) { PhotoSky.at(now) }
    val macaque = remember { Macaque() }

    Canvas(modifier) {
        // Fit like "crop", anchored to the right so the main shikhara always shows
        val k = max(size.width / IMG_W, size.height / IMG_H)
        val ox = size.width - IMG_W * k
        val oy = (size.height - IMG_H * k) * 0.12f // anchor near the top so the flag is never cut
        fun P(x: Float, y: Float) = Offset(ox + x * k, oy + y * k)
        val dst = IntSize((IMG_W * k).toInt(), (IMG_H * k).toInt())
        val dstOff = IntOffset(ox.toInt(), oy.toInt())
        val grade = ColorFilter.colorMatrix(sky.matrix)

        drawImage(hero, dstOffset = dstOff, dstSize = dst, colorFilter = grade)

        // Stars and moon (night), sun along today's arc
        if (sky.night > 0f) {
            for (i in 0 until 60) {
                val a = (0.4f + 0.6f * kotlin.math.abs(sin(clock * 0.8f + i))) * sky.night
                drawCircle(Color.White.copy(alpha = a), (if (i % 5 == 0) 2.4f else 1.5f) * k, P((i * 157.3f) % IMG_W, (i * 53.7f) % 420f))
            }
            val mc = P(160f + sky.moonF * 1350f, 680f - sin(PI * sky.moonF).toFloat() * 560f)
            drawCircle(Brush.radialGradient(listOf(Color(0x80FFFBE8), Color.Transparent), mc, 90f * k), 90f * k, mc, alpha = sky.night)
            drawCircle(Color(0xFFFDF6DC).copy(alpha = sky.night), 26f * k, mc)
        }
        if (sky.sunVisible) {
            val sc = P(140f + sky.sunF * 1400f, 720f - sky.sunAlt * 600f)
            drawCircle(Brush.radialGradient(listOf(Color(0xFFFFFBE6), Color(0xE6FFD54F), Color(0x00FF9800)), sc, 150f * k), 150f * k, sc)
            drawCircle(Color(0xFFFFF6D0), 40f * k, sc)
        }

        drawImage(cutout, dstOffset = dstOff, dstSize = dst, colorFilter = grade)

        // Temple lamps glow after sunset
        if (sky.lamps > 0f) listOf(1218f to 790f, 1016f to 785f, 1430f to 785f, 700f to 800f).forEach { (x, y) ->
            val c = P(x, y)
            drawCircle(Brush.radialGradient(listOf(Color(0xE6FFD27A), Color(0x00FFB300)), c, 40f * k), 40f * k, c, alpha = sky.lamps)
        }

        macaque.step(clock)
        drawMacaque(macaque, P(macaque.x, macaque.y), k, clock)
        drawDhwaja(P(1245.5f, 64f), k, clock)
        drawPhotoBirds(::P, k, clock, if (sky.night > 0.5f) Color(0xFF0C0F24) else Color(0xFF3A2418))
    }
}

private const val IMG_W = 1672f
private const val IMG_H = 941f

private fun DrawScope.drawDhwaja(at: Offset, k: Float, t: Float) {
    val len = 250f * k
    val top = Path().apply { moveTo(at.x, at.y) }
    val bottom = mutableListOf<Offset>()
    for (i in 1..12) {
        val x = i / 12f * len
        val wv = sin(t * 4f - x / (45f * k)) * (x / len) * 18f * k
        top.lineTo(at.x + x, at.y + (x / len) * 60f * k + wv)
        bottom.add(Offset(at.x + x, at.y + 70f * k + (x / len) * 75f * k + wv))
    }
    val ew = sin(t * 4f - len / (45f * k)) * 18f * k
    top.lineTo(at.x + len - 45f * k, at.y + 100f * k + ew)
    bottom.reversed().forEach { top.lineTo(it.x, it.y) }
    top.lineTo(at.x, at.y + 70f * k)
    top.close()
    drawPath(top, Brush.horizontalGradient(listOf(Color(0xFFFF8A00), Color(0xFFE64A19)), at.x, at.x + len))
}

private fun DrawScope.drawPhotoBirds(p: (Float, Float) -> Offset, k: Float, t: Float, color: Color) {
    val flocks = listOf(Triple(180f, 26f, 4), Triple(300f, 34f, 3), Triple(120f, 40f, 2), Triple(400f, 30f, 5), Triple(240f, 48f, 2))
    flocks.forEachIndexed { fi, (y, dur, n) ->
        for (j in 0 until n) {
            val prog = (((t + fi * 5.1f) / dur) % 1f)
            val pos = p(-120f + prog * 1950f + j * 46f, y + j * 16f - prog * 60f)
            val s = (1.6f + (fi % 3) * 0.4f) * k
            val w = sin(t * 9f + fi + j)
            val wing = Path().apply {
                moveTo(pos.x - 9f * s, pos.y - 9f * w * s)
                quadraticBezierTo(pos.x + s, pos.y - 4f * w * s, pos.x + 5f * s, pos.y)
                quadraticBezierTo(pos.x + 9f * s, pos.y - 4f * w * s, pos.x + 18f * s, pos.y - 9f * w * s)
            }
            drawPath(wing, color, style = Stroke(width = 2.2f * s, cap = StrokeCap.Round))
            drawOval(color, Offset(pos.x + 1f * s, pos.y - 1f * s), androidx.compose.ui.geometry.Size(10f * s, 2.4f * s))
        }
    }
}

/** Time-of-day state for the photo banner (sun/moon position and colour grade). */
private data class PhotoSky(
    val sunF: Float, val sunAlt: Float, val sunVisible: Boolean,
    val moonF: Float, val night: Float, val lamps: Float, val matrix: ColorMatrix
) {
    companion object {
        fun at(nowMillis: Long): PhotoSky {
            val cal = Calendar.getInstance().apply { timeInMillis = nowMillis }
            val tz = cal.timeZone.getOffset(nowMillis) / 3_600_000.0
            val doy = cal.get(Calendar.DAY_OF_YEAR)
            val g = 2 * PI * (doy - 1) / 365.0
            val eqt = 229.18 * (0.000075 + 0.001868 * cos(g) - 0.032077 * sin(g) - 0.014615 * cos(2 * g) - 0.040849 * sin(2 * g))
            val decl = 0.006918 - 0.399912 * cos(g) + 0.070257 * sin(g) - 0.006758 * cos(2 * g) + 0.000907 * sin(2 * g)
            val la = Math.toRadians(26.79)
            val cosH = ((sin(Math.toRadians(-0.833)) - sin(la) * sin(decl)) / (cos(la) * cos(decl))).coerceIn(-1.0, 1.0)
            val hDeg = Math.toDegrees(acos(cosH))
            val noon = 720 - 4 * (tz * 15) - eqt + 60 * tz
            val rise = (noon - 4 * hDeg).toFloat()
            val set = (noon + 4 * hDeg).toFloat()
            val m = cal.get(Calendar.HOUR_OF_DAY) * 60f + cal.get(Calendar.MINUTE)
            val f = (m - rise) / (set - rise)
            val alt = sin(PI * f.coerceIn(0f, 1f)).toFloat()
            var nf = (m - set) / (1440f - (set - rise)); if (nf < 0) nf += 1440f / (1440f - (set - rise)); nf %= 1f

            val phase = when {
                m >= rise - 100 && m < rise - 20 -> "brahma"
                m >= rise - 20 && m < rise + 150 -> "dawn"
                m >= rise + 150 && m < set - 60 -> "noon"
                m >= set - 60 && m < set + 40 -> "dusk"
                else -> "night"
            }
            val night = when (phase) { "night" -> 1f; "brahma" -> 0.7f; else -> 0f }
            return PhotoSky(
                sunF = f, sunAlt = alt, sunVisible = f > -0.03f && f < 1.03f,
                moonF = nf, night = night,
                lamps = when (phase) { "night", "brahma" -> 1f; "dusk" -> 0.6f; else -> 0f },
                matrix = gradeFor(phase)
            )
        }

        /** Brightness / saturation / tint per phase (the photo itself is a golden sunset). */
        private fun gradeFor(phase: String): ColorMatrix {
            fun m(b: Float, sat: Float, r: Float, gr: Float, bl: Float) = ColorMatrix().apply {
                setToSaturation(sat)
                val s = ColorMatrix(floatArrayOf(
                    b * r, 0f, 0f, 0f, 0f,
                    0f, b * gr, 0f, 0f, 0f,
                    0f, 0f, b * bl, 0f, 0f,
                    0f, 0f, 0f, 1f, 0f
                ))
                timesAssign(s)
            }
            return when (phase) {
                "brahma" -> m(0.55f, 0.6f, 0.75f, 0.8f, 1.25f)
                "dawn" -> m(1.03f, 0.95f, 1f, 1f, 1.05f)
                "noon" -> m(1.1f, 0.72f, 0.95f, 1.02f, 1.18f)
                "dusk" -> m(1f, 1.1f, 1.05f, 0.98f, 0.92f)
                else -> m(0.45f, 0.5f, 0.6f, 0.7f, 1.35f)
            }
        }
    }
}

/** Playful rhesus macaque (red face, brown fur) that hops between the temple roofs, like the website. */
private class Macaque {
    val spots = listOf(1130f to 650f, 1210f to 612f, 1330f to 660f, 1478f to 668f, 1400f to 600f, 1560f to 700f, 1265f to 560f)
    var at = 0; var x = 1130f; var y = 650f; var dir = 1f
    var hopping = false; var act = 0; var t0 = -1f; var dur = 2.5f
    var fx = 0f; var fy = 0f; var tx = 0f; var ty = 0f; var hop = 0f; var q = 0f
    private val rnd = kotlin.random.Random(System.nanoTime())

    fun step(t: Float) {
        if (t0 < 0f || t < t0) t0 = t
        if ((t - t0) / dur >= 1f) next(t)
        q = ((t - t0) / dur).coerceIn(0f, 1f)
        if (hopping) {
            val e = if (q < .5f) 2 * q * q else 1 - (-2 * q + 2).let { it * it } / 2
            x = fx + (tx - fx) * e
            y = fy + (ty - fy) * e - sin(PI * q).toFloat() * hop
        }
    }

    private fun next(t: Float) {
        if (!hopping) {
            val c = spots.indices.filter { it != at }.sortedBy { kotlin.math.hypot(spots[it].first - x, spots[it].second - y) }
            val pick = if (rnd.nextFloat() < .75f) c[rnd.nextInt(2)] else c[rnd.nextInt(c.size)]
            fx = x; fy = y; tx = spots[pick].first; ty = spots[pick].second; at = pick
            val d = kotlin.math.hypot(tx - x, ty - y)
            dir = if (tx >= x) 1f else -1f; hopping = true; dur = .55f + d / 420f; hop = 60f + d * .35f
        } else {
            x = tx; y = ty; hopping = false; dur = 1.6f + rnd.nextFloat() * 2.6f; act = rnd.nextInt(5)
            if (rnd.nextFloat() < .35f) dir = -dir
        }
        t0 = t
    }
}

private fun DrawScope.drawMacaque(m: Macaque, at: Offset, k: Float, t: Float) {
    var bodyY = 0f; var bodyR = 0f; var armR = 0f; var headR = 0f; var legs = 0f
    if (m.hopping) {
        val s = sin(PI * m.q).toFloat(); bodyR = -18f + 36f * m.q; legs = s; armR = -60f * s
    } else when (m.act) {
        0 -> { bodyY = sin(t * 3f) * 1.2f; headR = sin(t * 1.4f) * 10f }            // sit
        1 -> { armR = -120f + sin(t * 22f) * 12f; headR = -12f; bodyR = -4f }     // scratch
        2 -> { armR = -150f + sin(t * 10f) * 28f; headR = 8f + sin(t * 3f) * 4f } // wave
        3 -> { bodyR = 14f * sin(minOf(1f, m.q * 3f) * PI.toFloat() / 2f); headR = 16f } // peek
        else -> { val b = kotlin.math.abs(sin(t * 8f)); bodyY = -b * 12f; armR = -90f; legs = b } // bounce
    }
    val fur = Color(0xFF8B6542); val dark = Color(0xFF7D5838); val belly = Color(0xFFC9A074)
    val face = Color(0xFFD9665C)
    fun stroke(w: Float) = Stroke(width = w, cap = StrokeCap.Round)
    translate(at.x, at.y + bodyY * 2.1f * k) {
        scale(m.dir * 2.1f * k, 2.1f * k, pivot = Offset.Zero) {
            rotate(bodyR, pivot = Offset(0f, -20f)) {
                val sw = sin(t * (if (m.hopping) 6f else 1.6f)) * 10f
                drawPath(Path().apply {
                    moveTo(-12f, -14f)
                    cubicTo(-26f, -10f + sw * .3f, -36f + sw, -24f + sw * .4f, -32f + sw, -44f + sw * .2f)
                    cubicTo(-30f + sw, -54f, -22f + sw * .6f, -56f, -20f + sw * .5f, -50f)
                }, Color(0xFF7A5532), style = stroke(4f))
                val L = legs * 8f
                drawPath(Path().apply { moveTo(-6f, -14f); cubicTo(-12f - L, -6f, -10f - L, -2f + L * .2f, -4f - L * 1.4f, 0f) }, Color(0xFF6E4B2C), style = stroke(6f))
                drawOval(fur, Offset(-15f, -41f), androidx.compose.ui.geometry.Size(30f, 34f))
                drawOval(belly, Offset(-4f, -32f), androidx.compose.ui.geometry.Size(16f, 22f))
                drawPath(Path().apply { moveTo(6f, -14f); cubicTo(12f + L, -6f, 10f + L, -2f + L * .2f, 4f + L * 1.4f, 0f) }, dark, style = stroke(6f))
                rotate(armR, pivot = Offset(8f, -32f)) {
                    drawPath(Path().apply { moveTo(8f, -32f); cubicTo(16f, -26f, 18f, -18f, 14f, -12f) }, dark, style = stroke(5.5f))
                }
                rotate(headR, pivot = Offset(6f, -36f)) {
                    drawCircle(fur, 12f, Offset(6f, -46f))
                    drawCircle(Color(0xFFD77A6F), 3.6f, Offset(-4f, -48f))
                    drawPath(Path().apply {
                        moveTo(2f, -53f); cubicTo(6f, -58f, 14f, -56f, 16f, -50f); cubicTo(18f, -44f, 15f, -38f, 9f, -37f)
                        cubicTo(4f, -36f, 0f, -40f, 0f, -45f); close()
                    }, face)
                    drawOval(Color(0xFFC9534A), Offset(8f, -45f), androidx.compose.ui.geometry.Size(8f, 6f))
                    drawCircle(Color(0xFF1A0D08), 1.5f, Offset(7f, -48f)); drawCircle(Color(0xFF1A0D08), 1.5f, Offset(13f, -48f))
                    drawPath(Path().apply { moveTo(9f, -40f); quadraticBezierTo(11.5f, -38.5f, 14f, -40f) }, Color(0xFF6B1F18), style = Stroke(1f))
                    drawPath(Path().apply { moveTo(-4f, -56f); cubicTo(2f, -62f, 12f, -60f, 16f, -55f) }, Color(0xFFA27A52), style = Stroke(2.4f))
                }
            }
        }
    }
}
