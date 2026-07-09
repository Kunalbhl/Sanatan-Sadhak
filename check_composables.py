import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", 'r') as f:
    text = f.read()

# Let's find functions that use these colors but don't have @Composable
lines = text.split('\n')
in_composable = False
for i, line in enumerate(lines):
    if '@Composable' in line:
        in_composable = True
    elif line.startswith('fun ') and not in_composable:
        if 'Saffron' in line or 'DeepMaroon' in line:
            print(f"Line {i+1}: {line}")
    if line.startswith('}'): # naive
        pass

