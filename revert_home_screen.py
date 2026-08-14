import re

with open('app/src/main/java/com/example/ui/screens/home/HomeScreen.kt', 'r') as f:
    content = f.read()

content = content.replace('fun HomeScreen(viewModel: SadhakViewModel, homeViewModel: com.example.ui.viewmodel.HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {', 'fun HomeScreen(viewModel: SadhakViewModel) {')
content = content.replace('homeViewModel.dailyMantra', 'viewModel.dailyMantra')
content = content.replace('homeViewModel.shlokaOfTheDay', 'viewModel.shlokaOfTheDay')
content = content.replace('homeViewModel.multipleThoughts', 'viewModel.multipleThoughts')
content = content.replace('homeViewModel.multipleShlokas', 'viewModel.multipleShlokas')
content = content.replace('homeViewModel.multipleInspirations', 'viewModel.multipleInspirations')

with open('app/src/main/java/com/example/ui/screens/home/HomeScreen.kt', 'w') as f:
    f.write(content)
