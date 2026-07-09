import re

with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()

# We need to add logic in onClick of NavigationBarItem.
# Since we only have `onNavigate(item.route)` in SadhakBottomBar, we can just do the reset in `MainActivity.kt`
# Wait, MainActivity has `SadhakBottomBar(currentScreen = currentScreen, userRole = userRole, isEnglish = isEnglish, onNavigate = { route -> viewModel.setScreen(route) })`
# We can change it there.

bad = "onNavigate = { route -> viewModel.setScreen(route) }"
good = """onNavigate = { route ->
                            if (route == "Bhakti") {
                                viewModel.navigateToBhakti("Mantras")
                            } else {
                                viewModel.setScreen(route)
                            }
                        }"""

content = content.replace(bad, good)

# Also let's rename Devotee in Bottom bar
content = content.replace('NavigationItem("Bhakti Sadhana", if (isEnglish) "Bhakti Sadhana" else "भक्ति साधना", Icons.Default.SelfImprovement)', 'NavigationItem("Bhakti", if (isEnglish) "Bhakti" else "भक्ति", Icons.Default.SelfImprovement)')

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)
