package com.example.ui.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SadhakThemeColors

@Composable
fun SadhakDivider(
    modifier: Modifier = Modifier,
    color: Color = SadhakThemeColors.divider,
    thickness: Dp = 1.dp,
    symbol: String? = null
) {
    if (symbol.isNullOrEmpty()) {
        HorizontalDivider(
            modifier = modifier.fillMaxWidth(),
            thickness = thickness,
            color = color
        )
    } else {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = thickness,
                color = color
            )
            Text(
                text = symbol,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = SadhakThemeColors.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = thickness,
                color = color
            )
        }
    }
}
