import re

with open('app/src/main/java/com/example/ui/screens/panchang/CalendarScreens.kt', 'r') as f:
    content = f.read()

content = content.replace('fun CalendarScreen(viewModel: SadhakViewModel, onBack: () -> Unit, isEng: Boolean) {', 'fun CalendarScreen(viewModel: SadhakViewModel, panchangViewModel: com.example.ui.viewmodel.PanchangViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), onBack: () -> Unit, isEng: Boolean) {')

with open('app/src/main/java/com/example/ui/screens/panchang/CalendarScreens.kt', 'w') as f:
    f.write(content)
