with open('app/src/main/java/com/example/ui/screens/AllScreens.kt') as f:
    lines = f.read().split('\n')
depth = 0
for i in range(350, 362):
    line = lines[i]
    depth += line.count('{')
    depth -= line.count('}')
    print(f"{i+1}: {depth} : {line.strip()}")
