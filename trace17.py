with open('app/src/main/java/com/example/ui/screens/AllScreens.kt') as f:
    lines = f.read().split('\n')
depth = 1 # assume inside HomeScreen
for i in range(649, 946):
    line = lines[i]
    depth += line.count('{')
    depth -= line.count('}')
print(f"Depth at line 946: {depth}")
