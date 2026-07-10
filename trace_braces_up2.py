with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    lines = f.readlines()

count = 0
lazy_depth = -1
for i in range(270, 825):
    line = lines[i]
    count += line.count('{') - line.count('}')
    if "contentPadding = PaddingValues" in line:
        lazy_depth = count
        print(f"LazyColumn body depth is {lazy_depth} at {i+1}")
    
    if lazy_depth != -1 and count < lazy_depth:
        print(f"LazyColumn ended at {i+1}!!")
        break

print(f"Count at 822: {count}")
