package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.audio.BellPlayer
import com.example.utils.HapticFeedbackManager
import kotlin.math.abs
import kotlin.math.sin

private val Gold = Color(0xFFE8B84A)
private val GoldDeep = Color(0xFF9C6B12)

/**
 * Full-screen temple bell. The bell is a real pendulum: every tap (or auto ring) gives it an impulse, it
 * swings and settles by itself, the clapper lags behind it, and each strike plays the overlapping bronze
 * bell sound with haptics. Auto Bell rings it for you at a steady rhythm until you stop it or a timer ends.
 */
@Composable
fun AartiBellImmersive(isEng: Boolean, onRung: () -> Unit, onClose: () -> Unit) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    LaunchedEffect(Unit) { BellPlayer.init(context) }

    var angle by remember { mutableFloatStateOf(0f) }
    var vel by remember { mutableFloatStateOf(0f) }
    var clapper by remember { mutableFloatStateOf(0f) }
    var clapperVel by remember { mutableFloatStateOf(0f) }
    var dir by remember { mutableFloatStateOf(1f) }
    val waves = remember { mutableStateListOf<Float>() } // ages in seconds of the expanding sound rings
    var sessionRings by remember { mutableIntStateOf(0) }
    var lastStrike by remember { mutableLongStateOf(0L) }

    var autoOn by remember { mutableStateOf(false) }
    var rhythm by remember { mutableIntStateOf(1) } // 0 slow, 1 steady, 2 lively
    var timerMin by remember { mutableIntStateOf(0) } // 0 = until I stop
    var endAtMs by remember { mutableLongStateOf(0L) }
    var remainingSec by remember { mutableIntStateOf(0) }

    fun strike(strength: Float) {
        val now = android.os.SystemClock.elapsedRealtime()
        if (now - lastStrike < 70L) return
        lastStrike = now
        vel += dir * (70f + 60f * strength)
        dir = -dir
        waves.add(0f)
        if (waves.size > 6) waves.removeAt(0)
        sessionRings += 1
        onRung()
        BellPlayer.ring(strength)
        HapticFeedbackManager.vibrate(context, HapticFeedbackManager.FeedbackType.BELL_RING)
        HapticFeedbackManager.performHaptic(haptic, HapticFeedbackManager.FeedbackType.BELL_RING)
    }

    // Physics + animation frame loop
    LaunchedEffect(Unit) {
        var last = 0L
        while (true) {
            withFrameNanos { t ->
                val dt = if (last == 0L) 0.016f else ((t - last) / 1_000_000_000f).coerceIn(0.001f, 0.04f)
                last = t
                // bell pendulum (degrees)
                val acc = -16f * angle - 0.85f * vel
                vel += acc * dt
                angle = (angle + vel * dt).coerceIn(-38f, 38f)
                // clapper trails the bell, so it strikes the rim as the bell turns
                val target = -angle * 1.15f
                val cacc = -70f * (clapper - target) - 3.2f * clapperVel
                clapperVel += cacc * dt
                clapper = (clapper + clapperVel * dt).coerceIn(-45f, 45f)
                for (i in waves.indices) waves[i] = waves[i] + dt
                waves.removeAll { it > 1.6f }
            }
        }
    }

    // Auto bell: rings itself like a hand tapping, slightly uneven, until stopped or the timer ends
    LaunchedEffect(autoOn, rhythm, timerMin) {
        if (!autoOn) return@LaunchedEffect
        val base = when (rhythm) { 0 -> 5200L; 1 -> 2600L; else -> 1200L }
        val rnd = java.util.Random()
        while (autoOn) {
            strike(0.65f + rnd.nextFloat() * 0.35f)
            val jitter = (base * (0.85 + rnd.nextDouble() * 0.3)).toLong()
            var waited = 0L
            while (waited < jitter && autoOn) {
                kotlinx.coroutines.delay(100)
                waited += 100
                if (endAtMs > 0L) {
                    val left = ((endAtMs - System.currentTimeMillis()) / 1000L).toInt()
                    remainingSec = left.coerceAtLeast(0)
                    if (left <= 0) autoOn = false
                }
            }
        }
    }

    // Keep the screen on while the bell is open
    val view = LocalView.current
    DisposableEffect(Unit) {
        view.keepScreenOn = true
        onDispose { view.keepScreenOn = false }
    }

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF1B0709), Color(0xFF3A0E12), Color(0xFF120405))
                    )
                )
        ) {
            // Warm lamp glow behind the bell
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(Gold.copy(alpha = 0.22f), Color.Transparent),
                        center = Offset(size.width / 2f, size.height * 0.36f),
                        radius = size.width * 0.8f
                    ),
                    radius = size.width * 0.8f,
                    center = Offset(size.width / 2f, size.height * 0.36f)
                )
            }

            Column(
                modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f))
                            .clickable { autoOn = false; onClose() },
                        contentAlignment = Alignment.Center
                    ) { Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White) }
                    Spacer(Modifier.weight(1f))
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            if (isEng) "Rings this visit" else "इस बार के नाद",
                            color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp
                        )
                        Text("$sessionRings", color = Gold, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(Modifier.height(8.dp))

                // The bell: tap to ring, drag sideways to swing it
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .pointerInput(Unit) { detectTapGestures(onTap = { strike(1f) }) }
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDrag = { _, d -> vel += d.x * 0.5f },
                                onDragEnd = { if (abs(vel) > 120f) strike((abs(vel) / 260f).coerceIn(0.5f, 1f)) }
                            )
                        },
                    contentAlignment = Alignment.TopCenter
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val w = size.width
                        val h = size.height
                        val cx = w / 2f
                        val pivotY = h * 0.06f
                        val bellH = (h * 0.78f).coerceAtMost(w * 1.15f)
                        val bw = bellH * 0.78f

                        // Sound waves
                        for (age in waves) {
                            val p = (age / 1.6f).coerceIn(0f, 1f)
                            drawCircle(
                                color = Gold.copy(alpha = (1f - p) * 0.45f),
                                radius = bw * (0.45f + p * 0.9f),
                                center = Offset(cx, pivotY + bellH * 0.62f),
                                style = Stroke(width = 3f + (1f - p) * 4f)
                            )
                        }

                        // Beam
                        drawRoundRect(
                            brush = Brush.verticalGradient(listOf(Color(0xFF6B3F1D), Color(0xFF3B200C))),
                            topLeft = Offset(cx - bw * 0.7f, pivotY - 14f),
                            size = androidx.compose.ui.geometry.Size(bw * 1.4f, 22f),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(10f, 10f)
                        )

                        rotate(degrees = angle, pivot = Offset(cx, pivotY)) {
                            // Hanging chain
                            val chainEnd = pivotY + bellH * 0.12f
                            drawLine(GoldDeep, Offset(cx, pivotY), Offset(cx, chainEnd), strokeWidth = 7f, cap = StrokeCap.Round)
                            for (i in 0..3) {
                                drawCircle(Gold, radius = 7f, center = Offset(cx, pivotY + (chainEnd - pivotY) * (i + 0.5f) / 4f), style = Stroke(width = 3f))
                            }

                            val top = chainEnd
                            val bodyTop = top + bellH * 0.04f
                            val rimY = top + bellH * 0.66f
                            val half = bw / 2f
                            val body = Path().apply {
                                moveTo(cx - half * 0.28f, bodyTop)
                                cubicTo(cx - half * 0.34f, bodyTop - 6f, cx + half * 0.34f, bodyTop - 6f, cx + half * 0.28f, bodyTop)
                                cubicTo(cx + half * 0.50f, top + bellH * 0.28f, cx + half * 0.62f, top + bellH * 0.50f, cx + half * 0.86f, rimY - 14f)
                                quadraticTo(cx + half * 1.02f, rimY - 2f, cx + half * 0.96f, rimY + 12f)
                                lineTo(cx - half * 0.96f, rimY + 12f)
                                quadraticTo(cx - half * 1.02f, rimY - 2f, cx - half * 0.86f, rimY - 14f)
                                cubicTo(cx - half * 0.62f, top + bellH * 0.50f, cx - half * 0.50f, top + bellH * 0.28f, cx - half * 0.28f, bodyTop)
                                close()
                            }
                            drawPath(
                                body,
                                brush = Brush.horizontalGradient(
                                    listOf(Color(0xFF7A4F0A), Color(0xFFD9A93A), Color(0xFFFFE08A), Color(0xFFD9A93A), Color(0xFF6B430A)),
                                    startX = cx - half, endX = cx + half
                                )
                            )
                            // engraved bands
                            for (f in listOf(0.34f, 0.52f)) {
                                val y = top + bellH * f
                                val spread = half * (0.34f + (f - 0.2f) * 1.0f)
                                drawLine(GoldDeep.copy(alpha = 0.55f), Offset(cx - spread, y), Offset(cx + spread, y), strokeWidth = 4f, cap = StrokeCap.Round)
                            }
                            // rim highlight
                            drawLine(Color(0xFFFFF2B8).copy(alpha = 0.7f), Offset(cx - half * 0.92f, rimY + 5f), Offset(cx + half * 0.92f, rimY + 5f), strokeWidth = 5f, cap = StrokeCap.Round)

                            // Clapper, swinging inside and below the rim
                            rotate(degrees = clapper, pivot = Offset(cx, bodyTop + bellH * 0.1f)) {
                                val ctop = bodyTop + bellH * 0.1f
                                val cend = rimY + bellH * 0.07f
                                drawLine(GoldDeep, Offset(cx, ctop), Offset(cx, cend), strokeWidth = 5f, cap = StrokeCap.Round)
                                drawCircle(Brush.radialGradient(listOf(Color(0xFFFFE08A), GoldDeep), center = Offset(cx - 4f, cend - 4f), radius = 24f), radius = 17f, center = Offset(cx, cend))
                            }
                        }
                    }
                }

                Text(
                    text = if (isEng) "Tap the bell to ring it  ·  swipe sideways to swing it" else "घंटी बजाने के लिए छुएँ  ·  झुलाने के लिए सरकाएँ",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))

                // Auto bell controls
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color.White.copy(alpha = 0.07f))
                        .border(1.dp, Gold.copy(alpha = 0.25f), RoundedCornerShape(18.dp))
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        if (isEng) "Auto Bell" else "स्वचालित घंटी",
                        color = Gold, fontWeight = FontWeight.Bold, fontSize = 14.sp
                    )
                    ChipRow(
                        label = if (isEng) "Rhythm" else "गति",
                        options = listOf(if (isEng) "Slow" else "धीमी", if (isEng) "Steady" else "सामान्य", if (isEng) "Lively" else "तेज़"),
                        selected = rhythm,
                        onSelect = { rhythm = it }
                    )
                    val timers = listOf(0, 5, 10, 15, 30)
                    ChipRow(
                        label = if (isEng) "Stop after" else "बंद होगी",
                        options = timers.map { if (it == 0) (if (isEng) "I'll stop" else "मैं रोकूँगा") else "$it min" },
                        selected = timers.indexOf(timerMin).coerceAtLeast(0),
                        onSelect = { timerMin = timers[it] }
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (autoOn) Color(0xFF8E1B1B) else Gold)
                            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                                if (autoOn) {
                                    autoOn = false
                                } else {
                                    endAtMs = if (timerMin > 0) System.currentTimeMillis() + timerMin * 60_000L else 0L
                                    remainingSec = timerMin * 60
                                    autoOn = true
                                }
                            }
                            .padding(vertical = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val running = if (remainingSec > 0 && endAtMs > 0L)
                            (if (isEng) "Ringing · ${remainingSec / 60}:${"%02d".format(remainingSec % 60)} left · tap to stop" else "बज रही है · ${remainingSec / 60}:${"%02d".format(remainingSec % 60)} शेष · रोकने के लिए छुएँ")
                        else (if (isEng) "Ringing · tap to stop" else "बज रही है · रोकने के लिए छुएँ")
                        Text(
                            text = if (autoOn) running else (if (isEng) "Start Auto Bell" else "स्वचालित घंटी शुरू करें"),
                            color = if (autoOn) Color.White else Color(0xFF3A0E12),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChipRow(label: String, options: List<String>, selected: Int, onSelect: (Int) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
            options.forEachIndexed { i, text ->
                val on = i == selected
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (on) Gold.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.06f))
                        .border(1.dp, if (on) Gold else Color.Transparent, RoundedCornerShape(10.dp))
                        .clickable { onSelect(i) }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text, color = if (on) Gold else Color.White.copy(alpha = 0.8f), fontSize = 11.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
                }
            }
        }
    }
}
