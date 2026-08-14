import re

with open('app/src/main/java/com/example/ui/screens/home/HomeScreen.kt', 'r') as f:
    content = f.read()

# Make sure HomeViewModel is passed or retrieved
content = content.replace('fun HomeScreen(viewModel: SadhakViewModel) {', 'fun HomeScreen(viewModel: SadhakViewModel, homeViewModel: com.example.ui.viewmodel.HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {')

content = content.replace('viewModel.dailyMantra', 'homeViewModel.dailyMantra')
content = content.replace('viewModel.shlokaOfTheDay', 'homeViewModel.shlokaOfTheDay')
content = content.replace('viewModel.multipleThoughts', 'homeViewModel.multipleThoughts')
content = content.replace('viewModel.multipleShlokas', 'homeViewModel.multipleShlokas')
content = content.replace('viewModel.multipleInspirations', 'homeViewModel.multipleInspirations')

with open('app/src/main/java/com/example/ui/screens/home/HomeScreen.kt', 'w') as f:
    f.write(content)
