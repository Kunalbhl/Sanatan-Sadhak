package com.example.ui.screens.onboarding

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.components.GoldenLotusMedallion
import com.example.SanatanSadhakLogo
import com.example.ui.theme.SadhakThemeColors

data class OnboardingStep(
    val titleEn: String,
    val titleHi: String,
    val headlineEn: String,
    val headlineHi: String,
    val descEn: String,
    val descHi: String,
    val icon: ImageVector,
    val highlightEn: List<String>,
    val highlightHi: List<String>
)

@Composable
fun DevoteeOnboardingDialog(
    isEnglish: Boolean,
    onComplete: () -> Unit,
    onSkip: () -> Unit = onComplete
) {
    val steps = remember {
        listOf(
            OnboardingStep(
                titleEn = "Home Dashboard",
                titleHi = "दिव्य दैनिक पटल",
                headlineEn = "Your Daily Spiritual Center",
                headlineHi = "आपकी दैनिक साधना का केंद्र",
                descEn = "Every morning begins with divine deity greetings, accurate Vedic Panchang, Gita shlokas with meaning, daily mantras, and habit tracking.",
                descHi = "प्रतिदिन पावन ईष्टदेव अभिवादन, सटीक वैदिक पंचांग, सार्थ गीता श्लोक, दैनिक मंत्र एवं साधना प्रगति का दर्शन करें।",
                icon = Icons.Default.Home,
                highlightEn = listOf("12-Hour Deity Greetings", "Live Vedic Panchang", "Daily Shloka & Wisdom", "Sadhana Streak"),
                highlightHi = listOf("१२-घंटे ईष्टदेव अभिवादन", "सटीक वैदिक पंचांग", "दैनिक श्लोक व अर्थ", "नित्य साधना प्रगति")
            ),
            OnboardingStep(
                titleEn = "Vedic Panchang",
                titleHi = "सटीक वैदिक पंचांग",
                headlineEn = "Astral Timings & Auspicious Muhurtas",
                headlineHi = "काल गणना एवं शुभ मुहूर्त",
                descEn = "Access comprehensive Panchang elements: Tithi, Nakshatra, Yoga, Karana, Rahu Kaal, Yamaganda, Gulika Kaal, and Abhijeet Muhurta.",
                descHi = "तिथि, नक्षत्र, योग, करण, राहुकाल, यमगंड, गुलिक काल और अभिजीत मुहूर्त का शुद्ध व प्रामाणिक विवरण प्राप्त करें।",
                icon = Icons.Default.CalendarMonth,
                highlightEn = listOf("Tithi & Nakshatra", "Rahu Kaal & Gulika", "Abhijeet Muhurta", "Festival Calendar"),
                highlightHi = listOf("तिथि एवं नक्षत्र", "राहुकाल व गुलिक काल", "अभिजीत मुहूर्त", "पर्व व व्रत पंचांग")
            ),
            OnboardingStep(
                titleEn = "Sadhana Tracking",
                titleHi = "साधना प्रगति एवं संकल्प",
                headlineEn = "Daily Discipline & Habit Building",
                headlineHi = "नित्य नियम एवं साधना संकल्प",
                descEn = "Track your daily worship, japa goals, scripture study, and dhyan sessions to nurture a consistent spiritual habit.",
                descHi = "अपने दैनिक पूजा, जप लक्ष्य, शास्त्र पठन और ध्यान सत्रों का हिसाब रखें और अटूट साधना आदत बनाएं।",
                icon = Icons.Default.Timeline,
                highlightEn = listOf("Bhakti Streak Counter", "Daily Goal Tracker", "Karma Points Engine", "Sadhana Analytics"),
                highlightHi = listOf("भक्ति स्ट्रिक काउंटर", "दैनिक लक्ष्य ट्रैकर", "कर्म पॉइंट्स इंजन", "साधना विश्लेषण")
            ),
            OnboardingStep(
                titleEn = "My Mantra",
                titleHi = "निजी मंत्र संग्रह",
                headlineEn = "Custom Mantras & Ishta Dev Chants",
                headlineHi = "व्यक्तिगत मंत्र एवं ईष्ट मंत्र",
                descEn = "Save and categorize your personal guru mantras, family deity chants, and favorite stotrams for quick daily japa.",
                descHi = "अपने गुरु मंत्र, कुलदेवता मंत्र और पसंदीदा स्तोत्रों को नित्य जप के लिए सुरक्षित व व्यवस्थित करें।",
                icon = Icons.Default.Bookmark,
                highlightEn = listOf("Custom Mantra Editor", "Ishta Dev Tagging", "Target Bead Counter", "Offline Storage"),
                highlightHi = listOf("कस्टम मंत्र संपादक", "ईष्टदेव टैगिंग", "लक्ष्य मणका काउंटर", "ऑफलाइन संग्रहण")
            ),
            OnboardingStep(
                titleEn = "Japa Mala",
                titleHi = "पवित्र १०८ जप माला",
                headlineEn = "Sacred 108 Bead Chanting",
                headlineHi = "१०८ मणकों की पावन जप माला",
                descEn = "Experience traditional tactile bead counting with sacred sound effects, haptic vibrations, custom daily targets, and mala counters.",
                descHi = "पवित्र ध्वनि, सूक्ष्म कंपन (हैप्टिक), माला पूर्णता सूचना और दैनिक संकल्प लक्ष्यों के साथ मंत्र जप का अनुभव करें।",
                icon = Icons.Default.SelfImprovement,
                highlightEn = listOf("108 Bead Mala Counter", "Haptic Touch Feedback", "Custom Mantras & Targets", "Sankalpa Tracker"),
                highlightHi = listOf("१०८ मणका माला काउंटर", "हैप्टिक स्पर्श कंपन", "कस्टम मंत्र व लक्ष्य", "संकल्प ट्रैकर")
            ),
            OnboardingStep(
                titleEn = "Dhyan Sanctuary",
                titleHi = "ध्यान एवं शांत वातावरण",
                headlineEn = "Meditation & Mind Stillness",
                headlineHi = "गहन ध्यान एवं आत्मिक शांति",
                descEn = "Immerse in deep meditation sessions with customizable timers, continuous Om cosmic chants, divine Krishna flute, and temple resonance.",
                descHi = "अनुकूलन योग्य टाइमर, ॐ की अनाहत ध्वनि, दिव्य बाँसुरी और मंदिर घंटियों के साथ गहन ध्यान में लीन हों।",
                icon = Icons.Default.Spa,
                highlightEn = listOf("Sacred Timer Intervals", "Continuous Om Chants", "Divine Flute Melodies", "Dhyan History Log"),
                highlightHi = listOf("पवित्र समय अंतराल", "अखंड ॐ ध्वनि", "दिव्य बाँसुरी धुन", "ध्यान इतिहास")
            ),
            OnboardingStep(
                titleEn = "Knowledge Hub",
                titleHi = "वेद एवं धर्मग्रंथ संग्रह",
                headlineEn = "Eternal Vedic Scriptures",
                headlineHi = "सनातन धर्मग्रंथ एवं ज्ञान",
                descEn = "Explore authentic verses from Srimad Bhagavad Gita, Upanishads, Vedas, Stotras, and Puranas with verified bilingual translations.",
                descHi = "श्रीमद्भगवद्गीता, उपनिषद्, वेद, स्तोत्र एवं पुराणों के प्रामाणिक श्लोकों का हिंदी व अंग्रेजी अर्थ सहित अध्ययन करें।",
                icon = Icons.Default.MenuBook,
                highlightEn = listOf("Bhagavad Gita Chapters", "Sacred Upanishads", "Verse-by-Verse Meaning", "Bookmarks & Progress"),
                highlightHi = listOf("श्रीमद्भगवद्गीता अध्याय", "प्रामाणिक उपनिषद्", "श्लोकवार सरल अनुवाद", "बुकमार्क व अध्ययन प्रगति")
            ),
            OnboardingStep(
                titleEn = "Spiritual Chants",
                titleHi = "मंत्र व स्तोत्र पाठ",
                headlineEn = "Text Chants with Pronunciation Guidance",
                headlineHi = "शुद्ध उच्चारण व पाठ गाइड",
                descEn = "Read and recite daily mantras, chalisa, and stotrams with interactive text-to-speech voice assistance and audio speed controls.",
                descHi = "दैनिक मंत्र, चालीसा और स्तोत्रों का पाठ करें और टेक्स्ट-टू-स्पीच उच्चारण सहायता का लाभ उठाएं।",
                icon = Icons.Default.RecordVoiceOver,
                highlightEn = listOf("TTS Voice Guidance", "Speed & Pitch Controls", "Bilingual Script Text", "Loop Reading Mode"),
                highlightHi = listOf("टीटीएस स्वर उच्चारण", "गति व पिच नियंत्रण", "द्विभाषी श्लोक पाठ", "लूप पठन मोड")
            ),
            OnboardingStep(
                titleEn = "Devotional Audio",
                titleHi = "दिव्य भक्ति ऑडियो",
                headlineEn = "Stotrams, Chalisa & Mantras",
                headlineHi = "स्तोत्र, चालीसा एवं भजन",
                descEn = "Listen to sacred stotrams, Hanuman Chalisa, Shiva Tandav, and Vishnu Sahasranamam with a full-featured background queue and mini player.",
                descHi = "श्री हनुमान चालीसा, शिव तांडव स्तोत्र, विष्णु सहस्रनाम और दिव्य मंत्रों का बैकग्राउंड में निर्बाध श्रवण करें।",
                icon = Icons.Default.MusicNote,
                highlightEn = listOf("Background Audio Player", "Queue Management", "Loop & Repeat Modes", "Notification Controls"),
                highlightHi = listOf("बैकग्राउंड ऑडियो प्लेयर", "प्लेलिस्ट प्रबंधन", "लूप एवं रिपीट मोड", "नोटिफिकेशन नियंत्रण")
            ),
            OnboardingStep(
                titleEn = "Daily Shloka",
                titleHi = "दैनिक श्लोक व सुविचार",
                headlineEn = "Morning Wisdom & Reflection",
                headlineHi = "प्रातःकालीन सुविचार व अमृतवाणी",
                descEn = "Receive daily curated Gita shlokas and Upanishadic wisdom with deep spiritual insights for contemplative morning reflection.",
                descHi = "प्रतिदिन सुबह पावन गीता श्लोक और उपनिषद् ज्ञान का चिंतन करें जो जीवन को धर्ममार्ग पर अग्रसर करता है।",
                icon = Icons.Default.AutoAwesome,
                highlightEn = listOf("Morning Shloka Alert", "Deep Sanskrit Meanings", "One-Tap Shareable Cards", "Wisdom Archive"),
                highlightHi = listOf("प्रातः श्लोक अलर्ट", "गहन संस्कृत भावार्थ", "एक-टैप शेयर कार्ड", "अमृतवाणी आर्काइव")
            ),
            OnboardingStep(
                titleEn = "Notifications & Reminders",
                titleHi = "साधना एवं संध्या स्मरण",
                headlineEn = "Never Miss Auspicious Hours",
                headlineHi = "शुभ काल व साधना का समय पर स्मरण",
                descEn = "Receive gentle timely reminders for Brahma Muhurta, morning japa, Sandhya Aarti, and daily spiritual practices.",
                descHi = "ब्रह्म मुहूर्त, प्रातः कालीन जप, संध्या आरती और दैनिक साधना के लिए समय पर पावन सूचनाएं प्राप्त करें।",
                icon = Icons.Default.NotificationsActive,
                highlightEn = listOf("Brahma Muhurta Alerts", "Sandhya Aarti Alarms", "Daily Japa Reminders", "Panchang Notifications"),
                highlightHi = listOf("ब्रह्म मुहूर्त सूचना", "संध्या आरती अलर्ट", "नित्य जप स्मरण", "पंचांग नोटिफिकेशन")
            ),
            OnboardingStep(
                titleEn = "Sadhak Mitra AI",
                titleHi = "साधक मित्र एआई",
                headlineEn = "Your Spiritual Knowledge Guide",
                headlineHi = "आपका आध्यात्मिक ज्ञान सहायक",
                descEn = "Ask philosophical and scriptural questions. Sadhak Mitra references traditional texts for personal learning and reflection.",
                descHi = "धार्मिक एवं दार्शनिक जिज्ञासाओं के उत्तर पाएं। साधक मित्र पारंपरिक ग्रंथों के संदर्भ के साथ मार्गदर्शन करता है।",
                icon = Icons.Default.Psychology,
                highlightEn = listOf("Scripture Inquiries", "Dharmic Guidance", "Educational Clarity", "Traditional References"),
                highlightHi = listOf("ग्रंथ संबंधी जिज्ञासा", "धर्म मार्गदर्शन", "सरल व स्पष्ट उत्तर", "पारंपरिक शास्त्र संदर्भ")
            )
        )
    }

    var currentStepIndex by remember { mutableIntStateOf(-1) } // -1 is Welcome intro

    Dialog(
        onDismissRequest = { onComplete() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.65f))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 480.dp)
                    .heightIn(max = 680.dp)
                    .testTag("devotee_onboarding_modal"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = SadhakThemeColors.sacredBackground),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, SadhakThemeColors.sacredGold.copy(alpha = 0.7f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (currentStepIndex == -1) {
                        // Welcome Screen
                        WelcomeOnboardingView(
                            isEnglish = isEnglish,
                            onStartTour = { currentStepIndex = 0 },
                            onSkip = onSkip
                        )
                    } else {
                        // Step-by-Step Guided Walkthrough (0 to 7)
                        val step = steps[currentStepIndex]
                        StepWalkthroughView(
                            step = step,
                            stepIndex = currentStepIndex,
                            totalSteps = steps.size,
                            isEnglish = isEnglish,
                            onNext = {
                                if (currentStepIndex < steps.size - 1) {
                                    currentStepIndex++
                                } else {
                                    onComplete()
                                }
                            },
                            onBack = {
                                if (currentStepIndex > 0) {
                                    currentStepIndex--
                                } else {
                                    currentStepIndex = -1
                                }
                            },
                            onSkip = onSkip
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WelcomeOnboardingView(
    isEnglish: Boolean,
    onStartTour: () -> Unit,
    onSkip: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Canonical Logo
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                SadhakThemeColors.sacredGold.copy(alpha = 0.25f),
                                Color.Transparent
                            )
                        ),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                SanatanSadhakLogo(modifier = Modifier.size(76.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Greeting
            Text(
                text = if (isEnglish) "Jai Shree Ram 🕉" else "जय श्री राम 🕉",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = SadhakThemeColors.sacredSecondary,
                    fontSize = 22.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (isEnglish) "Welcome to Sanatan Sadhak" else "सनातन साधक में आपका स्वागत है",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = SadhakThemeColors.sacredPrimary,
                    fontSize = 18.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (isEnglish) "Your Daily Companion for Sanatan Sadhana" else "दैनिक सनातन साधना का दिव्य साथी",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = SadhakThemeColors.sacredTextSecondary,
                    fontSize = 13.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Intro Sacred Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SadhakThemeColors.sacredSurface)
                    .border(1.dp, SadhakThemeColors.sacredGold.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (isEnglish) {
                            "Sanatan Sadhak is designed to anchor your daily life in devotion, discipline, and dharma with authentic Vedic tools."
                        } else {
                            "सनातन साधक आपके दैनिक जीवन को भक्ति, अनुशासन और धर्म से जोड़ने वाला एक संपूर्ण एवं प्रामाणिक आध्यात्मिक साधन है।"
                        },
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = SadhakThemeColors.sacredTextPrimary,
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        QuickBenefitItem("📿", if (isEnglish) "Japa Mala" else "जप माला")
                        QuickBenefitItem("🔔", if (isEnglish) "Panchang" else "पंचांग")
                        QuickBenefitItem("📖", if (isEnglish) "Granth" else "धर्मग्रंथ")
                        QuickBenefitItem("🧘", if (isEnglish) "Dhyan" else "ध्यान")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Action Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onStartTour,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("btn_start_guided_tour"),
                colors = ButtonDefaults.buttonColors(containerColor = SadhakThemeColors.sacredPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isEnglish) "Start Guided Tour (12 Highlights)" else "मार्गदर्शिका आरंभ करें (१२ प्रमुख अंग)",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextButton(
                onClick = onSkip,
                modifier = Modifier.testTag("btn_skip_onboarding")
            ) {
                Text(
                    text = if (isEnglish) "Skip Tour & Enter App" else "मार्गदर्शिका छोड़ें व प्रवेश करें",
                    color = SadhakThemeColors.sacredTextSecondary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
private fun StepWalkthroughView(
    step: OnboardingStep,
    stepIndex: Int,
    totalSteps: Int,
    isEnglish: Boolean,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onSkip: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header Bar with Step Badge & Skip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SadhakThemeColors.sacredPrimary.copy(alpha = 0.12f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SadhakThemeColors.sacredGold.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = if (isEnglish) "Step ${stepIndex + 1} of $totalSteps" else "अंग ${stepIndex + 1} / $totalSteps",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = SadhakThemeColors.sacredPrimary,
                            fontSize = 11.sp
                        )
                    )
                }

                Text(
                    text = if (isEnglish) "Skip Tour" else "छोड़ें",
                    color = SadhakThemeColors.sacredTextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable { onSkip() }
                        .padding(4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Step Indicator Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (i in 0 until totalSteps) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(
                                if (i <= stepIndex) SadhakThemeColors.sacredSecondary else SadhakThemeColors.sacredGold.copy(alpha = 0.25f)
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Step Icon Medallion
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                SadhakThemeColors.sacredGold.copy(alpha = 0.25f),
                                SadhakThemeColors.sacredPrimary.copy(alpha = 0.08f)
                            )
                        ),
                        CircleShape
                    )
                    .border(1.5.dp, SadhakThemeColors.sacredGold.copy(alpha = 0.6f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = step.icon,
                    contentDescription = null,
                    tint = SadhakThemeColors.sacredPrimary,
                    modifier = Modifier.size(38.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Title
            Text(
                text = if (isEnglish) step.titleEn else step.titleHi,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = SadhakThemeColors.sacredPrimary,
                    fontSize = 20.sp
                ),
                textAlign = TextAlign.Center
            )

            // Headline
            Text(
                text = if (isEnglish) step.headlineEn else step.headlineHi,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = SadhakThemeColors.sacredSecondary,
                    fontSize = 13.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Description Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SadhakThemeColors.sacredSurface)
                    .border(1.dp, SadhakThemeColors.sacredGold.copy(alpha = 0.35f), RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Column {
                    Text(
                        text = if (isEnglish) step.descEn else step.descHi,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SadhakThemeColors.sacredTextPrimary,
                            lineHeight = 19.sp,
                            fontSize = 13.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    val highlights = if (isEnglish) step.highlightEn else step.highlightHi
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        highlights.forEach { hl ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🕉", fontSize = 12.sp, color = SadhakThemeColors.sacredSecondary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = hl,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = SadhakThemeColors.sacredTextPrimary,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 12.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation Controls: Back and Next / Finish
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp),
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SadhakThemeColors.sacredGold.copy(alpha = 0.6f)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = SadhakThemeColors.sacredPrimary)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isEnglish) "Back" else "पीछे",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }
            }

            Button(
                onClick = onNext,
                modifier = Modifier
                    .weight(1.5f)
                    .height(46.dp)
                    .testTag("btn_onboarding_next"),
                colors = ButtonDefaults.buttonColors(containerColor = SadhakThemeColors.sacredPrimary),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (stepIndex == totalSteps - 1) {
                            if (isEnglish) "Finish & Begin" else "संपन्न व आरंभ करें"
                        } else {
                            if (isEnglish) "Next" else "आगे बढ़ें"
                        },
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = if (stepIndex == totalSteps - 1) Icons.Default.Check else Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickBenefitItem(iconEmoji: String, title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = iconEmoji, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = SadhakThemeColors.sacredTextPrimary,
                fontSize = 11.sp
            )
        )
    }
}
