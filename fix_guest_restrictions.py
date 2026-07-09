import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

# 1. MantraLibraryView
mantra_def = "fun MantraLibraryView(viewModel: SadhakViewModel, isEnglish: Boolean) {"
mantra_rep = "fun MantraLibraryView(viewModel: SadhakViewModel, isEnglish: Boolean) {\n    val userRole by viewModel.userRole.collectAsState()"
content = content.replace(mantra_def, mantra_rep)

mantra_btn = """                    Button(
                        onClick = { showAddForm = !showAddForm },"""
mantra_btn_rep = """                    Button(
                        onClick = {
                            if (userRole == "Guest") viewModel.triggerGuestPromo() else showAddForm = !showAddForm
                        },"""
content = content.replace(mantra_btn, mantra_btn_rep)

# 2. KarmaTrackerView
karma_def = "fun KarmaTrackerView(viewModel: SadhakViewModel, isEnglish: Boolean) {"
karma_rep = "fun KarmaTrackerView(viewModel: SadhakViewModel, isEnglish: Boolean) {\n    val userRole by viewModel.userRole.collectAsState()"
content = content.replace(karma_def, karma_rep)

karma_btn = """                Button(
                    onClick = {
                        if (newLog.isNotBlank()) {"""
karma_btn_rep = """                Button(
                    onClick = {
                        if (userRole == "Guest") {
                            viewModel.triggerGuestPromo()
                            return@Button
                        }
                        if (newLog.isNotBlank()) {"""
content = content.replace(karma_btn, karma_btn_rep)

# 3. GratitudeJournalView
gratitude_def = "fun GratitudeJournalView(viewModel: SadhakViewModel, isEnglish: Boolean) {"
gratitude_rep = "fun GratitudeJournalView(viewModel: SadhakViewModel, isEnglish: Boolean) {\n    val userRole by viewModel.userRole.collectAsState()"
content = content.replace(gratitude_def, gratitude_rep)

gratitude_btn = """                Button(
                    onClick = {
                        if (newEntry.isNotBlank()) {"""
gratitude_btn_rep = """                Button(
                    onClick = {
                        if (userRole == "Guest") {
                            viewModel.triggerGuestPromo()
                            return@Button
                        }
                        if (newEntry.isNotBlank()) {"""
content = content.replace(gratitude_btn, gratitude_btn_rep)

# 4. CommunityScreen - wait, CommunityScreen already has it?
# Let's check CommunityScreen.

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)

