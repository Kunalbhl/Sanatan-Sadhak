with open('app/src/main/java/com/example/ui/screens/AllScreens.kt') as f:
    lines = f.read().split('\n')
depth = 1 # inside HomeScreen
for i in range(1890, 2090):
    line = lines[i]
    depth += line.count('{')
    depth -= line.count('}')
    print(f"{i+1}: {depth} : {line.strip()}")
