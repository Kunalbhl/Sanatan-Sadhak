with open('app/src/main/java/com/example/ui/screens/AllScreens.kt', 'r') as f:
    lines = f.read().split('\n')

lines.insert(941, "    }")

with open('app/src/main/java/com/example/ui/screens/AllScreens.kt', 'w') as f:
    f.write('\n'.join(lines))
print("Added closing brace for panchangCardContent")
