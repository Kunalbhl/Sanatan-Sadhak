package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SadhakThemeColors
import com.example.ui.theme.SadhakThemeDimensions
import java.util.Calendar
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Spiritual Time of Day for Dynamic Divine Environment
 */
enum class DivineTimeAtmosphere {
    MORNING,  // 05:00 - 11:59 (Sunrise, golden glow, awakening)
    DAY,      // 12:00 - 16:59 (Bright spiritual daytime, radiant sun)
    EVENING,  // 17:00 - 19:59 (Sunset, deep orange/golden twilight)
    NIGHT     // 20:00 - 04:59 (Calm spiritual night, moonlight, stars)
}

object DivineEnvironmentHelper {
    fun getCurrentAtmosphere(calendar: Calendar = Calendar.getInstance()): DivineTimeAtmosphere {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..11 -> DivineTimeAtmosphere.MORNING
            in 12..16 -> DivineTimeAtmosphere.DAY
            in 17..19 -> DivineTimeAtmosphere.EVENING
            else -> DivineTimeAtmosphere.NIGHT
        }
    }
}

/**
 * Dynamic Divine Environment Banner
 * Features:
 * - Time-based atmosphere (Morning, Day, Evening, Night) derived from device timezone
 * - Gently animated waving sacred flag (Dhwaja) with stationary pole
 * - Temple silhouettes with subtle ambient parallax movement
 * - Flying birds with natural flapping motion and smooth movement
 * - Devotee greeting: "Welcome Devotee" (no comma) with smooth typing/reveal entrance animation
 * - Vedic deity/day indicator
 */
