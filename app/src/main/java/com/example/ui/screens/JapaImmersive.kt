package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private val Saffron = Color(0xFFF2A33A)
private val GoldLight = Color(0xFFFFE08A)

/**
 * Full-screen Japa mala. A ring of beads (108 by default) that turns one bead every time the user taps
 * anywhere on the screen; beads already counted glow gold, the Sumeru bead sits at the top, and each tap
 * gives a haptic pulse. Nothing else is on screen except the mantra, the count and the progress.
 */
@Composable
fun JapaImmersive(
    isEng: Boolean,
    mantraTitle: String,
    mantraVerse: String,
    count: Int,
    beadsPerMala: Int,
    targetMalas: Int,
    onTap: () -> Unit,
    onUndo: () -> Unit,
    onReset: () -> Unit,
    onClose: () -> Unit
) {
    val view = LocalView.current
    DisposableEffect(Unit) {
        view.keepScreenOn = true
        onDispose { view.keepScreenOn = false }
    }

    val beads = beadsPerMala.coerceAtLeast(1)
    val inMala = count % beads
    val malasDone = count / beads
    val total = beads * targetMalas.coerceAtLeast(1)

    // The mala rotates so the current bead is always at the top, easing into place on every tap
    val rotation = remember { Animatable(0f) }
    LaunchedEffect(count) {
        val target = -(inMala * 360f / beads)
        val easing = spring<Float>(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMediumLow)
        if (inMala == 0 && count > 0) {
            // finished a mala: turn the last bead through and start the next round from the top
            rotation.animateTo(-360f, easing)
            rotation.snapTo(0f)
        } else {
            if (rotation.value - target > 180f) rotation.snapTo(rotation.value - 360f) // undo across a mala boundary
            rotation.animateTo(target, easing)
        }
    }
    // Soft pulse when a bead is counted
    val pulse = remember { Animatable(0f) }
    LaunchedEffect(count) {
        pulse.snapTo(1f)
        pulse.animateTo(0f, spring(stiffness = Spring.StiffnessLow))
    }

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color(0xFF1A0A06), Color(0xFF34120A), Color(0xFF100503))))
                .pointerInput(Unit) { detectTapGestures(onTap = { onTap() }) }
        ) {
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
                            .clickable { onClose() },
                        contentAlignment = Alignment.Center
                    ) { Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White) }
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = if (isEng) "Mala ${malasDone + 1} of $targetMalas" else "माला ${malasDone + 1} / $targetMalas",
                        color = Saffron, fontWeight = FontWeight.Bold, fontSize = 14.sp
                    )
                }

                Spacer(Modifier.height(8.dp))
                Text(mantraTitle, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                if (mantraVerse.isNotBlank()) {
                    Spacer(Modifier.height(6.dp))
                    Text(
                        mantraVerse,
                        color = GoldLight.copy(alpha = 0.9f),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }

                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Canvas(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
                        val c = Offset(size.width / 2f, size.height / 2f)
                        val radius = size.minDimension * 0.42f
                        val step = (2 * PI / beads).toFloat()
                        val beadR = (radius * step * 0.36f).coerceIn(3f, 20f)
                        val rot = Math.toRadians(rotation.value.toDouble()).toFloat()

                        // thread
                        drawCircle(Color(0xFF8A5A1E).copy(alpha = 0.6f), radius = radius, center = c, style = Stroke(width = 2f))

                        for (i in 0 until beads) {
                            // bead i sits at angle i*step, rotated so bead (inMala) is at the top (-90°)
                            val a = i * step + rot - (PI / 2).toFloat()
                            val p = Offset(c.x + radius * cos(a), c.y + radius * sin(a))
                            val counted = i < inMala
                            val isCurrent = i == inMala
                            val r = if (isCurrent) beadR * (1.5f + 0.35f * pulse.value) else beadR
                            val base = if (counted) Saffron else Color(0xFF6B3B1B)
                            val hi = if (counted) GoldLight else Color(0xFFB87A45)
                            drawCircle(
                                brush = Brush.radialGradient(listOf(hi, base, base.copy(alpha = 0.85f)), center = p - Offset(r * 0.3f, r * 0.3f), radius = r * 1.6f),
                                radius = r,
                                center = p
                            )
                            if (isCurrent) {
                                drawCircle(Saffron.copy(alpha = 0.35f + 0.3f * pulse.value), radius = r * 1.9f, center = p, style = Stroke(width = 3f))
                            }
                        }

                        // Sumeru (guru) bead + tassel at the top
                        val top = Offset(c.x, c.y - radius - beadR * 3.2f)
                        drawLine(Saffron.copy(alpha = 0.8f), Offset(top.x, top.y + beadR * 1.6f), Offset(top.x, top.y + beadR * 3.1f), strokeWidth = 4f)
                        drawCircle(
                            brush = Brush.radialGradient(listOf(GoldLight, Saffron), center = top, radius = beadR * 2.2f),
                            radius = beadR * 1.9f, center = top
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$inMala",
                            color = Color.White,
                            fontSize = (74f + 8f * pulse.value).sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isEng) "of $beads" else "/ $beads",
                            color = Color.White.copy(alpha = 0.6f), fontSize = 14.sp
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = if (isEng) "$count of $total chants" else "$count / $total जप",
                            color = Saffron, fontSize = 13.sp, fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Text(
                    text = if (isEng) "Tap anywhere to count a bead" else "एक मनका गिनने के लिए कहीं भी छुएँ",
                    color = Color.White.copy(alpha = 0.55f), fontSize = 12.sp
                )
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SmallAction(if (isEng) "Undo one" else "एक वापस", onUndo)
                    SmallAction(if (isEng) "Reset" else "रीसेट", onReset)
                }
            }
        }
    }
}

@Composable
private fun SmallAction(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .border(1.dp, Saffron.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 22.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) { Text(label, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold) }
}
