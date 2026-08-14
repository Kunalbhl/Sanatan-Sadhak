import re

with open('app/src/main/java/com/example/ui/screens/common/CommonScreens.kt', 'r') as f:
    content = f.read()

# Replace any viewModel.sendMessage(XYZ) with aiViewModel.sendMessage(XYZ, viewModel.userRole.value)
# Be careful not to replace it if it's already aiViewModel.sendMessage
content = re.sub(r'viewModel\.sendMessage\((.*?)\)', r'aiViewModel.sendMessage(\1, viewModel.userRole.value)', content)

# But wait, did my previous script partially replace it?
content = content.replace('aiViewModel.sendMessage(currentInput, viewModel.userRole.value, viewModel.userRole.value)', 'aiViewModel.sendMessage(currentInput, viewModel.userRole.value)')
content = content.replace('aiViewModel.sendMessage(it, viewModel.userRole.value, viewModel.userRole.value)', 'aiViewModel.sendMessage(it, viewModel.userRole.value)')

with open('app/src/main/java/com/example/ui/screens/common/CommonScreens.kt', 'w') as f:
    f.write(content)
