with open('app/src/main/java/com/example/ui/screens/AllScreens.kt') as f:
    lines = f.read().split('\n')
depth = 0
for i in range(351, 540):
    line = lines[i]
    depth += line.count('{')
    depth -= line.count('}')
print(f"Depth at line 539: {depth}")
