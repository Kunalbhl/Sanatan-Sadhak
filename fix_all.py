import sys

def main():
    with open('app/src/main/java/com/example/ui/screens/AllScreens.kt', 'r') as f:
        content = f.read()

    # The issue is that I closed a bracket `}` that might have belonged to `HomeScreen` or something, or I missed one.
    # Let's count depth
    lines = content.split('\n')
    depth = 0
    for i, line in enumerate(lines):
        depth += line.count('{')
        depth -= line.count('}')
        if depth < 0:
            print(f"Negative depth at {i+1}: {line}")
            break

if __name__ == '__main__':
    main()
