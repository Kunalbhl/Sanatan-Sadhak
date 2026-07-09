import re

with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()

# Change Sadhak to Devotee in bottom bar
content = content.replace('NavigationItem("Profile", if (isEnglish) "Sadhak" else "भक्त"', 'NavigationItem("Profile", if (isEnglish) "Devotee" else "भक्त"')

# Change Chat name
content = content.replace('NavigationItem("Chat", if (isEnglish) "Sadhak Mitra AI" else "साधक मित्र AI", Icons.Default.Chat)', 'NavigationItem("Chat", if (isEnglish) "Mitra AI" else "मित्र AI", Icons.AutoMirrored.Filled.Chat)')

# Update Bhakti Sadhana in navbar route (maybe route name changed? No, route is "Bhakti Sadhana")
# If "Bhakti Sadhana" doesn't fit, it's 14 characters. At 9sp, it should fit if there are 5 items. 
# We can make it 8.sp just in case, or softWrap = false.
content = content.replace('fontSize = 9.sp,', 'fontSize = 9.sp,\n                        softWrap = false,\n                        overflow = androidx.compose.ui.text.style.TextOverflow.Visible,')

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)
