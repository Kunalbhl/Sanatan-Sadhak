with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    lines = f.readlines()

count = 0
for i, line in enumerate(lines):
    if "fun HomeScreen" in line:
        home_start = i
        break

in_lazy = False
lazy_depth = 0
for i in range(home_start, len(lines)):
    line = lines[i]
    if "contentPadding = PaddingValues" in line and not in_lazy:
        in_lazy = True
        lazy_depth = count + line.count('{') - line.count('}')
        print(f"LazyCol body at {i+2}, depth {lazy_depth}")
        continue
    
    count += line.count('{') - line.count('}')
    
    if in_lazy and count <= lazy_depth:
        print(f"LazyCol ended at {i+1}")
        in_lazy = False
        break

