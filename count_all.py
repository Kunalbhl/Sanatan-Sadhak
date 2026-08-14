import sys
content = sys.stdin.read()
depth = 0
for i, line in enumerate(content.split('\n')):
    depth += line.count('{')
    depth -= line.count('}')
    if depth < 0:
        print(f"DEPTH NEGATIVE at {i+1}: {line}")
        depth = 0
print(f"Final depth: {depth}")
