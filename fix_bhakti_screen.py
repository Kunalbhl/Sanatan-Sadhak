import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

replacement = """fun BhaktiToolsScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    val selectedToolSubTab by viewModel.bhaktiTab.collectAsState()"""

content = content.replace("""fun BhaktiToolsScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    
    var selectedToolSubTab by remember { mutableStateOf("Mantras") } // "Mantras", "Timer", "KarmaLog", "Gratitude\"""", replacement)

# Replace the onClick to update the viewModel
onclick_rep = """                CategoryTab(
                    text = label,
                    isSelected = selectedToolSubTab == tab,
                    onClick = { viewModel.navigateToBhakti(tab) },
                    testTagStr = "bhakti_sub_tab_$tab"
                )"""

old_onclick = """                CategoryTab(
                    text = label,
                    isSelected = selectedToolSubTab == tab,
                    onClick = { selectedToolSubTab = tab },
                    testTagStr = "bhakti_sub_tab_$tab"
                )"""
content = content.replace(old_onclick, onclick_rep)

# Also update the label "Dhyana Timer" to "Dhyana Sadhana"
content = content.replace('"Timer" -> if (isEng) "Dhyana Timer" else "ध्यान घड़ी"', '"Timer" -> if (isEng) "Dhyana Sadhana" else "ध्यान साधना"')
content = content.replace('text = if (isEnglish) "Dhyana Timer" else "ध्यान घड़ी"', 'text = if (isEnglish) "Dhyana Sadhana" else "ध्यान साधना"')
content = content.replace('title = if (isEng) "Dhyana Timer" else "ध्यान घड़ी"', 'title = if (isEng) "Dhyana Sadhana" else "ध्यान साधना"')
content = content.replace('text = if (isEng) "Start your Timer..." else "घड़ी शुरू करें..."', 'text = if (isEng) "Start your Sadhana..." else "साधना शुरू करें..."')

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)

