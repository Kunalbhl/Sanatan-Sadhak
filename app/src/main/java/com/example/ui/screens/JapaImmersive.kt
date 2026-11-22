package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.audio.BeadPlayer
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sin
import kotlinx.coroutines.delay

private val Saffron = Color(0xFFF2A33A)
private val GoldLight = Color(0xFFFFE08A)

/** The kinds of mala a devotee can choose; each has its own bead look. */
data class MalaType(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val light: Color,
    val base: Color,
    val dark: Color,
    val ridged: Boolean = false,
    val gloss: Float = 0.5f
)

val malaTypes = listOf(
    MalaType("rudraksha", "Rudraksha", "रुद्राक्ष", Color(0xFFC08A55), Color(0xFF7A4B24), Color(0xFF3E2210), ridged = true, gloss = 0.28f),
    MalaType("tulsi", "Tulsi", "तुलसी", Color(0xFFB59A62), Color(0xFF6E5530), Color(0xFF33260F), gloss = 0.35f),
    MalaType("chandan", "Chandan (Sandalwood)", "चंदन", Color(0xFFF3DEB0), Color(0xFFD1A867), Color(0xFF8C6530), gloss = 0.5f),
    MalaType("sphatik", "Sphatik (Crystal)", "स्फटिक", Color(0xFFFFFFFF), Color(0xFFCFE4F2), Color(0xFF7FA6C2), gloss = 0.95f),
    MalaType("kamalgatta", "Kamal Gatta (Lotus seed)", "कमलगट्टा", Color(0xFF7C7469), Color(0xFF3F3A33), Color(0xFF16130F), gloss = 0.4f),
    MalaType("moonga", "Moonga (Coral)", "मूँगा", Color(0xFFFF8A78), Color(0xFFD1301F), Color(0xFF6E0E06), gloss = 0.7f)
)

data class JapaMantraOption(val id: String, val title: String, val verse: String)

/**
 * Full-screen Japa mala: a big 3D ring of beads seen from the front, with the bead nearest you in the
 * thumb's hold. Every tap (or small drag) draws one bead through; the whole ring slides along its thread,
 * the thumb presses, a wooden click and a haptic pulse follow. When a mala completes, the phone vibrates
 * for about a second and the deity of the chosen mantra is greeted by name.
 */
