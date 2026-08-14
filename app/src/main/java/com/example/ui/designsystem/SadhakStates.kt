package com.example.ui.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.common.UiState
import com.example.ui.theme.SadhakThemeColors
import com.example.ui.theme.SadhakThemeDimensions

@Composable
fun SadhakLoading(
    modifier: Modifier = Modifier,
    message: String = "ॐ..."
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("sadhak_loading_state")
            .padding(SadhakThemeDimensions.screenPaddingMedium),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = SadhakThemeColors.primary,
                strokeWidth = 3.dp,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = SadhakThemeColors.primary,
                    fontSize = 18.sp
                )
            )
        }
    }
}

@Composable
fun SadhakEmptyState(
    title: String = "कोई जानकारी नहीं मिली",
    modifier: Modifier = Modifier,
    description: String? = "कृपया बाद में पुनः प्रयास करें या अन्य श्रेणी चुनें।",
    icon: ImageVector = Icons.Default.Inbox,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("sadhak_empty_state")
            .padding(SadhakThemeDimensions.screenPaddingLarge),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = SadhakThemeColors.textSecondary.copy(alpha = 0.6f),
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = SadhakThemeColors.textPrimary
                ),
                textAlign = TextAlign.Center
            )
            if (!description.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = SadhakThemeColors.textSecondary
                    ),
                    textAlign = TextAlign.Center
                )
            }
            if (!actionLabel.isNullOrEmpty() && onAction != null) {
                Spacer(modifier = Modifier.height(20.dp))
                SadhakSecondaryButton(
                    text = actionLabel,
                    onClick = onAction,
                    modifier = Modifier.fillMaxWidth(0.6f)
                )
            }
        }
    }
}

@Composable
fun SadhakErrorState(
    message: String = "कुछ त्रुटि हुई। कृपया पुनः प्रयास करें।",
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("sadhak_error_state")
            .padding(SadhakThemeDimensions.screenPaddingLarge),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.ErrorOutline,
                contentDescription = null,
                tint = SadhakThemeColors.error,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "त्रुटि (Error)",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = SadhakThemeColors.error
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = SadhakThemeColors.textSecondary
                ),
                textAlign = TextAlign.Center
            )
            if (onRetry != null) {
                Spacer(modifier = Modifier.height(20.dp))
                SadhakPrimaryButton(
                    text = "पुनः प्रयास करें (Retry)",
                    icon = Icons.Default.Refresh,
                    onClick = onRetry,
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
            }
        }
    }
}

@Composable
fun SadhakOfflineState(
    modifier: Modifier = Modifier,
    message: String = "आप ऑफ़लाइन हैं। सहेजी गई सामग्री उपलब्ध है।",
    onRetry: (() -> Unit)? = null
) {
    SadhakEmptyState(
        title = "ऑफ़लाइन मोड (Offline Mode)",
        description = message,
        icon = Icons.Default.WifiOff,
        actionLabel = if (onRetry != null) "पुनः कनेक्ट करें" else null,
        onAction = onRetry,
        modifier = modifier.testTag("sadhak_offline_state")
    )
}

@Composable
fun <T> SadhakStateContent(
    state: UiState<T>,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
    loadingMessage: String = "ॐ...",
    emptyTitle: String = "कोई जानकारी नहीं मिली",
    emptyDescription: String? = "कृपया बाद में पुनः प्रयास करें।",
    successContent: @Composable (T) -> Unit
) {
    when (state) {
        is UiState.Loading -> {
            SadhakLoading(modifier = modifier, message = loadingMessage)
        }
        is UiState.Success -> {
            successContent(state.data)
        }
        is UiState.Empty -> {
            SadhakEmptyState(
                title = emptyTitle,
                description = emptyDescription,
                onAction = onRetry,
                modifier = modifier
            )
        }
        is UiState.Error -> {
            SadhakErrorState(
                message = state.message,
                onRetry = onRetry,
                modifier = modifier
            )
        }
        is UiState.Offline -> {
            SadhakOfflineState(
                onRetry = onRetry,
                modifier = modifier
            )
        }
    }
}
