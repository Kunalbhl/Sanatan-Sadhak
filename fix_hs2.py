import re

with open('app/src/main/java/com/example/ui/screens/home/HomeScreen.kt', 'r') as f:
    content = f.read()

content = content.replace('viewModel.panchangError', 'panchangViewModel.panchangError')
content = content.replace('viewModel.panchangTechnicalError', 'panchangViewModel.panchangTechnicalError')
content = content.replace('viewModel.loadDailyPanchangAndThought()', 'panchangViewModel.loadDailyPanchang()')
content = content.replace('panchangViewModel.panchangViewModel.panchangError', 'panchangViewModel.panchangError') # just in case

with open('app/src/main/java/com/example/ui/screens/home/HomeScreen.kt', 'w') as f:
    f.write(content)
