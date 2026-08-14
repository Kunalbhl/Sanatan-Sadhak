import re

with open('app/src/main/java/com/example/ui/screens/panchang/CalendarScreens.kt', 'r') as f:
    content = f.read()

content = content.replace('fun CalendarScreen(viewModel: com.example.ui.viewmodel.SadhakViewModel, onBack: () -> Unit, isEng: Boolean) {', 'fun CalendarScreen(viewModel: com.example.ui.viewmodel.SadhakViewModel, panchangViewModel: com.example.ui.viewmodel.PanchangViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), onBack: () -> Unit, isEng: Boolean) {')
content = content.replace('viewModel.getAllFestivals()', 'viewModel.getAllFestivals()') # leave this for now, it's still in SadhakViewModel? Wait, I didn't move it. Let's check.
content = content.replace('viewModel.getDetailedPanchangForDate', 'panchangViewModel.getDetailedPanchangForDate')

with open('app/src/main/java/com/example/ui/screens/panchang/CalendarScreens.kt', 'w') as f:
    f.write(content)
