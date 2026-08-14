package com.example.ui.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SadhakThemeColors
import com.example.ui.theme.SadhakThemeDimensions

@Composable
fun SadhakSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "खोजें (Search scriptures, mantras...)",
    onSearch: (() -> Unit)? = null,
    onFilterClick: (() -> Unit)? = null,
    testTag: String = "sadhak_search_bar"
) {
    val shape = RoundedCornerShape(SadhakThemeDimensions.cornerRadiusPill)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .testTag(testTag)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, SadhakThemeColors.divider, shape)
            .padding(horizontal = 14.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = SadhakThemeColors.primary,
            modifier = Modifier.size(SadhakThemeDimensions.iconSizeMedium)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .defaultMinSize(minHeight = 36.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (query.isEmpty()) {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = SadhakThemeColors.textSecondary,
                        fontSize = 14.sp
                    )
                )
            }

            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = SadhakThemeColors.textPrimary,
                    fontSize = 14.sp
                ),
                cursorBrush = SolidColor(SadhakThemeColors.primary),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearch?.invoke() }),
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (query.isNotEmpty()) {
            IconButton(
                onClick = { onQueryChange("") },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear Search",
                    tint = SadhakThemeColors.textSecondary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        if (onFilterClick != null) {
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = onFilterClick,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filter",
                    tint = SadhakThemeColors.secondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
