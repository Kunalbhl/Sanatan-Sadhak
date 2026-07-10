import re

def fix_imports(filename):
    with open(filename, "r") as f:
        text = f.read()
    lines = text.split("\n")
    imports = []
    other = []
    for line in lines:
        if line.startswith("import "):
            imports.append(line)
        elif line.strip() != "" or len(other) > 0:
            if line.startswith("package ") and len(other) == 0:
                other.append(line)
            else:
                if not line.startswith("import "):
                    other.append(line)
    
    # Actually let's just make sure package is first, then imports, then code
    pkg = ""
    imp = []
    code = []
    for line in lines:
        if line.startswith("package "):
            pkg = line
        elif line.startswith("import "):
            imp.append(line)
        else:
            if line.strip() != "" or len(code) > 0:
                code.append(line)
                
    with open(filename, "w") as f:
        f.write(pkg + "\n\n")
        for i in set(imp):
            f.write(i + "\n")
        f.write("\n")
        f.write("\n".join(code))

fix_imports("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt")
fix_imports("app/src/main/java/com/example/ui/screens/MissingScreens.kt")
