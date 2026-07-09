import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

content = content.replace("colors = OutlinedTextFieldDefaults.colors(", 
                          "colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, ")

# Just to be safe, sometimes we already specified focusedBorderColor, so we might have `focusedBorderColor = MaterialTheme.colorScheme.primary, focusedBorderColor = MaterialTheme.colorScheme.primary`
# Let's clean up potential duplicates:
content = content.replace("focusedBorderColor = MaterialTheme.colorScheme.primary, focusedBorderColor = MaterialTheme.colorScheme.primary)", "focusedBorderColor = MaterialTheme.colorScheme.primary)")
content = content.replace("focusedBorderColor = MaterialTheme.colorScheme.primary, focusedBorderColor = MaterialTheme.colorScheme.primary", "focusedBorderColor = MaterialTheme.colorScheme.primary")

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