@Composable
fun DivineEnvironmentBanner(
    isEng: Boolean,
    userName: String?,
    currentDate: String,
    hasNotifications: Boolean,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit = {},
    showProfileControl: Boolean = false,
    modifier: Modifier = Modifier,
    calendar: Calendar = Calendar.getInstance()
) {
    val atmosphere = remember(calendar) { DivineEnvironmentHelper.getCurrentAtmosphere(calendar) }

    // Flag Wind Animation (Cloth waves softly while pole remains fixed)
    val infiniteTransition = rememberInfiniteTransition(label = "divine_environment_anim")
    val flagWavePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "flag_wave"
    )

    // Temple Silhouettes Subtle Ambient Parallax
    val templeParallaxOffset by infiniteTransition.animateFloat(
        initialValue = -1.5f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(4500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "temple_parallax"
    )

    // Flying Birds Continuous Flight Animation
    val birdsProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(14000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "birds_progress"
    )

    val birdFlapPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "bird_flap"
    )

    // Greeting Reveal / Entrance Animation
    var hasAnimatedGreeting by rememberSaveable { mutableStateOf(false) }
    val greetingAlpha = remember { Animatable(if (hasAnimatedGreeting) 1f else 0f) }
    val greetingSlideY = remember { Animatable(if (hasAnimatedGreeting) 0f else 12f) }

    LaunchedEffect(Unit) {
        if (!hasAnimatedGreeting) {
            greetingAlpha.animateTo(1f, animationSpec = tween(700, easing = FastOutSlowInEasing))
            greetingSlideY.animateTo(0f, animationSpec = tween(700, easing = FastOutSlowInEasing))
            hasAnimatedGreeting = true
        }
    }

    val greetingTargetText = remember(isEng, userName) {
        if (!userName.isNullOrBlank() && userName != "Guest Devotee" && userName != "Guest Sadhak") {
            if (isEng) "Welcome $userName" else "स्वागतम् $userName"
        } else {
            if (isEng) "Welcome Devotee" else "स्वागतम् साधक"
        }
    }

    val dynamicDeityGreetingInfo = remember(calendar) {
        com.example.utils.DivineNameSystem.getGreetingForTime(calendar)
    }
    val dynamicDeityGreeting = if (isEng) {
        "${dynamicDeityGreetingInfo.deityNameEn} • ${dynamicDeityGreetingInfo.sacredMantraEn}"
    } else {
        "${dynamicDeityGreetingInfo.deityNameHi} • ${dynamicDeityGreetingInfo.sacredMantraHi}"
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SadhakThemeDimensions.screenPaddingMedium)
            .padding(top = 8.dp, bottom = 4.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = getAtmosphereBackgroundBrush(atmosphere)
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFD4A23A).copy(alpha = 0.55f),
                        Color(0xFFE58A16).copy(alpha = 0.35f),
                        Color(0xFF7A1720).copy(alpha = 0.25f)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        // Temple photo scene (same artwork as the website): sun/moon by local time,
        // waving dhwaja and flying birds; a soft scrim keeps the greeting readable.
        TemplePhotoScene(Modifier.matchParentSize())
        Box(
            Modifier
                .matchParentSize()
                .background(Brush.horizontalGradient(listOf(Color(0x8C320F05), Color(0x26320F05), Color.Transparent)))
        )
        Spacer(Modifier.fillMaxWidth().height(180.dp))

        // Foreground Devotee Greeting Content
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .graphicsLayer {
                        alpha = greetingAlpha.value
                        translationY = greetingSlideY.value
                    }
            ) {
                // 1. Primary Dominant Brand Header
                Text(
                    text = "SANATAN SADHAK",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = getAtmosphereTitleColor(atmosphere),
                        fontSize = 20.sp,
                        letterSpacing = 0.8.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.testTag("sanatan_sadhak_brand_title")
                )

                Spacer(modifier = Modifier.height(2.dp))

                // 2. Small Deity Dedication / Tagline
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(5.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE58A16))
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = dynamicDeityGreeting.uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = getAtmosphereAccentColor(atmosphere),
                            fontSize = 11.sp,
                            letterSpacing = 0.4.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                // 3. Secondary Devotee Greeting (Strictly "Welcome Devotee" / "Welcome [Name]")
                Text(
                    text = greetingTargetText,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = getAtmosphereTitleColor(atmosphere).copy(alpha = 0.95f),
                        fontSize = 15.sp,
                        letterSpacing = 0.1.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.testTag("devotee_greeting_text")
                )

                Spacer(modifier = Modifier.height(2.dp))

                // 4. Current Panchang Day & Date
                Text(
                    text = currentDate,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = getAtmosphereSubtitleColor(atmosphere),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Action Buttons: Notifications (Profile button omitted by default on Home per Canonical Spec)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = onNotificationClick,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFF8EE).copy(alpha = 0.85f))
                        .border(1.dp, Color(0xFFD4A23A).copy(alpha = 0.4f), CircleShape)
                        .testTag("home_notifications_button")
                ) {
                    BadgedBox(
                        badge = {
                            if (hasNotifications) {
                                Badge(
                                    containerColor = Color(0xFF7A1720),
                                    contentColor = Color.White
                                )
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = Color(0xFF7A1720),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                if (showProfileControl) {
                    IconButton(
                        onClick = onProfileClick,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFF8EE).copy(alpha = 0.85f))
                            .border(1.dp, Color(0xFFD4A23A).copy(alpha = 0.4f), CircleShape)
                            .testTag("home_profile_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = Color(0xFF7A1720),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Atmosphere Background Gradients
 */
private fun getAtmosphereBackgroundBrush(atmosphere: DivineTimeAtmosphere): Brush {
    return when (atmosphere) {
        DivineTimeAtmosphere.MORNING -> Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFFFF8EE),
                Color(0xFFFDECD2),
                Color(0xFFFBE4C4)
            )
        )
        DivineTimeAtmosphere.DAY -> Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFFFFDF8),
                Color(0xFFFFF5E4),
                Color(0xFFFCECD5)
            )
        )
        DivineTimeAtmosphere.EVENING -> Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFFFF3E0),
                Color(0xFFFBE0C2),
                Color(0xFFF8CCA4)
            )
        )
        DivineTimeAtmosphere.NIGHT -> Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF201319),
                Color(0xFF2B1622),
                Color(0xFF1B0F1A)
            )
        )
    }
}

// Text sits on the temple photo, so it stays light at every time of day
@Suppress("UNUSED_PARAMETER")
private fun getAtmosphereTitleColor(atmosphere: DivineTimeAtmosphere): Color = Color(0xFFFFF8EE)

// Text sits on the temple photo, so it stays light at every time of day
@Suppress("UNUSED_PARAMETER")
private fun getAtmosphereSubtitleColor(atmosphere: DivineTimeAtmosphere): Color = Color(0xFFFDE7C2)

// Text sits on the temple photo, so it stays light at every time of day
@Suppress("UNUSED_PARAMETER")
private fun getAtmosphereAccentColor(atmosphere: DivineTimeAtmosphere): Color = Color(0xFFFFC56B)

// Reusable Path instances to prevent heap allocations during Canvas frame callbacks
private object ReusableCanvasPath {
    val path1 = Path()
    val path2 = Path()
    val path3 = Path()
    val path4 = Path()
    val path5 = Path()
}

/**
 * Canvas Rendering: Temple Silhouettes, Animated Flag, Ambient Parallax, Celestial Radiance, Flying Birds
 */
