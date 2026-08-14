with open('app/src/main/java/com/example/ui/screens/AllScreens.kt') as f:
    lines = f.read().split('\n')
depth = 1 # assume inside HomeScreen
for i in range(649, 950):
    line = lines[i]
    depth += line.count('{')
    depth -= line.count('}')
    if depth <= 1:
        print(f"Depth <= 1 at line {i+1}: {line.strip()}")