@Composable
fun JapaImmersive(
    isEng: Boolean,
    malaTypeId: String,
    onMalaType: (String) -> Unit,
    mantraOptions: List<JapaMantraOption>,
    selectedMantraId: String,
    onMantra: (String) -> Unit,
    mantraTitle: String,
    mantraVerse: String,
    customMantraText: String,
    count: Int,
    beadsPerMala: Int,
    targetMalas: Int,
    onTap: () -> Unit,
    onUndo: () -> Unit,
    onReset: () -> Unit,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) { BeadPlayer.init(context) }

    val view = LocalView.current
    DisposableEffect(Unit) {
        view.keepScreenOn = true
        onDispose { view.keepScreenOn = false }
    }

    val beads = beadsPerMala.coerceAtLeast(1)
    val inMala = count % beads
    val malasDone = count / beads
    val total = beads * targetMalas.coerceAtLeast(1)
    val mala = malaTypes.firstOrNull { it.id == malaTypeId } ?: malaTypes.first()

    // Ring position in beads. It follows the count with a little overshoot so every bead "clicks" into place.
    val pos = remember { Animatable(count.toFloat()) }
    LaunchedEffect(count) {
        pos.animateTo(count.toFloat(), spring(dampingRatio = 0.55f, stiffness = Spring.StiffnessMedium))
    }
    // The thumb presses down as it draws a bead
    val press = remember { Animatable(0f) }
    LaunchedEffect(count) {
        press.snapTo(1f)
        press.animateTo(0f, spring(dampingRatio = 0.6f, stiffness = Spring.StiffnessMediumLow))
    }

    // Completed-mala greeting
    var prevCount by remember { mutableIntStateOf(count) }
    var showGreeting by remember { mutableStateOf(false) }
    LaunchedEffect(count) {
        if (count > prevCount && count > 0 && count % beads == 0) showGreeting = true
        prevCount = count
    }

    fun draw() {
        BeadPlayer.click()
        onTap()
    }

    val infinite = rememberInfiniteTransition(label = "ambient")
    val drift by infinite.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(90_000, easing = LinearEasing), RepeatMode.Restart),
        label = "drift"
    )
    val embers = remember { List(30) { i -> floatArrayOf(((i * 37) % 100) / 100f, ((i * 53) % 100) / 100f, 0.4f + ((i * 17) % 60) / 100f, 2f + (i % 5)) } }

    var dragAcc by remember { mutableFloatStateOf(0f) }

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color(0xFF0E0612), Color(0xFF2B0F14), Color(0xFF3B1608), Color(0xFF0D0504))))
                .pointerInput(Unit) { detectTapGestures(onTap = { draw() }) }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { dragAcc = 0f },
                        onDrag = { change, delta ->
                            change.consume()
                            dragAcc += abs(delta.x) + abs(delta.y)
                            val step = 70f
                            while (dragAcc >= step) { dragAcc -= step; draw() }
                        }
                    )
                }
        ) {
            // Background: a slowly turning lotus mandala and rising embers, both quietly alive
            Canvas(modifier = Modifier.fillMaxSize()) {
                val c = Offset(size.width / 2f, size.height * 0.46f)
                val maxR = size.minDimension * 0.62f
                drawCircle(
                    Brush.radialGradient(listOf(Saffron.copy(alpha = 0.16f + 0.1f * press.value), Color.Transparent), center = c, radius = maxR * 1.3f),
                    radius = maxR * 1.3f, center = c
                )
                val spin = drift * 360f * 2f + pos.value * 1.5f
                for (ring in 0..2) {
                    val petals = 12 + ring * 6
                    val len = maxR * (0.55f + ring * 0.2f)
                    val wid = len * (0.2f - ring * 0.03f)
                    for (k in 0 until petals) {
                        rotate(degrees = spin * (if (ring % 2 == 0) 1f else -1f) * 0.5f + k * 360f / petals, pivot = c) {
                            val p = Path().apply {
                                moveTo(c.x, c.y - len * 0.25f)
                                quadraticTo(c.x + wid, c.y - len * 0.65f, c.x, c.y - len)
                                quadraticTo(c.x - wid, c.y - len * 0.65f, c.x, c.y - len * 0.25f)
                                close()
                            }
                            drawPath(p, GoldLight.copy(alpha = 0.07f - ring * 0.012f), style = Stroke(width = 1.6f))
                        }
                    }
                }
                for (e in embers) {
                    val prog = ((drift * 40f * e[2] + e[1]) % 1f)
                    val y = size.height * (1f - prog)
                    val x = size.width * e[0] + sin((prog * 6f + e[1] * 9f).toDouble()).toFloat() * 18f
                    val a = (sin(prog * PI).toFloat()).coerceIn(0f, 1f) * 0.55f
                    drawCircle(GoldLight.copy(alpha = a), radius = e[3], center = Offset(x, y))
                }
            }

            Column(
                modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding().padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f))
                            .clickable { onClose() },
                        contentAlignment = Alignment.Center
                    ) { Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White) }
                    Spacer(Modifier.weight(1f))
                    // Which mala
                    var malaMenu by remember { mutableStateOf(false) }
                    Box {
                        SelectorChip((if (isEng) "Mala: " else "माला: ") + (if (isEng) mala.nameEn.substringBefore(" (") else mala.nameHi) + "  ▾") { malaMenu = true }
                        DropdownMenu(expanded = malaMenu, onDismissRequest = { malaMenu = false }) {
                            malaTypes.forEach { m ->
                                DropdownMenuItem(
                                    text = { Text(if (isEng) m.nameEn else m.nameHi) },
                                    onClick = { onMalaType(m.id); malaMenu = false }
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(6.dp))
                // Which mantra
                var mantraMenu by remember { mutableStateOf(false) }
                Box {
                    SelectorChip("$mantraTitle  ▾") { mantraMenu = true }
                    DropdownMenu(expanded = mantraMenu, onDismissRequest = { mantraMenu = false }) {
                        mantraOptions.forEach { m ->
                            DropdownMenuItem(text = { Text(m.title) }, onClick = { onMantra(m.id); mantraMenu = false })
                        }
                    }
                }
                if (mantraVerse.isNotBlank()) {
                    Spacer(Modifier.height(6.dp))
                    Text(
                        mantraVerse,
                        color = GoldLight.copy(alpha = 0.92f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp,
                        maxLines = 3,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }

                // The mala
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawMala(mala, beads, pos.value, press.value)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.offset(y = (-22).dp)) {
                        Text(
                            text = "$inMala",
                            color = Color.White,
                            fontSize = (68f + 6f * press.value).sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isEng) "of $beads  ·  Mala ${malasDone + 1} of $targetMalas" else "$beads में  ·  माला ${malasDone + 1} / $targetMalas",
                            color = Saffron, fontSize = 13.sp, fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (isEng) "$count of $total chants" else "$count / $total जप",
                            color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp
                        )
                    }
                }

                Text(
                    text = if (isEng) "Tap anywhere, or draw your finger across, to turn a bead" else "मनका सरकाने के लिए कहीं भी छुएँ या उँगली फिराएँ",
                    color = Color.White.copy(alpha = 0.55f), fontSize = 12.sp, textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SmallAction(if (isEng) "Undo one" else "एक वापस", onUndo)
                    SmallAction(if (isEng) "Reset" else "रीसेट", onReset)
                }
            }

            if (showGreeting) {
                val (title, subtitle) = greetingFor(selectedMantraId, customMantraText, isEng)
                MalaGreeting(
                    title = title,
                    subtitle = subtitle,
                    footer = if (isEng) "Mala ${count / beads} complete" else "माला ${count / beads} पूर्ण",
                    onDismiss = { showGreeting = false }
                )
            }
        }
    }
}

