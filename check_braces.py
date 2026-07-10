def check_braces(file_path):
    with open(file_path, "r") as f:
        lines = f.readlines()

    count = 0
    for i, line in enumerate(lines):
        count += line.count('{') - line.count('}')
        if count < 0:
            print(f"Brace count went negative at line {i+1}: {line.strip()}")
            return
    print(f"Final brace count: {count}")

check_braces("app/src/main/java/com/example/ui/screens/AllScreens.kt")
