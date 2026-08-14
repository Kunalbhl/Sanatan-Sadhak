with open('app/src/main/java/com/example/ui/screens/AllScreens.kt', 'r') as f:
    content = f.read()

depth = content.count('{') - content.count('}')
print(f"Final depth: {depth}")
