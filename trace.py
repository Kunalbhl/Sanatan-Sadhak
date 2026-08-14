with open('app/src/main/java/com/example/ui/screens/AllScreens.kt') as f:
    lines = f.read().split('\n')
start = 650
depth = 0
for i in range(start, 940):
    line = lines[i]
    if "panchangCardContent = " in line:
        depth = 0
    depth += line.count('{')
    depth -= line.count('}')
    if 910 <= i <= 935:
        print(f"{i+1}: {depth} : {line}")
