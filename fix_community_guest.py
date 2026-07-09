import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

fab = """                // Post creation FAB
                Button(
                    onClick = { showCreatePostDialog = true },"""
fab_rep = """                // Post creation FAB
                Button(
                    onClick = {
                        if (userRole == "Guest") viewModel.triggerGuestPromo() else showCreatePostDialog = true
                    },"""
content = content.replace(fab, fab_rep)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
