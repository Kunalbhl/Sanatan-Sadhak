with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    lines = f.readlines()

count = 0
for i in range(270, 825):
    line = lines[i]
    if "LazyColumn(" in line:
        print(f"LazyColumn starts at {i+1}, count {count}")
    
    count += line.count('{') - line.count('}')
    if "LazyColumn" in "".join(lines[270:i+1]) and count == 0:
        print(f"LazyColumn ended at {i+1}")
        break
print(f"Count at 822: {count}")

