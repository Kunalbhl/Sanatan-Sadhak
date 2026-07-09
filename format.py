import sys

def balance_braces(filename):
    with open(filename, 'r') as f:
        content = f.read()

    left = content.count('{')
    right = content.count('}')
    print(f"Before: Left {left} Right {right}")
    
    # We will just remove the extra } at the end if Right > Left, or add if Left > Right.
    # Actually, it's better to just remove the one at the end of the file.
    if right > left:
        diff = right - left
        # remove last `diff` occurrences of }
        for _ in range(diff):
            last_brace = content.rfind('}')
            content = content[:last_brace] + content[last_brace+1:]
    elif left > right:
        diff = left - right
        content += '}' * diff
        
    with open(filename, 'w') as f:
        f.write(content)
    
    left = content.count('{')
    right = content.count('}')
    print(f"After: Left {left} Right {right}")

balance_braces("app/src/main/java/com/example/ui/screens/AllScreens.kt")