private fun DrawScope.drawDivineAtmosphere(
    atmosphere: DivineTimeAtmosphere,
    flagWavePhase: Float,
    templeParallax: Float,
    birdsProgress: Float,
    birdFlapPhase: Float
) {
    val w = size.width
    val h = size.height

    // 1. Celestial Radiance (Sun / Moon)
    val isNight = atmosphere == DivineTimeAtmosphere.NIGHT
    val orbX = w * 0.82f
    val orbY = h * 0.28f
    val orbRadius = 24.dp.toPx()

    if (!isNight) {
        // Solar Divine Radiance
        val sunColor = when (atmosphere) {
            DivineTimeAtmosphere.MORNING -> Color(0xFFFFD54F)
            DivineTimeAtmosphere.DAY -> Color(0xFFFFE082)
            DivineTimeAtmosphere.EVENING -> Color(0xFFFF8A65)
            else -> Color(0xFFFFD54F)
        }
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(sunColor.copy(alpha = 0.45f), sunColor.copy(alpha = 0.12f), Color.Transparent),
                center = Offset(orbX, orbY),
                radius = orbRadius * 2.2f
            ),
            center = Offset(orbX, orbY),
            radius = orbRadius * 2.2f
        )
        drawCircle(
            color = sunColor.copy(alpha = 0.65f),
            center = Offset(orbX, orbY),
            radius = orbRadius * 0.7f
        )
    } else {
        // Lunar Divine Crescent & Stars
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFFFF9C4).copy(alpha = 0.35f), Color.Transparent),
                center = Offset(orbX, orbY),
                radius = orbRadius * 1.8f
            ),
            center = Offset(orbX, orbY),
            radius = orbRadius * 1.8f
        )
        // Crescent Moon
        drawCircle(
            color = Color(0xFFFFF9C4).copy(alpha = 0.85f),
            center = Offset(orbX, orbY),
            radius = orbRadius * 0.55f
        )
        drawCircle(
            color = Color(0xFF2B1622),
            center = Offset(orbX - orbRadius * 0.22f, orbY - orbRadius * 0.15f),
            radius = orbRadius * 0.50f
        )
    }

    // 2. Flying Birds Across Sky (3 subtle silhouettes with natural wing flap)
    val birdTint = if (isNight) Color(0xFFD4A23A).copy(alpha = 0.55f) else Color(0xFF7A1720).copy(alpha = 0.45f)
    drawFlyingBirds(
        w = w,
        h = h,
        progress = birdsProgress,
        flapPhase = birdFlapPhase,
        tint = birdTint
    )

    // 3. Background Temple Silhouettes (Layer 1 - Distant with subtle parallax)
    val distantTempleTint = if (isNight) Color(0xFF422131).copy(alpha = 0.35f) else Color(0xFFD4A23A).copy(alpha = 0.18f)
    val distantShift = templeParallax * 0.6f

    val distantShikhara1 = ReusableCanvasPath.path1.apply {
        reset()
        moveTo(w * 0.68f + distantShift, h)
        lineTo(w * 0.72f + distantShift, h * 0.38f)
        lineTo(w * 0.76f + distantShift, h)
        close()
    }
    drawPath(path = distantShikhara1, color = distantTempleTint)

    val distantShikhara2 = ReusableCanvasPath.path2.apply {
        reset()
        moveTo(w * 0.88f + distantShift, h)
        lineTo(w * 0.92f + distantShift, h * 0.45f)
        lineTo(w * 0.96f + distantShift, h)
        close()
    }
    drawPath(path = distantShikhara2, color = distantTempleTint)

    // 4. Foreground Main Sacred Temple Shikhara (Layer 2)
    val mainTempleTint = if (isNight) Color(0xFF5A2A3E).copy(alpha = 0.45f) else Color(0xFF7A1720).copy(alpha = 0.22f)
    val mainShift = templeParallax

    val mainTemplePath = ReusableCanvasPath.path3.apply {
        reset()
        // Base Mandapa
        moveTo(w * 0.74f + mainShift, h)
        lineTo(w * 0.74f + mainShift, h * 0.55f)
        // Stepped Shikhara Curve
        quadraticTo(w * 0.78f + mainShift, h * 0.35f, w * 0.81f + mainShift, h * 0.18f)
        // Amalak Finial
        lineTo(w * 0.82f + mainShift, h * 0.18f)
        quadraticTo(w * 0.85f + mainShift, h * 0.35f, w * 0.89f + mainShift, h * 0.55f)
        lineTo(w * 0.89f + mainShift, h)
        close()
    }
    drawPath(path = mainTemplePath, color = mainTempleTint)

    // Temple Kalash Finial (Top of Shikhara)
    val kalashX = w * 0.815f + mainShift
    val kalashY = h * 0.18f
    drawCircle(
        color = Color(0xFFD4A23A),
        center = Offset(kalashX, kalashY),
        radius = 2.5.dp.toPx()
    )

    // 5. Stationary Flag Pole (STABLE, vertical)
    val poleHeight = 16.dp.toPx()
    val poleTopY = kalashY - poleHeight
    drawLine(
        color = Color(0xFF9A5A18),
        start = Offset(kalashX, kalashY),
        end = Offset(kalashX, poleTopY),
        strokeWidth = 1.6.dp.toPx()
    )

    // 6. ANIMATED WAVING SACRED FLAG (DHWAJA)
    // Saffron triangular cloth gently waves using sine wave coordinates while pole remains stationary
    val flagLength = 22.dp.toPx()
    val flagHeight = 12.dp.toPx()
    val waveOffset1 = sin(flagWavePhase.toDouble()).toFloat() * 2.2f
    val waveOffset2 = sin((flagWavePhase + PI * 0.5).toDouble()).toFloat() * 3.0f

    val flagPath = ReusableCanvasPath.path4.apply {
        reset()
        moveTo(kalashX, poleTopY)
        // Upper edge with wind wave
        quadraticTo(
            kalashX + flagLength * 0.5f,
            poleTopY + waveOffset1,
            kalashX + flagLength,
            poleTopY + (flagHeight * 0.4f) + waveOffset2
        )
        // Lower edge fluttering back to pole
        quadraticTo(
            kalashX + flagLength * 0.45f,
            poleTopY + flagHeight + waveOffset1,
            kalashX,
            poleTopY + flagHeight
        )
        close()
    }

    drawPath(
        path = flagPath,
        brush = Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFE58A16),
                Color(0xFFFF9800),
                Color(0xFFFFB74D)
            ),
            startX = kalashX,
            endX = kalashX + flagLength
        )
    )
}