/**
 * Draws the mala as a ring seen from the front and slightly above: the bead in the thumb's hold is at the
 * bottom, nearest and biggest; beads shrink and dim as they travel round the back. The first bead is the
 * larger guru (Sumeru) bead with its tassel, and it travels with the ring.
 */
private fun DrawScope.drawMala(mala: MalaType, beads: Int, pos: Float, press: Float) {
    val cx = size.width / 2f
    val cy = size.height * 0.44f
    val rx = size.width * 0.43f
    val ry = (size.height * 0.40f).coerceAtMost(size.width * 0.52f)
    val step = (2 * PI / beads).toFloat()
    // bead size so neighbours just touch at the front, where the ring is widest-spaced
    val frontSpacing = 2f * PI.toFloat() * rx / beads
    val baseR = (frontSpacing * 0.62f).coerceIn(9f, size.width * 0.052f)

    // Thumb, behind the front beads: it presses down as a bead is drawn
    val thumbTop = cy + ry - baseR * 1.6f + press * baseR * 0.9f
    rotate(degrees = -7f, pivot = Offset(cx, thumbTop + baseR * 3f)) {
        drawRoundRect(
            brush = Brush.verticalGradient(
                listOf(Color(0xFFD59A72).copy(alpha = 0.92f), Color(0xFF9C6341).copy(alpha = 0.92f), Color(0xFF5E3822).copy(alpha = 0.0f)),
                startY = thumbTop, endY = thumbTop + baseR * 9f
            ),
            topLeft = Offset(cx - baseR * 2.4f, thumbTop),
            size = Size(baseR * 4.8f, baseR * 9f),
            cornerRadius = CornerRadius(baseR * 2.4f, baseR * 2.4f)
        )
    }

    // thread
    drawOval(
        color = Color(0xFF2A1608),
        topLeft = Offset(cx - rx, cy - ry),
        size = Size(rx * 2f, ry * 2f),
        style = Stroke(width = 3f)
    )

    // Beads, back to front so near ones overlap far ones
    data class B(val i: Int, val x: Float, val y: Float, val depth: Float)
    val list = ArrayList<B>(beads)
    for (i in 0 until beads) {
        val phi = (i - pos) * step + (PI / 2).toFloat()
        val s = sin(phi)
        list.add(B(i, cx + rx * cos(phi), cy + ry * s, (s + 1f) / 2f))
    }
    list.sortBy { it.depth }
    for (b in list) {
        val scale = 0.46f + 1.15f * b.depth.pow(1.7f)
        val guru = b.i == 0
        val r = baseR * scale * (if (guru) 1.5f else 1f)
        val c = Offset(b.x, b.y)

        // soft contact shadow
        drawCircle(Color.Black.copy(alpha = 0.18f + 0.3f * b.depth), radius = r * 1.04f, center = c + Offset(r * 0.14f, r * 0.24f))
        // body, lit from the upper left
        val shade = 0.55f + 0.45f * b.depth
        drawCircle(
            brush = Brush.radialGradient(
                listOf(mala.light.dim(shade), mala.base.dim(shade), mala.dark.dim(shade)),
                center = c - Offset(r * 0.32f, r * 0.34f), radius = r * 1.55f
            ),
            radius = r, center = c
        )
        if (mala.ridged && r > 10f) {
            // the grooved faces of a rudraksha
            for (k in -1..1) {
                drawArc(
                    color = mala.dark.copy(alpha = 0.55f),
                    startAngle = -78f, sweepAngle = 156f, useCenter = false,
                    topLeft = Offset(c.x - r * 0.34f + k * r * 0.5f, c.y - r * 0.92f),
                    size = Size(r * 0.68f, r * 1.84f),
                    style = Stroke(width = max(1f, r * 0.07f))
                )
            }
        }
        // highlight
        drawCircle(Color.White.copy(alpha = 0.6f * mala.gloss * shade), radius = r * 0.2f, center = c - Offset(r * 0.4f, r * 0.45f))

        if (guru) {
            // tassel hangs from the guru bead
            val ty = c.y + r * 0.9f
            for (k in -2..2) {
                drawLine(
                    color = if (k % 2 == 0) Color(0xFFC62828) else Saffron,
                    start = Offset(c.x + k * r * 0.12f, ty),
                    end = Offset(c.x + k * r * 0.34f, ty + r * 2.3f),
                    strokeWidth = max(2f, r * 0.1f), cap = StrokeCap.Round
                )
            }
            drawCircle(Saffron, radius = r * 0.3f, center = Offset(c.x, ty))
        }
    }

    // glow around the bead in the thumb's hold
    drawCircle(
        Saffron.copy(alpha = 0.22f + 0.25f * press), radius = baseR * 2.15f,
        center = Offset(cx, cy + ry), style = Stroke(width = 3f)
    )
}

