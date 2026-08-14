package com.example.ui.designsystem

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.ui.theme.SadhakThemeDimensions

enum class DeityFilterCategory(val displayName: String, val hindiName: String) {
    ALL("All", "सभी"),
    SHIVA("Shiva", "शिव"),
    KRISHNA("Krishna", "कृष्ण"),
    RAMA("Rama", "राम"),
    HANUMAN("Hanuman", "हनुमान"),
    GANESH("Ganesh", "गणेश"),
    DURGA("Durga", "दुर्गा"),
    LAKSHMI("Lakshmi", "लक्ष्मी"),
    SARASWATI("Saraswati", "सरस्वती"),
    VISHNU("Vishnu", "विष्णु"),
    KALI("Kali", "काली"),
    SURYA("Surya", "सूर्य"),
    NAVAGRAHA("Navagraha", "नवग्रह"),
    DATTATREYA("Dattatreya", "दत्तात्रेय"),
    OTHER("Other", "अन्य");

    companion object {
        fun fromName(name: String): DeityFilterCategory {
            return entries.firstOrNull { 
                it.displayName.equals(name, ignoreCase = true) || it.hindiName.equals(name, ignoreCase = true)
            } ?: ALL
        }
    }
}

@Composable
fun SadhakDeityChip(
    deity: DeityFilterCategory,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
    useHindiName: Boolean = false
) {
    val label = if (useHindiName) deity.hindiName else deity.displayName
    SadhakChip(
        text = label,
        isSelected = isSelected,
        onClick = onSelect,
        modifier = modifier,
        testTag = "deity_chip_${deity.name.lowercase()}"
    )
}

@Composable
fun SadhakDeityFilterRow(
    selectedDeity: String,
    onDeitySelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    useHindiNames: Boolean = false
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(vertical = SadhakThemeDimensions.sectionSpacingSmall),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DeityFilterCategory.entries.forEach { deityCategory ->
            val isSelected = selectedDeity.equals(deityCategory.displayName, ignoreCase = true) ||
                    selectedDeity.equals(deityCategory.hindiName, ignoreCase = true)

            SadhakDeityChip(
                deity = deityCategory,
                isSelected = isSelected,
                onSelect = { onDeitySelected(deityCategory.displayName) },
                useHindiName = useHindiNames
            )
        }
    }
}
