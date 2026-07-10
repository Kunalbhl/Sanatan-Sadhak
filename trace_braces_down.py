with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    lines = f.readlines()

count = 0
for i in range(470, 930):
    line = lines[i]
    count += line.count('{') - line.count('}')
    if count == 0 and i > 475:
        print(f"Braces hit 0 at {i+1}")
        break

print(f"Count at 928: {count}")
