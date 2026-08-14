with open('app/src/main/java/com/example/ui/screens/AllScreens.kt', 'r') as f:
    lines = f.read().split('\n')

new_lines = []
for i, line in enumerate(lines):
    if i in [941, 942, 943]: # 0-indexed for 942, 943, 944
        continue
    new_lines.append(line)

with open('app/src/main/java/com/example/ui/screens/AllScreens.kt', 'w') as f:
    f.write('\n'.join(new_lines))
print("Removed extra braces")
