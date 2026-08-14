package com.example.ui.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SadhakThemeColors
import com.example.ui.theme.SadhakThemeDimensions

@Composable
fun SadhakFeatureTile(
    title: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    shortDescription: String? = null,
    badge: String? = null,
    count: Int? = null,
    deity: String? = null,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    containerColor: Color = Color.Unspecified,
    iconTint: Color = Color.Unspecified,
    testTag: String? = null
) {
    val alphaValue = if (enabled) 1.0f else 0.5f
    val effectiveTint = if (iconTint != Color.Unspecified) iconTint else SadhakThemeColors.primary
    val effectiveBg = if (containerColor != Color.Unspecified) containerColor else MaterialTheme.colorScheme.surface

    SadhakCard(
        modifier = modifier
            .fillMaxWidth()
            .alpha(alphaValue),
        onClick = if (enabled) onClick else null,
        enabled = enabled,
        backgroundColor = effectiveBg,
        testTag = testTag
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(effectiveTint.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = effectiveTint,
                        modifier = Modifier.size(SadhakThemeDimensions.iconSizeMedium)
                    )
                }
                Spacer(modifier = Modifier.width(SadhakThemeDimensions.tileSpacing))
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SadhakThemeColors.textPrimary
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    if (deity != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(SadhakThemeDimensions.cornerRadiusPill))
                                .background(SadhakThemeColors.secondary.copy(alpha = 0.1f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = deity,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    color = SadhakThemeColors.secondary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }

                    if (badge != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(SadhakThemeDimensions.cornerRadiusPill))
                                .background(SadhakThemeColors.primary)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = badge,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    if (count != null && count > 0) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(SadhakThemeColors.accent.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = count.toString(),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    color = SadhakThemeColors.textPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }

                if (!shortDescription.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = shortDescription,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SadhakThemeColors.textSecondary
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (onClick != null && enabled) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = SadhakThemeColors.textSecondary,
                    modifier = Modifier.size(SadhakThemeDimensions.iconSizeMedium)
                )
            }
        }
    }
}
