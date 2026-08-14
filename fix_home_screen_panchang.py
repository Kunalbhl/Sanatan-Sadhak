import re

with open('app/src/main/java/com/example/ui/screens/home/HomeScreen.kt', 'r') as f:
    content = f.read()

# Add panchangViewModel parameter
content = content.replace('fun HomeScreen(viewModel: SadhakViewModel, homeViewModel: com.example.ui.viewmodel.HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {', 'fun HomeScreen(viewModel: SadhakViewModel, homeViewModel: com.example.ui.viewmodel.HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), panchangViewModel: com.example.ui.viewmodel.PanchangViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {')

# Replace viewModel.currentTithi with panchangViewModel.currentTithi etc.
panchang_fields = [
    'currentTithi', 'currentNakshatra', 'currentYoga', 'currentKarana', 
    'currentRashi', 'currentSunrise', 'currentSunset', 'currentRahukaal', 
    'currentAbhijeet', 'currentFestival', 'currentDetails', 'currentThought',
    'panchangSource', 'panchangLastUpdated', 'todayDetailedPanchang',
    'todayDetailedPanchangForDetail', 'loadDetailPanchang'
]

for field in panchang_fields:
    content = content.replace(f'viewModel.{field}', f'panchangViewModel.{field}')

with open('app/src/main/java/com/example/ui/screens/home/HomeScreen.kt', 'w') as f:
    f.write(content)
