import sys
content = sys.stdin.read()
depth = 0
for i, line in enumerate(content.split('\n')):
    depth += line.count('{')
    depth -= line.count('}')
    print(f"{i+645}: depth={depth} {line}")
