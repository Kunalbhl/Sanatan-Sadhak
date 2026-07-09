import re

with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()

bad_nav = """                            "Profile" -> ProfileScreen(viewModel = viewModel)
                            "Admin" -> {"""

good_nav = """                            "Profile" -> ProfileScreen(viewModel = viewModel, onNavigateAdmin = { viewModel.setScreen("Admin") })
                            "Admin" -> {"""

content = content.replace(bad_nav, good_nav)

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)