/**
 * Draw 3 Subtle Flying Birds across the canvas
 */
private fun DrawScope.drawFlyingBirds(
    w: Float,
    h: Float,
    progress: Float,
    flapPhase: Float,
    tint: Color
) {
    // Bird 1 (Leading Left -> Right)
    val b1X = ((progress * 1.2f) % 1.2f - 0.1f) * w
    val b1Y = h * 0.22f + sin((progress * 4 * PI).toDouble()).toFloat() * 3.5f
    val b1Flap = sin(flapPhase.toDouble()).toFloat() * 3.5f
    drawBirdSilhouette(Offset(b1X, b1Y), flap = b1Flap, size = 6.dp.toPx(), tint = tint)

    // Bird 2 (Right -> Left opposite trajectory)
    val b2Progress = (progress + 0.35f) % 1.0f
    val b2X = w - (((b2Progress * 1.2f) % 1.2f - 0.1f) * w)
    val b2Y = h * 0.15f + sin((b2Progress * 4 * PI + 1).toDouble()).toFloat() * 2.5f
    val b2Flap = sin((flapPhase + PI * 0.6).toDouble()).toFloat() * 3.0f
    drawBirdSilhouette(Offset(b2X, b2Y), flap = b2Flap, size = 4.5.dp.toPx(), tint = tint.copy(alpha = tint.alpha * 0.85f))

    // Bird 3 (Lower Sine Arc Left -> Right)
    val b3Progress = (progress + 0.7f) % 1.0f
    val b3X = ((b3Progress * 1.1f) % 1.1f - 0.1f) * w
    val b3Y = h * 0.30f + sin((b3Progress * 4 * PI + 2).toDouble()).toFloat() * 3.0f
    val b3Flap = sin((flapPhase + PI * 1.2).toDouble()).toFloat() * 3.2f
    drawBirdSilhouette(Offset(b3X, b3Y), flap = b3Flap, size = 5.dp.toPx(), tint = tint.copy(alpha = tint.alpha * 0.75f))
}

private fun DrawScope.drawBirdSilhouette(
    center: Offset,
    flap: Float,
    size: Float,
    tint: Color
) {
    val wingSpan = size * 1.4f
    val birdPath = ReusableCanvasPath.path5.apply {
        reset()
        // Left Wing
        moveTo(center.x - wingSpan, center.y - flap)
        quadraticTo(center.x - wingSpan * 0.5f, center.y - flap * 0.5f, center.x, center.y)
        // Right Wing
        quadraticTo(center.x + wingSpan * 0.5f, center.y - flap * 0.5f, center.x + wingSpan, center.y - flap)
    }
    drawPath(
        path = birdPath,
        color = tint,
        style = Stroke(width = 1.4.dp.toPx(), cap = StrokeCap.Round)
    )
}
