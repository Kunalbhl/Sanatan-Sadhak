import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

# Fix 1: userRole == "GUEST" to "Guest" in Mantras Add
content = content.replace('if (userRole == "GUEST") {', 'if (userRole == "Guest") {')

# Fix 2: Karma Tracker Guest check
bad_karma = """                Button(
                    onClick = {
                        if (inputDeed.isNotBlank()) {
                            viewModel.logKarmaDeed(inputDeed)
                            inputDeed = ""
                            statusMessage = if (isEnglish) "Good Karma logged! May you find peace." else "सत्कर्म दर्ज हुआ! ईश्वर आपका कल्याण करे।"
                        }
                    },"""
good_karma = """                Button(
                    onClick = {
                        if (userRole == "Guest") {
                            viewModel.triggerGuestPromo()
                        } else if (inputDeed.isNotBlank()) {
                            viewModel.logKarmaDeed(inputDeed)
                            inputDeed = ""
                            statusMessage = if (isEnglish) "Good Karma logged! May you find peace." else "सत्कर्म दर्ज हुआ! ईश्वर आपका कल्याण करे।"
                        }
                    },"""
content = content.replace(bad_karma, good_karma)

# Fix 3: Gratitude Guest check
bad_gratitude = """                Button(
                    onClick = {
                        if (inputDeed.isNotBlank()) {
                            viewModel.logGratitude(inputDeed)
                            inputDeed = ""
                            statusMessage = if (isEnglish) "Gratitude logged! Stay blessed." else "कृतज्ञता दर्ज की गई! सुखी रहें।"
                        }
                    },"""
good_gratitude = """                Button(
                    onClick = {
                        if (userRole == "Guest") {
                            viewModel.triggerGuestPromo()
                        } else if (inputDeed.isNotBlank()) {
                            viewModel.logGratitude(inputDeed)
                            inputDeed = ""
                            statusMessage = if (isEnglish) "Gratitude logged! Stay blessed." else "कृतज्ञता दर्ज की गई! सुखी रहें।"
                        }
                    },"""
content = content.replace(bad_gratitude, good_gratitude)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
