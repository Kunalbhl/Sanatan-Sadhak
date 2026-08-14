with open('app/src/main/java/com/example/ui/screens/AllScreens.kt', 'r') as f:
    lines = f.read().split('\n')

# Delete line 2224 (index 2223)
del lines[2223]

# Also let's check what's at the end of the file
if lines[-1].strip() == "}":
    print("End of file has '}', removing it")
    del lines[-1]

with open('app/src/main/java/com/example/ui/screens/AllScreens.kt', 'w') as f:
    f.write('\n'.join(lines))
print("Removed extra braces")
