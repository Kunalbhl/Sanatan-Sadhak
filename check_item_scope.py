with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    lines = f.readlines()

count = 0
for i in range(470, 822):
    line = lines[i]
    count += line.count('{') - line.count('}')

print(f"Count before 822: {count}")
