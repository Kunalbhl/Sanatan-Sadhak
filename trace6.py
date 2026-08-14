with open('app/src/main/java/com/example/ui/screens/AllScreens.kt') as f:
    lines = f.read().split('\n')
depth = 0
for i, line in enumerate(lines):
    depth += line.count('{')
    depth -= line.count('}')
    if depth < 0:
        print(f"Negative depth at line {i+1}: {line}")
