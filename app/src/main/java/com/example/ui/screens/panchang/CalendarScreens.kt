package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import com.example.ui.theme.SadhakThemeColors
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DetailedPanchang
import com.example.ui.components.SacredCard
import com.example.ui.viewmodel.PanchangLocation
import com.example.ui.viewmodel.PanchangViewModel
import com.example.ui.viewmodel.SadhakViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: SadhakViewModel,
    panchangViewModel: PanchangViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBack: () -> Unit,
    isEng: Boolean
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Detailed Panchang, 1 = Hindi Calendar
    var showLocationDialog by remember { mutableStateOf(false) }

    val panchangDetails by panchangViewModel.todayDetailedPanchang.collectAsState()
    val currentLocation by panchangViewModel.currentLocation.collectAsState()
    val selectedCal by panchangViewModel.selectedCalendar.collectAsState()
    val isCachedData by panchangViewModel.isCachedData.collectAsState()
    val isLoading by panchangViewModel.isLoading.collectAsState()
    val panchangError by panchangViewModel.panchangError.collectAsState()
    val festivals by viewModel.getAllFestivals().collectAsState(initial = emptyList())

    val dateFormatter = remember { SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()) }
    val formattedDateStr = dateFormatter.format(selectedCal.time)

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        // --- Top Bar ---
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.width(6.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isEng) "Panchang & Hindi Calendar" else "पंचांग एवं हिंदू कैलेंडर",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { showLocationDialog = true }.padding(vertical = 2.dp)
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = (if (isEng) currentLocation.name else currentLocation.nameHi) + if (isEng) " · Change" else " · बदलें",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                    )
                }
            }
            IconButton(onClick = { panchangViewModel.refreshPanchang() }) {
                Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = MaterialTheme.colorScheme.primary)
            }
        }

        // --- Tabs: Panchang vs Monthly Calendar ---
        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.padding(vertical = 6.dp),
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text(if (isEng) "Detailed Panchang" else "दैनिक पंचांग", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text(if (isEng) "Monthly Calendar" else "मासिक कैलेंडर", fontWeight = FontWeight.Bold) }
            )
        }

        // --- Content View ---
        if (selectedTab == 0) {
            // Detailed Panchang View
            FullPanchangView(
                panchangDetails = panchangDetails,
                selectedCal = selectedCal,
                formattedDateStr = formattedDateStr,
                isCachedData = isCachedData,
                isLoading = isLoading,
                error = panchangError,
                isEng = isEng,
                onPrevDay = { panchangViewModel.goToPreviousDay() },
                onToday = { panchangViewModel.goToToday() },
                onNextDay = { panchangViewModel.goToNextDay() }
            )
        } else {
            // Monthly Hindi Calendar View
            MonthlyCalendarView(
                panchangViewModel = panchangViewModel,
                festivals = festivals,
                isEng = isEng,
                onSelectDate = { year, month, day ->
                    panchangViewModel.selectDate(year, month, day)
                    selectedTab = 0 // Switch to Panchang for selected date
                }
            )
        }
    }

    // --- Location Picker Dialog ---
    if (showLocationDialog) {
        LocationPickerDialog(
            currentLocation = currentLocation,
            onSelectLocation = { loc ->
                panchangViewModel.setLocation(loc)
                showLocationDialog = false
            },
            onDismiss = { showLocationDialog = false },
            isEng = isEng
        )
    }
}