private fun Color.dim(f: Float) = Color(red * f, green * f, blue * f, alpha)

@Composable
private fun SelectorChip(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.09f))
            .border(1.dp, Saffron.copy(alpha = 0.45f), RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) { Text(label, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, maxLines = 1) }
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

/** Golden bloom with falling petals and the deity's greeting, shown when a mala is complete. */
@Composable
private fun MalaGreeting(title: String, subtitle: String, footer: String, onDismiss: () -> Unit) {
    val fade = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        fade.animateTo(1f, tween(700))
        delay(4200)
        fade.animateTo(0f, tween(500))
        onDismiss()
    }
    val infinite = rememberInfiniteTransition(label = "petals")
    val fall by infinite.animateFloat(0f, 1f, infiniteRepeatable(tween(5000, easing = LinearEasing)), label = "fall")
    val petals = remember { List(26) { i -> floatArrayOf(((i * 41) % 100) / 100f, ((i * 29) % 100) / 100f, 0.6f + ((i * 13) % 70) / 100f) } }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.62f * fade.value))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val c = Offset(size.width / 2f, size.height / 2f)
            for (k in 0 until 3) {
                val p = ((fall + k / 3f) % 1f)
                drawCircle(Saffron.copy(alpha = (1f - p) * 0.35f * fade.value), radius = size.minDimension * (0.2f + p * 0.5f), center = c, style = Stroke(width = 4f))
            }
            for (pt in petals) {
                val p = (fall * pt[2] + pt[1]) % 1f
                val x = size.width * pt[0] + sin((p * 8f + pt[1] * 6f).toDouble()).toFloat() * 26f
                val y = size.height * p
                rotate(degrees = p * 360f * pt[2], pivot = Offset(x, y)) {
                    drawOval(
                        Color(0xFFFFB347).copy(alpha = 0.8f * fade.value),
                        topLeft = Offset(x - 6f, y - 11f), size = Size(12f, 22f)
                    )
                }
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(28.dp)) {
            Text("🙏", fontSize = 44.sp, modifier = Modifier.padding(bottom = 8.dp))
            Text(title, color = GoldLight.copy(alpha = fade.value), fontSize = 32.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, lineHeight = 40.sp)
            if (subtitle.isNotBlank()) {
                Spacer(Modifier.height(8.dp))
                Text(subtitle, color = Color.White.copy(alpha = 0.85f * fade.value), fontSize = 16.sp, textAlign = TextAlign.Center)
            }
            Spacer(Modifier.height(18.dp))
            Text(footer, color = Saffron.copy(alpha = fade.value), fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

/** The greeting for the deity of the mantra being chanted (a custom mantra is matched by the names in its text). */
internal fun greetingFor(mantraId: String, customText: String, isEng: Boolean): Pair<String, String> {
    fun g(hi: String, en: String, subHi: String, subEn: String) = if (isEng) en to subEn else hi to subHi
    when (mantraId) {
        "gayatri" -> return g("जय माँ गायत्री", "Jai Maa Gayatri", "आपकी गायत्री माला पूर्ण हुई", "Your Gayatri mala is complete")
        "mrityunjaya" -> return g("हर हर महादेव", "Har Har Mahadev", "महामृत्युंजय माला पूर्ण हुई", "Your Maha Mrityunjaya mala is complete")
        "harekrishna" -> return g("हरे कृष्ण! जय श्री कृष्ण", "Hare Krishna! Jai Shri Krishna", "महामंत्र की माला पूर्ण हुई", "Your Maha Mantra mala is complete")
        "omnamahshivaya" -> return g("ॐ नमः शिवाय · हर हर महादेव", "Om Namah Shivaya · Har Har Mahadev", "आपकी शिव माला पूर्ण हुई", "Your Shiva mala is complete")
        "omnamobhagavate" -> return g("जय श्री हरि · जय वासुदेव", "Jai Shri Hari · Jai Vasudev", "आपकी वासुदेव माला पूर्ण हुई", "Your Vasudev mala is complete")
    }
    val t = customText.lowercase()
    fun has(vararg k: String) = k.any { t.contains(it) }
    return when {
        has("hanuman", "बजरंग", "हनुमान") -> g("जय बजरंगबली", "Jai Bajrangbali", "आपकी हनुमान माला पूर्ण हुई", "Your Hanuman mala is complete")
        has("shiv", "mahadev", "शिव", "महादेव", "rudra", "रुद्र") -> g("हर हर महादेव", "Har Har Mahadev", "आपकी माला पूर्ण हुई", "Your mala is complete")
        has("radha", "राधा") -> g("जय श्री राधे", "Jai Shri Radhe", "आपकी माला पूर्ण हुई", "Your mala is complete")
        has("krishna", "govind", "gopal", "कृष्ण", "गोविंद", "गोपाल") -> g("जय श्री कृष्ण", "Jai Shri Krishna", "आपकी माला पूर्ण हुई", "Your mala is complete")
        has("sita", "सीता", "ram", "राम") -> g("जय श्री राम", "Jai Shri Ram", "आपकी माला पूर्ण हुई", "Your mala is complete")
        has("ganesh", "ganpati", "गणेश", "गणपति") -> g("गणपति बप्पा मोरया", "Ganpati Bappa Morya", "आपकी माला पूर्ण हुई", "Your mala is complete")
        has("durga", "devi", "mata", "ambe", "दुर्गा", "देवी", "माता", "अम्बे") -> g("जय माता दी", "Jai Mata Di", "आपकी माला पूर्ण हुई", "Your mala is complete")
        has("kali", "काली") -> g("जय माँ काली", "Jai Maa Kali", "आपकी माला पूर्ण हुई", "Your mala is complete")
        has("lakshmi", "लक्ष्मी") -> g("जय माँ लक्ष्मी", "Jai Maa Lakshmi", "आपकी माला पूर्ण हुई", "Your mala is complete")
        has("saraswati", "सरस्वती") -> g("जय माँ सरस्वती", "Jai Maa Saraswati", "आपकी माला पूर्ण हुई", "Your mala is complete")
        has("surya", "सूर्य") -> g("जय सूर्य देव", "Jai Surya Dev", "आपकी माला पूर्ण हुई", "Your mala is complete")
        has("vishnu", "narayan", "hari", "विष्णु", "नारायण", "हरि") -> g("जय श्री हरि", "Jai Shri Hari", "आपकी माला पूर्ण हुई", "Your mala is complete")
        has("sai", "साई") -> g("ॐ साईं राम", "Om Sai Ram", "आपकी माला पूर्ण हुई", "Your mala is complete")
        else -> g("प्रणाम, साधक", "Pranam, Sadhak", "आपकी माला पूर्ण हुई", "Your mala is complete")
    }
}
