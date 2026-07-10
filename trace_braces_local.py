with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    lines = f.readlines()

count = 0
for i in range(821, 930):
    line = lines[i]
    if "item {" in line:
        print(f"Item starts at {i+1}, count {count}")
    
    count += line.count('{') - line.count('}')
    print(f"Line {i+1}: count {count}")