@Composable
fun FullPanchangView(
    panchangDetails: DetailedPanchang?,
    selectedCal: Calendar,
    formattedDateStr: String,
    isCachedData: Boolean,
    isLoading: Boolean,
    error: String?,
    isEng: Boolean,
    onPrevDay: () -> Unit,
    onToday: () -> Unit,
    onNextDay: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // --- Date Navigation Bar ---
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(onClick = onPrevDay, contentPadding = ButtonDefaults.ContentPadding) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous")
                Spacer(modifier = Modifier.width(2.dp))
                Text(if (isEng) "Prev Day" else "पिछला दिन", fontSize = 12.sp)
            }

            Button(onClick = onToday, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                Text(if (isEng) "Today" else "आज", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(onClick = onNextDay, contentPadding = ButtonDefaults.ContentPadding) {
                Text(if (isEng) "Next Day" else "अगला दिन", fontSize = 12.sp)
                Spacer(modifier = Modifier.width(2.dp))
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next")
            }
        }

        // Date Display Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.CalendarToday, contentDescription = "Date", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = formattedDateStr,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            )
            if (isCachedData) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier.clip(CircleShape).background(MaterialTheme.colorScheme.secondaryContainer).padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = if (isEng) "Cached" else "कैश्ड डेटा",
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSecondaryContainer, fontWeight = FontWeight.Bold)
                    )
                }
            }
        }

        if (isLoading) {
            Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (error != null && panchangDetails == null) {
            Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                Text(text = error, color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center)
            }
        } else if (panchangDetails != null) {
            val d = panchangDetails
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // 0. Source & Accuracy Transparency Header Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = SadhakThemeColors.sacredSurface),
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SadhakThemeColors.sacredDivider)
                    ) {
                        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (d.sourceType == "REMOTE_SOURCE")
                                        (if (isEng) "Source: Verified Ephemeris Source" else "स्रोत: प्रमाणित पंचांग स्रोत")
                                    else
                                        (if (isEng) "Source: Local Ephemeris Engine" else "स्रोत: स्थानीय खगोलीय गणना"),
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(
                                            if (d.sourceType == "REMOTE_SOURCE") SadhakThemeColors.success.copy(alpha = 0.15f)
                                            else MaterialTheme.colorScheme.secondaryContainer
                                        )
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = if (d.sourceType == "REMOTE_SOURCE")
                                            (if (isEng) "✓ VERIFIED / Verified Ephemeris Source" else "✓ प्रमाणित पंचांग स्रोत")
                                        else
                                            (if (isEng) "⚙ CALCULATED / Local Ephemeris" else "⚙ स्थानीय गणना"),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = if (d.sourceType == "REMOTE_SOURCE") SadhakThemeColors.success else MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${d.conventionNote} • ${d.calculationMethod}",
                                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                            )
                        }
                    }
                }

                // 1. Core Summary Card
                item {
                    SacredCard(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = d.tithi,
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                )
                                Text(
                                    text = d.weekday,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.secondary)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${d.lunarMonth} • ${d.paksha} • ${d.samvatYear}",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            )

                            if (d.festival.isNotEmpty() && d.festival != "—") {
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Star, "Festival", tint = SadhakThemeColors.warning, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = d.festival,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = SadhakThemeColors.warning)
                                    )
                                }
                            }
                        }
                    }
                }

                // 2. Sun & Moon Timings Card
                item {
                    SacredCard(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = if (isEng) "Sun & Moon Timings" else "सूर्य एवं चंद्र स्थिति",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                            )
                            Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.WbSunny, "Sunrise", tint = SadhakThemeColors.warning, modifier = Modifier.size(24.dp))
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(if (isEng) "Sunrise" else "सूर्योदय", style = MaterialTheme.typography.labelSmall)
                                    Text(d.sunrise, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.WbSunny, "Sunset", tint = SadhakThemeColors.error, modifier = Modifier.size(24.dp))
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(if (isEng) "Sunset" else "सूर्यास्त", style = MaterialTheme.typography.labelSmall)
                                    Text(d.sunset, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.NightsStay, "Moonrise", tint = SadhakThemeColors.primary, modifier = Modifier.size(24.dp))
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(if (isEng) "Moonrise" else "चंद्रोदय", style = MaterialTheme.typography.labelSmall)
                                    Text(d.moonrise, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.NightsStay, "Moonset", tint = SadhakThemeColors.secondary, modifier = Modifier.size(24.dp))
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(if (isEng) "Moonset" else "चंद्रास्त", style = MaterialTheme.typography.labelSmall)
                                    Text(d.moonset, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(if (isEng) "Ayana (अयन):" else "अयन:", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                                Text(d.ayana, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(if (isEng) "Ritu (Season):" else "ऋतु:", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                                Text(d.ritu, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            }
                        }
                    }
                }

                // 3. Panchang Angas Card
                item {
                    SacredCard(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = if (isEng) "Five Angas (पंच अंग)" else "पंचांग के पाँच अंग",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                            )
                            Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))

                            PanchangRow(label = if (isEng) "1. Tithi (तिथि)" else "1. तिथि", value = d.tithi, subValue = "Ends: ${d.tithiEndTime}")
                            PanchangRow(label = if (isEng) "2. Nakshatra (नक्षत्र)" else "2. नक्षत्र", value = d.nakshatra, subValue = "Lord: ${d.nakshatraLord}, Pada: ${d.nakshatraPada}")
                            PanchangRow(label = if (isEng) "3. Yoga (योग)" else "3. योग", value = d.yoga)
                            PanchangRow(label = if (isEng) "4. Karana (करण)" else "4. करण", value = d.karana)
                            PanchangRow(label = if (isEng) "5. Vara (वार)" else "5. वार", value = d.weekday)

                            Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(if (isEng) "Moon Sign (चंद्र राशि):" else "चंद्र राशि:", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                                Text(d.rashi, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(if (isEng) "Sun Sign (सूर्य राशि):" else "सूर्य राशि:", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                                Text(d.sunSign, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            }
                        }
                    }
                }

                // 4. Auspicious & Inauspicious Muhurtas Card
                item {
                    SacredCard(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = if (isEng) "Auspicious & Inauspicious Muhurtas" else "शुभ एवं अशुभ मुहूर्त",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                            )
                            Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.CheckCircle, "Auspicious", tint = SadhakThemeColors.success, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(if (isEng) "Abhijeet Muhurat:" else "अभिजीत मुहूर्त:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                }
                                Text(d.abhijeet, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = SadhakThemeColors.success))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Close, "Inauspicious", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(if (isEng) "Rahu Kalam:" else "राहुकाल (अशुभ):", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                }
                                Text(d.rahukaal, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(if (isEng) "Gulika Kaal:" else "गुलिक काल:", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                                Text(d.gulikaKaal, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(if (isEng) "Yamaganda:" else "यमगंड:", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                                Text(d.yamaganda, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            }
                        }
                    }
                }

                // 5. Choghadiya Card
                item {
                    SacredCard(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = if (isEng) "Day Choghadiya Sequence" else "दिन का चौघड़िया मुहूर्त",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                            )
                            Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                            Text(
                                text = d.choghadiya,
                                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp, fontWeight = FontWeight.Medium)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = if (isEng) "★ Auspicious: Amrita, Shubha, Labha, Chara | Inauspicious: Udveg, Kala, Roga"
                                else "★ शुभ चौघड़िया: अमृत, शुभ, लाभ, चर | वर्जित: उद्वेग, काल, रोग",
                                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary, fontStyle = FontStyle.Italic)
                            )
                        }
                    }
                }

                // 6. Calculation & Source Transparency Card
                item {
                    SacredCard(
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = if (isEng) "Calculation & Source" else "गणना एवं स्रोत जानकारी",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            )
                            Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.15f))

                            PanchangRow(
                                label = if (isEng) "Source:" else "स्रोत:",
                                value = if (d.sourceType == "REMOTE_SOURCE")
                                    (if (isEng) "Verified Ephemeris Source" else "प्रमाणित पंचांग स्रोत")
                                else
                                    (if (isEng) "Local Astronomical Engine" else "स्थानीय खगोलीय गणना")
                            )
                            PanchangRow(
                                label = if (isEng) "Location:" else "स्थान:",
                                value = d.locationName
                            )
                            PanchangRow(
                                label = if (isEng) "Timezone:" else "समय क्षेत्र:",
                                value = "UTC+05:30 (IST)"
                            )
                            PanchangRow(
                                label = if (isEng) "Convention:" else "पद्धति:",
                                value = d.conventionNote
                            )
                            PanchangRow(
                                label = if (isEng) "Accuracy Status:" else "सटीकता स्थिति:",
                                value = if (d.sourceType == "REMOTE_SOURCE")
                                    (if (isEng) "Verified" else "प्रमाणित")
                                else
                                    (if (isEng) "Calculated (Approximate)" else "गणना (अनुमानित)")
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = if (isEng) "Offline calculation based on astronomical approximations. For authoritative Panchang observances, compare with the configured trusted source."
                                else "खगोलीय अनुमानों पर आधारित ऑफ़लाइन गणना। प्रामाणिक पंचांग अनुष्ठानों के लिए, कॉन्फ़िगर किए गए विश्वसनीय स्रोत से तुलना करें।",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 12.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PanchangRow(label: String, value: String, subValue: String = "") {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
            Text(value, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
        }
        if (subValue.isNotEmpty()) {
            Text(subValue, style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant), modifier = Modifier.padding(start = 12.dp))
        }
    }
}

