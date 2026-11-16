package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DeepSacredMaroon
import com.example.ui.theme.SacredGold
import com.example.ui.theme.TempleCream
import kotlin.math.cos
import kotlin.math.sin

/**
 * Reusable Divine Authentication Background.
 * Provides subtle floating devotional mantras orbiting in soft opacity behind Login & Register forms.
 */
@Composable
fun DivineAuthBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "auth_bg_anim")
    val orbitPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 24000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orbit_phase"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(TempleCream)
    ) {
        // Floating Devotional Mantras in Soft Gold / Maroon
        val mantras = listOf(
            "ॐ नमः शिवाय",
            "श्री राम",
            "हरे कृष्ण",
            "ॐ गं गणपतये नमः",
            "ॐ नमो नारायणाय",
            "जय बजरंगबली",
            "जय श्री राम"
        )

        mantras.forEachIndexed { index, mantra ->
            val angle = orbitPhase + (index * (2 * Math.PI / mantras.size)).toFloat()
            val offsetX = (cos(angle.toDouble()) * 110).dp
            val offsetY = (sin(angle.toDouble()) * 140).dp

            Text(
                text = mantra,
                color = if (index % 2 == 0) DeepSacredMaroon else SacredGold,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .offset(x = offsetX, y = offsetY)
                    .alpha(0.12f)
            )
        }

        // Foreground Form Content
        content()
    }
}