@Composable
fun MonthlyCalendarView(
    panchangViewModel: PanchangViewModel,
    festivals: List<com.example.data.FestivalEntity>,
    isEng: Boolean,
    onSelectDate: (year: Int, month: Int, day: Int) -> Unit
) {
    val selectedCal by panchangViewModel.selectedCalendar.collectAsState()

    var viewMonth by remember(selectedCal) { mutableIntStateOf(selectedCal.get(Calendar.MONTH)) }
    var viewYear by remember(selectedCal) { mutableIntStateOf(selectedCal.get(Calendar.YEAR)) }

    val monthNamesEn = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
    val monthNamesHi = listOf("जनवरी", "फ़रवरी", "मार्च", "अप्रैल", "मई", "जून", "जुलाई", "अगस्त", "सितंबर", "अक्टूबर", "नवंबर", "दिसंबर")
    val monthName = if (isEng) monthNamesEn[viewMonth] else monthNamesHi[viewMonth]

    val monthCal = remember(viewMonth, viewYear) {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, viewYear)
            set(Calendar.MONTH, viewMonth)
            set(Calendar.DAY_OF_MONTH, 1)
        }
    }
    val daysInMonth = monthCal.getActualMaximum(Calendar.DAY_OF_MONTH)
    val startDayOfWeek = monthCal.get(Calendar.DAY_OF_WEEK) // 1=Sun, 2=Mon...
    val blanksCount = if (startDayOfWeek == 1) 6 else startDayOfWeek - 2

    val todayCal = remember { Calendar.getInstance() }
    val todayDay = todayCal.get(Calendar.DAY_OF_MONTH)
    val todayMonth = todayCal.get(Calendar.MONTH)
    val todayYear = todayCal.get(Calendar.YEAR)

    val selectedDay = selectedCal.get(Calendar.DAY_OF_MONTH)
    val selectedMonth = selectedCal.get(Calendar.MONTH)
    val selectedYear = selectedCal.get(Calendar.YEAR)

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 4.dp)) {
        // Navigation Month Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                if (viewMonth == 0) {
                    viewMonth = 11
                    viewYear--
                } else {
                    viewMonth--
                }
            }) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Prev Month", tint = MaterialTheme.colorScheme.primary)
            }

            Text(
                text = "$monthName $viewYear",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
            )

            IconButton(onClick = {
                if (viewMonth == 11) {
                    viewMonth = 0
                    viewYear++
                } else {
                    viewMonth++
                }
            }) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next Month", tint = MaterialTheme.colorScheme.primary)
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Days of week header
        val dowLabels = if (isEng) listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun") else listOf("सोम", "मंगल", "बुध", "गुरु", "शुक्र", "शनि", "रवि")
        Row(modifier = Modifier.fillMaxWidth()) {
            for (label in dowLabels) {
                Text(
                    text = label,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Days Grid
        val daysList = mutableListOf<Int?>()
        val actualBlanks = if (blanksCount < 0) 0 else blanksCount
        for (i in 0 until actualBlanks) daysList.add(null)
        for (i in 1..daysInMonth) daysList.add(i)

        val weeks = daysList.chunked(7)

        Column(modifier = Modifier.fillMaxWidth()) {
            for (week in weeks) {
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
                    for (day in week) {
                        Box(
                            modifier = Modifier.weight(1f).padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (day != null) {
                                val isToday = day == todayDay && viewMonth == todayMonth && viewYear == todayYear
                                val isSelected = day == selectedDay && viewMonth == selectedMonth && viewYear == selectedYear

                                val dateStr = String.format("%04d-%02d-%02d", viewYear, viewMonth + 1, day)
                                val hasFestival = festivals.any { it.date == dateStr }

                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when {
                                                isSelected -> MaterialTheme.colorScheme.primary
                                                isToday -> MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                                else -> Color.Transparent
                                            }
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = when {
                                                isSelected -> MaterialTheme.colorScheme.primary
                                                isToday -> MaterialTheme.colorScheme.secondary
                                                else -> Color.Transparent
                                            },
                                            shape = CircleShape
                                        )
                                        .clickable {
                                            onSelectDate(viewYear, viewMonth, day)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = day.toString(),
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = if (isToday || isSelected) FontWeight.Bold else FontWeight.Normal,
                                                color = when {
                                                    isSelected -> Color.White
                                                    isToday -> MaterialTheme.colorScheme.primary
                                                    else -> MaterialTheme.colorScheme.onSurface
                                                }
                                            )
                                        )
                                        if (hasFestival) {
                                            Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(SadhakThemeColors.warning))
                                        }
                                    }
                                }
                            } else {
                                Spacer(modifier = Modifier.size(38.dp))
                            }
                        }
                    }
                    if (week.size < 7) {
                        Spacer(modifier = Modifier.weight((7 - week.size).toFloat()))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Note
        Text(
            text = if (isEng) "★ Tap any date above to view complete Panchang & Muhurtas for that date."
            else "★ किसी भी तिथि पर टैप करके उस दिन का संपूर्ण पंचांग एवं मुहूर्त देखें।",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary, fontStyle = FontStyle.Italic),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun LocationPickerDialog(
    currentLocation: PanchangLocation,
    onSelectLocation: (PanchangLocation) -> Unit,
    onDismiss: () -> Unit,
    isEng: Boolean
) {
    var selectedLoc by remember { mutableStateOf(currentLocation) }
    var customName by remember { mutableStateOf("") }
    var customLat by remember { mutableStateOf("") }
    var customLng by remember { mutableStateOf("") }
    var customTz by remember { mutableStateOf("5.5") }
    var isCustomMode by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (isEng) "Select Location for Panchang" else "पंचांग स्थान चुनें")
        },
        text = {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Text(
                        text = if (isEng) "Preset Sacred Cities:" else "प्रसिद्ध धार्मिक नगर:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                items(PanchangViewModel.PRESET_LOCATIONS) { loc ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                isCustomMode = false
                                selectedLoc = loc
                            }
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = !isCustomMode && selectedLoc.name == loc.name,
                            onClick = {
                                isCustomMode = false
                                selectedLoc = loc
                            }
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(
                                text = if (isEng) loc.name else loc.nameHi,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Lat: ${loc.latitude}, Lng: ${loc.longitude}, TZ: +${loc.timezone}",
                                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                            )
                        }
                    }
                }

                item {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = isCustomMode, onClick = { isCustomMode = true })
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isEng) "Enter Custom Coordinates" else "कस्टम अक्षांश / देशांतर दर्ज करें",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    if (isCustomMode) {
                        Column(modifier = Modifier.padding(start = 32.dp, top = 4.dp)) {
                            OutlinedTextField(
                                value = customName,
                                onValueChange = { customName = it },
                                label = { Text(if (isEng) "City Name" else "नगर का नाम") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = customLat,
                                onValueChange = { customLat = it },
                                label = { Text(if (isEng) "Latitude (e.g. 28.61)" else "अक्षांश (उदा. 28.61)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = customLng,
                                onValueChange = { customLng = it },
                                label = { Text(if (isEng) "Longitude (e.g. 77.20)" else "देशांतर (उदा. 77.20)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = customTz,
                                onValueChange = { customTz = it },
                                label = { Text(if (isEng) "Timezone Offset (e.g. 5.5)" else "समय क्षेत्र (उदा. 5.5)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (isCustomMode) {
                    val lat = customLat.toDoubleOrNull() ?: 28.6139
                    val lng = customLng.toDoubleOrNull() ?: 77.2090
                    val tz = customTz.toDoubleOrNull() ?: 5.5
                    val name = customName.ifEmpty { "Custom Location" }
                    onSelectLocation(PanchangLocation(name, name, lat, lng, tz))
                } else {
                    onSelectLocation(selectedLoc)
                }
            }) {
                Text(if (isEng) "Apply Location" else "स्थान लागू करें")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(if (isEng) "Cancel" else "रद्द करें")
            }
        }
    )
}
